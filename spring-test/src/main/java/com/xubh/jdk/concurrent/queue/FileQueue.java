package com.xubh.jdk.concurrent.queue;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static java.nio.channels.FileChannel.MapMode.READ_WRITE;

public class FileQueue<T extends Serializable> {

    public static final int PAGE_SIZE = 128 * 1024 * 1024;
    public static final int SIZE_OF_INT = 4;
    private static final int INDEX_SIZE = SIZE_OF_INT + SIZE_OF_INT;
    private static final int HEADER_SIZE = SIZE_OF_INT + SIZE_OF_INT;
    private static final int ENDER_SIZE = HEADER_SIZE;
    public static String FILE_NAME = "data";
    public static String FILE_SUFFIX = ".dat";
    public static String INDEX_NAME = "data.inx";
    public String fileDir;
    private RandomAccessFile readFile;
    private RandomAccessFile writeFile;
    private RandomAccessFile indexFile;
    private FileChannel readChannel;
    private FileChannel writeChannel;
    private FileChannel indexChannel;
    private MappedByteBuffer readMbb;
    private MappedByteBuffer writeMbb;
    private MappedByteBuffer indexMbb;
    private ByteBuffer headerBb = ByteBuffer.allocate(HEADER_SIZE);

    private int readIndexFile;
    private int writeIndexFile;

    public FileQueue(String fileDir) throws IOException {
        if (fileDir == null || fileDir.trim().length() == 0) {
            throw new IllegalArgumentException("filename illegal");
        }

        if (!fileDir.endsWith("/")) {
            fileDir += File.separator;
        }

        File dir = new File(fileDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        this.fileDir = fileDir;

        indexFile = new RandomAccessFile(fileDir + INDEX_NAME, "rw");
        indexChannel = indexFile.getChannel();
        indexMbb = indexChannel.map(READ_WRITE, 0, INDEX_SIZE);

        readIndexFile = indexMbb.getInt();
        writeIndexFile = indexMbb.getInt();

        readFile = new RandomAccessFile(fileDir + FILE_NAME + readIndexFile + FILE_SUFFIX, "rw");
        readChannel = readFile.getChannel();

        writeFile = new RandomAccessFile(fileDir + FILE_NAME + writeIndexFile + FILE_SUFFIX, "rw");
        writeChannel = writeFile.getChannel();

        readMbb = readChannel.map(READ_WRITE, 0, PAGE_SIZE);
        writeMbb = writeChannel.map(READ_WRITE, 0, PAGE_SIZE);

        initWriteMbb();
        initReadMbb();
    }

    private static void unmap(MappedByteBuffer buffer) {
//		if (buffer == null) return;
//	    sun.misc.Cleaner cleaner = ((DirectBuffer) buffer).cleaner();
//	    if (cleaner != null) {
//		    cleaner.clean();
//	    }
    }

    private void initReadMbb() {
        int currentPos = readMbb.position();
        int type = readMbb.getInt();
        int length = readMbb.getInt();

        while (type == ITEM_TYPE.EMPTY.ordinal() && length > 0) {
            readMbb.position(currentPos + HEADER_SIZE + length);
            currentPos = readMbb.position();
            type = readMbb.getInt();
            length = readMbb.getInt();
        }

        readMbb.position(currentPos);
    }

    private void initWriteMbb() {
        int currentPos = writeMbb.position();
        int type = writeMbb.getInt();
        int length = writeMbb.getInt();

        while (length > 0) {
            writeMbb.position(currentPos + HEADER_SIZE + length);
            currentPos = writeMbb.position();
            type = writeMbb.getInt();
            length = writeMbb.getInt();
        }

        writeMbb.position(currentPos);
    }

    public synchronized void product(T item) throws Exception {
        if (item == null) {
            throw new IllegalArgumentException("item is null");
        }

        byte[] contents = toBytes(item);
        int length = contents.length;
        int writePos = writeMbb.position();

        // if reach the button of the filequeue
        if (writePos + length + ENDER_SIZE + HEADER_SIZE >= PAGE_SIZE) {
            writeIndexFile += 1;
            writeMbb.putInt(ITEM_TYPE.ROTATE.ordinal());
            writeMbb.force();

            unmap(writeMbb);
            closeResource(writeChannel);
            closeResource(writeFile);

            writeFile = new RandomAccessFile(fileDir + FILE_NAME + writeIndexFile + FILE_SUFFIX, "rw");
            writeChannel = writeFile.getChannel();
            writeMbb = writeChannel.map(READ_WRITE, 0, PAGE_SIZE);

            indexMbb.putInt(Integer.SIZE, writeIndexFile);
        }

        headerBb.clear();
        headerBb.putInt(ITEM_TYPE.FILL.ordinal());
        headerBb.putInt(length);
        headerBb.flip();

        writeMbb.put(headerBb);
        writeMbb.put(contents);

    }

    private byte[] toBytes(T item) throws IOException {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);

            oos.writeObject((Object) item);
            oos.flush();
            return baos.toByteArray();
        } finally {
            closeResource(baos);
            closeResource(oos);
        }

    }

    public synchronized T comsume() throws Exception {
        int readPos = readMbb.position();

        int type = readMbb.getInt();
        int length = readMbb.getInt();

        if (type == ITEM_TYPE.ROTATE.ordinal()) {
            readIndexFile += 1;

            readMbb.putInt(ITEM_TYPE.ROTATE.ordinal());
            readMbb.force();

            unmap(readMbb);
            closeResource(readChannel);
            closeResource(readFile);

            readFile = new RandomAccessFile(fileDir + FILE_NAME + readIndexFile + FILE_SUFFIX, "rw");
            readChannel = readFile.getChannel();
            readMbb = readChannel.map(READ_WRITE, 0, PAGE_SIZE);

            indexMbb.putInt(0, readIndexFile);
            type = readMbb.getInt();
            length = readMbb.getInt();
        }

        if (type == ITEM_TYPE.EMPTY.ordinal() || length <= 0) {
            readMbb.position(readPos);
            return null;
        }

        byte[] contents = new byte[length];
        readMbb.get(contents);
        readMbb.putInt(readPos, ITEM_TYPE.EMPTY.ordinal());

        T object = toObject(contents);

        return object;
    }

    private T toObject(byte[] content) throws IOException,
            ClassNotFoundException {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(content);
            ois = new ObjectInputStream(bais);

            return (T) ois.readObject();
        } finally {
            closeResource(bais);
            closeResource(ois);
        }
    }

    private void closeResource(Closeable c) throws IOException {
        if (c != null) {
            c.close();
        }
    }

    public void shutdown() throws IOException {

        if (writeMbb != null) {
            writeMbb.force();
            unmap(writeMbb);
        }
        if (readMbb != null) {
            readMbb.force();
            unmap(readMbb);
        }
        if (indexMbb != null) {
            indexMbb.force();
            unmap(indexMbb);
        }

        closeResource(readChannel);
        closeResource(readFile);
        closeResource(writeChannel);
        closeResource(writeFile);
        closeResource(indexChannel);
        closeResource(indexFile);
    }

    private enum ITEM_TYPE {
        EMPTY, FILL, ROTATE
    }
}

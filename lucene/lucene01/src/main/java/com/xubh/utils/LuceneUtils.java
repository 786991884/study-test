package com.xubh.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * lucene工具类
 */
public class LuceneUtils {
    private static Directory directory;
    private static Analyzer analyzer;
    private static IndexWriter indexWriter;
    private static String luceneDir;

    // 懒汉式，创建indexwriter
    static {
        try {
            InputStream in = LuceneUtils.class.getClassLoader()
                    .getResourceAsStream("lucene.properties");
            Properties p = new Properties();
            p.load(in);
            luceneDir = p.getProperty("lucene_dir");// 文件检索目录
            directory = FSDirectory.open(new File(luceneDir));
            analyzer = new IKAnalyzer();
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
                    Version.LATEST, analyzer);
            indexWriter = new IndexWriter(directory, indexWriterConfig);
            // 虚拟机退出时关闭
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        indexWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取IndexWriter
    public static IndexWriter getIndexWriter() {
        return indexWriter;
    }

    //获取indexsearcher
    public static IndexSearcher getIndexSearcher() throws Exception {
        return new IndexSearcher(DirectoryReader.open(directory));
    }

    // 打印控制台
    public static void writeDocument(Query parse) throws IOException {
        // 创建查询器
        IndexSearcher indexSearcher = new IndexSearcher(
                DirectoryReader.open(FSDirectory.open(new File(luceneDir))));
        // 搜索前n名结果：按照得分排序的结果
        TopDocs topDocs = indexSearcher.search(parse, Integer.MAX_VALUE);
        // 打印结果集
        System.out.println("搜索结果" + topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            System.out.println("得分：" + scoreDoc.score);
            Document doc = indexSearcher.doc(scoreDoc.doc);
            System.out.println("id：" + doc.get("id"));
            System.out.println("标题：" + doc.get("title"));
            System.out.println("内容：" + doc.get("content"));
            System.out.println();
        }
    }

    public static List<Document> fileDocumet(String path) throws Exception {

        List<Document> dList = new ArrayList<Document>();

        //读取F:\searchsource文档
        File folder = new File(path);
        if (!folder.isDirectory()) {
            return null;
        }
        File[] listFiles = folder.listFiles();
        for (File file : listFiles) {
            Document doc = new Document();
            //获取文件名
            String fileName = file.getName();
            doc.add(new StringField("fileName", fileName, Store.YES));
            //获取文件绝对路径
            String filePath = file.getAbsolutePath();
            doc.add(new StringField("filePath", filePath, Store.YES));
            //获取文件内容
            Tika tika = new Tika();
            String fileConent = tika.parseToString(file);
            doc.add(new TextField("fileConent", fileConent, Store.YES));
            dList.add(doc);
        }

        return dList;


    }

}

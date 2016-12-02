package oracle.wdp.struts2.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class UploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String savaPath;
	private String fileName;
	private String contentType;
	private File myFile;

	// ��ʲô�����ļ�����
	public void setMyFileContentType(String contentType) {
		this.contentType = contentType;
		System.out.println(this.contentType);
	}

	// ��ѡ����ļ�������
	public void setMyFileFileName(String fileName) {
		this.fileName = fileName;
		System.out.println(this.fileName);
	}

	// �ϴ��ļ�
	public void setMyFile(File file) throws IOException {
		this.myFile = file;
	}

	@Override
	public String execute() throws Exception {
		InputStream inputStream = new FileInputStream(myFile);
		// ����� �õ�Servlet��ض���ķ���
		HttpServletRequest request = ServletActionContext.getRequest();
		// �õ�������ʵ��·��
		String realFolder = request.getServletContext().getRealPath(savaPath);
		// �ļ���ȫ·��
		String realFilePath = realFolder + File.separator + fileName;
		System.out.println(realFilePath);
		OutputStream out = new FileOutputStream(realFilePath);
		// ����������д�������
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		inputStream.close();
		return SUCCESS;
	}

	public String getSavaPath() {
		return savaPath;
	}

	public void setSavaPath(String savaPath) {
		this.savaPath = savaPath;
	}
}

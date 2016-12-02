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

	// 传什么样的文件类型
	public void setMyFileContentType(String contentType) {
		this.contentType = contentType;
		System.out.println(this.contentType);
	}

	// 你选择的文件的名称
	public void setMyFileFileName(String fileName) {
		this.fileName = fileName;
		System.out.println(this.fileName);
	}

	// 上传文件
	public void setMyFile(File file) throws IOException {
		this.myFile = file;
	}

	@Override
	public String execute() throws Exception {
		InputStream inputStream = new FileInputStream(myFile);
		// 输出流 得到Servlet相关对象的方法
		HttpServletRequest request = ServletActionContext.getRequest();
		// 得到磁盘真实的路径
		String realFolder = request.getServletContext().getRealPath(savaPath);
		// 文件的全路径
		String realFilePath = realFolder + File.separator + fileName;
		System.out.println(realFilePath);
		OutputStream out = new FileOutputStream(realFilePath);
		// 从输入流读写到输出流
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

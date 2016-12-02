package oracle.wdp.struts2.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class MultiUploadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private String savePath;
	private File[] myImg;
	private String[] myImgContentType;
	private String[] myImgFileName;

	@Override
	public String execute() throws Exception {
		for (int i = 0; i < myImg.length; i++) {
			processUpload(myImg[i], myImgFileName[i]);
		}
		return SUCCESS;
	}

	private void processUpload(File file, String fileName) throws Exception {
		InputStream is = new FileInputStream(file);
		// 输出流 这里是如何得到servlet相关对象的方法
		HttpServletRequest request = ServletActionContext.getRequest();
		// 得到磁盘真实路径
		String realFolder = request.getServletContext().getRealPath(savePath);
		// 文件的全路径
		String realFilePath = realFolder + File.separator + fileName;
		System.out.println(realFilePath);
		OutputStream out = new FileOutputStream(realFilePath);
		// 从输入流读写到输出流
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}
		out.close();
		is.close();
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public void setMyImg(File[] myImg) {
		this.myImg = myImg;
	}

	public void setMyImgContentType(String[] myImgContentType) {
		this.myImgContentType = myImgContentType;
	}

	public void setMyImgFileName(String[] myImgFileName) {
		this.myImgFileName = myImgFileName;
	}
}

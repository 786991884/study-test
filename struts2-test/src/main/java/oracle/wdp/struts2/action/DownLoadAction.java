package oracle.wdp.struts2.action;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DownLoadAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private InputStream inputStream;
	private String filePath;

	public InputStream getInputStream() {
		inputStream = ActionContext.getContext().getClass()
				.getResourceAsStream(filePath);
		System.out.println(inputStream == null);
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() throws Exception {
		if (filePath.lastIndexOf("/") == -1) {
			String fileName = filePath
					.substring(filePath.lastIndexOf("\\") + 1);
			// return new String(fileName.getBytes("utf-8","iso-8859-1"));
			System.out.println(fileName);
			return URLEncoder.encode(fileName, "utf-8");
		} else {
			String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
			// return new String(fileName.getBytes("utf-8","iso-8859-1"));
			System.out.println(fileName);
			return URLEncoder.encode(fileName, "utf-8");
		}
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
		System.out.println(filePath);
	}
}

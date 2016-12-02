package oracle.wdp.struts2.model;

import java.io.Serializable;

public class MessageStore implements Serializable {
	private static final long serialVersionUID = 1L;

	public MessageStore() {
		setMessage("Hello Struts User");
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

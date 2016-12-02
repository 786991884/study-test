package oracle.wdp.struts2.action;

import com.opensymphony.xwork2.Action;

import oracle.wdp.struts2.model.MessageStore;

/**
 * 我们用一个跟struts没有关系的类作为action，这种设计无侵入 但是我们提供的方法签名：public String execute() throws
 * Exception
 * 
 * @author Lenovo
 * 
 */

public class HelloWorldAction_imp implements Action {

	private MessageStore messageStore;
	// 由于每次请求一个action实例，加大同要记录action的访问次数，必须是static的
	private static int helloCount = 0;
	// 要接收参数值，就提供get和set方法
	private String userName;

	@Override
	public String toString() {
		return messageStore + "(from toString)";
	}

	public HelloWorldAction_imp() {
		System.out.println(22);
	}

	@Override
	public String execute() throws Exception {
		messageStore = new MessageStore();
		if (userName != null) {
			messageStore.setMessage(messageStore.getMessage() + "" + userName);
		}
		helloCount++;
		return "success";
	}

	public MessageStore getMessageStore() {
		return messageStore;
	}

	public void setMessageStore(MessageStore messageStore) {
		this.messageStore = messageStore;
	}

	public int getHelloCount() {
		return helloCount;
	}

	public void setHelloCount(int helloCount) {
		HelloWorldAction_imp.helloCount = helloCount;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}

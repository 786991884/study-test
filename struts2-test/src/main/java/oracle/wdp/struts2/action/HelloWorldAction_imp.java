package oracle.wdp.struts2.action;

import com.opensymphony.xwork2.Action;

import oracle.wdp.struts2.model.MessageStore;

/**
 * ������һ����strutsû�й�ϵ������Ϊaction��������������� ���������ṩ�ķ���ǩ����public String execute() throws
 * Exception
 * 
 * @author Lenovo
 * 
 */

public class HelloWorldAction_imp implements Action {

	private MessageStore messageStore;
	// ����ÿ������һ��actionʵ�����Ӵ�ͬҪ��¼action�ķ��ʴ�����������static��
	private static int helloCount = 0;
	// Ҫ���ղ���ֵ�����ṩget��set����
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

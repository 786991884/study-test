package oracle.wdp.struts2.action;

import org.apache.struts2.interceptor.validation.SkipValidation;

import oracle.wdp.struts2.model.Person;

import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport {
	// Ϊ������Action�ļ򵥣�����һ�������������һ��bean�������û���Ϣ
	private static final long serialVersionUID = 1L;
	private Person personBean;

	// �����㲻����֤
	@SkipValidation
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	// personBean��firstName��θ�ֵ�ģ����û��personBean���ڣ�����Ҫʵ����һ����Ȼ����ø�ʵ����setFirstName
	// presonBean.lastName:�����Ѿ�����personBeanʵ�� �ˣ���ô�͵���setLastName
	public Person getPersonBean() {
		return personBean;
	}

	public void setPersonBean(Person personBean) {
		this.personBean = personBean;
	}

	// �����������ʲôʱ�䶼���ã�������vilidateExecute()��vilidateDoExecute()֮��
	// ÿ�����������������ִ�У��൱��ͨ����֤��������������ͨ����֤

	@Override
	public void validate() {
		// firstName����д
		if ("".equals(personBean.getFirstName().trim())) {
			// ��֤һ��Ҫ���������󣬷���struts2��Ϊû�д�����ʵ����һ��map
			// ������ʻ�����getText()��Ҫ�̳�ActionSupport
			// ռλ������
			addFieldError("personBean.firstName", getText("firstName.required"));
			addFieldError("personBean.firstName",
					getText("firstName.required", new String[] { "123" }));
		}
		if (personBean.getFirstName().length() == 0) {
			addFieldError("personBean.firstName", "First name is required.");
		}
		// email����д
		if (personBean.getEmail().length() == 0) {
			addFieldError("personBean.email", "Email is required.");
		}
		// 18���²���ע��
		if (personBean.getAge() < 18 || personBean.getAge() > 60) {
			addFieldError("personBean.age",
					"Age is required and must be 18 or older");
		}
	}
}

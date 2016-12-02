package oracle.wdp.struts2.action;

import org.apache.struts2.interceptor.validation.SkipValidation;

import oracle.wdp.struts2.model.Person;

import com.opensymphony.xwork2.ActionSupport;

public class RegisterAction extends ActionSupport {
	// 为了我们Action的简单，我们一般情况下用另外一个bean来保存用户信息
	private static final long serialVersionUID = 1L;
	private Person personBean;

	// 告诉你不用验证
	@SkipValidation
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	// personBean，firstName如何赋值的：如果没有personBean存在，首先要实例化一个，然后调用该实例的setFirstName
	// presonBean.lastName:由于已经有了personBean实例 了，那么就调用setLastName
	public Person getPersonBean() {
		return personBean;
	}

	public void setPersonBean(Person personBean) {
		this.personBean = personBean;
	}

	// 这个方法无论什么时间都调用，并且在vilidateExecute()或vilidateDoExecute()之后
	// 每个方法调用这个都会执行，相当与通用验证，另外两个不是通用验证

	@Override
	public void validate() {
		// firstName必须写
		if ("".equals(personBean.getFirstName().trim())) {
			// 验证一定要添加这个错误，否则struts2认为没有错误，其实就是一个map
			// 代码国际化调用getText()类要继承ActionSupport
			// 占位符传参
			addFieldError("personBean.firstName", getText("firstName.required"));
			addFieldError("personBean.firstName",
					getText("firstName.required", new String[] { "123" }));
		}
		if (personBean.getFirstName().length() == 0) {
			addFieldError("personBean.firstName", "First name is required.");
		}
		// email必须写
		if (personBean.getEmail().length() == 0) {
			addFieldError("personBean.email", "Email is required.");
		}
		// 18岁下不能注册
		if (personBean.getAge() < 18 || personBean.getAge() > 60) {
			addFieldError("personBean.age",
					"Age is required and must be 18 or older");
		}
	}
}

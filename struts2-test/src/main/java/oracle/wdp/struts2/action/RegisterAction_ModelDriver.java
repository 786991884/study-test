package oracle.wdp.struts2.action;

import oracle.wdp.struts2.model.Person;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RegisterAction_ModelDriver extends ActionSupport implements
		ModelDriven<Person> {

	private static final long serialVersionUID = 1L;
    private Person model=new Person();
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
 //firstName:getModel-->person-->setFirstName(输入值类型转换)
	@Override
	public Person getModel() {
		return model;
	}

}

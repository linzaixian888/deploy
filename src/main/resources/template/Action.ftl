package ${controllerPackage};

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import ${controllerPackage}.base.BaseAction;

@Namespace("/${controllerName?uncap_first}")
public class ${controllerName}Action extends BaseAction{
	@Action(value="${controllerName?uncap_first}",results={
			@Result(location="${prefix}${pageName}${suffix}")
	})
	public String ${controllerName?uncap_first}() throws Exception {
		return SUCCESS;
	}
	@Action("testJSON")
	public String testJSON() throws Exception {
		setJSON("当前action是:", "${controllerName}Action");
		return JSON;
	}
}

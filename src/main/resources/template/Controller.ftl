package ${controllerPackage};

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;

@Controller
@Scope("request")
@RequestMapping("/${controllerName?uncap_first}")
public class ${controllerName}Controller {
	@RequestMapping(value="/${controllerName?uncap_first}")
	public String ${controllerName?uncap_first}(){
		return "/${pageName}";
	}
}
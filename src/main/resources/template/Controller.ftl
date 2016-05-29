package ${controllerPackage};

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("request")
@RequestMapping("/${controllerName?uncap_first}")
public class ${controllerName}Controller {
	@RequestMapping(value="/${controllerName?uncap_first}")
	public String ${controllerName?uncap_first}(){
		return "/${pageName}";
	}
	
	@ResponseBody
	@RequestMapping(value="/testJSON")
	public Map testJSON(){
		Map map=new HashMap();
		map.put("当前action是:", "${controllerName}Controller");
		return map;
	}
}

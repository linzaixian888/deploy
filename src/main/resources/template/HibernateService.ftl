package ${servicePackage};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import ${mapperPackage}.${myClass.className}Dao;
import ${pojoPackage}.${myClass.className};
import ${servicePackage}.base.BaseService;

@Service

public class ${myClass.className}Service extends BaseService<${myClass.className},${myClass.idField.type}> implements I${myClass.className}Service{
	@Autowired
	private ${myClass.className}Dao ${myClass.className?uncap_first}Dao;	
	@Autowired
	public void setBaseDao(${myClass.className}Dao ${myClass.className?uncap_first}Dao) {
		super.setBaseDao(${myClass.className?uncap_first}Dao);
	}

} 

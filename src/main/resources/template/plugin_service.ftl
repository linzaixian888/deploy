package ${servicePackage};


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import ${pojoPackage}.${myClass.className};
import ${mapperPackage}.${myClass.className}Dao;
import com.bluedon.core.base.BaseServiceImpl;



@Service
public class ${myClass.className}Service  extends BaseServiceImpl<${myClass.className}, ${myClass.idField.type}> implements I${myClass.className}Service {
	@Resource
	private ${myClass.className}Dao baseDao;
	@Resource
	public void setBaseDao(${myClass.className}Dao baseDao) {
		// TODO Auto-generated method stub
		super.setBaseDao(baseDao);
	}
	
	
	
}

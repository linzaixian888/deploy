package ${servicePackage};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import ${daoPackage}.${daoI};
import ${pojoPackage}.${myClass.className};
import ${daoPackage}.base.${baseDaoI};
import ${servicePackage}.base.${baseServiceImpl};

@Service
public class ${serviceImpl} extends ${baseServiceImpl}<${myClass.className},${myClass.idField.type}> implements ${serviceI}{
	@Autowired
	private ${daoI} ${daoI?uncap_first};	
	@Override
	public ${baseDaoI}<${myClass.className}, ${myClass.idField.type}> get${baseDaoI}() {
		return ${daoI?uncap_first};
	}	

} 

package ${servicePackage};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import ${daoPackage}.${daoI};
import ${pojoPackage}.${myClass.className};

@Service
public class ${serviceImpl} extends ${baseServiceImpl}<${myClass.className},${myClass.idField.type}> implements ${serviceI}{
	@Autowired
	private ${daoI} ${daoI?uncap_first};	
	@Autowired
	public void set${baseDaoI}(${daoI} ${daoI?uncap_first}) {
		super.set${baseDaoI}(${daoI?uncap_first});
	}

} 

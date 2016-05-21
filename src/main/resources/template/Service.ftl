package ${servicePackage};


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
<#if mybatis??>
import org.apache.ibatis.session.RowBounds;
import ${mapperPackage}.${myClass.className}Mapper;
</#if>
<#if hibernate??>
import ${mapperPackage}.${myClass.className}Dao;
</#if>
import ${pojoPackage}.${myClass.className};

@Service
<#if mybatis??>
public class ${myClass.className}Service extends BaseService<${myClass.className}> implements I${myClass.className}Service{
	@Autowired
	private ${myClass.className}Mapper ${myClass.className?uncap_first}Mapper;	
	@Autowired
	public void setBaseMapper(${myClass.className}Mapper ${myClass.className?uncap_first}Mapper) {
		super.setBaseMapper(${myClass.className?uncap_first}Mapper);
	}

} 
</#if>
<#if hibernate??>
public class ${myClass.className}Service extends BaseService<${myClass.className},${myClass.idField.type}> implements I${myClass.className}Service{
	@Autowired
	private ${myClass.className}Dao ${myClass.className?uncap_first}Dao;	
	@Autowired
	public void setBaseDao(${myClass.className}Dao ${myClass.className?uncap_first}Dao) {
		super.setBaseDao(${myClass.className?uncap_first}Dao);
	}

} 
</#if>
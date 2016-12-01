package ${daoPackage};

import ${pojoPackage}.${myClass.className};
import org.springframework.data.jpa.repository.JpaRepository;

public interface ${daoI} extends ${daoI}Custom,JpaRepository<${myClass.className}, ${myClass.idField.type}>{
	
}
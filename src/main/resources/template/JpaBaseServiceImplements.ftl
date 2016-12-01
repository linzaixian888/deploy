package ${servicePackage}.base;

import java.io.Serializable;
import java.util.List;
import ${daoPackage}.base.${baseDaoI};

public abstract class ${baseServiceImpl}<T,PK extends Serializable> implements ${baseServiceI}<T, PK>{
	public abstract ${baseDaoI}<T,PK> get${baseDaoI}();
}



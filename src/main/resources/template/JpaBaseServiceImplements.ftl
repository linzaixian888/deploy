package ${servicePackage}.base;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ${daoPackage}.base.${baseDaoI};

public abstract class ${baseServiceImpl}<T,PK extends Serializable> implements ${baseServiceI}<T, PK>{
	public abstract ${baseDaoI}<T,PK> get${baseDaoI}();
	private JpaRepository<T, PK> getRepository(){
		return (JpaRepository<T, PK>) getBaseDao();
	}
	@Override
	public void testTransactional(T t, boolean isThrowRuntimException){
		getRepository().save(t);
		if(isThrowRuntimException){
			throw new RuntimeException("运行时异常");
		}
	}
	@Override
	public void deleteAllInBatch(){
		getRepository().deleteAllInBatch();
	}
	@Override
	public long count(){
		return getRepository().count();
	}
}



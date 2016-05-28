package ${servicePackage}.base;

import java.io.Serializable;
import java.util.List;
import ${daoPackage}.base.${baseDaoI};

public abstract class ${baseServiceImpl}<T,PK extends Serializable> implements ${baseServiceI}<T, PK>{
	public abstract ${baseDaoI}<T,PK> get${baseDaoI}();

	public T get(PK pk) {
		return get${baseDaoI}().get(pk);
	}

	public T load(PK pk) {
		return get${baseDaoI}().load(pk);
	}

	public PK save(T t) {
		return get${baseDaoI}().save(t);
	}

	public void update(T t) {
		get${baseDaoI}().update(t);
		
	}

	public T merge(T t) {
		return get${baseDaoI}().merge(t);
	}

	public void saveOrUpdate(T t) {
		get${baseDaoI}().saveOrUpdate(t);
		
	}

	public void delete(T t) {
		get${baseDaoI}().delete(t);
		
	}
	
	public int deleteAll() {
		return get${baseDaoI}().deleteAll();
		
	}

	public List<T> findAll() {
		return get${baseDaoI}().findAll();
	}

	public long countAll() {
		return get${baseDaoI}().countAll();
	}

}

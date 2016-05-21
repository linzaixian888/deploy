package ${servicePackage}.base;

import java.io.Serializable;
import java.util.List;
import ${mapperPackage}.base.IBaseDao;

public class BaseService<T,PK extends Serializable> implements IBaseService<T, PK>{
	private IBaseDao<T,PK> baseDao;
	
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public T get(PK pk) {
		return baseDao.get(pk);
	}

	public T load(PK pk) {
		return baseDao.load(pk);
	}

	public PK save(T t) {
		return baseDao.save(t);
	}

	public void update(T t) {
		baseDao.update(t);
		
	}

	public T merge(T t) {
		return baseDao.merge(t);
	}

	public void saveOrUpdate(T t) {
		baseDao.saveOrUpdate(t);
		
	}

	public void delete(T t) {
		baseDao.delete(t);
		
	}
	
	
	public int deleteAll() {
		return baseDao.deleteAll();
		
	}

	public List<T> findAll() {
		return baseDao.findAll();
	}

	public int countAll() {
		return baseDao.countAll();
	}
	
	public int updateByPojo(T t) {
		return baseDao.updateByPojo(t);
	}
	
	public int deleteByPojo(T t) {
		return baseDao.deleteByPojo(t);
	}
	
	public List<T> findByPojo(T t) {
		return baseDao.findByPojo(t);
	}
	
	public int insertByPojo(T t){
		return baseDao.insertByPojo(t);
	}
	
	public int insertBatch(List<T> list,String...columns) {
		return baseDao.insertBatch(list,columns);
	}

}

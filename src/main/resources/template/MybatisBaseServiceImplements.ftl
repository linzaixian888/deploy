package ${servicePackage};

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import ${daoPackage}.${baseDaoI};

public class ${baseServiceImpl}<T,PK> implements ${baseServiceI}<T,PK> {

	private ${baseDaoI}<T,PK> ${baseDaoI?uncap_first};

	public void set${baseDaoI}(${baseDaoI} ${baseDaoI?uncap_first}) {
		this.${baseDaoI?uncap_first} = ${baseDaoI?uncap_first};
	}

	public int save(T t) {
		return ${baseDaoI?uncap_first}.save(t);
	}

	public int saveBatch(List<T> list) {
		return ${baseDaoI?uncap_first}.saveBatch(list);
	}

	public int deleteById(PK id) {
		return ${baseDaoI?uncap_first}.deleteById(id);
	}

	public int deleteByIds(PK...ids) {
		return ${baseDaoI?uncap_first}.deleteByIds(ids);
	}

	public int deleteBatch(List<T> list) {
		return ${baseDaoI?uncap_first}.deleteBatch(list);
	}

	public int deleteAll() {
		return ${baseDaoI?uncap_first}.deleteAll();
	}

	public int deleteByPojo(T t) {
		return ${baseDaoI?uncap_first}.deleteByPojo(t);
	}

	public int update(T t) {
		return ${baseDaoI?uncap_first}.update(t);
	}

	public List<T> findAll() {
		return ${baseDaoI?uncap_first}.findAll();
	}

	public List<T> findAll(RowBounds rowBounds) {
		return ${baseDaoI?uncap_first}.findAll(rowBounds);
	}

	public List<T> findByPojo(T t) {
		return ${baseDaoI?uncap_first}.findByPojo(t);
	}

	public List<T> findByPojo(T t, RowBounds rowBounds) {
		return ${baseDaoI?uncap_first}.findByPojo(t,rowBounds);
	}

	public T findById(PK id) {
		return ${baseDaoI?uncap_first}.findById(id);
	}

	public List<T> findByIds(PK...ids) {
		return ${baseDaoI?uncap_first}.findByIds(ids);
	}

	public long countAll() {
		return ${baseDaoI?uncap_first}.countAll();
	}

	public long countByPojo(T t) {
		return ${baseDaoI?uncap_first}.countByPojo(t);
	}

}
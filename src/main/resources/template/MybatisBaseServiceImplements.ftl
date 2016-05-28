package ${servicePackage};

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import ${daoPackage}.${baseDaoI};

public class ${baseServiceImpl}<T> implements ${baseServiceI}<T> {

	private ${baseDaoI}<T> ${baseDaoI?uncap_first};

	public void set${baseDaoI}(${baseDaoI} ${baseDaoI?uncap_first}) {
		this.${baseDaoI?uncap_first} = ${baseDaoI?uncap_first};
	}

	public int insert(T t) {
		return ${baseDaoI?uncap_first}.insert(t);
	}

	public int insertBatch(List<T> list) {
		return ${baseDaoI?uncap_first}.insertBatch(list);
	}

	public int deleteById(Object id) {
		return ${baseDaoI?uncap_first}.deleteById(id);
	}

	public int deleteByIds(Object...ids) {
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

	public T findById(Object id) {
		return ${baseDaoI?uncap_first}.findById(id);
	}

	public List<T> findByIds(Object...ids) {
		return ${baseDaoI?uncap_first}.findByIds(ids);
	}

	public int countAll() {
		return ${baseDaoI?uncap_first}.countAll();
	}

	public int countByPojo(T t) {
		return ${baseDaoI?uncap_first}.countByPojo(t);
	}

}
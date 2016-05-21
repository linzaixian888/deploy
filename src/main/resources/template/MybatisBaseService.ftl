package ${servicePackage};

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.annotation.Transactional;
import ${mapperPackage}.BaseMapper;

public class BaseService<T> implements IBaseService<T> {

	private BaseMapper<T> baseMapper;

	public void setBaseMapper(BaseMapper baseMapper) {
		this.baseMapper = baseMapper;
	}

	public int insert(T t) {
		return baseMapper.insert(t);
	}

	public int insertBatch(List<T> list) {
		return baseMapper.insertBatch(list);
	}

	public int deleteById(Object id) {
		return baseMapper.deleteById(id);
	}

	public int deleteByIds(Object...ids) {
		return baseMapper.deleteByIds(ids);
	}

	public int deleteBatch(List<T> list) {
		return baseMapper.deleteBatch(list);
	}

	public int deleteAll() {
		return baseMapper.deleteAll();
	}

	public int deleteByPojo(T t) {
		return baseMapper.deleteByPojo(t);
	}

	public int update(T t) {
		return baseMapper.update(t);
	}

	public List<T> findAll() {
		return baseMapper.findAll();
	}

	public List<T> findAll(RowBounds rowBounds) {
		return baseMapper.findAll(rowBounds);
	}

	public List<T> findByPojo(T t) {
		return baseMapper.findByPojo(t);
	}

	public List<T> findByPojo(T t, RowBounds rowBounds) {
		return baseMapper.findByPojo(t,rowBounds);
	}

	public T findById(Object id) {
		return baseMapper.findById(id);
	}

	public List<T> findByIds(Object...ids) {
		return baseMapper.findByIds(ids);
	}

	public int countAll() {
		return baseMapper.countAll();
	}

	public int countByPojo(T t) {
		return baseMapper.countByPojo(t);
	}

}
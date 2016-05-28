package ${servicePackage};

import java.util.List;

import org.apache.ibatis.session.RowBounds;


public interface ${baseServiceI}<T> {


	/**
	 * 插入记录
	 * @param entity
	 * @return
	 */
	public abstract int insert(T t);

	/**
	 * 批量插入记录
	 * @param list
	 * @return
	 */
	public abstract int insertBatch(List<T> list);

	/**
	 * 根据ID删除记录
	 * @param ids
	 * @return
	 */
	public abstract int deleteById(Object id);

	/**
	 * 根据批量ID删除记录
	 * @param ids
	 * @return
	 */
	public abstract int deleteByIds(Object... ids);

	/**
	 * 根据ID批量删除记录
	 * @param list
	 * @return
	 */
	public abstract int deleteBatch(List<T> list);

	/**
	 * 删除所有记录 
	 * @return
	 */
	public abstract int deleteAll();

	/**
	 * 根据pojo有赋值的属性删除数据记录
	 * @param pojo
	 * @return
	 */
	public abstract int deleteByPojo(T t);

	/**
	 * 根据id更新记录
	 * @param entity
	 * @return
	 */
	public abstract int update(T t);

	/**
	 * 查询所有记录 
	 * @return
	 */
	public abstract List<T> findAll();

	/**
	 * 分页查询指定范围内的记录
	 * @param rowBounds
	 * @return
	 */
	public abstract List<T> findAll(RowBounds rowBounds);

	/**
	 * 根据pojo有赋值的属性查询数据记录
	 * @param pojo
	 * @return
	 */
	public abstract List<T> findByPojo(T t);

	/**
	 * 根据pojo有赋值的属性分页查询数据记录
	 * @param pojo,rowBounds
	 * @return
	 */
	public abstract List<T> findByPojo(T t, RowBounds rowBounds);

	/**
	 * 根据id查询数据记录
	 * @param id
	 * @return
	 */
	public abstract T findById(Object id);

	/**
	 * 根据多个id查询数据记录
	 * @param id
	 * @return
	 */
	public abstract List<T> findByIds(Object... ids);

	/**
	 * 查询表的记录数
	 * @return
	 */
	public abstract int countAll();

	/**
	 * 根据pojo有赋值的语句查询记录数
	 * @param pojo
	 * @return
	 */
	public abstract int countByPojo(T t);

}
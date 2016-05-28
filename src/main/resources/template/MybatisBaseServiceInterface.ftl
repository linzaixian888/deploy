package ${servicePackage};

import java.util.List;

import org.apache.ibatis.session.RowBounds;


public interface ${baseServiceI}<T,PK> {


	/**
	 * 插入记录
	 * @param entity
	 * @return
	 */
	public  int save(T t);

	/**
	 * 批量插入记录
	 * @param list
	 * @return
	 */
	public  int saveBatch(List<T> list);

	/**
	 * 根据ID删除记录
	 * @param ids
	 * @return
	 */
	public  int deleteById(PK id);

	/**
	 * 根据批量ID删除记录
	 * @param ids
	 * @return
	 */
	public  int deleteByIds(PK... ids);

	/**
	 * 根据ID批量删除记录
	 * @param list
	 * @return
	 */
	public  int deleteBatch(List<T> list);

	/**
	 * 删除所有记录 
	 * @return
	 */
	public  int deleteAll();

	/**
	 * 根据pojo有赋值的属性删除数据记录
	 * @param pojo
	 * @return
	 */
	public  int deleteByPojo(T t);

	/**
	 * 根据id更新记录
	 * @param entity
	 * @return
	 */
	public  int update(T t);

	/**
	 * 查询所有记录 
	 * @return
	 */
	public  List<T> findAll();

	/**
	 * 分页查询指定范围内的记录
	 * @param rowBounds
	 * @return
	 */
	public  List<T> findAll(RowBounds rowBounds);

	/**
	 * 根据pojo有赋值的属性查询数据记录
	 * @param pojo
	 * @return
	 */
	public  List<T> findByPojo(T t);

	/**
	 * 根据pojo有赋值的属性分页查询数据记录
	 * @param pojo,rowBounds
	 * @return
	 */
	public  List<T> findByPojo(T t, RowBounds rowBounds);

	/**
	 * 根据id查询数据记录
	 * @param id
	 * @return
	 */
	public  T findById(PK id);

	/**
	 * 根据多个id查询数据记录
	 * @param id
	 * @return
	 */
	public  List<T> findByIds(PK... ids);

	/**
	 * 查询表的记录数
	 * @return
	 */
	public  long countAll();

	/**
	 * 根据pojo有赋值的语句查询记录数
	 * @param pojo
	 * @return
	 */
	public  long countByPojo(T t);

}
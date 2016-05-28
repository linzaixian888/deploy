package ${daoPackage};
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
@SuppressWarnings("rawtypes")
public interface ${baseDaoI}<T,PK> {

	/**
	 * 插入记录
	 * @param entity
	 * @return
	 */
	int save(T entity);
	

	
	/**
	 * 批量插入记录
	 * @param list
	 * @return
	 */
	int saveBatch(List<T> list);
	
	/**
	 * 根据ID删除记录
	 * @param ids
	 * @return
	 */
	int deleteById(PK id);
	
	/**
	 * 根据ID删除记录
	 * @param ids
	 * @return
	 */
	int deleteByIds(PK...ids);
	
	/**
	 * 根据ID批量删除记录
	 * @param list
	 * @return
	 */
	int deleteBatch(List<T> list);
	
	/**
	  * 删除所有记录 
	  * @return
	  */
	int deleteAll();
	
	/**
	 * 根据pojo删除数据记录
	 * @param pojo
	 * @return
	 */
	int deleteByPojo(T t);
	
	 /**
	  * 根据id更新记录
	  * @param entity
	  * @return
	  */
	int update(T entity);
	
	 /**
	  * 查询所有记录 
	  * @return
	  */
	List<T> findAll();
	
	/**
	 * 分页查询指定范围内的记录
	 * @param rowBounds
	 * @return
	 */
	List<T> findAll(RowBounds rowBounds);
	
	/**
	 * 根据pojo查询数据记录
	 * @param pojo
	 * @return
	 */
	List<T> findByPojo(T t);
	
	/**
	 * 根据pojo分页查询数据记录
	 * @param pojo,rowBounds
	 * @return
	 */
	List<T> findByPojo(T t,RowBounds rowBounds);
	
	/**
	 * 根据id查询数据记录
	 * @param id
	 * @return
	 */
	T findById(PK id);
	
	
	
	/**
	 * 根据多个id查询数据记录
	 * @param id
	 * @return
	 */
	List<T> findByIds(PK...ids);
	
	/**
	 * 查询表的记录数
	 * @return
	 */
	long countAll();
	
	/**
	 * 根据pojo查询数据记录数
	 * @return
	 */
	long countByPojo(T t);
	

}


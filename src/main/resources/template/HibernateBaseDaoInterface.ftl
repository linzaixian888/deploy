package ${daoPackage}.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

public interface ${baseDaoI}<T, PK extends Serializable> {

	/**
	 * 获取Hibernate的原生Session对象
	 * @return
	 */
	public Session getSession();
	/**
	 * 返回指定ID的javabean对象
	 * @param pk
	 * @return
	 */
	public T get(PK pk);
	/**
	 * 返回指定ID的javabean对象(有缓存功能)
	 * @param pk
	 * @return
	 */
	public T load(PK pk);
	/**
	 * 保存javabean对象到数据库
	 * @param t
	 * @return
	 */
	public PK save(T t);
	/**
	 * 根据指定ID的javabean对象修改数据库
	 * @param t
	 */
	public void update(T t);
	/**
	 * 根据ID值发送查询语句，进行插入或修改操作
	 * @param t
	 * @return
	 */
	public T merge(T t);
	/**
	 * 根据ID值进行插入或修改操作
	 * @param t
	 */
	public void saveOrUpdate(T t);
	/**
	 * 根据javabean对象的ID值进行删除操作
	 * @param t
	 */
	public void delete(T t);
	/**
	 * 查询全部
	 * @return
	 */
	public List<T> findAll();
	/**
	 * 统计全部纪录数
	 * @return
	 */
	public long countAll();
	/**
	 * 删除全部
	 * @return
	 */
	public int deleteAll();
	/**
	 * 根据属性和属性值进行查询,返回查询的集合
	 * @param key
	 * @param value
	 * @return
	 */
	public List<T> queryProperty(String key, Object value);

	/**
	 * 根据属性和属性值进行查询，查询第一行纪录
	 * @param key
	 * @param value
	 * @return
	 */
	public T querySingleProperty(String key, Object value);

	public List<T> queryHql(QueryCallBack callBack, String hql);

	public List<T> queryHql(String hql, Object... params);

	public List<T> queryHql(Page page, String hql, Object... params);

	public List<T> queryHql(String hql, Map<String, Object> params);

	public List<T> queryHql(Page page, String hql, Map<String, Object> params);

	public List querySql(QueryCallBack callBack, String sql);

	public List<T> querySqlPojo(String sql, Object... params);

	public List<Map> querySqlMap(String sql, Object... params);

	public List<T> querySqlPojo(Page page, String sql, Object... params);

	public List<Map> querySqlMap(Page page, String sql, Object... params);

	public List<T> querySqlPojo(String sql, Map<String, Object> params);

	public List<Map> querySqlMap(String sql, Map<String, Object> params);

	public List<T> querySqlPojo(Page page, String sql,
			Map<String, Object> params);

	public List<Map> querySqlMap(Page page, String sql, Map<String, Object> params);

	public long countHql(QueryCallBack callBack, String hql);

	public long countHql(String hql, Object... params);

	public long countHql(String hql, Map<String, Object> params);

	public long countSql(QueryCallBack callBack, String sql);

	public long countSql(String sql, Object... params);

	public long countSql(String sql, Map<String, Object> params);

	public int executeHql(String hql, Object... params);

	public int executeSql(String sql, Object... params);

	<#--
	public List<T> findByPojo(T t);
	-->
	/**
	 * 查询结果集的第一行第一列
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object querySqlSingleValue(String sql, Object... params);
	<#--
	public int updateByPojo(T t);
	
	public int deleteByPojo(T t);
	
	public int insertByPojo(T t);
	
	public int insertBatch(List<T> list,String... columns);
	
	public String getIdName();
	public List<String> getColumnName();
	public String getTableName();
	-->

}
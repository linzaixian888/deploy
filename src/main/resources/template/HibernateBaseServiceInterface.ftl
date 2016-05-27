package ${servicePackage}.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session; 

public interface ${baseServiceI}<T, PK extends Serializable> {


	public  T get(PK pk);

	public  T load(PK pk);

	public  PK save(T t);

	public  void update(T t);

	public  T merge(T t);

	public  void saveOrUpdate(T t);

	public  void delete(T t);
	
	public  int deleteAll();

	public  List<T> findAll();
	
	public int countAll();
	
	<#--
	public int updateByPojo(T t);
	
	public int deleteByPojo(T t);
	
	public int insertByPojo(T t);
	
	public int insertBatch(List<T> list,String...columns);
	
	public List<T> findByPojo(T t);
	-->

}
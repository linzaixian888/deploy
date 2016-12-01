package ${daoPackage}.base;
import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
public abstract class BaseDaoImpl<T,PK extends Serializable> implements BaseDao<T, PK> {
	@PersistenceContext
	private EntityManager em;
}
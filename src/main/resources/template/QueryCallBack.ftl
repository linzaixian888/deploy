package ${mapperPackage}.base;

import org.hibernate.Query;

public interface QueryCallBack {
	public void filterQuery(Query query);
}

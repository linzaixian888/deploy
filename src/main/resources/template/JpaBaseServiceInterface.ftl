package ${servicePackage}.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface ${baseServiceI}<T, PK extends Serializable> {
	void testTransactional(T t, boolean isThrowRuntimException);
	void deleteAllInBatch();
	long count();

}
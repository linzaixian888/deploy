package  ${mapperPackage};
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import ${pojoPackage}.${myClass.className};
import com.bluedon.core.base.BaseDaoImpl;




@Repository
public class ${myClass.className}Dao extends BaseDaoImpl< ${myClass.className}, ${myClass.idField.type}> {
	

}

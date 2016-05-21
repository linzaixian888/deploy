package  ${mapperPackage};
import ${pojoPackage}.${myClass.className};
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import ${mapperPackage}.base.BaseDao;
@Repository
public class ${myClass.className}Dao extends BaseDao<${myClass.className}, ${myClass.idField.type}>{
	@Override
	public String getIdName() {
		// TODO Auto-generated method stub
		return "${myClass.idField.columnName}";
	}

	@Override
	public List<String> getColumnName() {
		List<String> list=new ArrayList<String>();
		<#list myClass.fields as item>
		list.add("${item.columnName}");
		</#list>
		return list;
	}

	@Override
	public String getTableName() {
		return "${myClass.tableName}";
	}
}

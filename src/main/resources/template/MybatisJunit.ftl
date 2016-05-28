package ${servicePackage};
import ${pojoPackage}.${myClass.className};
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="${springPath}")
public class Test${serviceI} {
	@Autowired
	private ${serviceI} ${serviceI?uncap_first};
	@Before
    public void setUp() throws Exception {
    	//ApplicationContext act=new ClassPathXmlApplicationContext("${springPath}");
		//${serviceI?uncap_first}=act.getBean(${serviceI}.class);
		${serviceI?uncap_first}.deleteAll();
    }
    @Test
	public void testSave(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${serviceI?uncap_first}.save(${myClass.className?uncap_first});
		long expected=${serviceI?uncap_first}.countAll();
		Assert.assertEquals(expected, 1);
	}
	@Test
	public void testSaveBatch(){
		int expected=5;
		List<${myClass.className}> list=new ArrayList<>();
		for(int i=0;i<expected;i++){
			${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			list.add(${myClass.className?uncap_first});
		}
		Assert.assertEquals(expected, ${serviceI?uncap_first}.saveBatch(list));
	}
	@Test
	public void testDeleteById(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${serviceI?uncap_first}.save(${myClass.className?uncap_first});
		int size=${serviceI?uncap_first}.deleteById(${myClass.className?uncap_first}.get${myClass.idField.columnName?cap_first}());
		Assert.assertEquals(1, size);
	}
	
	@Test
	public void testDeleteByIds(){
		int expected=5;
		${myClass.idField.type}[] array=new ${myClass.idField.type}[expected];
		List<${myClass.className}> list=new ArrayList<>();
		for(int i=0;i<expected;i++){
			${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${serviceI?uncap_first}.save(${myClass.className?uncap_first});
			array[i]=${myClass.className?uncap_first}.get${myClass.idField.columnName?cap_first}();
		}
		
		int size=${serviceI?uncap_first}.deleteByIds(array);
		Assert.assertEquals(expected, size);
	}
	
	@Test
	public void testDeleteBatch(){
		int expected=5;
		List<${myClass.className}> list=new ArrayList<>();
		for(int i=0;i<expected;i++){
			${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${serviceI?uncap_first}.save(${myClass.className?uncap_first});
			list.add(${myClass.className?uncap_first});
		}
		int size=${serviceI?uncap_first}.deleteBatch(list);
		Assert.assertEquals(expected, size);
	}
	
	
	@Test
    public void testDeleteAll(){
		int expected=5;
    	for(int i=0;i<expected;i++){
    		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${serviceI?uncap_first}.save(${myClass.className?uncap_first});
    	}
    	int size=${serviceI?uncap_first}.deleteAll();
		Assert.assertEquals(expected, size);
	}
	
	
	@Test
	public void testFindAll(){
    	int expected=5;
    	for(int i=0;i<expected;i++){
    		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${serviceI?uncap_first}.save(${myClass.className?uncap_first});
    	}
		int size=${serviceI?uncap_first}.findAll().size();
		Assert.assertEquals(expected, size);
		
    }
    @Test
	public void testFindById(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${serviceI?uncap_first}.save(${myClass.className?uncap_first});
		${myClass.className} ${myClass.className?uncap_first}2 =${serviceI?uncap_first}.findById(${myClass.className?uncap_first}.get${myClass.idField.columnName?cap_first}());
		Assert.assertNotNull(${myClass.className?uncap_first}2);
	}
	
	
	 @Test
	public void testFindByIds(){
		int expected=5;
		${myClass.idField.type}[] array=new ${myClass.idField.type}[expected];
		List<${myClass.className}> list=new ArrayList<>();
		for(int i=0;i<expected;i++){
			${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${serviceI?uncap_first}.save(${myClass.className?uncap_first});
			array[i]=${myClass.className?uncap_first}.get${myClass.idField.columnName?cap_first}();
		}
		
		List<${myClass.className}> resultList=${serviceI?uncap_first}.findByIds(array);
		Assert.assertEquals(expected, resultList.size());
	}
    
	@Test
	public void testCountAll(){
    	int expected=5;
    	for(int i=0;i<expected;i++){
    		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${serviceI?uncap_first}.save(${myClass.className?uncap_first});
    	}
		long size=${serviceI?uncap_first}.countAll();
		Assert.assertEquals(expected, size);
		
    }
    

}


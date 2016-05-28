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
    public void testSaveOrUpdate(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${serviceI?uncap_first}.saveOrUpdate(${myClass.className?uncap_first});
		long expected=${serviceI?uncap_first}.countAll();
		Assert.assertEquals(expected, 1);
    }
    
    @Test
    public void testSaveOrUpdate2(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
    	${serviceI?uncap_first}.save(${myClass.className?uncap_first});
    	${serviceI?uncap_first}.saveOrUpdate(${myClass.className?uncap_first});
    	
    }
    
    @Test
	public void testMerge(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
    	${serviceI?uncap_first}.save(${myClass.className?uncap_first});
    	${myClass.className} ${myClass.className?uncap_first}2=${serviceI?uncap_first}.merge(${myClass.className?uncap_first});
    	Assert.assertNotNull(${myClass.className?uncap_first}2);
	}
    
    @Test
    public void testMerge2(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
    	${serviceI?uncap_first}.save(${myClass.className?uncap_first});
    	${serviceI?uncap_first}.delete(${myClass.className?uncap_first});
    	${myClass.className} ${myClass.className?uncap_first}2=${serviceI?uncap_first}.merge(${myClass.className?uncap_first});
    	Assert.assertNotNull(${myClass.className?uncap_first}2);
    }
	
	@Test
    public void testDelete(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${serviceI?uncap_first}.save(${myClass.className?uncap_first});
		${serviceI?uncap_first}.delete(${myClass.className?uncap_first});
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
    public void testUpdate(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${serviceI?uncap_first}.save(${myClass.className?uncap_first});
		${serviceI?uncap_first}.update(${myClass.className?uncap_first});
    }
    
    @Test
	public void testGet(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.idField.type} id=${serviceI?uncap_first}.save(${myClass.className?uncap_first});
		${myClass.className} ${myClass.className?uncap_first}2 =${serviceI?uncap_first}.get(id);
		Assert.assertNotNull(${myClass.className?uncap_first}2);
	}
	
	@Test
	public void testLoad(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.idField.type} id=${serviceI?uncap_first}.save(${myClass.className?uncap_first});
		${myClass.className} ${myClass.className?uncap_first}2 =${serviceI?uncap_first}.load(id);
		Assert.assertNotNull(${myClass.className?uncap_first}2);
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


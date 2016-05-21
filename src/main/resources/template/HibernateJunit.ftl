package ${servicePackage};
import ${pojoPackage}.${myClass.className};
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;
public class test${myClass.className}Service {
	private I${myClass.className}Service ${myClass.className?uncap_first}Service;
	@Before
    public void setUp() throws Exception {
    	ApplicationContext act=new ClassPathXmlApplicationContext("spring.xml");
		${myClass.className?uncap_first}Service=act.getBean(I${myClass.className}Service.class);
		${myClass.className?uncap_first}Service.deleteAll();
    }
    
    @Test
	public void testSave(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
		int expected=${myClass.className?uncap_first}Service.countAll();
		Assert.assertEquals(expected, 1);
	}
	
	@Test
    public void testSaveOrUpdate(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.className?uncap_first}Service.saveOrUpdate(${myClass.className?uncap_first});
		int expected=${myClass.className?uncap_first}Service.countAll();
		Assert.assertEquals(expected, 1);
    }
    
    @Test
    public void testSaveOrUpdate2(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
    	${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
    	${myClass.className?uncap_first}Service.saveOrUpdate(${myClass.className?uncap_first});
    	
    }
    
    @Test
	public void testMerge(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
    	${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
    	${myClass.className} ${myClass.className?uncap_first}2=${myClass.className?uncap_first}Service.merge(${myClass.className?uncap_first});
    	Assert.assertNotNull(${myClass.className?uncap_first}2);
	}
    
    @Test
    public void testMerge2(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
    	${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
    	${myClass.className?uncap_first}Service.delete(${myClass.className?uncap_first});
    	${myClass.className} ${myClass.className?uncap_first}2=${myClass.className?uncap_first}Service.merge(${myClass.className?uncap_first});
    	Assert.assertNotNull(${myClass.className?uncap_first}2);
    }
	
	@Test
	public void testInsertByPojo(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		int size=${myClass.className?uncap_first}Service.insertByPojo(${myClass.className?uncap_first});
		Assert.assertEquals(1, size);
	}
	
	@Test
	public void testInsertBatch(){
		int expected=5;
		List<${myClass.className}> list=new  ArrayList<${myClass.className}>();
    	for(int i=0;i<expected;i++){
    			${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			list.add(${myClass.className?uncap_first});
    	}
    	int size=${myClass.className?uncap_first}Service.insertBatch(list);
		Assert.assertEquals(expected, size);
	}
	
	@Test
    public void testDelete(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
		${myClass.className?uncap_first}Service.delete(${myClass.className?uncap_first});
    }
    @Test
    public void testDeleteAll(){
		int expected=5;
    	for(int i=0;i<expected;i++){
    		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
    	}
    	int size=${myClass.className?uncap_first}Service.deleteAll();
		Assert.assertEquals(expected, size);
	}
	@Test
	public void testDeleteByPojo(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
		int size=${myClass.className?uncap_first}Service.deleteByPojo(${myClass.className?uncap_first});
		Assert.assertEquals(1, size);
    }
	@Test
    public void testUpdate(){
    	${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
		${myClass.className?uncap_first}Service.update(${myClass.className?uncap_first});
    }
    
    @Test
	public void testGet(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.idField.type} id=${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
		${myClass.className} ${myClass.className?uncap_first}2 =${myClass.className?uncap_first}Service.get(id);
		Assert.assertNotNull(${myClass.className?uncap_first}2);
	}
	
	@Test
	public void testLoad(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		${myClass.idField.type} id=${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
		${myClass.className} ${myClass.className?uncap_first}2 =${myClass.className?uncap_first}Service.load(id);
		Assert.assertNotNull(${myClass.className?uncap_first}2);
	}
    @Test
	public void testFindAll(){
    	int expected=5;
    	for(int i=0;i<expected;i++){
    		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
    	}
		int size=${myClass.className?uncap_first}Service.findAll().size();
		Assert.assertEquals(expected, size);
		
    }
    
	@Test
	public void testCountAll(){
    	int expected=5;
    	for(int i=0;i<expected;i++){
    		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
			${myClass.className?uncap_first}Service.save(${myClass.className?uncap_first});
    	}
		int size=${myClass.className?uncap_first}Service.countAll();
		Assert.assertEquals(expected, size);
		
    }

}


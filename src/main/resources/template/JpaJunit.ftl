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
		${serviceI?uncap_first}.deleteAllInBatch();
    }
    
    @Test
	public void testTransactional(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		try {
			${serviceI?uncap_first}.testTransactional(${myClass.className?uncap_first},true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long actual=${serviceI?uncap_first}.count();
		Assert.assertEquals(0, actual);
		
	}
	@Test
	public void testTransactional2(){
		${myClass.className} ${myClass.className?uncap_first} =new ${myClass.className}();
		try {
			${serviceI?uncap_first}.testTransactional(${myClass.className?uncap_first},false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long actual=${serviceI?uncap_first}.count();
		Assert.assertEquals(1, actual);
		
	}
	

}


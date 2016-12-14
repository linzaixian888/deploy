package ${controllerPackage};
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "${springPath}",  
"${springMVCPath}"}) 
public class ${controllerName}ControllerTest  {
	@Autowired
	private WebApplicationContext webApplicationContext;
	private ${controllerName}Controller ${controllerName?uncap_first}Controller;
	private MockMvc mockMvc;
	@Before
    public void setUp() throws Exception {
		 mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	@Test
	public void test() throws Exception{
		 mockMvc.perform(MockMvcRequestBuilders.post("/${controllerName?uncap_first}/testJSON")).andExpect(MockMvcResultMatchers.status().isOk());
	}

}


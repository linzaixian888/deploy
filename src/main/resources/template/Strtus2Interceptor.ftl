package ${interceptorPackage};

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ${interceptorClassName} extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String actionClassName = invocation.getAction().getClass().getName();
		String actionMethodName = invocation.getProxy().getMethod();
		System.out.println("class:"+actionClassName+"----method:"+actionMethodName);
		return invocation.invoke();
	}
	
}

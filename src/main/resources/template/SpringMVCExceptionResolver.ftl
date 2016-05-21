package ${interceptorPackage};

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionResolver implements HandlerExceptionResolver{
	private String errorView;
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, java.lang.Exception ex) {
		ModelAndView view=new ModelAndView("/error");
		StringPrintWriter spw=new StringPrintWriter();
		ex.printStackTrace(spw);
		view.addObject("exceptionStack",spw.getString());
		return view;
	}
	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}
}
class StringPrintWriter extends PrintWriter{
	public StringPrintWriter(){  
		 super(new StringWriter());  
    }  
	public StringPrintWriter(int initialSize) {  
        super(new StringWriter(initialSize));  
  }  
   
  public String getString() {  
        flush();  
        return ((StringWriter) this.out).toString();  
  }  
   
  @Override  
  public String toString() {  
      return getString();  
  }  
}

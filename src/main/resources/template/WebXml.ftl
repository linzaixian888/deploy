<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
	<#if dataPool = "druid">
	<servlet>
		<servlet-name>DruidStatView</servlet-name>
		<servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
		<init-param>
	        <param-name>allow</param-name><!--允许的ip,逗号分隔,不支持ipv6-->
	        <param-value></param-value>
    	</init-param>
	    <init-param>
	        <param-name>deny</param-name><!--禁止的ip,逗号分隔,deny优先于allow,不支持ipv6-->
	        <param-value></param-value>
	    </init-param>
	    <init-param>
	        <param-name>resetEnable</param-name><!--不允许允许清空统计数据 -->
	        <param-value>false</param-value>
    	</init-param>
    	<init-param>
		    <param-name>loginUsername</param-name>  <!-- 用户名 -->  
		    <param-value>${username}</param-value>  
		</init-param>  
		<init-param>  
		    <param-name>loginPassword</param-name>   <!-- 密码 -->  
		    <param-value>${password!}</param-value>  
		 </init-param>  
	</servlet>
	<servlet-mapping>
		<servlet-name>DruidStatView</servlet-name>
		<url-pattern>/druid/*</url-pattern>
	</servlet-mapping>
	<filter>
	    <filter-name>DruidWebStatFilter</filter-name>
	    <filter-class>com.alibaba.druid.support.http.WebStatFilter</filter-class>
	    <init-param>
	        <param-name>exclusions</param-name>
	        <param-value>*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*</param-value>
	    </init-param>
	    <init-param>
	        <param-name>sessionStatMaxCount</param-name>
	        <param-value>1000</param-value>
	    </init-param>
	    <init-param>
	        <param-name>sessionStatEnable</param-name><!--打开session统计功能-->
	        <param-value>true</param-value>
	    </init-param>
	    <init-param>
		    <param-name>profileEnable</param-name>
		    <param-value>true</param-value>
		</init-param>
	</filter>
	  <filter-mapping>
		    <filter-name>DruidWebStatFilter</filter-name>
		    <url-pattern>/*</url-pattern>
	  </filter-mapping>
	</#if>
	<#if springmvc??> 
	<filter>  
      <filter-name>CharacterEncodingFilter</filter-name>  
      <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>  
     <init-param>  
          <param-name>encoding</param-name>  
          <param-value>utf-8</param-value> 
      </init-param>  
  </filter>  
  <filter-mapping>  
     <filter-name>CharacterEncodingFilter</filter-name>  
     <url-pattern>*.${controllerSuffix}</url-pattern>  
 </filter-mapping>  
	</#if>
	
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>${springPath}</param-value>
	</context-param>
	
	<!-- Creates the Spring Container shared by all Servlets and Filters -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	
	<#if springmvc??>
	<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>${springMVCPath}</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>	
	</servlet>
	
	<servlet-mapping>
		<servlet-name>appServlet</servlet-name>
		<url-pattern>*.${controllerSuffix}</url-pattern>
	</servlet-mapping>
	</#if>
	<#if hibernate??>
		<!-- 解决Hibernate延迟加载问题过滤器，需放在struts2过滤器之前 -->
	<filter>
		<filter-name>openSessionInViewFilter</filter-name>
		<filter-class>
			org.springframework.orm.hibernate5.support.OpenSessionInViewFilter
		</filter-class>
		<init-param>
			<param-name>sessionFactoryBeanName</param-name>
			<param-value>sessionFactory</param-value>
		</init-param>
		<init-param>
			<param-name>singleSession</param-name>
			<param-value>true</param-value>
		</init-param>
		<init-param>
			<param-name>flushMode</param-name>
			<param-value>AUTO</param-value>
		</init-param>
	</filter>
	<filter-mapping>
        <filter-name>openSessionInViewFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
	</#if>
	<#if struts2??>
	<filter>
        <filter-name>struts2</filter-name>
        <filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
    </filter>

    <filter-mapping>
        <filter-name>struts2</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
	</#if>
	<display-name>${projectName}</display-name>
	<welcome-file-list>
		<welcome-file>index.html</welcome-file>
		<welcome-file>index.htm</welcome-file>
		<welcome-file>index.jsp</welcome-file>
		<welcome-file>index.ftl</welcome-file>
		<welcome-file>default.html</welcome-file>
		<welcome-file>default.htm</welcome-file>
		<welcome-file>default.jsp</welcome-file>
	</welcome-file-list>
</web-app>

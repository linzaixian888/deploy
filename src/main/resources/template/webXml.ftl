<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">
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
	
	<#if spring??>
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>${springPath}</param-value>
	</context-param>
	
	<!-- Creates the Spring Container shared by all Servlets and Filters -->
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	</#if>
	
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
			org.springframework.orm.hibernate4.support.OpenSessionInViewFilter
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

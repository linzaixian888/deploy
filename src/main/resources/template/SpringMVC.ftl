<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xmlns:mvc="http://www.springframework.org/schema/mvc"
	xmlns:context="http://www.springframework.org/schema/context" 
	xmlns:tx="http://www.springframework.org/schema/tx"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
						http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-3.0.xsd	
						http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-3.0.xsd
						http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
						http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.2.xsd ">

	<!-- annotation默认的方法映射适配器 -->
	<!--<bean id="handlerMapping"
		class="org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping" />
	 -->
	<bean
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.JstlView" />
	</bean>

	<bean id="freemarkerConfiguration"  
        class="org.springframework.beans.factory.config.PropertiesFactoryBean">  
        <property name="location" value="classpath:freemarker.properties" />  
    </bean>
	<!-- 配置freeMarker的模板 -->
	<bean id="freemarkerConfig"
		class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
		<property name="templateLoaderPath" value="/" />
		<property name="freemarkerSettings" ref="freemarkerConfiguration" />
		<property name="freemarkerVariables">
			<map>
				<entry key="xml_escape" value-ref="fmXmlEscape" />
			</map>
		</property>
	</bean>

	<bean id="fmXmlEscape" class="freemarker.template.utility.XmlEscape" />
	<!-- springmvc 拦截器 -->
	<mvc:interceptors>
		<bean class="${interceptorPackage}.${interceptorClassName}"></bean>
	</mvc:interceptors>
	<!-- Enables the Spring MVC @Controller programming model -->
	<mvc:annotation-driven />


	<!-- Resolves views selected for rendering by @Controllers to .jsp resources 
		in the /WEB-INF/views directory -->
	<bean id="viewResolver"
		class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.freemarker.FreeMarkerView" />
		<!-- <property name="viewNames" value="*.ftl" /> -->
		<property name="contentType" value="text/html; charset=utf-8" />
		<property name="cache" value="true" />
		<property name="prefix" value="${prefix}" />
		<property name="suffix" value="${suffix}" />
		<property name="order" value="2" />
		<property name="requestContextAttribute" value="request"/>
	</bean>

	<!-- 处理异常的类 -->
	<bean id="exceptionResolver" class="${interceptorPackage}.ExceptionResolver">
		<property name="errorView" value="${errorView}" />
	</bean>    
	<!-- Imports user-defined @Controller beans that process client requests -->
	<import resource="${springScanPath}" />

</beans>
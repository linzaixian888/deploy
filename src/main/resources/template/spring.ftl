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
	
	<#if hibernate??>
	<context:component-scan base-package="${mapperPackage}" />
	</#if>
	<!-- 扫描src/com下的services包 -->
	<context:component-scan base-package="${servicePackage}" />
	
	<context:annotation-config/>
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<property name="driverClassName" value="${driver}" />
		<property name="url" value="${url}" />
		<property name="username" value="${username}" />
		<property name="password" value="${password}" />
		<property name="maxActive" value="100"></property>
		<property name="maxIdle" value="30"></property>
		<property name="maxWait" value="500"></property>
		<property name="testOnBorrow" value="true"></property>
		<property name="validationQuery" value="select 1"> </property>
	</bean>
	<#if hibernate??>
			<bean id="sessionFactory"
				class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
				<property name="dataSource" ref="dataSource"></property>
				<property name="packagesToScan">
					<list>
						<value>${pojoPackage}</value>
					</list>
				</property>
				<property name="hibernateProperties">
					<props>
						<prop key="hibernate.format_sql">${format_sql}</prop>
						<prop key="hibernate.current_session_context_class">org.springframework.orm.hibernate4.SpringSessionContext
						</prop>
						<prop key="hibernate.hbm2ddl.auto">${hbm2ddl}</prop>
						<prop key="hibernate.dialect">${dialect}</prop>
						<prop key="hibernate.show_sql">${show_sql}</prop>
					</props>
				</property>
			</bean>
			<bean id="transactionManager"
				class="org.springframework.orm.hibernate4.HibernateTransactionManager">
				<property name="sessionFactory" ref="sessionFactory"></property>
			</bean>
	</#if>
	<#if mybatis??>
		<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
			<property name="configLocation" value="${mybatisPath}"></property>
			<property name="mapperLocations" value="classpath:${mapperXMLPath}/*.xml" />
			<property name="dataSource" ref="dataSource" />
		</bean>
		${r"<!--加载Mapper -->"}
		<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
			<property name="basePackage" value="${mapperPackage}" />
		</bean>
		<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
			<property name="dataSource" ref="dataSource" />
		</bean>
	</#if>
	
	
	<!-- <tx:annotation-driven transaction-manager="transactionManager"/>  -->
	<!-- 配置事务传播特性 -->
	<tx:advice id="txAdvice" transaction-manager="transactionManager">
		<tx:attributes>
			<tx:method name="insert*" propagation="REQUIRED" />
			<tx:method name="save*" propagation="REQUIRED" />
			<tx:method name="del*" propagation="REQUIRED" />
			<tx:method name="update*" propagation="REQUIRED" />
			<tx:method name="add*" propagation="REQUIRED" />
			<tx:method name="find*" propagation="REQUIRED" read-only="true" />
			<tx:method name="get*" propagation="REQUIRED" read-only="true" />
			<tx:method name="count*" propagation="REQUIRED" read-only="true" />
			<tx:method name="query*" propagation="REQUIRED" read-only="true" />
			<tx:method name="load*" propagation="REQUIRED" read-only="true" />
			<tx:method name="*" propagation="REQUIRED" />
		</tx:attributes>
	</tx:advice>
	<aop:config>
		<aop:pointcut expression="execution(* ${servicePackage}..*.*(..))" id="serviceMethod"/>
		<aop:advisor advice-ref="txAdvice" pointcut-ref="serviceMethod"/>
	</aop:config>
	<#if type == "bs"&&springmvc??>
	${r"<!--用来处理文件上传的-->"}
	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<property name="defaultEncoding" value="utf-8" />
	</bean>
	</#if>
	<#if type == "bs"&&struts2??>
	${r"<!-- 配置freemarkerManager -->"}
	<bean id="freemarkerManager" class="org.apache.struts2.views.freemarker.FreemarkerManager" />
	</#if>
</beans>
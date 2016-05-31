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
	<context:component-scan base-package="${daoPackage}" />
	</#if>
	<!-- 扫描src/com下的services包 -->
	<context:component-scan base-package="${servicePackage}" />
	
	<context:annotation-config/>
	<#if dataPool == "dbcp">
	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
		<property name="driverClassName" value="${driver}" />
		<property name="url" value="${url}" />
		<property name="username" value="${username}" />
		<property name="password" value="${password}" />
		<property name="initialSize" value="5"></property><!--initialSize: 初始化连接-->  
		<property name="minIdle" value="5"></property><!--minIdle: 最小空闲连接-->  
		<property name="maxIdle" value="15"></property><!--maxIdle: 最大空闲连接-->  
		<property name="maxActive" value="15"></property><!--maxActive: 最大连接数量-->  
		<property name="maxWait" value="3000"></property><!--maxWait: 超时等待时间以毫秒为单位 3000毫秒/1000等于3秒-->  
		<property name="testOnBorrow" value="true"></property>
		<property name="validationQuery" value="select 1"> </property>
	</bean>
	<#elseif dataPool == "druid">
	<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close"> 
	      <!-- 基本属性 url、user、password -->
	      <property name="url" value="jdbc:mysql://localhost:3306/test" />
	      <property name="username" value="root" />
	      <property name="password" value="123456" />
	      <!-- 配置初始化大小、最小、最大 -->
	      <property name="initialSize" value="5" />
	      <property name="minIdle" value="5" /> 
	      <property name="maxActive" value="20" />
	      <!-- 配置获取连接等待超时的时间 -->
	      <property name="maxWait" value="60000" />
	      <!-- 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒 -->
	      <property name="timeBetweenEvictionRunsMillis" value="60000" />
	      <!-- 配置一个连接在池中最小生存的时间，单位是毫秒 -->
	      <property name="minEvictableIdleTimeMillis" value="300000" />
	      <property name="validationQuery" value="SELECT 1" />
	      <property name="testWhileIdle" value="true" />
	      <property name="testOnBorrow" value="false" />
	      <property name="testOnReturn" value="false" />
	      <!-- 打开PSCache，并且指定每个连接上PSCache的大小 -->
	      <property name="poolPreparedStatements" value="false" />
	      <property name="maxPoolPreparedStatementPerConnectionSize" value="20" />
	
	      <!-- 配置监控统计拦截的filters -->
	      <property name="filters" value="wall,stat" /> <!--wall在前面,拦截时间不算在统计里面-->
 	</bean>
 	</#if>
	<#if hibernate??>
		${r"<!-- hibernate4专用 -->"}
		<!--	<bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean">
				<property name="dataSource" ref="dataSource"></property>
				<property name="packagesToScan">
					<list>
						<value>${pojoPackage}</value>
					</list>
				</property>
				<property name="hibernateProperties">
					<props>
						<prop key="hibernate.format_sql">${format_sql}</prop>
						<prop key="hibernate.current_session_context_class">org.springframework.orm.hibernate4.SpringSessionContext</prop>
						<prop key="hibernate.hbm2ddl.auto">${hbm2ddl}</prop>
						<prop key="hibernate.dialect">${dialect}</prop>
						<prop key="hibernate.show_sql">${show_sql}</prop>
					</props>
				</property>
			</bean>
			<bean id="transactionManager" class="org.springframework.orm.hibernate4.HibernateTransactionManager">
				<property name="sessionFactory" ref="sessionFactory"></property>
			</bean>
			-->
			${r"<!-- hibernate5专用 -->"}
			<bean id="sessionFactory" class="org.springframework.orm.hibernate5.LocalSessionFactoryBean">
				<property name="dataSource" ref="dataSource"></property>
				<property name="packagesToScan">
					<list>
						<value>${pojoPackage}</value>
					</list>
				</property>
				<property name="hibernateProperties">
					<props>
						<prop key="hibernate.format_sql">${format_sql}</prop>
						<prop key="hibernate.transaction.coordinator_class">jdbc</prop>
						<prop key="hibernate.hbm2ddl.auto">${hbm2ddl}</prop>
						<prop key="hibernate.dialect">${dialect}</prop>
						<prop key="hibernate.show_sql">${show_sql}</prop>
					</props>
				</property>
			</bean>
			<bean id="transactionManager" class="org.springframework.orm.hibernate5.HibernateTransactionManager">
				<property name="sessionFactory" ref="sessionFactory"></property>
			</bean>
	</#if>
	<#if mybatis??>
		<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
			<property name="configLocation" value="${mybatisPath}"></property>
			<property name="mapperLocations" value="classpath:${daoXMLPath}/*.xml" />
			<property name="dataSource" ref="dataSource" />
		</bean>
		${r"<!--加载Mapper -->"}
		<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
			<property name="basePackage" value="${daoPackage}" />
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
			<tx:method name="*" propagation="REQUIRED" read-only="true" />
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
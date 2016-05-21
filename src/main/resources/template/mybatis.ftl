<?xml version="1.0" encoding="UTF-8" ?> 
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
<typeAliases>
<#list myClasses as item>
	<typeAlias alias="${item.className}" type="${pojoPackage}.${item.className}"/>
</#list>
</typeAliases>
<plugins>
	<plugin interceptor="com.linzaixian.mybatis.plugin.PagingPlugin">
		<property name="dialect" value="mysql"/>
		<property name="showSql" value="${show_sql}"/>
	</plugin>
</plugins>
</configuration> 
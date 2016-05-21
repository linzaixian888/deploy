<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE struts PUBLIC
	"-//Apache Software Foundation//DTD Struts Configuration 2.3//EN"
	"http://struts.apache.org/dtds/struts-2.3.dtd">

<struts>
	<!-- 配置使用Spring管理Action -->
	<constant name="struts.objectFactory" value="spring" />
	<!-- 是否显示详细错误信息 -->
	<constant name="struts.devMode" value="true" />
	<!-- 国际化资源文件名称 -->
	<constant name="struts.custom.i18n.resources" value="i18n" />
	<!-- 是否自动加载国际化资源文件 -->
	<constant name="struts.i18n.reload" value="false" />
	<!-- 配置文件重新加载 -->
	<constant name="struts.configuration.xml.reload" value="true" />
	<!-- 动态方法是否启用 -->
	<constant name="struts.enable.DynamicMethodInvocation" value="false" />
	<!-- convention类重新加载 -->
	<constant name="struts.convention.classes.reload" value="true" />
	<!-- 浏览器是否缓存静态内容 -->
	<constant name="struts.serve.static.browserCache" value="false" />
	<!-- 上传文件大小限制设置 -->
	<constant name="struts.multipart.maxSize" value="-1" />
	<!-- 主题 -->
	<constant name="struts.ui.theme" value="simple" />
	<!-- 编码 -->
	<constant name="struts.i18n.encoding" value="UTF-8" />
	<!-- 后缀 -->
	<constant name="struts.action.extension" value="${controllerSuffix}" />
	<!-- 是否不扫描类 -->
	<constant name="struts.convention.action.disableScanning"
		value="false" />
	<!-- 默认返回的结果类型 -->
	<constant name="struts.convention.relative.result.types"
		value="freemarker,jsp" />
	<!-- 扫描的action类后缀 -->
	<constant name="struts.convention.action.suffix" value="Action" />
	<!-- 结果资源的路径 -->
	<constant name="struts.convention.result.path" value="${prefix}" />
	<!-- URL资源分隔符 -->
	<constant name="struts.convention.action.name.separator"
		value="_" />
	<!-- 扫描的action包以及子包 -->
	<constant name="struts.convention.action.packages"
		value="${controllerPackage}" />
	<#if 1!=1><#--下面的注释掉-->
	<!-- 设置Convention插件是否从jar包中搜索Action类 -->
	<constant name="struts.convention.action.disableJarScanning"
		value="true" />
	<!-- 设置Convention插件需要搜索的jar包 -->
	<constant name="struts.convention.action.includeJars"
		value=".*/BDCore.*jar(!/)?,.*/BDSystem.*jar(!/)?,.*/BDMenu.*jar(!/)?,.*/BDBsmon.*jar(!/)?" />
	</#if>
	<package name="default"  extends="json-default">
		<interceptors>  
			<interceptor name="${interceptorClassName?cap_first}" class="${interceptorPackage}.${interceptorClassName}"/> 
			<interceptor-stack name="myStack">
				<interceptor-ref name="${interceptorClassName?cap_first}" />
				<interceptor-ref name="defaultStack" />
			</interceptor-stack>
  	   </interceptors> 	   
		<default-interceptor-ref name="myStack"></default-interceptor-ref>
		<global-results>
			<result name="error" type="freemarker">${prefix}${errorView}${suffix}</result>
			<result name="json" type="json">
				<param name="root">result</param>
			</result>
		</global-results>
		<global-exception-mappings>
			<exception-mapping result="error" exception="java.lang.Exception" /> 
		</global-exception-mappings>
	</package>
	<!-- Add packages here -->
	
</struts>

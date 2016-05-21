<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "mybatis-3-mapper.dtd" >
<mapper namespace="${mapperPackage}.${myClass.className}Mapper" >
	<resultMap type="${myClass.className}" id="${myClass.className}">
		${r"<"}id property="${myClass.idField.name}" column="${myClass.idField.columnName}"/${r">"}
			<#list noIDFieldList as item>
		${r"<"}result property="${item.name}" column="${item.columnName}" /${r">"}
			</#list>
	</resultMap>
  <@insert sqlType="${sqlType}" />
  <insert id="insertBatch" parameterType="${myClass.className}">
   	INSERT INTO ${myClass.tableName}${insertBatchKeySql} VALUES
   	<foreach item="item" index="index" collection="list" separator="," >
    	${insertBatchValueSql}
    </foreach>
  </insert>
  <delete id="deleteById" parameterType="${myClass.idField.type}">
  	  DELETE FROM ${myClass.tableName} WHERE ${myClass.idField.columnName} = ${r"#"}{${myClass.idField.name}}
  </delete>
   <delete id="deleteByIds" parameterType="${myClass.idField.type}">
  	  DELETE FROM ${myClass.tableName} WHERE ${myClass.idField.columnName} IN
      <foreach item="item" index="index" collection="array" open="("  separator="," close=")" >
    	${r"#"}{item}
      </foreach>
  </delete>
   <delete id="deleteBatch" parameterType="${myClass.className}">
  	  DELETE FROM ${myClass.tableName} WHERE ${myClass.idField.columnName} IN
      <foreach item="item" index="index" collection="list" open="("  separator="," close=")" >
    	${r"#"}{item.${myClass.idField.name}}
      </foreach>
  </delete>
   <delete id="deleteAll">
  	  DELETE FROM ${myClass.tableName} 
  </delete>
  <delete id="deleteByPojo" parameterType="${myClass.className}">
  	  DELETE FROM ${myClass.tableName} 
  	  <where>
  	 	<#list findByPojoSql as item>
      		${item}
      	</#list>
  	 </where>
  </delete>
  <update id="update" parameterType="${myClass.className}">
      	UPDATE ${myClass.tableName} 
      	<set>
      	<#list updateSql as item>
      		${item}
      	</#list>
      	</set>
      	WHERE ${myClass.idField.columnName} =${r"#"}{${myClass.idField.name}}
  </update>
  <select id="findAll" resultType="${myClass.className}">
  	 SELECT * FROM ${myClass.tableName}
  </select>
  <select id="findByPojo" resultType="${myClass.className}">
  	 SELECT * FROM ${myClass.tableName}
  	 <where>
  	 	<#list findByPojoSql as item>
      		${item}
      	</#list>
  	 </where>
  </select>
  <select id="findById" resultType="${myClass.className}">
  	SELECT * FROM ${myClass.tableName} WHERE ${myClass.idField.columnName} = ${r"#"}{${myClass.idField.name}}
  </select>
  <select id="findByIds" resultType="${myClass.className}">
  	SELECT * FROM ${myClass.tableName}
  	WHERE ${myClass.idField.columnName} IN
  	<foreach item="item" index="index" collection="array" open="("  separator="," close=")"> ${r"#"}{item}</foreach>
  </select>
  <select id="countAll" resultType="int" >
     SELECT count(*) as total FROM ${myClass.tableName}
  </select>
  <select id="countByPojo" resultType="int">
  	 SELECT count(*) FROM ${myClass.tableName}
  	 <where>
  	 	<#list findByPojoSql as item>
      		${item}
      	</#list>
  	 </where>
  </select>
</mapper>



<#macro insert sqlType="">
	<#if sqlType=="mysql">
		<insert id="insert" parameterType="${myClass.className}" useGeneratedKeys="true" keyProperty="${myClass.idField.name}">
	   		INSERT INTO ${myClass.tableName}${insertSql}
	 	 </insert>
	<#else>
		<insert id="insert" parameterType="${myClass.className}" >
	   		INSERT INTO ${myClass.tableName}${insertSql}
	 	 </insert>
	</#if>
</#macro>

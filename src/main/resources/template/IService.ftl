package ${servicePackage};

import ${pojoPackage}.${myClass.className};
<#if mybatis??>
public interface I${myClass.className}Service extends IBaseService<${myClass.className}>{

}
</#if>
<#if hibernate??>
public interface I${myClass.className}Service extends IBaseService<${myClass.className},${myClass.idField.type}>{

}
</#if>
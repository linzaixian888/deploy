package ${pojoPackage};
<#if myClass.childFields?size!=0>
import java.util.List;
</#if>
import java.util.Date;
/**
 * ${myClass.remark}
 * @author lzx
 *
 */
public class ${myClass.className}{
	/**
	 * ${myClass.idField.remark}
	 */
	private ${myClass.idField.type} ${myClass.idField.name};
	<#list myClass.fields as item> 
	/**
	 * ${item.remark}
	 */
	private ${item.type} ${item.name};
	</#list>
	<#list myClass.parentFields as item> 
	private ${item.type} ${item.name};
	</#list>
	<#list myClass.childFields as item> 
	private List<${item.type}> ${item.name};
	</#list>
	public void set${myClass.idField.name?cap_first}(${myClass.idField.type} ${myClass.idField.name}){
		this.${myClass.idField.name} = ${myClass.idField.name};
	}
	public ${myClass.idField.type} get${myClass.idField.name?cap_first}(){
		return this.${myClass.idField.name};
	}
	<#list myClass.fields as item> 
	public void set${item.name?cap_first}(${item.type} ${item.name}){
		this.${item.name} = ${item.name};
	}
	public ${item.type} get${item.name?cap_first}(){
		return this.${item.name};
	}
	</#list>
	<#list myClass.parentFields as item> 
	public void set${item.name?cap_first}(${item.type} ${item.name}){
		this.${item.name} = ${item.name};
	}
	public ${item.type} get${item.name?cap_first}(){
		return this.${item.name};
	}
	</#list>
	<#list myClass.childFields as item> 
	public void set${item.name?cap_first}(List<${item.type}> ${item.name}){
		this.${item.name} = ${item.name};
	}
	public List<${item.type}> get${item.name?cap_first}(){
		return this.${item.name};
	}
	</#list>	

}

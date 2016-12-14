package ${pojoPackage};
<#if myClass.childFields?size!=0>
import java.util.List;
</#if>
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import javax.persistence.GenerationType;
import ${pojoPackage}.base.BasePojo;
/**
 * ${myClass.remark}
 * @author lzx
 *
 */
@Entity
@Table(name="${myClass.tableName}")
public class ${myClass.className} extends BasePojo{
	<#if myClass.idField??>
	/**
	 * ${myClass.idField.remark}
	 */
		<#if sqlType=="postgresql">
	@Id
	@GeneratedValue(generator = "nativeGenerator")    
	@GenericGenerator(name = "nativeGenerator", strategy = "native" ,parameters =  { @Parameter(name = "sequence", value = "${myClass.tableName}_${myClass.idField.columnName}_seq") })
	@Column(name = "${myClass.idField.columnName}")
		<#else>
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "${myClass.idField.columnName}")
		</#if>
	private ${myClass.idField.type} ${myClass.idField.name};
	</#if>
	
	<#list myClass.fields as item> 
	/**
	 * ${item.remark}
	 */
	@Column(name = "${item.columnName}")
	private ${item.type} ${item.name};
	</#list>
	<#list myClass.parentFields as item> 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "${item.columnName}",insertable=false,updatable=false)
	private ${item.type} ${item.name};
	</#list>
	<#list myClass.childFields as item> 
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="${item.columnName}",insertable=false,updatable=false)
	private List<${item.type}> ${item.name};
	</#list>
	<#if myClass.idField??>
	public void set${myClass.idField.name?cap_first}(${myClass.idField.type} ${myClass.idField.name}){
		this.${myClass.idField.name} = ${myClass.idField.name};
	}
	public ${myClass.idField.type} get${myClass.idField.name?cap_first}(){
		return this.${myClass.idField.name};
	}
	</#if>
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

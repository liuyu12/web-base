package cn.edu.ahtcm.codegenerator.generator;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import cn.edu.ahtcm.webcommon.service.RedisService;
import cn.edu.ahtcm.webcommon.util.SpringContextUtil;
import com.google.common.base.Splitter;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.internal.util.StringUtility;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * @author liuyu
 */
public class MapperPlugin extends PluginAdapter {
    private Set<String> mappers = new HashSet<String>();
    private int type;
    private String author;
    private boolean validate;
    private int mapperType;

    /**
     * redis服务
     */
    private RedisService redisService;

    @Override
    public void setContext(Context context) {
        super.setContext(context);
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        this.type = Integer.valueOf(this.properties.getProperty("type"));
        this.author = this.properties.getProperty("author");
        this.validate = Boolean.valueOf(this.properties.getProperty("validate"));
        this.mapperType = Integer.valueOf(this.properties.getProperty("mapperType"));
        String mappers = this.properties.getProperty("mappers");
        if (StringUtility.stringHasValue(mappers)) {
            Splitter splitter = Splitter.on(",");
            for (String mapper : splitter.splitToList(mappers)) {
                this.mappers.add(mapper);
            }
        }

    }

    /**
     * 生成实体类
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // 记录主键信息
        if(redisService == null){
            redisService = SpringContextUtil.getBean(RedisService.class);
        }
        String primaryType = introspectedTable.getPrimaryKeyColumns().get(0).getFullyQualifiedJavaType().getShortName();
        redisService.set(SystemConstant.PRIMARY_KEY,primaryType,SystemConstant.PRIMARY_KEY_TIME);

        // 判断是否生成实体类
        if(!(this.type == SystemConstant.TYPE_ENTITY || this.type == SystemConstant.TYPE_BOTH)){
            return false;
        }
        // 生成实体类
        processEntityClass(topLevelClass,introspectedTable);
        return true;
    }

    /**
     * 处理实体类的包和@Table注解
     *
     * @param topLevelClass
     * @param introspectedTable
     */
    private void processEntityClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
        tableName = tableName.substring(tableName.lastIndexOf(".")+1);
        // 作者信息
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * @author " + this.author);
        topLevelClass.addJavaDocLine(" */");
        // 添加Data注解
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addAnnotation("@Data");

        // 添加Entity注解
        topLevelClass.addImportedType("javax.persistence.*");
        topLevelClass.addAnnotation("@Entity");
        // 添加Table注解
        topLevelClass.addAnnotation("@Table(name=\"" + tableName + "\")");
        // 引入校验注解
        if(validate){
            topLevelClass.addImportedType("cn.edu.ahtcm.webcommon.validation.groups.*");
            topLevelClass.addImportedType("javax.validation.constraints.*");
        }
    }



    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    /**
     * xml文件的生成
     * @param sqlMap
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean sqlMapGenerated(GeneratedXmlFile sqlMap, IntrospectedTable introspectedTable) {
        try {
            java.lang.reflect.Field field = sqlMap.getClass().getDeclaredField("isMergeable");
            field.setAccessible(true);
            field.setBoolean(sqlMap, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 生成的Mapper接口
     *
     * @param interfaze
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        //获取实体类
        FullyQualifiedJavaType entityType = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        //import接口
        for (String mapper : mappers) {
            interfaze.addImportedType(new FullyQualifiedJavaType(mapper));
            interfaze.addSuperInterface(new FullyQualifiedJavaType(mapper + "<" + entityType.getShortName() + ">"));
        }
        //import实体类
        interfaze.addImportedType(entityType);
        // 添加注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * @author " + this.author);
        interfaze.addJavaDocLine(" */");
        return true;
    }

    /**
     * 生成带BLOB字段的对象
     *
     * @param topLevelClass
     * @param introspectedTable
     * @return
     */
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        processEntityClass(topLevelClass, introspectedTable);
        return false;
    }

    // 下面所有return false的方法都不生成。这些都是基础的CRUD方法，使用通用Mapper实现

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientSelectAllMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapDeleteByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapInsertElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeySelectiveElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean sqlMapUpdateByPrimaryKeyWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean providerApplyWhereMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean providerInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }

    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return SystemConstant.MAPPER_TKMYBATIS != mapperType;
    }


}

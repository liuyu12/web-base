package cn.edu.ahtcm.codegenerator.service.impl;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import cn.edu.ahtcm.codegenerator.entity.*;
import cn.edu.ahtcm.codegenerator.enums.Constant;
import cn.edu.ahtcm.codegenerator.generator.MapperCommentGenerator;
import cn.edu.ahtcm.codegenerator.service.GenerateService;
import cn.edu.ahtcm.webcommon.exception.ServiceException;
import cn.edu.ahtcm.webcommon.service.RedisService;
import cn.edu.ahtcm.webcommon.util.FileUtil;
import cn.edu.ahtcm.webcommon.util.StringUtil;
import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyu
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class GenerateServiceImpl implements GenerateService {
    @Resource
    private RedisService redisService;
    /**
     *Java文件路径
     */
    private final String JAVA_PATH = "/src/main/java";

    /**
     * 资源文件路径
     */
    private final String RESOURCES_PATH = "/src/main/resources";

    @Override
    public void generController(DataBaseBean dataBaseBean, ProjectBean projectBean, ControllerBean controllerBean){
        // 校验参数避免空指针异常
        Preconditions.checkNotNull(dataBaseBean);
        Preconditions.checkNotNull(projectBean);
        Preconditions.checkNotNull(controllerBean);
        List<String> tables = getTableNames(dataBaseBean.getTables());
        Map data = new HashMap(10);
        String mapperPackage = getPackage(projectBean, Constant.MAPPER_PACKAGER);
        String servicePackage = getPackage(projectBean, Constant.SERVICE_PACKAGE);
        String serviceImplPackage = getPackage(projectBean, Constant.SERVICE_IMPL_PACKAGE);
        String entityPackage = getPackage(projectBean, Constant.ENTITY_PACKAGE);
        String controllerPackage = getPackage(projectBean, Constant.CONTROLLER_PACKAGE);
        data.put("mapperPackage", mapperPackage);
        data.put("entityPackage", entityPackage);
        data.put("servicePackage", servicePackage);
        data.put("serviceImplPackage", serviceImplPackage);
        data.put("controllerPackage", controllerPackage);
        data.put("controllerBean", controllerBean);
        data.put("author", Strings.nullToEmpty(projectBean.getAuthor()));
        for(String table : tables){
            // 获取实体名称
            String entityName = StringUtil.tableNameConvertUpperCamel(table);
            if(dataBaseBean.isAlias()){
                Preconditions.checkNotNull(dataBaseBean.getAliasName());
                entityName = StringUtil.tableNameConvertUpperCamel(dataBaseBean.getAliasName());
            }
            data.put("entityNameUpper", StringUtils.capitalize(entityName));
            data.put("entityNameLower", StringUtils.uncapitalize(entityName));
            data.put(SystemConstant.PRIMARY_KEY, getPrimaryType(dataBaseBean,projectBean));
            // 使用jpaController模板
            File controllerFile = new File(projectBean.getPosition() + JAVA_PATH + StringUtil.packageConvertPath(controllerPackage) + "/" + entityName + "Controller.java");
            if(controllerBean.getMapperType() == SystemConstant.MAPPER_JPA){
                generTemplateFile(controllerFile,data,"jpaController.ftl");
            }
            if(controllerBean.getMapperType() == SystemConstant.MAPPER_TKMYBATIS){
                generTemplateFile(controllerFile,data,"tkController.ftl");
            }
        }
    }

    @Override
    public void generService(DataBaseBean dataBaseBean, ProjectBean projectBean, ServiceBean serviceBean){
        // 校验参数避免空指针异常
        Preconditions.checkNotNull(dataBaseBean);
        Preconditions.checkNotNull(projectBean);
        Preconditions.checkNotNull(serviceBean);
        List<String> tables = getTableNames(dataBaseBean.getTables());
        Map data = new HashMap(10);
        String mapperPackage = getPackage(projectBean, Constant.MAPPER_PACKAGER);
        String servicePackage = getPackage(projectBean, Constant.SERVICE_PACKAGE);
        String serviceImplPackage = getPackage(projectBean, Constant.SERVICE_IMPL_PACKAGE);
        String entityPackage = getPackage(projectBean, Constant.ENTITY_PACKAGE);
        data.put("mapperPackage", mapperPackage);
        data.put("entityPackage", entityPackage);
        data.put("servicePackage", servicePackage);
        data.put("serviceImplPackage", serviceImplPackage);
        data.put("serviceBean", serviceBean);
        data.put("author", Strings.nullToEmpty(projectBean.getAuthor()));
        for(String table : tables){
            // 获取实体名称
            String entityName = StringUtil.tableNameConvertUpperCamel(table);
            if(dataBaseBean.isAlias()){
                Preconditions.checkNotNull(dataBaseBean.getAliasName());
                entityName = StringUtil.tableNameConvertUpperCamel(dataBaseBean.getAliasName());
            }
            data.put("entityNameUpper", StringUtils.capitalize(entityName));
            data.put("entityNameLower", StringUtils.uncapitalize(entityName));
            data.put(SystemConstant.PRIMARY_KEY, getPrimaryType(dataBaseBean,projectBean));
            // 生成主键信息
            data.put(SystemConstant.PRIMARY_KEY, getPrimaryType(dataBaseBean,projectBean));
            // 使用jpaService模板
            File serviceFile = new File(projectBean.getPosition() + JAVA_PATH + StringUtil.packageConvertPath(servicePackage) + "/" + entityName + "Service.java");
            File serviceImplFile = new File(projectBean.getPosition() + JAVA_PATH + StringUtil.packageConvertPath(serviceImplPackage) + "/" + entityName + "ServiceImpl.java");
            if(serviceBean.getMapperType() == SystemConstant.MAPPER_JPA){
                generTemplateFile(serviceFile,data,"jpaService.ftl");
                generTemplateFile(serviceImplFile,data,"jpaServiceImpl.ftl");
            }
            // 使用tkService模板
            if(serviceBean.getMapperType() == SystemConstant.MAPPER_TKMYBATIS){
                generTemplateFile(serviceFile,data,"tkService.ftl");
                generTemplateFile(serviceImplFile,data,"tkServiceImpl.ftl");
            }

        }
    }

    /**
     * 获取主键类型
     * @return
     */
    private String getPrimaryType(DataBaseBean dataBaseBean,ProjectBean projectBean){
        String primaryType = (String) redisService.get(SystemConstant.PRIMARY_KEY);
        if(Strings.isNullOrEmpty(primaryType)){
            // 生成主键信息
            generModelorMapper(dataBaseBean,projectBean,new EntityBean(),new MapperBean(),SystemConstant.TYPE_BOTH_NO);
        }
        return Strings.nullToEmpty((String) redisService.get(SystemConstant.PRIMARY_KEY));
    }

    @Override
    public void generModelorMapper(DataBaseBean dataBaseBean, ProjectBean projectBean, EntityBean entityBean, MapperBean mapperBean, int type) {
        // 校验参数避免空指针异常
        Preconditions.checkNotNull(dataBaseBean);
        Preconditions.checkNotNull(projectBean);
        if(type == SystemConstant.TYPE_ENTITY || type == SystemConstant.TYPE_BOTH){
            Preconditions.checkNotNull(entityBean);
        }
        if(type == SystemConstant.TYPE_MAPPER || type == SystemConstant.TYPE_BOTH){
            Preconditions.checkNotNull(mapperBean);
        }

        Context context = new Context(ModelType.FLAT);
        context.setId("LiuYu");
        context.setTargetRuntime("MyBatis3Simple");
        // 注释器配置
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.addProperty(PropertyRegistry.CONTEXT_BEGINNING_DELIMITER, "`");
        commentGeneratorConfiguration.addProperty(PropertyRegistry.CONTEXT_ENDING_DELIMITER, "`");
        commentGeneratorConfiguration.addProperty("validate",entityBean.getValidate().toString());
        commentGeneratorConfiguration.addProperty("mapperType",mapperBean.getMapperType() + "");
        commentGeneratorConfiguration.setConfigurationType(MapperCommentGenerator.class.getCanonicalName());
        context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

        // 设置自定义插件
        PluginConfiguration pluginConfiguration = new PluginConfiguration();
        pluginConfiguration.setConfigurationType("cn.edu.ahtcm.codegenerator.generator.MapperPlugin");
        // 添加配置参数
        pluginConfiguration.addProperty("type",type + "");
        pluginConfiguration.addProperty("author",projectBean.getAuthor());
        pluginConfiguration.addProperty("validate",entityBean.getValidate().toString());
        pluginConfiguration.addProperty("mapperType",mapperBean.getMapperType() + "");
        // 添加配置参数
        context.addPluginConfiguration(pluginConfiguration);


        // 设置数据库连接
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        if(SystemConstant.DBTYPE_MYSQL.equals(dataBaseBean.getDbType())){
            jdbcConnectionConfiguration.setDriverClass("com.mysql.cj.jdbc.Driver");
            jdbcConnectionConfiguration.setConnectionURL(dataBaseBean.getUrl() + "?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
        }
        jdbcConnectionConfiguration.setUserId(dataBaseBean.getUserId());
        jdbcConnectionConfiguration.setPassword(dataBaseBean.getPassword());
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);

        // 实体类生成配置
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        // 项目路径
        javaModelGeneratorConfiguration.setTargetProject(projectBean.getPosition() + JAVA_PATH);
        // 实体包路径
        javaModelGeneratorConfiguration.setTargetPackage(projectBean.getBasePackage() + "." + projectBean.getModulePackage() + "." + projectBean.getEntityPackage());
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

        // 生成tk-mybatis的mapper
        if(type == SystemConstant.TYPE_MAPPER || type == SystemConstant.TYPE_BOTH){
            // dao.xml配置
            SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
            sqlMapGeneratorConfiguration.setTargetProject(projectBean.getPosition() + RESOURCES_PATH);
            sqlMapGeneratorConfiguration.setTargetPackage(getPackage(projectBean, Constant.MAPPERXML_PACKAGE));
            context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
            // dao.java配置
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
            javaClientGeneratorConfiguration.setTargetProject(projectBean.getPosition() + JAVA_PATH);
            javaClientGeneratorConfiguration.setTargetPackage(getPackage(projectBean, Constant.MAPPER_PACKAGER));
            javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
            if(SystemConstant.MAPPER_TKMYBATIS == mapperBean.getMapperType()){
                pluginConfiguration.addProperty("mappers","cn.edu.ahtcm.webcommon.tkmybatis.Mapper");
            }
        }

        // 表格配置
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        // 主键生成策略
        tableConfiguration.setGeneratedKey(new GeneratedKey("id", "Mysql", true, null));
        context.addTableConfiguration(tableConfiguration);

        List<String> warnings;
        MyBatisGenerator generator;
        Configuration configuration = new Configuration();
        configuration.addContext(context);
        warnings = new ArrayList<>();
        DefaultShellCallback callback = new DefaultShellCallback(true);
        List<String> tables = getTableNames(dataBaseBean.getTables());
        String entityName;
        for(String table : tables){
            // 设置表名
            tableConfiguration.setTableName(table);
            // 设置catalog
            String url = dataBaseBean.getUrl();
            String catalog = url.substring(url.lastIndexOf("/")+1);
            tableConfiguration.setCatalog(catalog);
            entityName = StringUtil.tableNameConvertUpperCamel(table);
            // 设置表别名
            if(dataBaseBean.isAlias()){
                Preconditions.checkArgument(!Strings.isNullOrEmpty(dataBaseBean.getAliasName()),"表别名不能为空");
                entityName = dataBaseBean.getAliasName();
            }
            tableConfiguration.setDomainObjectName(StringUtil.tableNameConvertUpperCamel(entityName));
            try{
                configuration.validate();
                generator = new MyBatisGenerator(configuration, callback, warnings);
                generator.generate(null);
            }catch (Exception e){
                log.error("实体类" + entityName +"和" + entityName + "Mapper生成失败_" + e.getMessage(),e);
                throw new ServiceException(e.getMessage());
            }
            // 生成接口，在实体类之后生成,保存了主键信息
            if(type == SystemConstant.TYPE_MAPPER || type == SystemConstant.TYPE_BOTH){
                if(SystemConstant.MAPPER_JPA == mapperBean.getMapperType()){
                    // 生成Jpa接口
                    generJpaMapper(dataBaseBean,projectBean,mapperBean,entityName);
                }
            }
            // 日志打印
            if(type == SystemConstant.TYPE_MAPPER || type == SystemConstant.TYPE_BOTH){
                if(SystemConstant.MAPPER_JPA != mapperBean.getMapperType()){
                    log.info(entityName + "Mapper.java生成成功");
                    log.info(entityName + "Mapper.xml生成成功");
                }
            }
            if(type == SystemConstant.TYPE_ENTITY || type == SystemConstant.TYPE_BOTH){
                log.info(entityName + ".java生成成功");
            }
        }
    }

    /**
     * 生成japMapper
     * @param projectBean
     * @param mapperBean
     * @param entityName
     */
    private void generJpaMapper(DataBaseBean dataBaseBean ,ProjectBean projectBean,MapperBean mapperBean,String entityName){
        String mapperPackage = getPackage(projectBean, Constant.MAPPER_PACKAGER);
        String entityPackage = getPackage(projectBean, Constant.ENTITY_PACKAGE);
        Map data = new HashMap();
        data.put("mapperPackage", mapperPackage);
        data.put("entityPackage", entityPackage);
        data.put("entityName", entityName);
        data.put("primaryType", getPrimaryType(dataBaseBean,projectBean));
        data.put("author", Strings.nullToEmpty(projectBean.getAuthor()));
        File file = new File(projectBean.getPosition() + JAVA_PATH + StringUtil.packageConvertPath(mapperPackage) + "/" + entityName + "Mapper.java");
        generTemplateFile(file,data,"jpaMapper.ftl");
    }

    /**
     * 根据模板生成文件
     * @param file
     * @param data
     * @param template
     */
    private void generTemplateFile(File file,Map<String,Object> data,String template){
        freemarker.template.Configuration cfg = getConfiguration();
        FileUtil.createParentFile(file);
        FileWriter fileWriter = null;
        try{
            fileWriter = new FileWriter(file);
            cfg.getTemplate(template).process(data, fileWriter);
            log.info(file.getName()+"生成成功");
        }catch (Exception e1){
            log.error(file.getAbsolutePath() + "_" + e1.getMessage(),e1);
            throw new ServiceException(file.getName()+"生成出错");
        }finally {
            try{
                fileWriter.close();
            }catch (Exception e){
                log.error("流关闭失败" + "_" + e.getMessage(),e);
            }
        }
    }


    private String getPackage(ProjectBean projectBean, Constant constant) {
        if (constant == Constant.MAPPER_PACKAGER) {
            return projectBean.getBasePackage() + "." + projectBean.getModulePackage() + "." + projectBean.getMapperPackage();
        }
        if (constant == Constant.MAPPERXML_PACKAGE) {
            return projectBean.getMapperXmlPackage() + "." + projectBean.getModulePackage();
        }
        if (constant == Constant.ENTITY_PACKAGE) {
            return projectBean.getBasePackage() + "." + projectBean.getModulePackage() + "." + projectBean.getEntityPackage();
        }
        if (constant == Constant.SERVICE_PACKAGE) {
            return projectBean.getBasePackage() + "." + projectBean.getModulePackage() + "." + projectBean.getServicePackage();
        }
        if (constant == Constant.SERVICE_IMPL_PACKAGE) {
            return projectBean.getBasePackage() + "." + projectBean.getModulePackage() + "." + projectBean.getServiceImplPackage();
        }
        if (constant == Constant.CONTROLLER_PACKAGE) {
            return projectBean.getBasePackage() + "." + projectBean.getModulePackage() + "." + projectBean.getControllerPackage();
        }
        if (constant == Constant.CORE_PACKAGE) {
            return projectBean.getBasePackage() + "." + "common.core";
        }
        return null;
    }


    /**
     * 配置
     * @return
     * @throws IOException
     */
    private freemarker.template.Configuration getConfiguration(){
        freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_23);
        cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(),"templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
        return cfg;
    }


    /**
     * 将表格字符串转换成list
     * @param str
     * @return 表格
     */
    private List<String> getTableNames(String str){
        List<String> list = new ArrayList<>();
        if(Strings.isNullOrEmpty(str)){
            return list;
        }
        Splitter splitter = Splitter.on(",");
        return splitter.splitToList(str);
    }


}

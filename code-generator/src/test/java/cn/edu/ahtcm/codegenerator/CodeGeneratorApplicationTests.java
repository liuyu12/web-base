package cn.edu.ahtcm.codegenerator;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import cn.edu.ahtcm.codegenerator.entity.*;
import cn.edu.ahtcm.codegenerator.service.GenerateService;
import cn.edu.ahtcm.codegenerator.service.impl.GenerateServiceImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.spi.MDCAdapter;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CodeGeneratorApplicationTests {
//    @Resource
//    private GenerateService service;
//    public void generModelorMapper() throws Exception {
//        GenerateService service =  new GenerateServiceImpl();
//        DataBaseBean dataBaseBean = new DataBaseBean();
//        dataBaseBean.setUserId("root");
//        dataBaseBean.setPassword("root");
//        dataBaseBean.setUrl("jdbc:mysql://127.0.0.1/web_base");
//        dataBaseBean.setTables("user");
//        dataBaseBean.setAlias(true);
//        dataBaseBean.setAliasName("Hello");
//        ProjectBean projectBean =  new ProjectBean();
//        projectBean.setPosition("H:/study/web-base/code-generator");
//        projectBean.setBasePackage("cn.edu.ahtcm.codegenerator");
//        projectBean.setModulePackage("test");
//        projectBean.setEntityPackage("entity");
//        EntityBean entityBean = new EntityBean();
//        entityBean.setValidate(true);
//        service.generModelorMapper(dataBaseBean,projectBean,entityBean,null,1);
//    }
//
//    @Test
//    public void generJpaMapper() throws Exception {
////        GenerateService service =  new GenerateServiceImpl();
//        DataBaseBean dataBaseBean = new DataBaseBean();
//        dataBaseBean.setUserId("root");
//        dataBaseBean.setPassword("root");
//        dataBaseBean.setUrl("jdbc:mysql://127.0.0.1/web_base");
//        dataBaseBean.setTables("role");
//        ProjectBean projectBean =  new ProjectBean();
//        projectBean.setPosition("H:/study/web-base/code-generator");
//        projectBean.setBasePackage("cn.edu.ahtcm.codegenerator");
//        projectBean.setModulePackage("role");
//        projectBean.setEntityPackage("entity");
//        projectBean.setMapperPackage("dao");
//        projectBean.setMapperXmlPackage("mapper");
//        EntityBean entityBean = new EntityBean();
//        entityBean.setValidate(true);
//        projectBean.setMapperPackage("dao");
//        MapperBean mapperBean = new MapperBean();
//        mapperBean.setMapperType(SystemConstant.MAPPER_TKMYBATIS);
//        service.generModelorMapper(dataBaseBean,projectBean,entityBean,mapperBean,SystemConstant.TYPE_BOTH);
//    }
//
//    @Test
//    public void generService() throws Exception {
////        GenerateService service =  new GenerateServiceImpl();
//        DataBaseBean dataBaseBean = new DataBaseBean();
//        dataBaseBean.setUserId("root");
//        dataBaseBean.setPassword("root");
//        dataBaseBean.setUrl("jdbc:mysql://127.0.0.1/web_base");
//        dataBaseBean.setTables("role");
//        ProjectBean projectBean =  new ProjectBean();
//        projectBean.setPosition("H:/study/web-base/code-generator");
//        projectBean.setBasePackage("cn.edu.ahtcm.codegenerator");
//        projectBean.setModulePackage("role");
//        projectBean.setEntityPackage("entity");
//        EntityBean entityBean = new EntityBean();
//        entityBean.setValidate(true);
//        projectBean.setMapperPackage("dao");
//        projectBean.setServicePackage("service");
//        projectBean.setServiceImplPackage("service.impl");
//        MapperBean mapperBean = new MapperBean();
//        mapperBean.setMapperType(SystemConstant.MAPPER_JPA);
//
//        ServiceBean serviceBean = new ServiceBean();
//        serviceBean.setMapperType(SystemConstant.MAPPER_JPA);
//        service.generModelorMapper(dataBaseBean,projectBean,entityBean,mapperBean,SystemConstant.TYPE_BOTH);
//        service.generService(dataBaseBean,projectBean,serviceBean);
//    }
//
//    @Test
//    public void generController() throws Exception {
////        GenerateService service =  new GenerateServiceImpl();
//        DataBaseBean dataBaseBean = new DataBaseBean();
//        dataBaseBean.setUserId("root");
//        dataBaseBean.setPassword("root");
//        dataBaseBean.setUrl("jdbc:mysql://127.0.0.1/web_base");
//        dataBaseBean.setTables("role");
//        ProjectBean projectBean =  new ProjectBean();
//        projectBean.setPosition("H:/study/web-base/code-generator");
//        projectBean.setBasePackage("cn.edu.ahtcm.codegenerator");
//        projectBean.setModulePackage("role");
//        projectBean.setEntityPackage("entity");
//        EntityBean entityBean = new EntityBean();
//        entityBean.setValidate(true);
//        projectBean.setMapperPackage("dao");
//        projectBean.setServicePackage("service");
//        projectBean.setServiceImplPackage("service.impl");
//        projectBean.setControllerPackage("controller");
//        MapperBean mapperBean = new MapperBean();
//        mapperBean.setMapperType(SystemConstant.MAPPER_JPA);
//
//        ServiceBean serviceBean = new ServiceBean();
//        serviceBean.setMapperType(SystemConstant.MAPPER_JPA);
////        service.generModelorMapper(dataBaseBean,projectBean,entityBean,mapperBean,SystemConstant.TYPE_BOTH);
//
//        ControllerBean controllerBean = new ControllerBean();
//        controllerBean.setMapperType(SystemConstant.MAPPER_JPA);
//        service.generController(dataBaseBean,projectBean,controllerBean);
//    }
//    @Test
//    public void generCode() throws Exception {
////        GenerateService service =  new GenerateServiceImpl();
//        DataBaseBean dataBaseBean = new DataBaseBean();
//        dataBaseBean.setUserId("root");
//        dataBaseBean.setPassword("root");
//        dataBaseBean.setUrl("jdbc:mysql://127.0.0.1/web_base");
//        dataBaseBean.setTables("role");
//        ProjectBean projectBean =  new ProjectBean();
//        projectBean.setPosition("H:/study/web-base/code-generator");
//        projectBean.setBasePackage("cn.edu.ahtcm.codegenerator");
//        projectBean.setModulePackage("role");
//        projectBean.setEntityPackage("entity");
//        EntityBean entityBean = new EntityBean();
//        entityBean.setValidate(true);
//
//        projectBean.setMapperPackage("dao");
//        projectBean.setServicePackage("service");
//        projectBean.setServiceImplPackage("service.impl");
//        projectBean.setControllerPackage("controller");
//        projectBean.setMapperXmlPackage("mapper");
//        MapperBean mapperBean = new MapperBean();
//        mapperBean.setMapperType(SystemConstant.MAPPER_TKMYBATIS);
//
//        ServiceBean serviceBean = new ServiceBean();
//        serviceBean.setMapperType(SystemConstant.MAPPER_TKMYBATIS);
//
//        ControllerBean controllerBean = new ControllerBean();
//        controllerBean.setMapperType(SystemConstant.MAPPER_TKMYBATIS);
//        service.generCode(dataBaseBean,projectBean,entityBean,mapperBean,serviceBean,controllerBean);
//    }
//
//    public static void main(String[] args) throws Exception {
//        CodeGeneratorApplicationTests test = new CodeGeneratorApplicationTests();
//        test.generJpaMapper();
//    }

}

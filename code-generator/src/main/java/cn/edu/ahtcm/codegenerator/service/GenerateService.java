package cn.edu.ahtcm.codegenerator.service;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import cn.edu.ahtcm.codegenerator.entity.*;
import org.mybatis.generator.exception.InvalidConfigurationException;

/**
 * @author Administrator
 */
public interface GenerateService {
    /**
     * 生成实体类
     * @param dataBaseBean 数据库配置
     * @param projectBean 项目配置
     * @param entityBean 实体配置
     * @param mapperBean mapper配置
     * @param type 模式 1:生成model 2:生成mapper 2:都生成
     */
    default void generModelorMapper(DataBaseBean dataBaseBean, ProjectBean projectBean, EntityBean entityBean,MapperBean mapperBean,int type){};

    /**
     * 生成Service
     * @param dataBaseBean 数据库配置
     * @param projectBean 项目配置
     * @param serviceBean Service配置
     */
    default void generService(DataBaseBean dataBaseBean, ProjectBean projectBean, ServiceBean serviceBean){};

    /**
     * 生成controller
     * @param dataBaseBean 数据库配置
     * @param projectBean 项目配置
     * @param controllerBean controller配置
     */
    default void generController(DataBaseBean dataBaseBean, ProjectBean projectBean, ControllerBean controllerBean){};


    /**
     * 生成一套代码
     * @param dataBaseBean
     * @param projectBean
     * @param entityBean
     * @param mapperBean
     * @param serviceBean
     * @param controllerBean
     */
    default void generCode(DataBaseBean dataBaseBean, ProjectBean projectBean, EntityBean entityBean,MapperBean mapperBean,ServiceBean serviceBean,ControllerBean controllerBean){
        generModelorMapper(dataBaseBean,projectBean,entityBean,mapperBean, SystemConstant.TYPE_BOTH);
        generService(dataBaseBean,projectBean,serviceBean);
        generController(dataBaseBean,projectBean,controllerBean);
    };

}

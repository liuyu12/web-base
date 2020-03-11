package cn.edu.ahtcm.codegenerator.controller;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import cn.edu.ahtcm.codegenerator.entity.*;
import cn.edu.ahtcm.codegenerator.service.GenerateService;
import cn.edu.ahtcm.webcommon.core.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuyu
 */
@RestController
public class GenerateController {
    @Resource
    private GenerateService generateService;

    @PostMapping("generCode")
    public Result genCode(@RequestParam(defaultValue = "4") Integer type,@Validated DataBaseBean dataBaseBean, @Validated ProjectBean projectBean,
                          @Validated EntityBean entityBean, @Validated MapperBean mapperBean, @Validated ServiceBean serviceBean, @Validated ControllerBean controllerBean){
        if(type == 0){
            generateService.generModelorMapper(dataBaseBean,projectBean,entityBean,mapperBean, SystemConstant.TYPE_ENTITY);
            return Result.ok("实体类生成成功");
        }
        if(type == 1){
            generateService.generModelorMapper(dataBaseBean,projectBean,entityBean,mapperBean, SystemConstant.TYPE_MAPPER);
            return Result.ok("mapper生成成功");
        }
        if(type == 2){
            generateService.generService(dataBaseBean,projectBean,serviceBean);
            return Result.ok("service生成成功");
        }
        if(type == 3){
            generateService.generController(dataBaseBean,projectBean,controllerBean);
            return Result.ok("controller生成成功");
        }
        if(type == 4){
            generateService.generCode(dataBaseBean,projectBean,entityBean,mapperBean,serviceBean,controllerBean);
            return Result.ok("代码生成成功");
        }
        return Result.ok("type值在0~4");

    }
}

package cn.edu.ahtcm.codegenerator.controller;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import cn.edu.ahtcm.codegenerator.entity.*;
import cn.edu.ahtcm.codegenerator.service.GenerateService;
import cn.edu.ahtcm.webcommon.core.Result;
import cn.edu.ahtcm.webcommon.service.RedisService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Resource
    private RedisService redisService;

    @PostMapping("generCode")
    public Result genCode(@RequestParam(defaultValue = "4") Integer type, @Validated DataBaseBean dataBaseBean, @Validated ProjectBean projectBean,
                          @Validated EntityBean entityBean, @Validated MapperBean mapperBean, @Validated ServiceBean serviceBean, @Validated ControllerBean controllerBean,
                          @RequestParam(defaultValue = "false") Boolean entity, @RequestParam(defaultValue = "false") Boolean mapper, @RequestParam(defaultValue = "false") Boolean service, @RequestParam(defaultValue = "false") Boolean controller) {
        StringBuffer message = new StringBuffer();
        if (entity) {
            generateService.generModelorMapper(dataBaseBean, projectBean, entityBean, mapperBean, SystemConstant.TYPE_ENTITY);
            message.append("实体类生成成功;");
        }
        if (mapper) {
            generateService.generModelorMapper(dataBaseBean, projectBean, entityBean, mapperBean, SystemConstant.TYPE_MAPPER);
            message.append("mapper生成成功;");
        }
        if (service) {
            generateService.generService(dataBaseBean, projectBean, serviceBean);
            message.append("service生成成功;");
        }
        if (controller) {
            generateService.generController(dataBaseBean, projectBean, controllerBean);
            message.append("controller生成成功;");
        }
        if (!(entity || mapper || service || controller)) {
            message = new StringBuffer("请至少选择一项功能");
            return Result.fail(message.toString());
        }
        return Result.ok(message.toString());

    }

    /**
     * 保存配置
     *
     * @param config
     * @return
     */
    @PostMapping("saveConfig")
    public Result saveConfig(@RequestParam String config) {
        redisService.set("config", config);
        return Result.ok();
    }

    /**
     * 获取配置
     *
     * @return
     */
    @GetMapping("getConfig")
    public Result getConfig() {
        return Result.ok(redisService.get("config"));
    }
}

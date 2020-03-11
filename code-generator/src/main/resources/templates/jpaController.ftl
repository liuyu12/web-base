package ${controllerPackage};

import ${entityPackage}.${entityNameUpper};
import ${servicePackage}.${entityNameUpper}Service;
import cn.edu.ahtcm.webcommon.core.BaseController;
import cn.edu.ahtcm.webcommon.core.Result;
import cn.edu.ahtcm.webcommon.domain.RequestPage;
import cn.edu.ahtcm.webcommon.validation.groups.Add;
import cn.edu.ahtcm.webcommon.validation.groups.Delete;
import cn.edu.ahtcm.webcommon.validation.groups.Update;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * @author ${author}
 */
@Validated
@RestController
@RequestMapping("${entityNameLower}")
public class ${entityNameUpper}Controller extends BaseController {
    @Resource
    private ${entityNameUpper}Service ${entityNameLower}Service;

    /**
     * 按条件查询
     * @param requestPage
     * @param ${entityNameLower}
     * @return
     */
    @GetMapping(LIST)
    public Page<${entityNameUpper}> list(RequestPage requestPage,${entityNameUpper} ${entityNameLower}){
        return ${entityNameLower}Service.list(requestPage,${entityNameLower});
    }

    /**
     * 保存
     * @param ${entityNameLower}
     * @return
     */
    @PostMapping(SAVE)
    public Result save(@Validated({Add.class}) ${entityNameUpper} ${entityNameLower}){
        ${entityNameLower}Service.save(${entityNameLower});
        return Result.ok();
    }

    /**
     * 修改
     * @param ${entityNameLower}
     * @return
     */
    @PostMapping(UPDATE)
    public Result upddate(@Validated({Update.class}) ${entityNameUpper} ${entityNameLower}){
        ${entityNameLower}Service.update(${entityNameLower});
        return Result.ok();
    }

    /**
     * 删除
     * @param ${entityNameLower}
     * @return
     */
    @PostMapping(DELETE)
    public Result delete(@Validated({Delete.class}) ${entityNameUpper} ${entityNameLower}){
        ${entityNameLower}Service.remove(${entityNameLower}.getId());
        return Result.ok();
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @GetMapping(DETAIL)
    public Result detail(@RequestParam ${primaryType} id){
        return Result.ok(${entityNameLower}Service.getById(id));
    }



}

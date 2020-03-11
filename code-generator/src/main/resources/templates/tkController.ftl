package ${controllerPackage};

import ${entityPackage}.${entityNameUpper};
import ${servicePackage}.${entityNameUpper}Service;
import cn.edu.ahtcm.webcommon.core.BaseController;
import cn.edu.ahtcm.webcommon.core.Result;
import cn.edu.ahtcm.webcommon.validation.groups.Add;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
     * 查询列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping(LIST)
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<${entityNameUpper}> list = ${entityNameLower}Service.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return Result.ok(pageInfo);
    }

    @PostMapping(SAVE)
    public Result add(@Validated(Add.class) ${entityNameUpper} ${entityNameLower}) {
        ${entityNameLower}Service.save(${entityNameLower});
        return Result.ok();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping(DELETE)
    public Result delete(@RequestParam ${primaryType} id) {
        ${entityNameLower}Service.deleteById(id);
        return Result.ok();
    }

    /**
     * 修改
     * @param ${entityNameLower}
     * @return
     */
    @PostMapping(UPDATE)
    public Result update(${entityNameUpper} ${entityNameLower}) {
        ${entityNameLower}Service.update(${entityNameLower});
        return Result.ok();
    }

    /**
     * 查看详情
     * @param id
     * @return
     */
    @GetMapping(DETAIL)
    public Result detail(@RequestParam ${primaryType} id) {
        ${entityNameUpper} ${entityNameLower} = ${entityNameLower}Service.findById(id);
        return Result.ok(${entityNameLower});
    }

}

package ${servicePackage};

import ${entityPackage}.${entityNameUpper};
import cn.edu.ahtcm.webcommon.core.Service;
import cn.edu.ahtcm.webcommon.domain.RequestPage;
import org.springframework.data.domain.Page;

/**
 * @author ${author}
 */
public interface ${entityNameUpper}Service extends Service<${entityNameUpper},${primaryType}> {

    /**
     * 列表分页查询
     * @param requestPage
     * @param ${entityNameLower}
     * @return
     */
    default Page<${entityNameUpper}> list(RequestPage requestPage, ${entityNameUpper} ${entityNameLower}){
        return null;
    };
}

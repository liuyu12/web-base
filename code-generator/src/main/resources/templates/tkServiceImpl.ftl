package ${serviceImplPackage};

import ${entityPackage}.${entityNameUpper};
import ${servicePackage}.${entityNameUpper}Service;
import cn.edu.ahtcm.webcommon.tkmybatis.AbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ${author}
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ${entityNameUpper}ServiceImpl extends AbstractService<${entityNameUpper}> implements ${entityNameUpper}Service {
}

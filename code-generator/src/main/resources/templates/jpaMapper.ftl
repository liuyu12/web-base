package ${mapperPackage};

import ${entityPackage}.${entityName};
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author ${author}
*/
public interface ${entityName}Mapper extends JpaRepository<${entityName},${primaryType}>,JpaSpecificationExecutor<${entityName}> {
}

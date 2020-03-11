package ${serviceImplPackage};

import ${entityPackage}.${entityNameUpper};
import ${mapperPackage}.${entityNameUpper}Mapper;
import ${servicePackage}.${entityNameUpper}Service;
import cn.edu.ahtcm.webcommon.domain.RequestPage;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @author ${author}
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ${entityNameUpper}ServiceImpl implements ${entityNameUpper}Service {
    @Resource
    private ${entityNameUpper}Mapper ${entityNameLower}Mapper;

    @Override
    public void save(${entityNameUpper} entity) {
        ${entityNameLower}Mapper.save(entity);
    }

    @Override
    public void update(${entityNameUpper} entity) {
        ${entityNameLower}Mapper.save(entity);
    }

    @Override
    public void remove(Integer id) {
        ${entityNameLower}Mapper.deleteById(id);
    }

    @Override
    public ${entityNameUpper} getById(Integer id) {
        return ${entityNameLower}Mapper.findById(id).get();
    }

    @Override
    public List<${entityNameUpper}> listAll() {
        return ${entityNameLower}Mapper.findAll();
    }

    @Override
    public Page<${entityNameUpper}> list(RequestPage requestPage, ${entityNameUpper} ${entityNameLower}) {
        PageRequest page = PageRequest.of(requestPage.getPage(),requestPage.getSize());
        Specification<${entityNameUpper}> specification = new Specification<${entityNameUpper}>() {
            @Override
            public Predicate toPredicate(Root<${entityNameUpper}> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = Lists.newArrayList();
                // TODO 设置查询条件
                // if(StringUtils.isNotEmpty(${entityNameLower}.get${entityNameUpper}name())){
                //      predicates.add(criteriaBuilder.equal(root.get("${entityNameLower}name"),${entityNameLower}.get${entityNameUpper}name()));
                // }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        return ${entityNameLower}Mapper.findAll(specification,page);
    }
}

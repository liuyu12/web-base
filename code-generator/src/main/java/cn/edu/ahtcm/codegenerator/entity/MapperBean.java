package cn.edu.ahtcm.codegenerator.entity;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import lombok.Data;

/**
 * @author liuyu
 */
@Data
public class MapperBean {
    private Integer mapperType = SystemConstant.MAPPER_JPA;
}

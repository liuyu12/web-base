package cn.edu.ahtcm.codegenerator.entity;

import cn.edu.ahtcm.codegenerator.constant.SystemConstant;
import lombok.Builder;
import lombok.Data;

/**
 * @author liuyu
 */
@Data
public class ControllerBean {
    private Integer mapperType = SystemConstant.MAPPER_JPA;

}

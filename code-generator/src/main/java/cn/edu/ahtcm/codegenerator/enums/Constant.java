package cn.edu.ahtcm.codegenerator.enums;


/**
 * @author liuyu
 */

public enum Constant {
    BASE_PACKAGE(1),
    MODULE_PACKAGE(2),
    ENTITY_PACKAGE(3),
    MAPPER_PACKAGER(3),
    MAPPERXML_PACKAGE(4),
    SERVICE_PACKAGE(5),
    SERVICE_IMPL_PACKAGE(6),
    CONTROLLER_PACKAGE(7),
    CORE_PACKAGE(8)
    ;
    private Integer value;

    Constant(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

package cn.edu.ahtcm.webcommon.util;

import com.google.common.base.CaseFormat;

/**
 * @author liuyu
 */
public class StringUtil {

    private StringUtil(){}

    /**
     * 字符串转换成小写驼峰
     * @param tableName
     * @return
     */
    public static  String tableNameConvertLowerCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName.toLowerCase());
    }

    /**
     * 字符串转换成大写驼峰
     * @param tableName
     * @return
     */
    public static  String tableNameConvertUpperCamel(String tableName) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName.toLowerCase());

    }

    /**
     *
     * @param tableName
     * @return
     */
    public static  String tableNameConvertMappingPath(String tableName) {
        //兼容使用大写的表名
        tableName = tableName.toLowerCase();
        return "/" + (tableName.contains("_") ? tableName.replaceAll("_", "/") : tableName);
    }

    /**
     *
     * @param modelName
     * @return
     */
    public static  String modelNameConvertMappingPath(String modelName) {
        String tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, modelName);
        return tableNameConvertMappingPath(tableName);
    }

    /**
     * 包名转换成路径
     * @param packageName
     * @return
     */
    public static  String packageConvertPath(String packageName) {
        return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
    }
}

package cn.edu.ahtcm.webcommon.util;

import com.google.common.base.Preconditions;

import java.io.File;

/**
 * @author liuyu
 */
public class FileUtil {
    private FileUtil(){}

    /**
     * 生成父文件夹
     * @param file
     */
    public static void createParentFile(File file){
        Preconditions.checkNotNull(file);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
    }
}

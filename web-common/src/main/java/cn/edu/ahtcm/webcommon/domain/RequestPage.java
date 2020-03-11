package cn.edu.ahtcm.webcommon.domain;

import lombok.Data;

/**
 * @author liuyu
 */
@Data
public class RequestPage {
    private Integer page = 1;
    private Integer size = 10;
    private String sort;
}

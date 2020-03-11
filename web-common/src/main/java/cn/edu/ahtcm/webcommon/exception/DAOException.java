package cn.edu.ahtcm.webcommon.exception;

/**
 * @author liuyu
 */
public class DAOException extends RuntimeException {

    public DAOException(){}

    public DAOException(String message){
        super(message);
    }
}

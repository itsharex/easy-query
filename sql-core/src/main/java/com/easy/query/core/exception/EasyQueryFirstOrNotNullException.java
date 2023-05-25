package com.easy.query.core.exception;

/**
 * @FileName: EasyQueryNotFoundException.java
 * @Description: 文件说明
 * @Date: 2023/3/7 12:38
 * @author xuejiaming
 */
public class EasyQueryFirstOrNotNullException extends EasyQueryException {
    private static final long serialVersionUID = 4426844319659828849L;

    private final String code;

    public EasyQueryFirstOrNotNullException(String msg) {
        this(msg, null, null);
    }

    public EasyQueryFirstOrNotNullException(String msg, String code) {
        this(msg, code, null);
    }

    public EasyQueryFirstOrNotNullException(Throwable e) {
        this(null, null, e);
    }

    public EasyQueryFirstOrNotNullException(String msg, String code, Throwable e) {
        super(msg, e);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
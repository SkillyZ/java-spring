package com.skilly.excecption;

import com.skilly.enums.ResultEnum;

/**
 * Created by 1254109699@qq.com on 2018/1/1.
 */
public class GirlException extends RuntimeException{
    private Integer code;

    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code=resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


}
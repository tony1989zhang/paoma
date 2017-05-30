package com.chssl17.bean;

import com.chssl17.entity.LoginEntity;
import com.chssl17.http.JRParser;

import org.xutils.http.annotation.HttpResponse;


/**
 * 作者：V先生 on 2016/8/1 17:30
 * 作用：基类
 */
@HttpResponse(parser = JRParser.class)
public class LoginBean extends BaseBean{
    public LoginEntity resData;

    @Override
    public String toString() {
        return "LoginBean_parser{" +
                "errCode=" + errCode +
                ", resMsg='" + resMsg + '\'' +
                ", resData=" + resData +
                '}';
    }
}

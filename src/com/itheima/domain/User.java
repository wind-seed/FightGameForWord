package com.itheima.domain;

import java.util.Random;

public class User {
    //id  用户名  密码 状态
    private String id;
    private String username;
    private String password;
    private boolean status;  //false  禁用    true 可用

    public User(){
        id = creatID();
        status = true;//因为boolean的默认值为false
    }
    public User(String username, String password) {
        id = creatID();  //不由用户自己设定
        this.username = username;
        this.password = password;
        status = true;//因为boolean的默认值为false
    }

    public String creatID(){
        StringBuilder sb = new StringBuilder("heima");
        Random rd= new Random();
        for (int i = 0; i < 5; i++) {
            int num = rd.nextInt(10);
            sb.append(num);
        }
        return sb.toString();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isStatus() {
        return status;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

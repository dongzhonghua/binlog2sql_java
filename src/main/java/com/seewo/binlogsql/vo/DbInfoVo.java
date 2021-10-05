package com.seewo.binlogsql.vo;

import lombok.ToString;

/**
 * @author linxixin@cvte.com
 * @version 1.0
 * @description
 */
@ToString
public class DbInfoVo {

    private String host;
    private Integer port;
    private String username;
    private String password;

    public DbInfoVo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }


}

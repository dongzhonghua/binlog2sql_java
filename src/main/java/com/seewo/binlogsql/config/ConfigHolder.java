package com.seewo.binlogsql.config;

import com.seewo.binlogsql.vo.DbInfoVo;

import cn.hutool.setting.dialect.Props;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dongzhonghua
 * Created on 2021-10-05
 */
@Slf4j
public class ConfigHolder {
    public static DbInfoVo source = new DbInfoVo();
    public static DbInfoVo to = new DbInfoVo();

    static {
        Props props = new Props("application.properties");
        source.setHost(props.getProperty("source.host"));
        source.setPort(Integer.valueOf(props.getProperty("source.port")));
        source.setUsername(props.getProperty("source.username"));
        source.setPassword(props.getProperty("source.password"));

        to.setHost(props.getProperty("to.host"));
        to.setPort(Integer.valueOf(props.getProperty("to.port")));
        to.setUsername(props.getProperty("to.username"));
        to.setPassword(props.getProperty("to.password"));
        log.info(source.toString());
        log.info(to.toString());
    }
}

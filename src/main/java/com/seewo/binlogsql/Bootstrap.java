package com.seewo.binlogsql;

import com.seewo.binlogsql.vo.CommonFilter;
import com.seewo.binlogsql.vo.DbInfoVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author linxixin@cvte.com
 * @since 1.0
 */
@Slf4j
public class Bootstrap {
    public static void main(String[] args) throws Exception {
        log.error("#############");
        DbInfoVo dbInfoVo = new DbInfoVo();
        dbInfoVo.setHost("60.205.229.152");
        dbInfoVo.setPort(3306);
        dbInfoVo.setUsername("root");
        dbInfoVo.setPassword("mysql123");
        new BinlogListenSql(dbInfoVo)
                .setFilter(new CommonFilter().setStartTime(System.currentTimeMillis()))
                .connectAndListen();
    }
}

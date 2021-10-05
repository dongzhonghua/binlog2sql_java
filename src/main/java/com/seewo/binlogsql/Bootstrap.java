package com.seewo.binlogsql;

import com.seewo.binlogsql.config.ConfigHolder;
import com.seewo.binlogsql.vo.CommonFilter;
import com.seewo.binlogsql.vo.DbInfoVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author linxixin@cvte.com
 * @since 1.0
 */
@Slf4j
public class Bootstrap {
    public static void main(String[] args) {
        log.info("#############");
        DbInfoVo dbInfoVo = ConfigHolder.source;
        new BinlogListenSql(dbInfoVo)
                .setFilter(new CommonFilter().setStartTime(System.currentTimeMillis()))
                .connectAndListen();
    }
}

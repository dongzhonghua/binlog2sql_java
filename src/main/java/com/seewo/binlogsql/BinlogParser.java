package com.seewo.binlogsql;

import static com.seewo.binlogsql.handler.SyncDataJdbcHandler.performUpdateSql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.seewo.binlogsql.handler.BinlogEventHandle;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author linxixin@cvte.com
 * @since 1.0
 */
@Slf4j
public class BinlogParser {

    @Getter
    private Map<EventType, BinlogEventHandle> handleRegisterMap = new HashMap<>();

    public void registerHandle(BinlogEventHandle handle, EventType... eventTypes) {
        for (EventType eventType : eventTypes) {
            handleRegisterMap.put(eventType, handle);
        }
    }

    public void handle(Event event) {
        BinlogEventHandle binlogEventHandle = handleRegisterMap.get(event.getHeader().getEventType());
        if (binlogEventHandle != null) {
            List<String> sql = binlogEventHandle.handle(event, false);
            if (!sql.isEmpty()) {
                log.info("handle sql: " + sql);
                for (String s : sql) {
                    int i = performUpdateSql(s);
                    log.info("同步SQL：影响行数：{},  sql={}", i, sql);
                }
            }
        }
    }
}

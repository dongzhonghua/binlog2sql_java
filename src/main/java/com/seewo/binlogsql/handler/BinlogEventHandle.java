package com.seewo.binlogsql.handler;

import java.util.List;

import com.github.shyiko.mysql.binlog.event.Event;

/**
 * @author linxixin@cvte.com
 * @version 1.0
 * @description
 */
public interface BinlogEventHandle {

    List<String> handle(Event event, boolean isTurn);
}

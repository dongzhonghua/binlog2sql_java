package com.seewo.binlogsql.handler;

import java.util.function.Predicate;

import com.github.shyiko.mysql.binlog.event.Event;

/**
 * @author linxixin@cvte.com
 * @since 1.0
 */
public interface EventFilter extends Predicate<Event> {
    @Override
    boolean test(Event event);
}

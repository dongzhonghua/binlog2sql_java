package com.seewo.binlogsql.handler;

import static com.seewo.binlogsql.tool.SqlGenerateTool.changeToRowVo;
import static com.seewo.binlogsql.tool.SqlGenerateTool.getComment;
import static com.seewo.binlogsql.tool.SqlGenerateTool.updateSql;
import static com.seewo.binlogsql.tool.TableTool.getTableInfo;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.seewo.binlogsql.Filter;
import com.seewo.binlogsql.vo.RowVo;
import com.seewo.binlogsql.vo.TableVo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linxixin@cvte.com
 * @version 1.0
 * @description
 */
public class UpdateHandle implements BinlogEventHandle {

    private final Filter filter;

    public UpdateHandle(Filter filterVo) {
        this.filter = filterVo;
    }

    @Override
    public List<String> handle(Event event, boolean isTurn) {

        UpdateRowsEventData updateRowsEventData = event.getData();
        TableVo tableVoInfo = getTableInfo(updateRowsEventData.getTableId());

        if (!filter.filter(tableVoInfo)) {
            return Collections.emptyList();
        }
        List<Pair> updateRows = updateRowsEventData.getRows().stream().map(entry -> {
            RowVo key = changeToRowVo(tableVoInfo, entry.getKey());
            RowVo value = changeToRowVo(tableVoInfo, entry.getValue());
            return new Pair(key, value);
        }).collect(Collectors.toList());

        if (isTurn) {
            List<Pair> reversedPairs = updateRows.stream()
                    .map(rowPair -> new Pair(rowPair.getAfter(), rowPair.getBefore()))
                    .collect(Collectors.toList());
            return updateSql(tableVoInfo, reversedPairs, getComment(event.getHeader()));
        } else {
            return updateSql(tableVoInfo, updateRows, getComment(event.getHeader()));
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pair {
        private RowVo before;
        private RowVo after;
    }


}

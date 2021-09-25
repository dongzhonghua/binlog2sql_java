package com.seewo.binlogsql.handler;

import static com.seewo.binlogsql.tool.SqlGenerateTool.changeToRowVo;
import static com.seewo.binlogsql.tool.SqlGenerateTool.deleteSql;
import static com.seewo.binlogsql.tool.SqlGenerateTool.getComment;
import static com.seewo.binlogsql.tool.SqlGenerateTool.insertSql;
import static com.seewo.binlogsql.tool.TableTool.getTableInfo;

import java.util.Collections;
import java.util.List;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.seewo.binlogsql.Filter;
import com.seewo.binlogsql.vo.RowVo;
import com.seewo.binlogsql.vo.TableVo;

/**
 * @author linxixin@cvte.com
 * @version 1.0
 * @description
 */
public class DeleteHandle implements BinlogEventHandle {

    private final Filter filter;

    public DeleteHandle(Filter filter) {
        this.filter = filter;
    }

    @Override
    public List<String> handle(Event event, boolean isTurn) {
        DeleteRowsEventData deleteRowsEventData = event.getData();
        TableVo tableVoInfo = getTableInfo(deleteRowsEventData.getTableId());

        if (!filter.filter(tableVoInfo)) {
            return Collections.emptyList();
        }

        List<RowVo> rows = changeToRowVo(tableVoInfo, deleteRowsEventData.getRows());

        if (isTurn) {
            return insertSql(tableVoInfo, rows, getComment(event.getHeader()));
        } else {
            return deleteSql(tableVoInfo, rows, getComment(event.getHeader()));
        }
    }

}

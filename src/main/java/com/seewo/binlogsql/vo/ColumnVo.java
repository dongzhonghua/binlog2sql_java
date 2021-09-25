package com.seewo.binlogsql.vo;

import java.sql.JDBCType;

import com.github.shyiko.mysql.binlog.event.deserialization.ColumnType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author linxixin@cvte.com
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnVo {
    private String name;
    private ColumnType columnType;
    private JDBCType jdbcType;
}

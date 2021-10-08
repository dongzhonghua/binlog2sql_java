package com.seewo.binlogsql.handler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seewo.binlogsql.C3p0Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dongzhonghua
 * Created on 2021-09-29
 */
@Slf4j
public class SyncDataJdbcHandler {

    public static List<Object> performQuerySql(String sql) {
        List<Object> list = new ArrayList<>();
        try {
            ResultSet rs = getLink().executeQuery(sql);
            ResultSetMetaData md = rs.getMetaData(); //获得结果集结构信息,元数据
            int columnCount = md.getColumnCount();   //获得列数
            while (rs.next()) {
                Map<String, Object> rowData = new HashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), rs.getObject(i));
                }
                list.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static int performUpdateSql(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
             connection = C3p0Utils.getConnection();
            statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        } finally {
            C3p0Utils.close(connection, statement);
        }
    }

    public static Statement getLink() throws SQLException {
        return C3p0Utils.getConnection().createStatement();
    }
}
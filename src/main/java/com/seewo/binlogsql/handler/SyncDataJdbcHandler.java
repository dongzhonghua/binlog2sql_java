package com.seewo.binlogsql.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.seewo.binlogsql.config.ConfigHolder;
import com.seewo.binlogsql.vo.DbInfoVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dongzhonghua
 * Created on 2021-09-29
 */
@Slf4j
public class SyncDataJdbcHandler {

    private static SyncDataJdbcHandler mysqlLink = null;
    private static Connection conn;
    private static Statement statement;
    private static ResultSet rs;

    private SyncDataJdbcHandler() {
        try {
            DbInfoVo dbInfoVo = ConfigHolder.to;
            // 注册JDBC驱动程序：需要初始化驱动程序，以便可以打开与数据库的通信通道。
            // Class.forName("com.mysql.jdbc.Driver").newInstance();
            // 打开一个连接：需要使用DriverManager.getConnection()方法创建一个Connection对象，它表示与数据库的物理连接。
            String url = "jdbc:mysql://" + dbInfoVo.getHost() + ":" + dbInfoVo.getPort() + "/typecho";
            conn = DriverManager.getConnection(url, dbInfoVo.getUsername(), dbInfoVo.getPassword());
            // 执行查询：需要使用一个类型为Statement或PreparedStatement的对象，并提交一个SQL语句到数据库执行查询。如下：
            statement = conn.createStatement();
            log.info(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Object> performQuerySql(String sql) {
        List<Object> list = new ArrayList<>();
        try {
            rs = statement.executeQuery(sql);
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
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static SyncDataJdbcHandler getInstance() {
        if (mysqlLink == null) {
            mysqlLink = new SyncDataJdbcHandler();
        }
        return mysqlLink;
    }

    public static Statement getLink() {
        return statement;
    }

    public static boolean closeLink() {
        try {
            conn.close();
            statement.close();
            if (rs != null) {
                rs.close();
            }
            mysqlLink = null;
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
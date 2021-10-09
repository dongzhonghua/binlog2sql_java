package com.seewo.binlogsql;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.seewo.binlogsql.config.ConfigHolder;
import com.seewo.binlogsql.vo.DbInfoVo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dongzhonghua
 * Created on 2021-10-08
 */
@Slf4j
public class C3p0Utils {

    //通过标识名来创建相应连接池
    static ComboPooledDataSource dataSource = new ComboPooledDataSource();

    static {
        DbInfoVo dbInfoVo = ConfigHolder.to;
        String url = "jdbc:mysql://" + dbInfoVo.getHost() + ":" + dbInfoVo.getPort() + "/typecho";
        log.info(url);
        //连接配置
        dataSource.setJdbcUrl(url);
        try {
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        dataSource.setPassword(dbInfoVo.getPassword());
        dataSource.setUser(dbInfoVo.getUsername());

        //连接池配置
        dataSource.setAcquireIncrement(5);//每创建的数量间隔
        dataSource.setInitialPoolSize(5);//初始化池的大小
        dataSource.setMaxPoolSize(20);//最大大小
        dataSource.setMinPoolSize(5);//最小大小
        dataSource.setCheckoutTimeout(10000);
    }

    //从连接池中取用一个连接
    public static Connection getConnection() {
        try {
            Connection connection = dataSource.getConnection();
            log.info("    最大连接数：" + dataSource.getMaxPoolSize());
            log.info("    最小连接数：" + dataSource.getMinPoolSize());
            log.info("      总连接数：" + dataSource.getNumConnections());
            log.info("正在使用连接数：" + dataSource.getNumBusyConnections());
            log.info("    空闲连接数：" + dataSource.getNumIdleConnections());
            return connection;
        } catch (Exception e) {
            log.error("Exception in C3p0Utils!", e);
            throw new RuntimeException("数据库连接出错!", e);
        }
    }

    //释放连接回连接池
    public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.error("Exception in C3p0Utils!", e);
                throw new RuntimeException("数据库连接关闭出错!", e);
            }
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                log.error("Exception in C3p0Utils!", e);
                throw new RuntimeException("数据库连接关闭出错!", e);
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("Exception in C3p0Utils!", e);
                throw new RuntimeException("数据库连接关闭出错!", e);
            }
        }
    }

    public static void close(Connection conn, Statement pst) {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                log.error("Exception in C3p0Utils!", e);
                throw new RuntimeException("数据库连接关闭出错!", e);
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                log.error("Exception in C3p0Utils!", e);
                throw new RuntimeException("数据库连接关闭出错!", e);
            }
        }
    }
}


package com.jy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;

public class JdbcTest {
    private Connection conn = null;
    private String url = "jdbc:sqlite:jy_demo.db";
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ResultSetMetaData rm = null;
    private static final Logger LOG = LoggerFactory.getLogger(JdbcTest.class);

    /**
     * 连接数据库
     *
     */
    public JdbcTest() throws Exception {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(url);
    }

    /**
     * 连接
     * @return
     * @throws Exception
     */
    private Connection getConn() throws Exception {
        if (conn == null) {
            conn = DriverManager.getConnection(url);
        }
        return conn;
    }

    /**
     * 关闭连接
     * @param con
     * @param ps
     * @param rs
     * @throws Exception
     */
    private void closeConn(Connection con, PreparedStatement ps, ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
            rs = null;
        }
        if (ps != null) {
            ps.close();
            ps = null;
        }
        if (con != null) {
            con.close();
            conn = null;
        }
    }



    public static void main(String[] args) throws Exception {
        LOG.info("logging start====================================");
        JdbcTest j = new JdbcTest();
        String sql = "select * from jy_tbl";
        Connection conn = j.getConn();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int l = rs.getMetaData().getColumnCount();
        String columnName = null;
        String columnTypeName = null;
        int anInt = 0;
        String str = null;
        while (rs.next()) {
            for (int i = 0; i < l; i++) {
                columnName = rs.getMetaData().getColumnName(i + 1);
                columnTypeName = rs.getMetaData().getColumnTypeName(i + 1);
                if ("INTEGER".equals(columnTypeName)) {
                    anInt = rs.getInt(columnName);
                    LOG.info(columnName+"-"+columnTypeName+":"+anInt);
                }else if ("CHAR".equals(columnTypeName)){
                    str = rs.getString(columnName);
                    LOG.info(columnName+"-"+columnTypeName+":"+str);
                }
            }
        }
        LOG.info("logging end  ====================================");
    }
}
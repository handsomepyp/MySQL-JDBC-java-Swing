package com;

import java.awt.*;
import java.sql.*;

public class JDBC_test {

    public static void main(String[] args) throws Exception {

        // 1.加载数据访问驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.连接到数据"库"上去
        String url = "jdbc:mysql://127.0.0.1:3306/?user=root";
        String user= "root";
        String passwd= "100318";//
        Connection conn= DriverManager.getConnection(url,user,passwd);
        //3.构建SQL命令
        try {
            if(!conn.isClosed())
                System.out.println("数据库连接成功");
            Statement statement = conn.createStatement();
            long start = System.currentTimeMillis();

            String sql = "select * from project.user where u_name = 429656";

            ResultSet rs = statement.executeQuery(sql);
            long end = System.currentTimeMillis();
            while(rs.next()) {
                System.out.print(rs.getString("u_id"));
                System.out.print(" ");
                System.out.print(rs.getString("u_name"));
                System.out.print(" ");
                System.out.print(rs.getString("u_pwd"));
                System.out.print("\n");
            }

            System.out.println("耗时：" + (end - start)/1000 + "秒");
        }
        catch(Exception e) {
            System.out.print("获取信息错误");
            e.printStackTrace();
        }
    }

}

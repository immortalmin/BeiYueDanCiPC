package BeiYueDanCi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库管理类
 */
public class DBConnection {
    static Connection conn = null;
    private static String username = "root";
    private static String password = "csk1314520";
    private static String url = "jdbc:mysql://localhost:3306/word?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
    //String url = "jdbc:mysql://47.98.239.237:3306/word?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT";
    public Connection getConnection(){
        try{
            conn = DriverManager.getConnection(url,username,password);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }
    public void closeConnection() throws  SQLException{
        if(conn!=null){
            try{
                conn.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}

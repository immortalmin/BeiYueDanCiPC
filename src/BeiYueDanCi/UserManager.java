package BeiYueDanCi;

import org.omg.CORBA.OBJ_ADAPTER;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class UserManager extends DBConnection{
    private Connection conn = null;
    public UserManager(){}


    /**
     * 验证用户名、密码
     * @param username
     * @param pwd
     * @return -1：用户不存在  0：密码错误  1：密码正确
     * @throws SQLException
     */
    public HashMap<String,Object> queryUser(String username, String pwd) throws SQLException {
        conn = getConnection();
        User user = new User();
        Statement statement = null;
        ResultSet rs = null;
        HashMap<String, Object> res = new HashMap<>();
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM USER WHERE username=\""+username+"\";");
            if(rs.next()){
                if(rs.getString("pwd").equals(MD5Util.getMD5Str(pwd))){
                    res.put("what",1);
                    user.setUid(rs.getString("uid"));
                    user.setUsername(rs.getString("username"));
                    user.setPwd(rs.getString("pwd"));
                    user.setProfile_photo(rs.getString("profile_photo"));
                    user.setTelephone(rs.getString("telephone"));
                    user.setEmail(rs.getString("email"));
                    user.setMotto(rs.getString("motto"));
                    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Double time = Double.parseDouble(rs.getString("last_login"));
                    String d = format.format(time);
                    user.setLast_login(format.parse(d));
                    res.put("user",user);
                    return res;
                }
                res.put("what",0);
                return res;
            }else{
                res.put("what",-1);
                return res;
            }
        }catch (SQLException | ParseException e){
            e.printStackTrace();
        }finally {
            if(statement!=null) statement.close();
            if(rs!=null) rs.close();
            closeConnection();
        }
        res.put("what",-1);
        return res;
    }
}

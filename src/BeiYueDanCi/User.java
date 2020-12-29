package BeiYueDanCi;

import java.util.Date;

public class User {

    private String uid;
    private String username;
    private String pwd;
    private String profile_photo;
    private String telephone;
    private String email;
    private String motto;
    private Date last_login;

    public User(){}

    public User(String uid, String username, String pwd, String profile_photo, String telephone, String email, String motto, Date last_login) {
        this.uid = uid;
        this.username = username;
        this.pwd = pwd;
        this.profile_photo = profile_photo;
        this.telephone = telephone;
        this.email = email;
        this.motto = motto;
        this.last_login = last_login;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }
}

package BeiYueDanCi;

import Toast.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashMap;

public class LoginPanel extends JPanel implements ActionListener {

    private Font textFont;
    private JButton loginBtn,registerBtn;
    private JTextField userField;
    private JPasswordField pwdField;
    private FinishLogin finishLogin;
    private JFrame jFrame;
    private UserManager userManager = new UserManager();

    public LoginPanel(JFrame jFrame){
        this.jFrame = jFrame;
        setLayout(new FlowLayout());

        textFont = new Font("宋体",Font.PLAIN,20);
        JLabel titleLabel = new JLabel("BEIYUE",JLabel.CENTER);
        titleLabel.setFont(new Font("宋体",Font.BOLD,40));
        JPanel titlePanel = new JPanel(new FlowLayout());
        titlePanel.add(titleLabel);

        JLabel userLabel = new JLabel("用户名：",JLabel.CENTER);
        userLabel.setFont(textFont);
        userField = new JTextField(15);
        userField.setFont(textFont);
        JLabel pwdLabel = new JLabel("密  码：",JLabel.CENTER);
        pwdLabel.setFont(textFont);
        pwdField = new JPasswordField(15);
        pwdField.setFont(textFont);

        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(80));
        verticalBox.add(titlePanel);
        verticalBox.add(Box.createVerticalStrut(40));
        //用户名
        Box horizontalUserBox = Box.createHorizontalBox();
        horizontalUserBox.add(userLabel);
        horizontalUserBox.add(userField);
        //密码
        Box horizontalPwdBox = Box.createHorizontalBox();
        horizontalPwdBox.add(pwdLabel);
        horizontalPwdBox.add(pwdField);
        //按钮
        FlowLayout btnFlowLayout = new FlowLayout();
        Box horizontalBtnBox = Box.createHorizontalBox();
        loginBtn = new JButton("登录");
        loginBtn.setBackground(Color.GREEN);
        loginBtn.setForeground(Color.WHITE);
        registerBtn = new JButton("注册");
        registerBtn.setBackground(Color.ORANGE);
        registerBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(this);
        registerBtn.addActionListener(this);
        horizontalBtnBox.add(loginBtn);
        horizontalBtnBox.add(Box.createHorizontalStrut(30));
        horizontalBtnBox.add(registerBtn);
        JPanel btnPanel = new JPanel(btnFlowLayout);
        btnPanel.add(horizontalBtnBox);

        verticalBox.add(horizontalUserBox);
        verticalBox.add(Box.createVerticalStrut(30));
        verticalBox.add(horizontalPwdBox);
        verticalBox.add(Box.createVerticalStrut(30));
        verticalBox.add(btnPanel);
        this.add(verticalBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==loginBtn){
            try{
                HashMap<String,Object> res;
                res = userManager.queryUser(userField.getText(),new String(pwdField.getPassword()));
                String what = res.get("what").toString();
                if("1".equals(what)){
                    User user = (User)res.get("user");
                    finishLogin.finish(user);
                }else if("0".equals(what)){
                    Toast toast = new Toast();
                    toast.showToast(jFrame,5, ToastStatus.ERROR,"密码错误",2000);
                }else if("-1".equals(what)){
                    Toast toast = new Toast();
                    toast.showToast(jFrame,5, ToastStatus.ERROR,"用户不存在",2000);
                }
            }catch (SQLException e1){
                e1.printStackTrace();
            }

        }else if(e.getSource()==registerBtn){

        }
    }

    interface FinishLogin{
        void finish(User user);
    }

    public void setFinishLogin(FinishLogin finishLogin) {
        this.finishLogin = finishLogin;
    }
}

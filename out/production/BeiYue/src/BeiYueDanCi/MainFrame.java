package BeiYueDanCi;

import Toast.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    private Container container;
    private static MainFrame mainFrame;
    private static MainPanel mainPanel;
    private SpellPanel spellPanel;
    private LoginPanel loginPanel;
    private JMenuItem spellItem,profileItem,mainPageItem;
    private ImageIcon bg;
    private boolean userState = false;
    private User user=null;
    public MainFrame(){
        super("北月单词");
        container = getContentPane();
        container.setLayout(new BorderLayout());

        //初始化菜单
        JMenuBar jMenuBar = new JMenuBar();

        //页面
        JMenu pageMenu = new JMenu("页面");
        mainPageItem = new JMenuItem("主界面");
        pageMenu.add(mainPageItem);
        jMenuBar.add(pageMenu);

        //背诵
        JMenu reciteMenu = new JMenu("背诵");
        spellItem = new JMenuItem("拼写");
        reciteMenu.add(spellItem);
        jMenuBar.add(reciteMenu);
        //我的
        JMenu mineMenu = new JMenu("我的");
        profileItem = new JMenuItem("请登录");
        mineMenu.add(profileItem);
        jMenuBar.add(mineMenu);


        setJMenuBar(jMenuBar);

        //绑定监听事件
        spellItem.addActionListener(this);
        profileItem.addActionListener(this);
        mainPageItem.addActionListener(this);


        mainPanel = new MainPanel();
//        container.add(mainPanel);
        setContentPane(mainPanel);

        setSize(800,500);
        setLocationRelativeTo(null);//居中显示
        setVisible(true);
    }

    public static void main(String[] args){
        mainFrame = new MainFrame();

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==spellItem){
            if(user==null){
                loginPanel = new LoginPanel(mainFrame);
                loginPanel.setFinishLogin(new LoginPanel.FinishLogin() {
                    @Override
                    public void finish(User u) {
                        Toast toast = new Toast();
                        toast.showToast(mainFrame,5, ToastStatus.SUCCESS,"登录成功",2000);
                        userState = true;
                        user = u;
                        profileItem.setText("Hi,"+u.getUsername());
                        mainFrame.remove(loginPanel);
                        mainFrame.setContentPane(mainPanel);
                        mainFrame.setVisible(true);
                    }
                });
                mainFrame.remove(mainPanel);
                mainFrame.setContentPane(loginPanel);
            }else{
                spellPanel = new SpellPanel(user.getUid());
                spellPanel.setFinishSpell(new SpellPanel.FinishSpell() {
                    @Override
                    public void finish() {
                        mainFrame.remove(spellPanel);
                        mainFrame.setContentPane(mainPanel);
                        mainFrame.setVisible(true);
                    }
                });
                mainFrame.remove(mainPanel);
                mainFrame.setContentPane(spellPanel);
            }
            mainFrame.setVisible(true);
        }else if(e.getSource()==profileItem){
            if(!userState){
                loginPanel = new LoginPanel(mainFrame);
                loginPanel.setFinishLogin(new LoginPanel.FinishLogin() {
                    @Override
                    public void finish(User u) {
                        Toast toast = new Toast();
                        toast.showToast(mainFrame,5, ToastStatus.SUCCESS,"登录成功",2000);
                        userState = true;
                        user = u;
                        profileItem.setText("Hi,"+user.getUsername());
                        mainFrame.remove(loginPanel);
                        mainFrame.setContentPane(mainPanel);
                        mainFrame.setVisible(true);
                    }
                });
                mainFrame.remove(mainPanel);
                mainFrame.setContentPane(loginPanel);
                mainFrame.setVisible(true);
            }else{
                //跳转到个人界面
            }

        }else if(e.getSource()==mainPageItem){
            mainFrame.remove(getContentPane());
            mainFrame.setContentPane(mainPanel);
            mainFrame.setVisible(true);
        }
    }
}

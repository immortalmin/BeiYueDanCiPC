package BeiYueDanCi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MainPanel extends JPanel {

    private ImageIcon bg;//背景图片
    public MainPanel(){
        setLayout(new BorderLayout());

        /*现在只能显示一张图片，如果后期要在页面上显示签到之类的，需要用到JLayeredPane(分层显示），可以看下面的博客
        https://blog.csdn.net/CL18652469346/article/details/53114707/*/
        /*图片资源的获取，其中相对路径是在out/production/BeiYue路径下的*/
        /*图片没法占满整个JLabel,拉大边框，就会有空白*/
        /*ImageIcon img = new ImageIcon(getClass().getResource("./img/bg1.jpg"));
        Image img2 = img.getImage();
        Image img3 = img2.getScaledInstance(label.getWidth(),label.getHeight(),img2.SCALE_DEFAULT);
        bg = new ImageIcon(img3);
        JLabel label = new JLabel(bg);
        this.add(label);*/

        JLabel titleLabel = new JLabel("北月单词",JLabel.CENTER);
        titleLabel.setFont(new Font("黑体",Font.BOLD,60));
        titleLabel.setVerticalAlignment(SwingConstants.CENTER);
        JLabel versionLabel = new JLabel("V1.0.0",JLabel.CENTER);
        versionLabel.setFont(new Font("宋体",Font.PLAIN,30));
        versionLabel.setVerticalAlignment(SwingConstants.CENTER);

        this.add(titleLabel);
        this.add(versionLabel,BorderLayout.SOUTH);

    }


}

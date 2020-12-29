package BeiYueDanCi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpellPanel extends JPanel implements KeyListener, ActionListener {

    private JLabel wordLabel,correctLabel;
    private JTextField spellField;
    private ArrayList<Word> wordList;
    private boolean wordFlag[];//如果背过了，则标记为true,否则为false
    private int index=0;
    private boolean state = true;//true:等待用户输入结果  false:等待判断结果
    private boolean newRound = true;//true：表示是新一轮的第一次背诵  false：这一轮这个单词已经背错过了
    private boolean judgeResult = false;//结果是否正确
    private final WordManager wordManager = new WordManager();
    private final DateUtil dateUtil = new DateUtil();
    private final int add_day[] = {1,2,4,7,15};
    private int finishNum=0;//已经完成的单词数
    private int requireNum = 10;//需要完成的单词数
    private FinishSpell finishSpell;
    private ReadMp3 readMp3;
    private String uid;
    ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);

    public SpellPanel(String uid) {
        this.uid = uid;
        getWordList();
        setLayout(new FlowLayout());

        Panel wordPanel = new Panel();
        wordPanel.setLayout(new BorderLayout());
        wordLabel = new JLabel(wordList.get(index).getWord_ch_short(),JLabel.CENTER);
        wordLabel.setFont(new Font("宋体",Font.BOLD,30));
        wordPanel.add(wordLabel);

        Panel spellPanel = new Panel();
        spellPanel.setLayout(new BorderLayout());
        spellField = new JTextField(15);
        spellField.setFont(new Font("宋体",Font.BOLD,30));
        spellField.addKeyListener(this);
        spellPanel.add(spellField);

        Panel correctPanel = new Panel();
        correctPanel.setLayout(new BorderLayout());
        correctLabel = new JLabel("正确答案："+wordList.get(index).getWord_en(),JLabel.CENTER);
        correctLabel.setForeground(Color.RED);
        correctLabel.setFont(new Font("宋体",Font.ITALIC,25));
        correctPanel.add(correctLabel);
        correctLabel.setVisible(false);

        //playVoice(wordList.get(index).getWord_en());


        Box verticalBox = Box.createVerticalBox();
        verticalBox.add(Box.createVerticalStrut(100));
        verticalBox.add(wordPanel);
        verticalBox.add(Box.createVerticalStrut(20));
        verticalBox.add(spellPanel);
        verticalBox.add(Box.createVerticalStrut(50));
        verticalBox.add(correctPanel);
        this.add(verticalBox);
    }

    /**
     * 获取要背诵的单词列表
     */
    public void getWordList(){
        try{
            wordList = wordManager.getReviewWords(uid,requireNum);
        }catch (SQLException e){
            e.printStackTrace();
        }
        wordFlag = new boolean[wordList.size()];
//        System.out.println(wordList.toString());
//        for(int i=0;i<10;i++){
//            HashMap<String,Object> word = new HashMap<>();
//            word.put("word_en","accuse"+i);
//            word.put("word_ch","指责"+i);
//            wordList.add(word);
//        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public interface FinishSpell{
        void finish();
    }

    public void setFinishSpell(FinishSpell finishSpell) {
        this.finishSpell = finishSpell;
    }

    /**
     * 判断用户输入的单词是否正确
     * 只判断英文字母的排序是否正确，不分大小写
     * @param a
     * @param b
     * @return 返回是否相等
     */
    private boolean wordJudge(String a,String b){
        if(a.length()==0||b.length()==0) return false;
        a = a.replaceAll("[^a-zA-Z]","");
        a = a.toLowerCase();
        b = b.replaceAll("[^a-zA-Z]","");
        b = b.toLowerCase();
        return a.equals(b);
    }

    public void Judge(){
        Word word = wordList.get(index);
        if(wordJudge(wordList.get(index).getWord_en(),spellField.getText())){
            if(newRound){
                wordFlag[index]=true;
                finishNum++;
                word.setCorrect_times(word.getCorrect_times()+1);
                word.setLast_date(dateUtil.getToday());
                if(word.getCorrect_times()>=6){
                    word.setReview_date("1970-01-01");
                }else{
                    word.setReview_date(dateUtil.getAfterDay(add_day[word.getCorrect_times()-1]));
                }
                wordList.set(index,word);
            }
            correctLabel.setForeground(Color.GREEN);
            correctLabel.setText("回答正确√");
            correctLabel.setVisible(true);
            spellField.setText(wordList.get(index).getWord_en());
            judgeResult = true;
        }else{
            correctLabel.setForeground(Color.RED);
            correctLabel.setText("正确答案："+wordList.get(index).getWord_en());
            correctLabel.setVisible(true);
            judgeResult = false;
            newRound = false;
            word.setError_times(word.getError_times()+1);
        }
        state = false;
    }

    /**
     * 每轮结束之后的重置
     * @param what 0：回答错误  1：回答正确
     */
    public void resetAll(int what) {
        if(what==0){
            correctLabel.setVisible(false);
            spellField.setText("");
        }else{
            if (finishNum>=wordList.size()){
                JOptionPane.showConfirmDialog(null,"背诵完成！","完成",JOptionPane.CLOSED_OPTION);
                //暂时不更新数据
                /*try{
                    wordManager.updateSpellWords(wordList);
                }catch (SQLException e){
                    e.printStackTrace();
                }*/
                finishSpell.finish();
                return;
            }
            index++;
            if(index==wordList.size()) index=0;
            while(wordFlag[index]){//如果这个单词已经背过了，找到下一个没背过的单词
                index++;
                if(index==wordList.size()) index=0;
            }
            //playVoice(wordList.get(index).getWord_en());
            wordLabel.setText(wordList.get(index).getWord_ch_short());
            spellField.setText("");
            correctLabel.setVisible(false);
            judgeResult=false;
            newRound = true;
        }
        state = true;
    }

    /**
     * 有些单词的音频播放不完整，以后再搞这个吧
     * @param word
     */
    private void playVoice(String word) {
        try{
            readMp3 = new ReadMp3(word);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(!state){//已经判断出答案时
            if(!judgeResult){//答案是错误的
                resetAll(0);
            }else{//答案是正确的
                resetAll(1);
            }
            return;
        }
        if(e.getKeyChar()==KeyEvent.VK_ENTER){
            Judge();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}

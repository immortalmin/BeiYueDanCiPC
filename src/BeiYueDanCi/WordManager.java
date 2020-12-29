package BeiYueDanCi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WordManager extends DBConnection{
    private Connection conn = null;
    public WordManager(){

    }

    /**
     * 查询拼写的单词列表
     * @param uid 用户id
     * @param number 单词数量
     * @return
     * @throws SQLException
     */
    public ArrayList<Word> getReviewWords(String uid,int number) throws SQLException{
        conn = getConnection();
        ArrayList<Word> wordList = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            String review_date = "2020/12/31";
            rs = statement.executeQuery("SELECT cid,collect.`wid`,word_group AS word_en,C_meaning AS word_ch,correct_times,error_times," +
                    "last_date,review_date,'0' AS dict_source FROM collect INNER JOIN words ON collect.`wid`=words.`wid` WHERE uid="+uid+" AND " +
                    "correct_times<=5 AND review_date<='"+review_date+"' UNION ALL SELECT cid,collect.`kid` AS wid,word_en,word_ch,correct_times," +
                    "error_times,last_date,review_date,'1' AS dict_source FROM collect INNER JOIN k_words ON collect.`kid`=k_words.`wid` WHERE " +
                    "uid="+uid+" AND correct_times<=5 AND review_date<='"+review_date+"' limit "+number);
            while(rs.next()){
                Word word = new Word();
                word.setCid(rs.getString("cid"));
                word.setWid(rs.getString("wid"));
                word.setWord_en(rs.getString("word_en"));
                word.setWord_ch(rs.getString("word_ch"));
                word.setCorrect_times(Integer.parseInt(rs.getString("correct_times")));
                word.setError_times(Integer.parseInt(rs.getString("error_times")));
                word.setLast_date(rs.getString("last_date"));
                word.setReview_date(rs.getString("review_date"));
                word.setDict_source(rs.getString("dict_source"));
                wordList.add(word);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(statement!=null) statement.close();
            if(rs!=null) rs.close();
            closeConnection();
        }
        return wordList;
    }

    /**
     * 更新背诵数据
     * @param wordList 单词列表
     * @return
     * @throws SQLException
     */
    public void updateSpellWords(ArrayList<Word> wordList) throws SQLException{
        conn = getConnection();
        Statement statement = null;
        ResultSet rs = null;
        try{
            statement = conn.createStatement();
            for(int i=0;i<wordList.size();i++){
                Word word = wordList.get(i);
                statement.executeUpdate("UPDATE collect SET correct_times="+word.getCorrect_times()+",error_times="+word.getError_times()
                        +",last_date=\""+word.getLast_date()+"\",review_date=\""+word.getReview_date()+"\" WHERE cid="+word.getCid());

//                rs = statement.executeQuery("SELECT cid,collect.`wid`,word_group AS word_en,C_meaning AS word_ch,correct_times,error_times," +
//                        "last_date,review_date,'0' AS dict_source FROM collect INNER JOIN words ON collect.`wid`=words.`wid` WHERE uid="+uid+" AND " +
//                        "correct_times<=5 AND review_date<='"+review_date+"' UNION ALL SELECT cid,collect.`kid` AS wid,word_en,word_ch,correct_times," +
//                        "error_times,last_date,review_date,'1' AS dict_source FROM collect INNER JOIN k_words ON collect.`kid`=k_words.`wid` WHERE " +
//                        "uid="+uid+" AND correct_times<=5 AND review_date<='"+review_date+"' limit ");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(statement!=null) statement.close();
            if(rs!=null) rs.close();
            closeConnection();
        }
    }


}

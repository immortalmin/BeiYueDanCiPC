package BeiYueDanCi;

public class Word {
    private String cid;
    private String wid;
    private String word_en;
    private String word_ch;
    private String word_ch_short;
    private int correct_times;
    private int error_times;
    private String last_date;
    private String review_date;
    private String dict_source;

    Word(){}
    Word(String cid,String wid,String word_en,String word_ch,String correct_times,String error_times,String last_date,String review_date,String dict_source){
        this.cid = cid;
        this.wid = wid;
        this.word_en = word_en;
        this.word_ch = word_ch;
        this.word_ch_short = (word_ch.length()>20?word_ch.substring(0,20):word_ch);
        this.correct_times = Integer.parseInt(correct_times);
        this.error_times = Integer.parseInt(error_times);
        this.last_date = last_date;
        this.review_date = review_date;
        this.dict_source = dict_source;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getWord_en() {
        return word_en;
    }

    public void setWord_en(String word_en) {
        this.word_en = word_en;
    }

    public String getWord_ch() {
        return word_ch;
    }

    public void setWord_ch(String word_ch) {
        this.word_ch = word_ch;
        if(word_ch!=null)
            this.word_ch_short = (word_ch.length()>20?word_ch.substring(0,20):word_ch);
    }

    public String getWord_ch_short() {
        return word_ch_short;
    }

    public void setWord_ch_short(String word_ch_short) {
        this.word_ch_short = word_ch_short;
    }

    public int getCorrect_times() {
        return correct_times;
    }

    public void setCorrect_times(int correct_times) {
        this.correct_times = correct_times;
    }

    public int getError_times() {
        return error_times;
    }

    public void setError_times(int error_times) {
        this.error_times = error_times;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    public String getDict_source() {
        return dict_source;
    }

    public void setDict_source(String dict_source) {
        this.dict_source = dict_source;
    }

    @Override
    public String toString() {
        return "BeiYueDanCi.Word{" +
                "cid='" + cid + '\'' +
                ", wid='" + wid + '\'' +
                ", word_en='" + word_en + '\'' +
                ", word_ch='" + word_ch + '\'' +
                ", word_ch_short='" + word_ch_short + '\'' +
                ", correct_times='" + correct_times + '\'' +
                ", error_times='" + error_times + '\'' +
                ", last_date='" + last_date + '\'' +
                ", review_date='" + review_date + '\'' +
                ", dict_source='" + dict_source + '\'' +
                '}';
    }
}

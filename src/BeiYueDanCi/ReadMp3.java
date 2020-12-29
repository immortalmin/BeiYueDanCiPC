package BeiYueDanCi;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ReadMp3 {

    ReadMp3(String word) throws IOException, Exception{
        word = word.replaceAll("sb.","somebody").replaceAll("sth.","something").replaceAll("/"," or ");
        URL url = new URL("http://dict.youdao.com/dictvoice?type=1&audio="+ URLEncoder.encode(word.toLowerCase()));
        URLConnection con = null;
        try{
            con = url.openConnection();
        }catch(IOException e){
            e.printStackTrace();
        }

        BufferedInputStream bis = new BufferedInputStream(con.getInputStream());
        Bitstream bt = new Bitstream(bis);

        //获取mp3时间长度
        Header header = bt.readFrame();
        int mp3Length = con.getContentLength();
        int time = (int) header.total_ms(mp3Length);
        Player player = new Player(bis);
        new Thread(()->{
            try{
                player.play();
            }catch (JavaLayerException e){
                e.printStackTrace();
            }
        }).start();

    }

}
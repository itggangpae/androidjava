package kakao.itstudy.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ParsingActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper()){
      @Override
      public void handleMessage(Message msg){
          String data = (String)msg.obj;
          TextView result = (TextView)findViewById(R.id.result);
          result.setText(data);
      }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parsing);

        Button btnHTML = (Button)findViewById(R.id.btnHTML);
        btnHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스레드를 생성해서 시작
                new Thread(){
                    @Override
                    public void run(){
                        try{
                            //문자열을 다운로드
                            URL url = new URL("https://finance.naver.com/");
                            //연결
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //옵션 설정
                            con.setConnectTimeout(30000);
                            con.setUseCaches(false);

                            //문자열을 읽기 위한 스트림 생성
                            BufferedReader br = null;
                            //인코딩 방식에 따라 스트림을 다르게 생성
                            String headerType = con.getContentType();
                            if(headerType.toUpperCase().indexOf("EUC-KR") >= 0){
                                br = new BufferedReader(
                                        new InputStreamReader(
                                                con.getInputStream(), "EUC-KR"));
                            }else{
                                br = new BufferedReader(
                                        new InputStreamReader(
                                                con.getInputStream(), "UTF-8"));
                            }
                            //문자열 읽기
                            StringBuilder sb = new StringBuilder();
                            while(true){
                                String line = br.readLine();
                                if(line == null){
                                    break;
                                }
                                sb.append(line + "\n");
                            }

                            //다운로드 받은 문자열을 확인
                            //Log.e("다운로드 받은 문자열", sb.toString());

                            //HTML 파싱 수행
                            //HTML 파일을 메모리에 펼치기
                            Document doc = Jsoup.parse(sb.toString());
                            //찾고자 하는 선택자를 이용해서 Elements를 찾아오기
                            //a 태그를 전부 찾아오기
                            Elements elements = doc.select("a");

                            //파싱한 결과를 저장할 문자열 객체
                            //ListView에 출력할 때는 List를 이용
                            StringBuilder sbResult = new StringBuilder();
                            //하나씩 순회
                            for(Element element : elements){
                                //문자열을 가져오기
                                String contents = element.text();
                                sbResult.append(contents + "\n");
                            }
                            //결과를 핸들러에게 전송해서 출력
                            Message message = new Message();
                            message.obj = sbResult.toString();
                            handler.sendMessage(message);

                            br.close();
                            con.disconnect();

                        }catch(Exception e){
                            Log.e("예외 발생 - HTML", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });
    }
}
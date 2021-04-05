package kakao.itstudy.webserverparameter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    //결과를 출력할 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    //결과를 출력
                    TextView tfResult = (TextView)findViewById(R.id.tfResult);
                    //넘겨받은 데이터 읽어오기
                    String display = (String)msg.obj;
                    tfResult.setText(display);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //버튼을 찾고 버튼의 클릭 이벤트 핸들러 작성
        Button btnKakao = (Button)findViewById(R.id.btnKakao);
        btnKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다운로드 받는 스레드를 생성해서 시작
                new Thread(){
                    @Override
                    public void run(){
                        try{
                            //URL 작성
                            //파라미터 인코딩하기
                            String query = URLEncoder.encode("자바", "utf-8");
                            URL url = new URL(
                                    "https://dapi.kakao.com/v3/search/book?size=50&query="
                                            + query);
                            //HttpURLConnection을 생성하고 옵션 작성
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();
                            //con.setRequestMethod("GET");
                            con.setConnectTimeout(30000);
                            con.setUseCaches(false);
                            //헤더 설정
                            con.setRequestProperty(
                                    "Authorization", "KakaoAK 15c9da685820553ed698bac71b861f3b");

                            //다운로드 받는 코드 작성 - 문자 냐 문자가 아니냐에 따라 달라짐
                            //문자인 경우는 인코딩 값을 아느냐 그렇지 않느냐에 따라 달라짐

                            //문자열의 경우는 문자열 스트림(BufferedReader-읽기, PrintWriter-쓰기)을 생성
                            BufferedReader br =
                                    new BufferedReader(
                                            new InputStreamReader(
                                                    con.getInputStream()));
                            //다운로드 받은 내용을 저장할 객체
                            StringBuilder sb = new StringBuilder();
                            while(true){
                                //스트림에서 한 줄 읽기 - 읽은 내용이 없으면 null을 리턴
                                String line = br.readLine();
                                if(line == null){
                                    break;
                                }
                                sb.append(line + "\n");
                            }
                            //다운로드 받은 내용을 확인
                            //Log.e("다운받은 내용" , sb.toString());

                            //정리 작성
                            br.close();
                            con.disconnect();

                            //다운로드 받은 데이터 파싱
                            String json = sb.toString();
                            //결과를 저장할 객체 생성
                            String result = "";
                            if(json != null){
                                //문자열을 객체로 변환
                                JSONObject jsonObject = new JSONObject(json);

                                //데이터 개수를 찾아오기 위해서 meta 키의 데이터를 객체로 변환
                                JSONObject meta = jsonObject.getJSONObject("meta");
                                //total_count의 내용을 정수로 읽어오기
                                int count = meta.getInt("total_count");
                                result = result + "데이터 개수:" + count +"\n";

                                //documents 키의 값을 배열로  가져오기
                                JSONArray documents = jsonObject.getJSONArray("documents");
                                for(int i=0; i<documents.length(); i=i+1){
                                    //인덱스에 해당하는 데이터 가져오기
                                    JSONObject item = documents.getJSONObject(i);

                                    String title = item.getString("title");
                                    int price = item.getInt("price");
                                    result = result + "제목:" + title + " 가격:" + price + "\n";
                                }
                                //파싱이 종료되서 결과를 핸들러에게 전송
                                Message msg = new Message();
                                msg.obj = result;
                                msg.what = 1;
                                handler.sendMessage(msg);


                            }



                        }catch(Exception e){
                            Log.e("다운로드 예외", e.getLocalizedMessage());
                            Snackbar.make(
                                    getWindow().getDecorView().getRootView(),
                                    e.getLocalizedMessage(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    }
                }.start();
            }
        });
    }
}
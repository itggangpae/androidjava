package kakao.itstudy.network;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HtmlDownloadActivity extends AppCompatActivity {

    //데이터를 출력할 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            //전송된 데이터 가져오기 - 문자열
            String html = (String)msg.obj;
            //텍스트 뷰에 출력
            TextView tfResult = (TextView)findViewById(R.id.tfResult);
            tfResult.setText(html);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_download);

        //EditText 와 Button을 찾아오기
        EditText tfUrl = (EditText)findViewById(R.id.tfUrl);
        Button btnDownload = (Button)findViewById(R.id.btnDownload);

        //버튼 클릭 이벤트 처리
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    public void run(){
                        try{
                            //다운로드 받을 URL을 생성
                            URL url = new URL(tfUrl.getText().toString());

                            //연결 객체를 생성
                            HttpURLConnection con =
                                    (HttpURLConnection)url.openConnection();

                            //필요한 옵션 설정
                            con.setConnectTimeout(30000); //연결 제한 시간 설정
                            con.setUseCaches(false);//캐시 사용 하지 않음
                            //자주 변경되는 데이터는 false
                            //변경되지 않는 데이터는 true
                            con.setRequestMethod("GET");//요청 방식 설정
                            //get 생략 가능

                            //응답이 제대로 오면
                            if(con.getResponseCode() == HttpURLConnection.HTTP_OK){
                                //전송된 문자열 읽기 - 무조건 UTF-8로 읽어옵니다.
                                /*
                                BufferedReader br =
                                        new BufferedReader(
                                                new InputStreamReader(
                                                        con.getInputStream()));

                                 */

                                BufferedReader br;
                                //연결된 곳의 헤더 정보를 가져옵니다.
                                String headerType = con.getContentType();
                                //문자열 비교 - EUC-KR이 포함되어 있는지 확인
                                if(headerType.toUpperCase().indexOf("EUC-KR") >= 0){
                                    br = new BufferedReader(
                                            new InputStreamReader(
                                                con.getInputStream(),
                                                    "EUC-KR"));

                                }else{
                                    br = new BufferedReader(
                                            new InputStreamReader(
                                                    con.getInputStream(),
                                                    "UTF-8"));
                                }


                                //문자열을 추가할 StringBuilder 생성
                                StringBuilder sb = new StringBuilder();
                                //문자열을 줄단위로 읽어서 sb에 추가
                                while(true){
                                    String line = br.readLine(); //한 줄 읽기
                                    //읽은게 없으면 읽기 종료
                                    if(line == null){
                                        break;
                                    }
                                    sb.append(line + "\n");
                                }
                                //연결 해제
                                br.close();
                                con.disconnect();

                                //핸들러에게 메시지를 전송
                                Message msg = new Message();
                                msg.obj = sb.toString();
                                handler.sendMessage(msg);
                            }



                        }catch(Exception e){
                            Log.e("예외 발생", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });
    }
}
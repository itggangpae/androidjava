package kakao.itstudy.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    //Handler 객체 생성
    Handler handler = new Handler(Looper.getMainLooper()){
        //핸들러가 메시지를 받으면 호출되는 메소드
        @Override
        public void handleMessage(Message msg){

            //데이터를 받아서 UI를 갱신
            //전송받은 데이터 가져오기 - 원래의 자료형으로 변환
            //Object 자료형은 참조형으로만 형변환이 가능한데 1.8 버전 부터는 기본형으로도
            //변환이 가능
            int i = (Integer)msg.obj;

            //TextView에 i를 출력
            TextView resultView = (TextView)findViewById(R.id.resultView);
            resultView.setText(i + "");

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        //스레드를 생성해서 핸들러를 호출
        new Thread(){
            @Override
            public void run(){
                try{
                    for(int i=0; i<10; i++){
                        //1초 대기
                        Thread.sleep(1000);
                        //Message 객체를 만들어서 데이터를 저장
                        Message msg = new Message();
                        msg.obj = i;
                        //핸들러에게 메시지 보내기
                        handler.sendMessage(msg);
                    }
                }catch(Exception e){
                    Log.e("예외 발생", e.getLocalizedMessage());
                }
            }
        }.start();
        */

        new Thread(){
            int value;
            public void run(){
                try{
                    for(int i=0; i<10; i++){
                        Thread.sleep(1000);
                        handler.post(new Runnable(){
                            public void run(){
                                TextView resultView =
                                        (TextView)findViewById(R.id.resultView);
                                resultView.setText(value +"");
                                value++;
                            }
                        });
                    }
                }catch(Exception e){

                }
            }
        }.start();
    }
}
package kakao.itstudy.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressActivity extends AppCompatActivity {
    //진행율 표시를 위한 대화상자
    ProgressDialog progressDialog;
    //진행율 표시를 위한 뷰
    ProgressBar progressBar;

    private TextView tfResult;

    //UI를 주기적으로 갱신할 핸들러 객체

    //이렇게만 만드는 경우는 handler의 post 메소드를 호출해서 Runnable 객체를 대입해서
    //UI를 갱신 - 다른 작업이 없을 때 수
    //Handler handler = new Handler(Looper.getMainLooper());행

    //메시지를 전송해서 바로 작업을 수행시키는 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
      //핸들러에게 메시지가 전송되면 호출되는 메소드
      @Override
      public void handleMessage(Message msg){
          //전송받은 코드를 저장
          int what = msg.what;
          //데이터를 가져옵니다.
          int i = (Integer)msg.obj;
          //내용을 출력
          tfResult.setText("i=" + i);
          if(i < 100){
              progressDialog.setProgress(i);
              progressBar.setProgress(i);
              progressBar.setVisibility(View.VISIBLE);
          }else{
              progressDialog.dismiss();
              progressBar.setVisibility(View.INVISIBLE);
          }
      }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        //ProgressBar 찾아오기
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        //텍스트 뷰 찾아오기
        tfResult = (TextView)findViewById(R.id.tfResult);

        //버튼의 클릭 이벤트 처리 코드
        Button btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                /*
                //0.05초마다 1부터 100까지 증가시키면서(주기적인 작업)
                //tfResult에 출력하기
                try{
                    for(int i=1; i<=100; i++){
                        //tfResult.setText("i=" + i);

                        //전송할 메시지를 생성
                        Message msg = new Message();
                        //데이터 저장
                        msg.obj = i;
                        //다른 곳과 구분하고자 할 때는 what을 이용
                        msg.what = 1;

                        //메시지 전송
                        handler.sendMessage(msg);
                        Thread.sleep(50);
                    }
                }catch (Exception e){
                    Log.e("예외 발생", e.getLocalizedMessage());
                }
                */

                //스레드를 이용해서 핸들러를 호출
                new Thread(){
                    @Override
                    public void run(){
                        try{
                            for(int i=1; i<=100; i++){
                                Message msg = new Message();
                                msg.obj = i;
                                msg.what = 1;
                                handler.sendMessage(msg);
                                Thread.sleep(50);
                            }
                        }catch(Exception e){
                            Log.e("예외 발생", e.getMessage());
                        }
                    }
                }.start();

                progressDialog = new ProgressDialog(ProgressActivity.this);
                progressDialog.setProgress(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setTitle("다운로드 중");
                progressDialog.setMessage("잠시 대기...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }
        });
    }
}
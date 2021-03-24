package kakao.itstudy.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerActivity extends AppCompatActivity {
    TextView lblTime;
    Button btnStart, btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //뷰의 참조 찾아오기
        lblTime = (TextView)findViewById(R.id.lblTime);
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);

        //타이머 생성 - 1초마다 수행
        CountDownTimer timer = new CountDownTimer(
                1000000 * 100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //현재 날짜 및 시간 가져오기
                Date date = new Date();
                //날짜 및 시간을 문자열로 만들기 위한 포맷 설정
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd hh:mm:ss");
                //포맷에 맞춘 문자열을 생성
                String time = sdf.format(date);
                //TextView에 시간 출력
                lblTime.setText(time);

            }

            @Override
            public void onFinish() {
                lblTime.setText("타이머 종료 ");
            }
        };

        //버튼을 클릭 했을 때 처리
        btnStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //타이머 시작
                timer.start();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //타이머 시작
                timer.cancel();
            }
        });

        //2초마다 주기적으로 텍스트 뷰에 텍스트를 출력
        //주기적으로 화면 갱신을 할려면 Timer 나 Handler를 사용해야 합니다.
        try{
            for(int i=0; i<10; i=i+1){
                Thread.sleep(2000);
                lblTime.setText("i=" + i);
                Log.e("i", i +"");
            }
        }catch(Exception e){}
    }
}
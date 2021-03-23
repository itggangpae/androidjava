package kakao.itstudy.androidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        //순서대로 배치하는 레이아웃 을 생성
        LinearLayout linear = new LinearLayout(this);
        //버튼을 생성
        Button btn = new Button(this);
        btn.setText("직접 코딩한 버튼");
        //버튼을 레이아웃에 배치
        linear.addView(btn);
        //레이아웃을 화면에 보여지도록 Activity의 뷰로 설정
        setContentView(linear);
    }
}
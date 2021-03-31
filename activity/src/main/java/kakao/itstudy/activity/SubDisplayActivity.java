package kakao.itstudy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SubDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_display);

        Button btnDisplay = (Button)findViewById(R.id.btnDisplay);
        btnDisplay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //출력 영역 찾아오기
                LinearLayout container =
                        (LinearLayout)findViewById(R.id.container);
                //sub.xml 내용을 전개하기 위한 Inflater를 찾아오기
                LayoutInflater inflater =
                        (LayoutInflater)getSystemService(
                                Context.LAYOUT_INFLATER_SERVICE);
                //기존 Layout에 sub.xml 파일 출력하기
                inflater.inflate(R.layout.sub, container, true);



            }
        });
    }
}
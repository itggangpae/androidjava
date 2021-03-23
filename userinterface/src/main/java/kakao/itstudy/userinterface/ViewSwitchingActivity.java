package kakao.itstudy.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ViewSwitchingActivity extends AppCompatActivity {
    //버튼 3개와 LinearLayout 3개
    Button btn1, btn2, btn3;
    LinearLayout page1, page2, page3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_switching);


        //버튼 3개와 Layout3개를 찾아오기
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);

        page1 = (LinearLayout)findViewById(R.id.page1);
        page2 = (LinearLayout)findViewById(R.id.page2);
        page3 = (LinearLayout)findViewById(R.id.page3);

        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.VISIBLE);
                page2.setVisibility(View.INVISIBLE);
                page3.setVisibility(View.INVISIBLE);

                btn1.setVisibility(View.INVISIBLE);
                btn2.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.VISIBLE);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.VISIBLE);
                page3.setVisibility(View.INVISIBLE);

                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.INVISIBLE);
                btn3.setVisibility(View.VISIBLE);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                page1.setVisibility(View.INVISIBLE);
                page2.setVisibility(View.INVISIBLE);
                page3.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.INVISIBLE);
            }
        });

    }
}
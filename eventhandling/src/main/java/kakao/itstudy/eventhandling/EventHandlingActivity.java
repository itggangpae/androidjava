package kakao.itstudy.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EventHandlingActivity extends AppCompatActivity {
    //뷰 참조를 위한 변수
    LinearLayout container;
    EditText tfEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_handling);

        container = (LinearLayout)findViewById(R.id.container);
        tfEdit = (EditText)findViewById(R.id.tfEdit);

        //container 터치 이벤트 처리
        container.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //키보드 관리 객체 생성
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                //tfEdit에 입력 중인 키보드를 숨기기
                imm.hideSoftInputFromWindow(tfEdit.getWindowToken(), 0);


                return false;
            }
        });
    }
}
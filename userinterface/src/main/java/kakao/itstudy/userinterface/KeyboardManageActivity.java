package kakao.itstudy.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class KeyboardManageActivity extends AppCompatActivity {
    //뷰들의 참조를 저장할 프로퍼티를 선언
    EditText tfName;
    Button btnKeyboardShow, btnKeyboardHide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_manage);

        //뷰를 찾아오기
        tfName = (EditText)findViewById(R.id.tfName);
        btnKeyboardShow = (Button)findViewById(R.id.btnKeyboardShow);
        btnKeyboardHide = (Button)findViewById(R.id.btnKeyboardHide);

        //버튼 클릭 이벤트 처리
        btnKeyboardShow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                //tfName에 입력하는 키보드를 화면에 출력합니다.
                imm.showSoftInput(tfName, 0);
            }
        });

        btnKeyboardHide.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                InputMethodManager imm =
                        (InputMethodManager)getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                //tfName에 입력하는 키보드를 화면에 출력합니다.
                imm.hideSoftInputFromWindow(
                        tfName.getWindowToken(), 0);
            }
        });

    }
}
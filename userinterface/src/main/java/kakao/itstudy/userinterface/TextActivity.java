package kakao.itstudy.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class TextActivity extends AppCompatActivity {
    //xml 파일에 디자인 한 뷰의 참조를 저장하기 위한 변수
    TextView txtView;
    EditText txtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);

        //xml 파일에 디자인 한 뷰 찾아오기
        txtView = (TextView)findViewById(R.id.txtView);
        txtId = (EditText)findViewById(R.id.txtId);

        //txtId 의 내용이 변경되면 작업을 해주는 코드
        txtId.addTextChangedListener(new TextWatcher(){
            //EditText의 내용이 변경되기 직전에 호출되는 메소드
            //s가 입력된 문자열
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            //텍스트가 변경된 후에 호출되는 메소드
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //EditText의 내용을 TextView에 출력
                txtView.setText(txtId.getText());
            }
            //텍스트가 변경된 후에 호출되는 메소드
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
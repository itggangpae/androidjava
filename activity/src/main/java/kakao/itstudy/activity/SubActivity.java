package kakao.itstudy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //자신을 출력한 Activity가 전달한 데이터를 가져오기
        //데이터가 없으면 null이 리턴됩니다.
        String msg = getIntent().getStringExtra("data");
        //출력하기
        EditText subEdit = (EditText)findViewById(R.id.subEdit);
        subEdit.setText(msg);

        //버튼을 누르면 MainActivity로 돌아가는 처리
        Button btnGoMain = (Button)findViewById(R.id.btnGoMain);
        btnGoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //subEdit에 입력한 내용을 가져오기
                String msg = subEdit.getText().toString();
                //데이터 저장
                Intent intent = new Intent();
                intent.putExtra("subData", msg);
                //데이터 전송
                setResult(RESULT_OK, intent);

                //현재 Activity를 제거
                finish();
            }
        });
    }
}
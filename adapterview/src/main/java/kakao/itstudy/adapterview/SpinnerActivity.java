package kakao.itstudy.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class SpinnerActivity extends AppCompatActivity {
    //스피너는 처음 화면에 출력이 될 때 선택이 되었다고 선택 이벤트를 발생시킵니다.
    //처음 출력할 때는 선택 이벤트를 사용하지 않기 위한 프로퍼티
    boolean mInitSpinner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);

        //Spinner 가져오기
        Spinner spinner = (Spinner)findViewById(R.id.selecthero);
        spinner.setPrompt("데이터베이스를 선택하세요!!");

        //Adapter 생성하기
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        SpinnerActivity.this,
                        R.array.dbms,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        //spinner에 adapter 설정
        spinner.setAdapter(adapter);

        //아이템을 선택했을 때 이벤트 처리
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){

                    //항목을 선택했을 때 처리
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position,
                                               long id) {
                        if(mInitSpinner == true) {
                            //선택한 항목 가져오기
                            Snackbar.make(
                                    getWindow().getDecorView().getRootView(),
                                    adapter.getItem(position),
                                    Snackbar.LENGTH_LONG).show();
                        }else{
                            mInitSpinner = true;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


    }
}
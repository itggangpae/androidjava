package kakao.itstudy.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    //ListView 출력 관련 프로퍼티
    ListView listview;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        //ListView 출력 관련 초기화 작업
        listview = (ListView)findViewById(R.id.listview);

        list = new ArrayList<>();
        list.add("IoC");
        list.add("DI");
        list.add("AOP");

        //android.R 로 시작하면 안드로이드에서 제공하는 리소스
        //R 로 시작하면 사용자가 삽입한 리소스
        /*
        adapter = new ArrayAdapter<>(
                ListViewActivity.this,
                android.R.layout.simple_list_item_1,
                list);

         */

        //라디오 버튼을 옆에 배치하는 모양으로 설정
        /*
        adapter = new ArrayAdapter<>(
                ListViewActivity.this,
                android.R.layout.simple_list_item_single_choice,
                list);

         */

        //체크 박스 버튼을 옆에 배치하는 모양으로 설정
        adapter = new ArrayAdapter<>(
                ListViewActivity.this,
                android.R.layout.simple_list_item_multiple_choice,
                list);

        //ListView에 Adapter 설정
        listview.setAdapter(adapter);

        //구분선 설정
        listview.setDivider(new ColorDrawable(Color.CYAN));
        listview.setDividerHeight(3);
        //listview의 선택모드 설정
        //listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //ListView에서 항목을 클릭했을 때 처리
        listview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
            @Override
            //첫번째 매개변수는 listview
            //두번째 매개변수는 클릭한 항목 뷰
            //세번째 매개변수는 누른 항목의 인덱스
            //네번째 매개변수는 항목 뷰의 아이디
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item = list.get(position);
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        item, Snackbar.LENGTH_LONG).show();
            }
        });

        //데이터 삽입 버튼을 눌렀을 때 처리
        Button btninsert = (Button)findViewById(R.id.btninsert);
        btninsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //입력한 내용 가져오기
                EditText newitem = (EditText)findViewById(R.id.newitem);
                String item = newitem.getText().toString().trim();
                //전송하기 전에 유효성 검사를 수행 - 널 체크, 중복 검사 등 등
                if(item.length() <= 0){
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "아이템은 필수 입력입니다.", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                //list의 데이터를 순회하면서 모두 대문자로 변경해서 비교해서 중복 검사
                for(String spring:list){
                    if(spring.toUpperCase().equals(item.toUpperCase())) {
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "아이템은 중복 될 수 없습니다.", Snackbar.LENGTH_LONG)
                                .show();
                        return;
                    }
                }
                //list에 삽입
                list.add(item);
                //listview 업데이트
                adapter.notifyDataSetChanged();
                newitem.setText("");
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "아이템이 정상적으로 추가되었습니다.", Snackbar.LENGTH_LONG)
                        .show();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(newitem.getWindowToken(), 0);

            }
        });

        //삭제 버튼의 클릭 이벤트 처리
        Button btndelete = (Button)findViewById(R.id.btndelete);
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //하나를 선택할 수 있을 때 삭제 처리
                /*
                //작업을 수행할 데이터 유효성 검사
                //선택된 항목의 인덱스를 찾아오기
                int pos = listview.getCheckedItemPosition();
                //인덱스는 데이터 범위 내의 인덱스이어야 합니다.
                if(pos < 0 || pos >= list.size()){
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "아이템이 선택되지 않아서 삭제할 수 없습니다.", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }

                //작업을 수행
                //데이터 삭제하고 다시 출력하고 선택 된 항목 해제
                list.remove(pos);
                adapter.notifyDataSetChanged();
                listview.clearChoices();
                */

                //여러 개 선택해서 삭제
                //선택 항목 관련 정보를 가져옴
                SparseBooleanArray sb = listview.getCheckedItemPositions();
                //유효성 검사
                if(sb.size() <= 0){
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                            "리스트 뷰에 데이터가 없습니다.", Snackbar.LENGTH_LONG)
                            .show();
                    return;
                }
                //배열 순회
                //배열을 순회하면서 true 이면 삭제
                //앞에서부터 진행하면 안되고 뒤에서부터 진행
                for(int i=listview.getCount() - 1; i >= 0; i--){
                    if(sb.get(i) == true){
                        list.remove(i);
                    }
                }
                listview.clearChoices();
                adapter.notifyDataSetChanged();

                //수행 결과를 출력
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "아이템이 정상적으로 삭제되었습니다.", Snackbar.LENGTH_LONG)
                        .show();
            }
        });
    }
}
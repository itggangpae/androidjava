package kakao.itstudy.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
        adapter = new ArrayAdapter<>(
                ListViewActivity.this,
                android.R.layout.simple_list_item_1,
                list);

        //ListView에 Adapter 설정
        listview.setAdapter(adapter);

        //구분선 설정
        listview.setDivider(new ColorDrawable(Color.CYAN));
        listview.setDividerHeight(3);

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


    }
}
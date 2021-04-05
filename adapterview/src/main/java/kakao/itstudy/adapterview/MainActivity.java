package kakao.itstudy.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //ListView 출력을 위한 프로퍼티
    ListView listView;
    //데이터 모임과 Adapter의 제너릭은 항상 일치
    ArrayList<String> list;
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView 찾아오기
        listView = (ListView)findViewById(R.id.listview);
        //데이터 생성
        list = new ArrayList<>();
        list.add("C");
        list.add("C++");
        list.add("Java");
        list.add("Python");
        list.add("JavaScript");
        list.add("C#");
        list.add("Ruby");
        list.add("Scala");
        list.add("Kotlin");
        list.add("Swift");
        list.add("R");
        list.add("Dart");
        list.add("Go");

        //어댑터 생성 - ArrayList를 배열로 만들어도 됩니다.
        //adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1, list);

        //리소스를 이용해서 Adapter를 생성
        adapter = ArrayAdapter.createFromResource(
                MainActivity.this, R.array.oop,
                android.R.layout.simple_list_item_1);


        //ListView 와 ArrayAdpater 연결
        listView.setAdapter(adapter);

    }
}
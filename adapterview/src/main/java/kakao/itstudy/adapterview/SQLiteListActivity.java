package kakao.itstudy.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLiteListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite_list);

        //SimpleAdapter를 사용하는 ListView 출력
        ListView simpleListView = (ListView)findViewById(R.id.simpleListView);
        //SimpleAdapter는 Map의 List로 데이터를 생성해야 합니다.
        List<Map<String, String>> simpleData = new ArrayList<>();
        //데이터베이스에서 데이터 읽어오기
        JobDBHelper helper = new JobDBHelper(SQLiteListActivity.this);
        SQLiteDatabase db = helper.getReadableDatabase();
        //데이터 읽는 SQL 실행
        Cursor cursor = db.rawQuery("select * from job_data", null);
        while(cursor.moveToNext()){
            Log.e("이름", cursor.getString(1));
            HashMap<String, String> map = new HashMap<>();
            map.put("name", cursor.getString(1));
            map.put("content", cursor.getString(2));
            simpleData.add(map);
        }

        //SimpleAdapter 생성
        //네번째 매개변수가 Map에서 출력할 키의 배열입니다.
        //출력할 데이터
        Log.e("데이터", simpleData.toString());
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                SQLiteListActivity.this, simpleData,
                android.R.layout.simple_list_item_2,
                new String[]{"name", "content"},
                new int[]{android.R.id.text1, android.R.id.text2});

        simpleListView.setAdapter(simpleAdapter);

    }
}
package kakao.itstudy.adapterview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;

import java.security.acl.AclNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class CustomCellActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cell);

        //데이터 생성
        List<DataStructure> list =
                new ArrayList<>();
        DataStructure ds =  new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("Array");
        ds.setDescription("데이터를 순차적으로 연속해서 저장하는 자료구조");
        list.add(ds);

        ds =  new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("LinkedList");
        ds.setDescription("다음 데이터의 주소를 저장해서 연속적으로 저장한 것처럼 보이도록 한 자료구조");
        list.add(ds);

        ds =  new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("Stack");
        ds.setDescription("마지막에 저장한 데이터를 가장 먼저 꺼내는 자료구조 - LIFO");
        list.add(ds);

        ds =  new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("Queue");
        ds.setDescription("처음에 저장한 데이터를 가장 먼저 꺼내는 자료구조 - FIFO");
        list.add(ds);

        ds =  new DataStructure();
        ds.setIcon(R.mipmap.ic_launcher);
        ds.setName("Deque");
        ds.setDescription("양쪽에서 삽입과 삭제가 가능한 자료구조");
        list.add(ds);

        //Adapter 만들기
        MyAdapter myAdapter = new MyAdapter(
                CustomCellActivity.this, list);

        //ListView에 출력
        ListView listView = (ListView)findViewById(R.id.customlistview);
        listView.setAdapter(myAdapter);

        //애니메이션 집합을 생성
        AnimationSet set = new AnimationSet(true);
        //위치 이동 애니메이션 생성
        Animation rtl = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f
        );
        //시간을 설정
        rtl.setDuration(1000);

        //집합에 추가
        set.addAnimation(rtl);

        //불투명도 애니메이션을 생성하고 추가
        Animation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(1000);
        set.addAnimation(alpha);

        //애니메이션 적용
        LayoutAnimationController controller =
                new LayoutAnimationController(set, 0.5f);
        listView.setLayoutAnimation(controller);

    }
}
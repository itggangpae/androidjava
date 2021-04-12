package kakao.itstudy.supportlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class TabUseActivity extends AppCompatActivity {

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_use);

        //화면에 출력할 Fragment 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        //첫번째 화면 설정
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment1)
                .commit();

        //탭 만들기
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab((tabs.newTab().setText("통화기록")));
        tabs.addTab((tabs.newTab().setText("스팸기록")));
        tabs.addTab((tabs.newTab().setText("연락처")));

        //탭을 눌렀을 때 수행할 이벤트 처리 코드를 작성
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //선택한 탭의 인덱스를 찾아오기
                int position = tab.getPosition();

                Fragment selected = null;

                if(position == 0){
                    selected = fragment1;
                }else if(position == 1){
                    selected = fragment2;
                }else{
                    selected = fragment3;
                }
                //선택된 Fragment로 변경
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, selected)
                        .commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //NavigationView 찾아오기
        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottom_navigation);
        //메뉴를 선택했을 때 이벤트 처리
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //누른 항목의 아이디를 가져와서
                switch (item.getItemId()){
                    case R.id.tab1:
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "첫번째 화면",
                                Snackbar.LENGTH_LONG).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment1)
                                .commit();
                        break;
                    case R.id.tab2:
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "두번째 화면",
                                Snackbar.LENGTH_LONG).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment2)
                                .commit();
                        break;
                    case R.id.tab3:
                        Snackbar.make(getWindow().getDecorView().getRootView(),
                                "세번째 화면",
                                Snackbar.LENGTH_LONG).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.container, fragment3)
                                .commit();
                        break;
                }
                return false;
            }
        });

    }
}
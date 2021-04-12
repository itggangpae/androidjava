package kakao.itstudy.supportlibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //프로퍼티에 객체를 생성해서 대입
        drawer = findViewById(R.id.main_drawer);
        toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.drawer_open,
                R.string.drawer_close);
        //삼선 아이콘 추가
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        //항목 뷰 눌렀을 때 수행할 코드 작성
        NavigationView navigationView = findViewById(R.id.main_drawer_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){

                    @Override
                    public boolean onNavigationItemSelected(
                            @NonNull MenuItem item) {
                        if(item.getItemId() == R.id.menu_drawer_home){
                            Snackbar.make(getWindow().getDecorView().getRootView(),
                                    "홈 선택", Snackbar.LENGTH_LONG).show();
                        }
                        if(item.getItemId() == R.id.menu_drawer_message){
                            Snackbar.make(getWindow().getDecorView().getRootView(),
                                    "메시 선택", Snackbar.LENGTH_LONG).show();
                        }
                        if(item.getItemId() == R.id.menu_drawer_add){
                            Snackbar.make(getWindow().getDecorView().getRootView(),
                                    "추가 선택", Snackbar.LENGTH_LONG).show();
                        }

                        return false;
                    }
                });
    }

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;


    //메뉴를 눌럿을 때 호출되는 메소드
    public boolean onOptionsItemSelected(MenuItem item){
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
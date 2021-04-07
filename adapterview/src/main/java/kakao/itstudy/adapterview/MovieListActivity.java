package kakao.itstudy.adapterview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {
    //다운로드 진행 상황을 표시할 뷰
    private ProgressBar downloadview;

    //파싱한 결과 데이터를 저장할 프로퍼티를 생성
    private int count;
    private ArrayList<Movie> movieList;

    //ListView 관련 프로퍼티
    ListView listView;

    //ArrayAdapter<Movie> adapter;
    //사용자 항목 뷰를 사용하기 위한 Adapter
    MovieAdapter adapter;

    //ListView의 데이터를 다시 출력할 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            //ListView 재출력
            adapter.notifyDataSetChanged();
            //진행 상황을 나타내는 뷰를 사라지게 합니다.
            downloadview.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //영화 목록을 저장할 List 생성
        movieList = new ArrayList<>();

        //ListView 출력을 위한 설정
        listView = (ListView)findViewById(R.id.listview);
        /*
        adapter = new ArrayAdapter<>(
                MovieListActivity.this,
                android.R.layout.simple_list_item_1, movieList);

         */
        //사용자 항목 뷰를 출력하기 위한 Adapter 생성
        adapter = new MovieAdapter(MovieListActivity.this, movieList);

        listView.setAdapter(adapter);
        listView.setDivider(new ColorDrawable(Color.CYAN));
        listView.setDividerHeight(3);

        //진행 상황을 표시할 뷰를 찾아오기
        downloadview = (ProgressBar)findViewById(R.id.downloadview);
        //화면에 출력
        downloadview.setVisibility(View.VISIBLE);
        //다운로드 받고 JSON 파싱을 수행한 후 재출력을 위해서 핸들러를 호출하는
        //스레드를 생성하고 실행
        new Thread(){
            public void run(){
                try{
                    //URL을 생성 - 이 때 한글이 포함되었는지 확인하고 한글이 있으면 인코딩해서 작성
                    URL url = new URL("http://cyberadam.cafe24.com/movie/list");
                    //HttpURLConnection을 생성
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    //옵션 설정 - 전송방식 그리고 파라미터를 확인
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(30000);
                    con.setUseCaches(false);
                    //POST 방식이면 파라미터에 파일이 포함되었는지 여부에 따라 코드를 추가해야 함

                    //문자열을 다운로드 받아서 저장할 객체 와 스트림을 생성
                    StringBuilder sb = new StringBuilder();
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));

                    //문자열을 줄 단위로 읽어서 sb에 추가
                    while(true){
                        //한 줄을 가져오기
                        String line = br.readLine();
                        //읽은 내용이 없으면 더 이상 가져오지 않음
                        if(line == null){
                            break;
                        }
                        //읽은 내용이 있을 때는 line에 추가
                        sb.append(line + "\n");
                    }
                    //연결을 해제
                    br.close();
                    con.disconnect();

                    //데이터 확인
                    Log.e("다운로드 받은 문자열", sb.toString());

                    //전체 문자열을 JSONObject로 변환
                    JSONObject root = new JSONObject(sb.toString());

                    //데이터 개수 가져오기
                    count = root.getInt("count");

                    //영화 목록 가져오기
                    JSONArray movies = root.getJSONArray("list");
                    //영화목록 순회하기
                    for(int i=0; i<movies.length(); i++){
                        //하나의 데이터 가져오기
                        JSONObject item = movies.getJSONObject(i);
                        //하나의 행을 저장할 객체를 생성
                        Movie movie = new Movie();

                        //각 속성을 가져와서 movie에 대입
                        movie.setTitle(item.getString("title"));
                        movie.setSubtitle(item.getString("subtitle"));
                        movie.setRating(item.getDouble("rating"));
                        movie.setLink(item.getString("link"));
                        movie.setThumbnail(item.getString("thumbnail"));

                        //파싱한 데이터를 List에 대입
                        movieList.add(movie);
                    }
                    Log.e("파싱한 데이터", movieList.toString());

                    //핸들러에게 메시지 전송
                    Message msg = new Message();
                    handler.sendMessage(msg);

                    //보낼 데이터가 없을 때는 아래처럼 작성해도 됨
                    //handler.sendEmptyMessage(1);

                }catch(Exception e){
                    Log.e("다운로드 또는 파싱 예외", e.getLocalizedMessage());
                }
            }
        }.start();


    }
}
package kakao.itstudy.adapterview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MovieAdapter extends BaseAdapter {
    //뷰 전개를 위한 LayoutInflater를 생성할 Context
    Context context;
    //출력할 데이터
    ArrayList<Movie> list;

    //뷰 전개를 위한 LayoutInflater
    LayoutInflater inflater;

    //이미지 뷰에 다운로드 받은 이미지를 출력해주는 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            Map<String, Object> map = (Map<String, Object>)msg.obj;
            //전달된 데이터 꺼내기
            ImageView imageView = (ImageView)map.get("imageview");
            Bitmap bitmap = (Bitmap)map.get("bitmap");
            //이미지 뷰에 이미지 출력
            imageView.setImageBitmap(bitmap);
        }
    };

    //2개의 프로퍼티를 주입받기 위한 생성자
    public MovieAdapter(Context context, ArrayList<Movie> list){
        this.context = context;
        this.list = list;
        //xml 파일을 View 객체로 변환할 LayoutInflator
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    //출력할 항목의 개수
    @Override
    public int getCount() {
        return list.size();
    }

    //기본 모양으로 출력할 때 보여질 문자열을 설정하는 메소드
    @Override
    public Object getItem(int position) {
        return list.get(position).getTitle();
    }

    //항목 뷰의 아이디
    @Override
    public long getItemId(int position) {
        return position;
    }

    //실제 출력될 항목 뷰를 설정하는 메소드
    @Override
    public View getView(
            int position, View convertView, ViewGroup parent) {
        //재사용 할 뷰가 없으면 생성
       if(convertView == null){
           convertView = inflater.inflate(
                   R.layout.movie_cell, parent, false);
       }

       //뷰 찾아오기
       TextView titleView =
                (TextView)convertView.findViewById(R.id.movietitle);
       titleView.setText(list.get(position).getTitle());

       TextView subTitleView =
               (TextView)convertView.findViewById(R.id.moviesubtitle);
       subTitleView.setText(list.get(position).getSubtitle());

       RatingBar ratingBar =
                (RatingBar)convertView.findViewById(R.id.movierating);
       //평점이 10점 만점으로 되어 있고 double 자료형
        //rating은 별 5개로 되어있고 float으로 설정해야 하므로 2로 나누고
        //flat으로 형 변환해서 대입
       ratingBar.setRating((float)list.get(position).getRating()/2);

       ImageView imageView =
                (ImageView)convertView.findViewById(R.id.movieimage);
       //이미지를 다운로드 받아서 핸들러에게 출력을 요청
       //핸들러에게 ImageView 와 Bitmap을 전달
        new Thread(){
            public void run(){
                try{
                    //이미지를 다운로드 받을 URL을 생성
                    URL url = new URL(
                            "http://cyberadam.cafe24.com/movieimage/"
                                    + list.get(position).getThumbnail() );
                    //이미지 가져오기
                    InputStream is = url.openStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);

                    //핸들러에게 전송할 메시지
                    Message msg = new Message();
                    //2개의 데이터를 전달하기 위한 Map을 생성
                    Map<String, Object> map =
                            new HashMap<>();
                    map.put("imageview", imageView);
                    map.put("bitmap", bitmap);
                    //메시지 전송
                    msg.obj = map;
                    handler.sendMessage(msg);

                }catch(Exception e){
                    Log.e("이미지 다운로드 실패", e.getMessage());
                }
            }
        }.start();
       return convertView;
    }
}

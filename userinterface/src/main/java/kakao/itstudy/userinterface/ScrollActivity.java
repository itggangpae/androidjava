package kakao.itstudy.userinterface;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

public class ScrollActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        //이미지를 가져오기
        Drawable bitmap = getResources().getDrawable(
                R.drawable.largeimage, null);
        //이미지의 너비와 높이 가져오기
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        //이미지 뷰 찾아오기
        ImageView imageView = (ImageView)findViewById(R.id.imageview);
        //이미지 설정
        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapWidth;
    }
}
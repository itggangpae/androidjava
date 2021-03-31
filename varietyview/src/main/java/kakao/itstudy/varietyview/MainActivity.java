package kakao.itstudy.varietyview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //텍스트 뷰 찾아오기
        TextView spanView = (TextView)findViewById(R.id.spanView);
        spanView.setMovementMethod(new ScrollingMovementMethod());
        //출력할 문자열을 생성
        String data = "대한민국 \n img \n 나의 자랑스런 조국";
        //Spannable String으로 생성
        SpannableStringBuilder builder =
                new SpannableStringBuilder(data);
        //삽입할 위치 찾기
        int start = data.indexOf("img");
        //img 가 있다면
        if(start > -1){
            int end = start + "img".length();
            //이미지를 가져오기
            Drawable dr = ResourcesCompat.getDrawable(
                    getResources(), R.drawable.korea, null);
            //이미지 위치 및 크기 설정
            dr.setBounds(0, 0,
                    dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
            //ImageSpan 생성
            ImageSpan span = new ImageSpan(dr);
            //builder에 추가 - start 부터 end 까지 영역에 치환
            builder.setSpan(span, start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        //스타일을 변경할 위치 찾기
        start = data.indexOf("대한민국");
        if(start > -1){
            //적용할 마지막 위치  찾기
            int end = start + "대한민국".length();
            StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
            //글자 크기를 다른 글자들에 비해서 2배
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(2.0f);
            builder.setSpan(styleSpan, start, end+2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(sizeSpan, start, end+2,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        }

        spanView.setText(builder);


    }
}
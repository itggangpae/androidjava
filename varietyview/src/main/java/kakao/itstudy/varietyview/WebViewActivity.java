package kakao.itstudy.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        //웹 뷰를 찾아오기
        WebView webView = (WebView)findViewById(R.id.webView);
        //URL이 리다이렉트 되는 경우에는 이 코드를 추가해야 만 웹 뷰로 출력합니다.
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://tjoeun.co.kr/");

        Button btnLoadHtml = (Button)findViewById(R.id.btnLoadHtml);
        btnLoadHtml.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //출력하는 URL에 자바스크립트 코드가 있는 경우 WebView의 설정을 변경해야 합니다.
                WebSettings set = webView.getSettings();
                set.setJavaScriptEnabled(true);

                webView.loadUrl("file:///android_asset/test.html");
            }
        });

    }
}
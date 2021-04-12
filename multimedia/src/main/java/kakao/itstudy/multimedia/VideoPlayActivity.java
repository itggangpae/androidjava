package kakao.itstudy.multimedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.material.snackbar.Snackbar;

public class VideoPlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        //VideoView 가져오기
        VideoView videoView =
                (VideoView)findViewById(R.id.videoView);
        //재생할 비디오 설정
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //비디오 파일 경로
        String path = "android.resource://" + getPackageName()
                 +"/" + R.raw.trailer;
        videoView.setVideoURI(Uri.parse(path));
        videoView.requestFocus();

        //재생 버튼 이벤트 처리
        Button startBtn = (Button)findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                videoView.seekTo(0);
                videoView.start();
            }
        });

        //비디오 재생 준비가 되면
        videoView.setOnPreparedListener(
                new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Snackbar.make(getWindow().getDecorView().getRootView(),
                        "재생 준비 완료", Snackbar.LENGTH_LONG).show();
            }
        });


    }
}
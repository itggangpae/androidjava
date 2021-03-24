package kakao.itstudy.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class SoundActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        //진동 버튼의 클릭 이벤트 처리
        Button btnVib = (Button)findViewById(R.id.btnVib);
        btnVib.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator)getSystemService(
                        Context.VIBRATOR_SERVICE);
                vib.vibrate(3000);
            }
        });

        //효과음 버튼의 클릭 이벤트 처리
        Button btnAlarm = (Button)findViewById(R.id.btnAlarm);
        btnAlarm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //효과음 재생
                Uri notification = RingtoneManager.getDefaultUri(
                        RingtoneManager.TYPE_NOTIFICATION);
                Ringtone ringtone =
                        RingtoneManager.getRingtone(
                                getApplicationContext(), notification);
                ringtone.play();
            }
        });

        Button btnSong = (Button)findViewById(R.id.btnSong);
        btnSong.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //노래  재생
                MediaPlayer player =
                        MediaPlayer.create(getApplicationContext(),
                                R.raw.song);
                player.start();
            }
        });


    }
}
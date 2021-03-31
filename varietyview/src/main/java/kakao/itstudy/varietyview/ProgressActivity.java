package kakao.itstudy.varietyview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ProgressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        //뷰를 찾아오기
        ProgressBar rect =
                (ProgressBar)findViewById(R.id.progressbar);
        ProgressBar circle =
                (ProgressBar)findViewById(R.id.progressindicator);
        Button btnStart = (Button)findViewById(R.id.btnStart);
        Button btnStop = (Button)findViewById(R.id.btnStop);

        //버튼을 눌렀을 때 수행할 동작
        btnStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                rect.setProgress(50);
                circle.setVisibility(View.VISIBLE);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                rect.setProgress(0);
                circle.setVisibility(View.INVISIBLE);
            }
        });

        //시크바와 텍스트 뷰를 찾아오기
        TextView lblSeekValue =
                (TextView)findViewById(R.id.lblSeekValue);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekbar);

        //시크바의 값을 변경할 때 처리
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener(){
                    //값이 변경될 때 호출되는 메소드
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        //현재 값을 TextView에 출력
                        String msg = String.format("%d", progress);
                        lblSeekValue.setText(msg);
                    }
                    //값을 변경하기 위해서 thumb을 선택했을 때
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }
                    //값의 변경이 종료되고 thumb에서 손을 뗄 때
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Log.e("시크바", "값 변경 종료");
                        Snackbar.make(seekBar, "값 변경 종료",
                                Snackbar.LENGTH_LONG).show();

                    }
                });

    }
}
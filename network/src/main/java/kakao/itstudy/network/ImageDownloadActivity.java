package kakao.itstudy.network;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloadActivity extends AppCompatActivity {

    //핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            //여러 스레드에서 사용하도록 하고자 할 때는 msg의 what을 이용해서 분기

            //이미지 뷰 찾아오기
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            //출력할 이미지를 저장하기 위한 변수
            Bitmap bitmap = null;
            switch(msg.what){
                case 1:
                    //데이터 가져오기
                    bitmap = (Bitmap)msg.obj;
                    imageView.setImageBitmap(bitmap);
                    break;
                case 2:
                    //파일이 존재하는 경우
                    Snackbar.make(imageView, "파일이 이미 존재합니다.",
                            Snackbar.LENGTH_LONG).show();
                    //기존 파일 출력
                    //파일의 경로 가져오기
                    String dirPath = Environment.getDataDirectory().getAbsolutePath();
                    String filePath = dirPath + "/data/kakao.itstudy.network/files/web.jpg";
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;


                case 3:
                    //파일이 존재하지 않는 경우
                    Snackbar.make(imageView, "파일이 없어서 다운로드 받아서 출력합니다.",
                            Snackbar.LENGTH_LONG).show();
                    //다운로드 받은 파일 출력
                    //파일의 경로 가져오기
                    String dirPath1 = Environment.getDataDirectory().getAbsolutePath();
                    String filePath1 = dirPath1 + "/data/kakao.itstudy.network/files/web.jpg";
                    imageView.setImageBitmap(BitmapFactory.decodeFile(filePath1));
                    break;



            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_download);

        //이미지 다운로드 받아서 바로 출력하는 버튼의 클릭 이벤트 핸들러 작성
        Button btnDisplay = (Button)findViewById(R.id.btnDisplay);
        //Anonymous Class 이용해서 이벤트 처리 - Java 의 Nested Class, 람다 복습
        btnDisplay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //스레드 생성
                new Thread(){
                  @Override
                  public void run(){
                      //이미지 바로 가져오기
                      try{
                          //이미지를 다운로드 받을 스트림 생성
                          InputStream is = new URL(
                                  "https://post-phinf.pstatic.net/MjAxOTAyMTNfNjcg/MDAxNTUwMDI5NjIyMzc3.u4pgJ3xM1J2tBHOhARaUr7ouaQMFbOgTdz4XcNF5Opwg.v-Pty9P0JkUWWM4DFpNchzbBqpZwx4WJuFCf7yM7MtYg.JPEG/1.jpg?type=w1200")
                                  .openStream();
                          //이미지를 가져와서 bitmap에 저장
                          Bitmap bitmap = BitmapFactory.decodeStream(is);
                          //핸들러에게 전송할 메시지를 생성
                          Message message = new Message();
                          message.what = 1;
                          message.obj = bitmap;
                          //핸들러에게 메시지 전송
                          handler.sendMessage(message);

                      }catch(Exception e){
                          Log.e("예외 발생1", e.getLocalizedMessage());
                      }
                  }
                }.start();
            }
        });

        //두번째 버튼의 클릭 이벤트 핸들러
        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 앱 내에 파일이 존재하는지 확인 - web.jpg

                //파일 경로 생성

                //Data가 저장되는 Directory 경로를 생성
                String dirPath =
                        Environment.getDataDirectory().getAbsolutePath();
                //파일 경로 생성 - kakao.itstudy.network는 패키지 이름
                //web.jpg는 저장할 파일이름
                String path = dirPath + "/data/kakao.itstudy.network/files/web.jpg";

                //파일이 있는지 확인
                //File 클래스를 다시 공부할 때 존재 여부, 마지막 수정 날짜, 생성 날짜, 크기를
                //가져오는 것은 중요합니다.
                if(new File(path).exists()){
                    //핸들러에게 전송할 메시지를 생성해서 출력을 요청
                    //이미 파일이 있는 경우 이므로 데이터를 전달할 필요가 없습니다.
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }else{
                    //이미지 파일이 없는 경우 이미지 파일을 다운로드 받아서 저장하고 핸들러에게
                    //메시지를 전송
                    new Thread(){
                        @Override
                        public void run(){
                            //이미지 데이터 다운로드 받기
                            try{
                                //다운로드 받을 URL 생성
                                URL url = new URL(
                                        "https://image.newsis.com/2020/10/13/NISI20201013_0000616339_web.jpg?rnd=20201013164450");
                                //연결
                                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                                //옵션 설정
                                con.setConnectTimeout(30000);
                                con.setUseCaches(false);

                                //텍스트가 아닌 데이터를 가져오기 위한 스트림을 생성
                                InputStream is = con.getInputStream();
                                //기록할 파일 스트림을 생성
                                FileOutputStream fos = openFileOutput("web.jpg", 0);
                                //데이터를 임시로 저장할 바이트 배열을 생성
                                byte [] raster = new byte[con.getContentLength()];
                                //다운로드 받아서 저장하기
                                while(true){
                                    int read = is.read(raster);
                                    if(read <= 0){
                                        break;
                                    }
                                    fos.write(raster, 0, read);
                                }
                                is.close();
                                fos.close();
                                con.disconnect();

                                //핸들러에게 메시지 전송
                                Message msg = new Message();
                                msg.what = 3;
                                handler.sendMessage(msg);

                            }catch(Exception e){
                                Log.e("예외 발생2", e.getLocalizedMessage());
                            }
                        }
                    }.start();
                }

            }
        });

    }
}
package kakao.itstudy.webserverparameter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class ItemPostActivity extends AppCompatActivity {

    //입력을 위한 뷰
    EditText itemnameinput, priceinput, descriptioninput;

    //출력을 위한 핸들러
    Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg){
            //스낵바에 출력할 내용을 저장할 변수
            String str = null;
            switch (msg.what){
                case 0:
                    //넘겨받은 메시지를 str에 대입
                    str = (String)msg.obj;
                    break;
                case 1:
                    //삽입 결과를 가져옵니다.
                    boolean insertResult = (Boolean)msg.obj;
                    if(insertResult == true){
                        str = "데이터 삽입 성공";
                    }else{
                        str = "데이터 삽입 실패";
                    }
                    break;
                case 2:
                    //삽입 결과를 가져옵니다.
                    boolean deleteResult = (Boolean)msg.obj;
                    if(deleteResult == true){
                        str = "데이터 삭제 성공";
                    }else{
                        str = "데이터 삭제 실패";
                    }
                    break;
            }
            //스낵바 출력
            Snackbar.make(getWindow().getDecorView().getRootView(),
                    str, Snackbar.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_post);

        //입력을 위한 뷰 찾아아괴
        itemnameinput = (EditText)findViewById(R.id.itemnameinput);
        priceinput = (EditText)findViewById(R.id.priceinput);
        descriptioninput = (EditText)findViewById(R.id.descriptioninput);

        //삽입 버튼을 찾고 버튼의 클릭 이벤트 핸들러 작성
        Button btnInsert = (Button)findViewById(R.id.btnInsert);
        btnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Message msg = new Message();

                //입력받은 데이터의 유효성을 검사
                String itemname =
                        itemnameinput.getText().toString();
                //입력한 데이터가 있는지 확인해서 입력한 데이터가 없다면 더 이상 작업 진행을
                //하지 않음
                if(itemname.trim().isEmpty()){
                    msg.what = 0;
                    msg.obj = "아이템 이름은 필수 입니다.";
                    handler.sendMessage(msg);
                    return;
                }
                String price = priceinput.getText().toString();
                if(price.trim().isEmpty()){
                    msg.what = 0;
                    msg.obj = "가격은 필수 입니다.";
                    handler.sendMessage(msg);
                    return;
                }
                String description = descriptioninput.getText().toString();
                if(description.trim().isEmpty()){
                    msg.what = 0;
                    msg.obj = "아이템 설명은 필수 입니다.";
                    handler.sendMessage(msg);
                    return;
                }

                //현재 시간을 yyyy-MM-dd hh:mm:ss 형식의 문자열로 만들기
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat(
                        "yyyy-MM-dd hh:mm:ss");
                String updatedate = sdf.format(date);

                //서버에 전송하고 결과를 받아올 스레드를 생성하고 시작
                new Thread(){
                    public void run(){
                        try{
                            //업로드할 URL을 생성
                            URL url = new URL("http://cyberadam.cafe24.com/item/insert");
                            //HttpURLConnection 생성
                            HttpURLConnection con = (HttpURLConnection)url.openConnection();

                            //옵션 설정
                            con.setUseCaches(false);
                            con.setConnectTimeout(30000);

                            //생략하면 GET 방식
                            con.setRequestMethod("POST");

                            //파일 업로드가 있는 경우 설정
                            String lineEnd = "\r\n";
                            //랜덤한 문자열 생성
                            String boundary = UUID.randomUUID().toString();
                            con.setRequestProperty("ENCTYPE", "multipart/form-data");
                            con.setRequestProperty(
                                    "Content-Type", "multipart/form-data;boundary=" + boundary);

                            //파일을 제외한 파라미터 이름 생성
                            String [] dataName =
                                    {"itemname", "price", "description", "updatedate"};
                            //파라미터 값 생성
                            String [] data = {
                                    itemname, price, description, updatedate
                            };

                            //파라미터를 전송할 수 있도록 헤더에 포함
                            String delimiter = "--" + boundary + lineEnd;
                            StringBuilder postDataBuilder =
                                    new StringBuilder();
                            for(int i=0; i<data.length; i++){
                                postDataBuilder.append(delimiter);
                                postDataBuilder.append(
                                        "Content-Disposition: form-data; name=\"" +
                                        dataName[i] + "\"" + lineEnd + lineEnd + data[i] +
                                        lineEnd);
                            }

                            //파일 파라미터 작성 - pictureurl
                            //파일 이름 생성
                            //대부분의 경우 클라이언트에서 만든 파일이름은 큰 의미가 없음
                            //서버에서 이 이름에 구별할 수 있는 코드를 추가해서 이름은 새로 만듭니다.
                            String fileName = "ritchie.jpeg";
                            //pictureurl은 파일 파라미터 이름이므로 서버의 파라미터 이름 그대로 작성
                            if(fileName != null){
                                postDataBuilder.append(delimiter);
                                postDataBuilder.append(
                                        "Content-Disposition: form-data;" +
                                        "name=\"pictureurl\";filename=\"" +
                                        fileName + "\"" + lineEnd);
                            }

                            //실제 파일 전송

                            //파라미터를 전송
                            //POST 방식 인 경우 - 문자열을 전송하지 않고 바이트 배열을 전송합니다.
                            DataOutputStream ds = new DataOutputStream(con.getOutputStream());
                            ds.write(postDataBuilder.toString().getBytes());
                            //파일이 있는 경우는 실제 파일을 전송하고 종료해야 하고
                            //파일이 없는 경우는 종료만 하면 됩니다.
                            if(fileName != null){
                                ds.writeBytes(lineEnd);
                                //raw 디렉토리에 저장한 파일의 스트림 생성

                                //어디서 파일의 내용을 가져오는지에 따라 변경됨
                                InputStream fres = getResources().openRawResource(R.raw.ritchie);
                                //읽은 내용을 저장할 바이트 배열 생성
                                byte [] buffer = new byte[fres.available()];
                                //읽은 바이트 수를 저장할 변수
                                int length = -1;
                                while(true){
                                    //파일의 내용을 읽고 읽은 바이트 수를 length에 저장
                                    length = fres.read(buffer);
                                    //읽은 내용이 없으면 종료
                                    if(length <= 0){break;}
                                    //읽은 내용은 전송
                                    ds.write(buffer, 0, length);
                                }
                                //파일 전송 종료

                                //종료 기호를 전송
                                ds.writeBytes(lineEnd);
                                ds.writeBytes(lineEnd);
                                ds.writeBytes("--" + boundary + "--" + lineEnd);
                            }else{
                                ds.writeBytes(lineEnd);
                                ds.writeBytes("--" + boundary + "--" + lineEnd);
                            }

                            //전송하는  스트림을 닫기
                            ds.flush();
                            ds.close();

                            //결과를 받아오기
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            while(true){
                                String line = br.readLine();
                                if(line == null){
                                    break;
                                }
                                sb.append(line + "\n");
                            }

                            br.close();
                            con.disconnect();

                            //json 파싱을 수행
                            JSONObject jsonObject = new JSONObject(sb.toString());
                            //성공하면 true 실패하면 false
                            boolean result = jsonObject.getBoolean("result");

                            //메시지의 구분 번호로 1번
                            //성공 과 실패 여부를 obj에 저장
                            //메시지를 핸들러에게 전송
                            msg.what = 1;
                            msg.obj = result;
                            handler.sendMessage(msg);

                        }catch(Exception e){
                            Log.e("업로드 예외", e.getLocalizedMessage());
                            Snackbar.make(
                                    getWindow().getDecorView().getRootView(),
                                    e.getLocalizedMessage(),
                                    Snackbar.LENGTH_LONG
                            ).show();

                        }
                    }
                }.start();
            }
        });

        Button btnDelete = (Button)findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new Thread(){
                    public void run(){
                        try{
                            //URL 생성
                            URL url = new URL("http://cyberadam.cafe24.com/item/delete");
                            //연결 객체 생성
                            HttpURLConnection con =
                                    (HttpURLConnection)url.openConnection();

                            //옵션 설정
                            con.setConnectTimeout(30000);
                            con.setUseCaches(false);

                            con.setRequestMethod("POST");


                            //파일이 없을 때 POST 방식의 파라미터 전송
                            //itemid 이고 정수입니다.
                            String data = URLEncoder.encode("itemid", "UTF-8") + "="
                                    + URLEncoder.encode("7", "UTF-8");
                            //파라미터 전송
                            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
                            wr.write(data);
                            wr.flush();

                            //결과 받아오기
                            //스트림 생성
                            BufferedReader br = new BufferedReader(
                                    new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            while(true){
                                String line = br.readLine();
                                if(line == null){
                                    break;
                                }
                                sb.append(line + "\n");
                            }

                            JSONObject jsonObject = new JSONObject(sb.toString());
                            boolean result = jsonObject.getBoolean("result");

                            Message msg = new Message();
                            msg.obj = result;
                            msg.what = 2;
                            handler.sendMessage(msg);


                        }catch(Exception e){
                            Log.e("삭제 예외", e.getLocalizedMessage());
                        }
                    }
                }.start();
            }
        });
    }
}
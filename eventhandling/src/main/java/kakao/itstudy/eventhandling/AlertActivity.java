package kakao.itstudy.eventhandling;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.LinkedList;

public class AlertActivity extends AppCompatActivity {
    //버튼을 참조할 변수
    private Button btnAlert;

    //선택한 데이터를 저장할 문자열 프로퍼티
    private String myDBMS;

    //대화상자에 출력할 데이터 배열
    private String[] languages;
    //선택한 데이터를 저장할 문자열 프로퍼티
    private String myLanguage;

    //여러 개를 선택했을 때 저장할 문자열 List
    private LinkedList<String> myLangugeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);

        //버튼 찾아오기
        btnAlert = (Button) findViewById(R.id.btnAlert);

        //버튼을 클릭 했을 때 처리
        btnAlert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //대화상자 객체 생성
                AlertDialog.Builder dlg =
                        new AlertDialog.Builder(AlertActivity.this);
                dlg.setTitle("제목")
                        .setMessage("메시지")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(AlertActivity.this,
                                                "토스트가 대화상자가 닫히고 난 후 출력됩니다.",
                                                Toast.LENGTH_LONG).show();
                                        Log.e("로그", "대화상자가 닫히고 난 후 출력");

                                    }
                                })
                        .show();
                /*
                //토스트 출력 - 대화상자 출력 구문과 동시에 실행되는 것처럼 동작
                Toast.makeText(AlertActivity.this,
                        "언제 출력되나요!!",
                        Toast.LENGTH_LONG).show();

                 */
            }
        });

        //선택 대화상자 출력하기
        Button btnItemDialog = (Button) findViewById(R.id.btnItemDialog);
        //클릭 했을 때 처리
        btnItemDialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("내가 사용할 데이터베이스")
                        .setIcon(android.R.drawable.ic_lock_idle_lock)
                        .setItems(R.array.dbms, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //arrays.xml 파일에 디자인 한 배열을 가져오기
                                String[] dbmses =
                                        getResources().getStringArray(R.array.dbms);
                                //선택한 데이터를 가져오기
                                myDBMS = dbmses[which];
                                //데이터 사용
                                Log.e("dbms", myDBMS);

                            }
                        })
                        .show();
            }
        });

        //배열을 생성
        languages = new String[]{
                "Pascal", "C", "C++", "Java", "Assembly", "VisualBasic",
                "C#", "Objective-C", "Python", "R", "Swift", "Kotlin",
                "Scala", "Ruby", "Dart", "Haskell", "Closure", "Go",
                "Delphi", "PowerBuilder", "Fortran", "COBOL"
        };
        //버튼 찾아오기
        Button btnOneLanguage = (Button) findViewById(R.id.btnOneLanguage);
        //클릭 했을 때 처리
        btnOneLanguage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("언어를 하나 고르세요!!!")
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(languages, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //선택한 항목을 myLanguage 에 저장
                                myLanguage = languages[which];
                                Log.e("선택한 언어", myLanguage);
                            }
                        })
                        .show();
            }
        });

        //여러 개 선택한 항목들을 저장할 List 생성
        myLangugeList = new LinkedList<>();
        //버튼 찾아오기
        Button btnMultiLanguage = (Button) findViewById(R.id.btnMultiLanguage);
        //버튼 클릭 이벤트 처리
        btnMultiLanguage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("좋아하는 언어를 모두 선택하세요!!!!")
                        .setIcon(android.R.drawable.ic_dialog_map)
                        .setMultiChoiceItems(languages, new boolean[]{
                                false, true, true, false, false,
                                false, false, false, false, false,
                                false, false, false, false, false,
                                false, false}, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                //선택을 한 경우에는 선택한 항목을 List에 추가
                                //선택을 하지 않은 경우는 List에서 제거
                                if (isChecked == true) {
                                    myLangugeList.add(languages[which]);
                                } else {
                                    myLangugeList.remove(languages[which]);
                                }
                                //출력
                                Log.e("내가 선택한 언어들", myLangugeList.toString());
                            }
                        })
                        .show();
            }
        });

        //로그인 버튼을 찾고 클릭 이벤트 처리
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //대화상자에 출력할 뷰를 생성 - login.xml 파일을 이용
                LinearLayout login = (LinearLayout) View.inflate(
                        AlertActivity.this,
                        R.layout.login,
                        null);
                //대화상자를 생성해서 출력
                new AlertDialog.Builder(AlertActivity.this)
                        .setTitle("로그인")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setView(login)
                        .setNegativeButton("취소", null)
                        .setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //id 입력 란 찾아오기
                                EditText tfId = login.findViewById(R.id.tfId);
                                //비밀번호 입력 란 찾아오기
                                EditText tfPassword = login.findViewById(R.id.tfPassword);

                                //입력한 아이디와 비밀번호를 로그에 출력하기
                                Log.e("아이디", tfId.getText().toString());
                                Log.e("비밀번호", tfPassword.getText().toString());
                            }
                        })
                        .show();
            }
        });

    }
}
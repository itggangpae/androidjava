package kakao.itstudy.adapterview;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JobDBHelper extends SQLiteOpenHelper {
    //default constructor가 없어서 생성자를 만들어 줍니다.
    public JobDBHelper(Context context){
        //상위 클래스의 생성자 호출
        super(context, "jobdb", null, 1);
    }

    //테이블을 생성하고 샘플 데이터를 삽입
    @Override
    public void onCreate(SQLiteDatabase db) {
        //테이블 생성 구문
        String tableSQL = "create table job_data(" +
                "_id integer primary key autoincrement, " +
                "name text not null," +
                "content text)";
        db.execSQL(tableSQL);

        //샘플 데이터 작성
        db.execSQL("insert into job_data(name, content) " +
                "values('SI', '외부 시스템 개발')");
        db.execSQL("insert into job_data(name, content) " +
                "values('SM', '시스템 운영 및 유지보수')");
        db.execSQL("insert into job_data(name, content) " +
                "values('QA', '품질 관리 및 테스트')");
        db.execSQL("insert into job_data(name, content) " +
                "values('DevOps', '시스템 개발 과 시스템 운영 환경 구축')");
        db.execSQL("insert into job_data(name, content) " +
                "values('MLOps', '머신러닝 과 시스템 운영 환경 구축')");
        db.execSQL("insert into job_data(name, content) " +
                "values('Back-End', '서비스 제공을 위한 프로그램 구현')");
        db.execSQL("insert into job_data(name, content) " +
                "values('Front-End', '서비스 사용 위한 프로그램 구현')");
        db.execSQL("insert into job_data(name, content) " +
                "values('FullStack', 'BackEnd + FrontEnd')");
        db.execSQL("insert into job_data(name, content) " +
                "values('DBA', '데이터베이스 설계 및 구축 과 운영')");
        db.execSQL("insert into job_data(name, content) " +
                "values('데이터분석가', '데이터 분석 및 AI 구현')");
        db.execSQL("insert into job_data(name, content) " +
                "values('Solution 기업', " +
                "'SI를 하다가 동일한 콘텐츠를 자주 구현하기 때문에 자체 프레임워크를 개발')");


    }

    //버전이 변경된 경우 호출
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //기존 테이블을 삭제하고 새로 생성
        db.execSQL("drop table job_data");
        onCreate(db);
    }
}

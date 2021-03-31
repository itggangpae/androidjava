package kakao.itstudy.localdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHandler extends SQLiteOpenHelper {

    //생성자 - SQLiteOpenHelper가 Default Constructor가 없어서 반드시 생성
    public MyDBHandler(@Nullable Context context) {
        super(context, "item.db", null, 1);
    }

    //데이터베이스를 사용할려고 할 때 데이터베이스 파일이 없으면 호출되는 메소드
    //테이블을 생성하고 샘플 데이터를 삽입
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table item(_id integer primary key," +
                "itemname text, quantity integer)");
    }

    //데이터베이스 버전이 변경된 경우 호출되는 메소드
    //기존 테이블을 제거하고 테이블을 새로 만들기
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //테이블 제거
        db.execSQL("drop table item");
        //테이블 생성
        onCreate(db);
    }

    //데이터를 삽입하는 메소드
    public void addItem(Item item){
        //ContentValues를 이용한 삽입

        //삽입할 객체를 생성
        ContentValues row = new ContentValues();
        row.put("itemname", item.get_itemname());
        row.put("quantity", item.get_quantity());

        //데이터베이스에 접속해서 row 삽입
        SQLiteDatabase db = getWritableDatabase();
        db.insert("item", null, row);
        db.close();
    }

    //제품이름을 가지고 검색해서 1개의 데이터를 리턴하는 메소드
    public Item findItem(String itemname){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(
                "select * from item where itemname =  ?",
                new String[]{itemname});

        Item item = new Item();
        //1개인 경우는 if 여러 개인 경우는 while
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            //첫번째 컬럼을 가져와서 정수로 변환한 후 id에 삽입
            item.set_id(Integer.parseInt(cursor.getString(0)));
            item.set_itemname(cursor.getString(1));
            item.set_quantity(Integer.parseInt(cursor.getString(2)));
            //커서 종료
            cursor.close();
        }else{
            item = null;
        }
        db.close();
        return item;
    }

    //itemname을 받아서 삭제하는 메소드
    public void deleteItem(String itemname){
        SQLiteDatabase db = getWritableDatabase();
        //?를 이용해서 파라미터를 바인딩 해서 SQL을 실행
        db.execSQL("delete from item where itemname = ?",
                new String[]{itemname});
        db.close();
    }
}

package kakao.itstudy.adapterview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    //2개는 외부로 부터 대입받아야 합니다.
    //뷰 전개를 위한 Context 프로퍼티
    Context context;
    //출력할 데이터 모임
    List<DataStructure> data;

    //뷰 전개(xml 파일의 내용을 자바 코드로 변경하는 것)를 위한 프로퍼티
    LayoutInflater inflater;

    //외부로부터 Context 와 List를 주입(Injection)받기 위한 생성
    public MyAdapter(Context context, List<DataStructure> data){
        this.context = context;
        this.data = data;
        //inflater를 생성
        inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

    }

    //ListView에 출력되는 데이터의 개수를 설정하는 메소드
    //이 메소드에서 리턴한 숫자만큼 아래 메소드들을 수행
    @Override
    public int getCount() {
        return data.size();
    }

    //항목 뷰에 보여지는 문자열을 출력하기 위한 객체를 리턴하는 메소드
    //position은 출력할 항목 뷰의 인덱스
    @Override
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    //출력되는 항목 뷰의 아이디를 설정하는 메소드
    //position을 그대로 리턴하는 것이 일반적
    @Override
    public long getItemId(int position) {
        return position;
    }

    //실제 항목 뷰를 생성하는 메소드
    //position - 인덱스
    //convertView - 이전에 출력한 뷰로 재사용을 위해서 전달 - null 일 수 있음
    //parent - ListView
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //재사용을 위한 뷰가 없으면 생성
        if(convertView == null){
            //layout 디렉토리의 icontent.xml 파일의 내용을 가지고
            //부모는 parent로 하고 최상위 뷰에 붙일 것인지 여부는 false 로 설정해서 생성
            convertView = inflater.inflate(R.layout.icontent, parent, false);
        }

        //이미지 출력
        //이미지 뷰를 찾아올 때 부모 뷰를 기준으로 찾아와야 합니다.
        ImageView imageView = (ImageView)convertView.findViewById(R.id.img);
        //position 번째 데이터를 가져와서 icon의 값을 가지고 출력
        imageView.setImageResource(data.get(position).getIcon());

        //제목 출력
        TextView textView = (TextView)convertView.findViewById(R.id.text);
        textView.setText(data.get(position).getName());

        //버튼을 가져와서 버튼 클릭 이벤트 작성
        Button button = (Button)convertView.findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //데이터 찾아오기
                DataStructure ds = data.get(position);
                //출력하기
                Snackbar.make(v, ds.getName() + ":" + ds.getDescription(),
                        Snackbar.LENGTH_LONG).show();

            }
        });
        return convertView;
    }
}

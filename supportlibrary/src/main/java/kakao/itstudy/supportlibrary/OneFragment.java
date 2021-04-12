package kakao.itstudy.supportlibrary;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.google.android.material.snackbar.Snackbar;

public class OneFragment extends ListFragment {
    @Override
    //뷰를 설정하는 메소드 - 이 경우는 상속을 받아서 뷰를 만들 필요하는 없음
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //데이터 생성해서 출력
        String [] datas =
                {"Java", "Node.js", "Django", "PHP"};
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(),
                        android.R.layout.simple_list_item_1, datas);
        setListAdapter(adapter);
    }

    @Override
    //항목을 클릭했을 때 호출되는 메소드
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        //선택한 항목에 해당하는 데이터를 찾아서 스낵바로 출력
        Snackbar.make(v, (String)l.getAdapter().getItem(position),
                Snackbar.LENGTH_LONG).show();
    }
}

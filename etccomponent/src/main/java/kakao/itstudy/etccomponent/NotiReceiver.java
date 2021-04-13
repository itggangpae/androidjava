package kakao.itstudy.etccomponent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       Log.e("리시버 호출", "사용자 알림에 의해서 호출되었습니다.");
    }
}
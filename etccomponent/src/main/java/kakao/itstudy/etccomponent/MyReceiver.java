package kakao.itstudy.etccomponent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //이 메소드가 부팅이 완료되서 호출된 것이라면
        if(intent.getAction() == Intent.ACTION_BOOT_COMPLETED){
            Log.e("메시지", "부팅 완료됨");
        }
    }
}
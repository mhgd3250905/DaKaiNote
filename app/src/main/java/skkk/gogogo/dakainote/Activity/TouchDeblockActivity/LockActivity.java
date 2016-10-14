package skkk.gogogo.dakainote.Activity.TouchDeblockActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.TouchDeblockView.TouchDeblockingView;

public class LockActivity extends AppCompatActivity {
    private TouchDeblockingView tdvLock;
    private SharedPreferences sPref;
    private String mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sPref=getSharedPreferences("note", MODE_PRIVATE);
        mPassword = sPref.getString("password", "");

        setContentView(R.layout.activity_lock);
        tdvLock= (TouchDeblockingView) findViewById(R.id.tdv_lock);
        tdvLock.setOnDrawFinishedListener(new TouchDeblockingView.OnDrawFinishListener() {

            @Override
            public boolean OnDrawFinished(List<Integer> passList) {
                StringBuilder sb=new StringBuilder();
                for (Integer i:passList){
                    sb.append(i);
                }
                if (sb.toString().equals(mPassword)){
                    Toast.makeText(LockActivity.this, "正确", Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    Toast.makeText(LockActivity.this, "错误", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        });
    }
}

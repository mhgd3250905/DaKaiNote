package skkk.gogogo.dakainote.Activity.TouchDeblockActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.TouchDeblockView.TouchDeblockingView;

public class TouchDeblockActivity extends AppCompatActivity {
    TouchDeblockingView tdvDeblock;
    List<Integer> passList;
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sPref=getSharedPreferences("note",MODE_PRIVATE);
        setContentView(R.layout.activity_touch_deblock);
        tdvDeblock= (TouchDeblockingView) findViewById(R.id.tdv_deblock);

        tdvDeblock.setOnDrawFinishedListener(new TouchDeblockingView.OnDrawFinishListener() {
            @Override
            public boolean OnDrawFinished(List<Integer> passList) {
                if (passList.size()<3){
                    Toast.makeText(TouchDeblockActivity.this, "密码过短...", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    TouchDeblockActivity.this.passList=passList;
                    return true;
                }
            }
        });
    }

    public void settingPW(View v){
        //重置密码
        tdvDeblock.resetPoints();
    }

    public void savePW(View v){
        //保存密码
        if (passList!=null){
            StringBuilder sb=new StringBuilder();
            for (Integer i:passList){
                sb.append(i);
            }
            sPref.edit().putString("password",sb.toString()).commit();
            Toast.makeText(TouchDeblockActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TouchDeblockActivity.this,LockActivity.class));
        }
    }
}

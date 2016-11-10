package skkk.gogogo.dakainote.Presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.Activity.HomeActivity.UIHomeActivity;
import skkk.gogogo.dakainote.Application.MyApplication;
import skkk.gogogo.dakainote.DbTable.Note;
import skkk.gogogo.dakainote.Fragment.NoteListFragment;
import skkk.gogogo.dakainote.MyUtils.SQLUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.TouchDeblockView.TouchDeblockingView;

/**
 * Created by admin on 2016/10/22.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/10/22$ 13:29$.
*/
public class UIHomePresenter {

    private UIHomeActivity mUIHomeActivity;
    private SharedPreferences sPref;
    private MyApplication mApplication;
    private List<Note> myNotes=new ArrayList<Note>();


    public UIHomePresenter(UIHomeActivity uiHomeActivity) {
        mApplication= (MyApplication) uiHomeActivity.getApplicationContext();
        mUIHomeActivity = uiHomeActivity;
        sPref=uiHomeActivity.getSharedPreferences("note", Context.MODE_PRIVATE);
    }

    public boolean isServiceNeedOpen(){
        return sPref.getBoolean("notify",true);
    }

    /*
    * @方法 获取夜间模式判定
    * @参数 null
    * @返回值 boolean
    */
    public boolean getThemeNight(){
        return sPref.getBoolean("night", false);
    }

    /*
    * @方法 获取Note风格
    * @参数 null
    * @返回值 int
    */
    public int getNoteStyle(){
        return sPref.getInt("note_style", 1);
    }

    /*
    * @方法 获取缓存中的NoteList
    * @参数 null
    * @返回值 List<Note>
    */
    public List<Note> getNoteList(){
        myNotes=mApplication.getNotes();
        return myNotes;
    }

    /*
    * @方法 获取Note是否上锁
    * @参数 null
    * @返回值 boolean
    */
    public boolean getLockedFlag(){
        return sPref.getBoolean("lock", false);
    }

    /*
    * @方法 获取保存的密码
    * @参数 null
    * @返回值 String
    */
    public String getPassword(){
        return sPref.getString("password", "");
    }


    /*
    * @方法 解锁
    * @参数 List<Integer> passList / TouchDeblockingView tdvLock / Dialog dialog
    * @返回值 boolean
    */
    public boolean deblocking(List<Integer> passList,TouchDeblockingView tdvLock,Dialog dialog){
        if (passList.size() < 4) {
            Snackbar.make(tdvLock, "密码过短，请重新输入", Snackbar.LENGTH_SHORT).show();
            tdvLock.resetPoints();
            return false;
        } else {

            StringBuilder sb = new StringBuilder();

            for (Integer i : passList) {
                sb.append(i);
            }

            if (getPassword().equals(sb.toString())) {
                dialog.dismiss();
                return true;
            } else {
                return false;
            }
        }
    }

    /*
    * @方法 选择侧滑菜单不同栏位的处理逻辑
    * @参数
    * @返回值 是否弹出Toast
    */
    public boolean checkNavigationItem(NoteListFragment noteListFragment,int id){
        switch (id){
            case R.id.nav_list:
                myNotes = SQLUtils.getNoteList();
                break;
            case R.id.nav_image:
                myNotes = SQLUtils.getImageNoteList();
                break;
            case R.id.nav_pin:
                myNotes = SQLUtils.getPinNoteList();
                break;
            case R.id.nav_schedule:
                myNotes = SQLUtils.getScheduleNoteList();
                break;
            case R.id.nav_voice:
                myNotes = SQLUtils.getVoiceNoteList();
                break;
        }

        if (myNotes.size() == 0) {
           return true;
        } else {
            noteListFragment.updateAll(myNotes);
            return false;
        }
    }
}

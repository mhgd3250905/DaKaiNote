package skkk.gogogo.dakainote.MyUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.DbTable.Note;

/**
 * Created by admin on 2016/8/22.
 */
/*
* 
* 描    述：数据库工具类
* 作    者：ksheng
* 时    间：2016/8/22$ 22:38$.
*/
public class SQLUtils {
    /*
    * @方法 获取数据库中所有的条目
    *
    */
    public static List<Note> getNoteList(){
        List<Note> noteList=new ArrayList<Note>();
        int j=0;
        for (int i= DataSupport.findAll(Note.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.findAll(Note.class).get(i));
            j++;
        }
        return noteList;
    }

    /*
    * @方法 获取数据库中所有有图片的
    *
    */
    public static List<Note> getImageNoteList(){
        List<Note> noteList=new ArrayList<Note>();
        int j=0;

        for (int i= DataSupport.where("imageisexist = ?","1").find(Note.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.where("imageisexist = ?","1").find(Note.class).get(i));
            j++;
        }
        return noteList;
    }

    /*
    * @方法 获取数据库中所有PIN的
    *
    */
    public static List<Note> getPinNoteList(){
        List<Note> noteList=new ArrayList<Note>();
        int j=0;
        for (int i= DataSupport.where("pinisexist = ?","1").find(Note.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.where("pinisexist = ?","1").find(Note.class).get(i));
            j++;
        }
        return noteList;
    }

    /*
    * @方法 获取数据库中所有包含关键字的
    *
    */
    public static List<Note> getkeyNoteList(String key){
        List<Note> noteList=new ArrayList<Note>();
        int j=0;
        for (int i= DataSupport.where("title like ?","_" + key + "%").find(Note.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.where("title like ?","_" + key + "%").find(Note.class).get(i));
            j++;
        }
        return noteList;
    }





}

package skkk.gogogo.dakainote.MyUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import skkk.gogogo.dakainote.DbTable.ImageCache;
import skkk.gogogo.dakainote.DbTable.NoteNew;
import skkk.gogogo.dakainote.DbTable.VoiceCache;

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
    public static List<NoteNew> getNoteList(){
        List<NoteNew> noteList=new ArrayList<NoteNew>();
        int j=0;
        for (int i= DataSupport.findAll(NoteNew.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.findAll(NoteNew.class).get(i));
            j++;
        }
        return noteList;
    }

    /*
    * @方法 获取数据库中所有有图片的
    *
    */
    public static List<NoteNew> getImageNoteList(){
        List<NoteNew> noteList=new ArrayList<NoteNew>();
        int j=0;

        for (int i= DataSupport.where("imageisexist = ?","1").find(NoteNew.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.where("imageisexist = ?","1").find(NoteNew.class).get(i));
            j++;
        }
        return noteList;
    }

    /*
    * @方法 获取数据库中所有PIN的
    *
    */
    public static List<NoteNew> getPinNoteList(){
        List<NoteNew> noteList=new ArrayList<NoteNew>();
        int j=0;
        for (int i= DataSupport.where("pinisexist = ?","1").find(NoteNew.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.where("pinisexist = ?","1").find(NoteNew.class).get(i));
            j++;
        }
        return noteList;
    }

    /*
   * @方法 获取数据库中所有Voice的
   *
   */
    public static List<NoteNew> getVoiceNoteList(){
        List<NoteNew> noteList=new ArrayList<NoteNew>();
        int j=0;
        for (int i= DataSupport.where("pinisexist = ?","1").find(NoteNew.class).size()-1;i>=0;i--){
            noteList.add(j, DataSupport.where("pinisexist = ?","1").find(NoteNew.class).get(i));
            j++;
        }
        return noteList;
    }


    /*
    * @方法 获取缓存数据库中所有的图片信息
    *
    */
    public static List<ImageCache> getImageInItem(long notekey){
        List<ImageCache> images=new ArrayList<ImageCache>();
        images= DataSupport.where("noteKey=?", notekey + "").find(ImageCache.class);
        return images;
    }

    /*
   * @方法 获取缓存数据库中所有的Voice信息
   *
   */
    public static List<VoiceCache> getVoiceInItem(long notekey){
        List<VoiceCache> voiceCaches=new ArrayList<VoiceCache>();
        voiceCaches= DataSupport.where("noteKey=?", notekey + "").find(VoiceCache.class);
        return voiceCaches;
    }





}

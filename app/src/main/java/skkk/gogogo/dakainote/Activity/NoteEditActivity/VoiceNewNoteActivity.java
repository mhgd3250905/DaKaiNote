package skkk.gogogo.dakainote.Activity.NoteEditActivity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;

import skkk.gogogo.dakainote.DbTable.VoiceCache;
import skkk.gogogo.dakainote.MyUtils.AudioRecorder;
import skkk.gogogo.dakainote.MyUtils.LogUtils;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.RecordButton;

/**
 * Created by admin on 2016/8/30.
 */
/*
* 
* 描    述：
* 作    者：ksheng
* 时    间：2016/8/30$ 22:56$.
*/
public class VoiceNewNoteActivity extends ShowNewNoteActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置NestedScrollView
        nsvNoteDetail= (NestedScrollView) findViewById(R.id.nsv_note_detail);
        //设置录音按钮
        rbVoice= (RecordButton) findViewById(R.id.rb_voice);
        rbVoice.setAudioRecord(new AudioRecorder());
        rbVoice.setRecordListener(new RecordButton.RecordListener() {
            @Override
            public void recordEnd(String filePath) {
                fl_note_voice.setVisibility(View.VISIBLE);

                //设置图片存在
                isVoiceExist=true;
                VoiceCache voiceCache = new VoiceCache();
                voiceCache.setNoteKey(noteKey);
                voiceCache.setVoicePath(filePath);
                voiceCache.save();
                //获取当前fragment

                mVoiceNewNoteFragment.insertVoice(noteKey);
                LogUtils.Log("这里是onActivityResult");
                isDelete = true;

                rbVoice.setVisibility(View.GONE);
            }
        });
    }

}

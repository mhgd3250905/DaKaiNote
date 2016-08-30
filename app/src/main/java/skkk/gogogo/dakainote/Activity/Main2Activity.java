package skkk.gogogo.dakainote.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import skkk.gogogo.dakainote.MyUtils.AudioRecorder;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.AudioButton;
import skkk.gogogo.dakainote.View.RecordButton;

public class Main2Activity extends AppCompatActivity {

    private RecordButton rbVoice;
    private AudioButton abVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        abVoice= (AudioButton) findViewById(R.id.ab_voice);
        rbVoice= (RecordButton) findViewById(R.id.rb_voice);
        rbVoice.setAudioRecord(new AudioRecorder());
        rbVoice.setRecordListener(new RecordButton.RecordListener() {
            @Override
            public void recordEnd(String filePath) {
                abVoice.setVoicePath(filePath);
            }
        });
    }
}

package skkk.gogogo.dakainote.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import skkk.gogogo.dakainote.MyUtils.AudioRecorder;
import skkk.gogogo.dakainote.R;
import skkk.gogogo.dakainote.View.RecordButton;

public class Main2Activity extends AppCompatActivity {

    private RecordButton rbVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rbVoice= (RecordButton) findViewById(R.id.rb_voice);
        rbVoice.setAudioRecord(new AudioRecorder());
    }
}

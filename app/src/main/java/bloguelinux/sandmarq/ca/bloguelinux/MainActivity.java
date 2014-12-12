package bloguelinux.sandmarq.ca.bloguelinux;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    String url = "http://live.bloguelinux.ca/"; // your URL here
    MediaPlayer mediaPlayer = new MediaPlayer();
    Button bPlay ;
    Button bPause;
    Button bStop ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bPlay = (Button) findViewById(R.id.bPlay);
        bPause = (Button) findViewById(R.id.bPause);
        bStop = (Button) findViewById(R.id.bStop);
        bPlay.setClickable(false);
        bPlay.setEnabled(false);
        bPause.setClickable(false);
        bPause.setEnabled(false);
        bStop.setClickable(false);
        bStop.setEnabled(false);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        bPlay.setClickable(true);
        bPlay.setEnabled(true);
        bPause.setClickable(true);
        bPause.setEnabled(true);
        bStop.setClickable(true);
        bStop.setEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void play(View view) {
        bPlay.setClickable(false);
        bPlay.setEnabled(false);
        bPause.setClickable(true);
        bPause.setEnabled(true);
        bStop.setClickable(true);
        bStop.setEnabled(true);
        mediaPlayer.start();
    }


    public void pause(View view) {
        bPlay.setClickable(true);
        bPlay.setEnabled(true);
        bPause.setClickable(false);
        bPause.setEnabled(false);
        bStop.setClickable(false);
        bStop.setEnabled(false);
        mediaPlayer.pause();
    }


    public void stop(View view) {
        bPlay.setClickable(false);
        bPlay.setEnabled(false);
        bPause.setClickable(false);
        bPause.setEnabled(false);
        bStop.setClickable(false);
        bStop.setEnabled(false);
        mediaPlayer.stop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}


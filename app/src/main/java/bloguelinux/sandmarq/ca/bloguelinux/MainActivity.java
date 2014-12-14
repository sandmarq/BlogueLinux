package bloguelinux.sandmarq.ca.bloguelinux;

import android.content.res.Resources;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    String url = "http://live.bloguelinux.ca/"; // your URL here
    Uri myUri = Uri.parse(url);
    MediaPlayer mediaPlayer = new MediaPlayer();
    Button bPlay;
    Button bPause;
    Button bStop;
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bPlay = (Button) findViewById(R.id.bPlay);
        bPause = (Button) findViewById(R.id.bPause);
        bStop = (Button) findViewById(R.id.bStop);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        bPlay.setClickable(true);
        bPlay.setEnabled(true);
        bPause.setClickable(false);
        bPause.setEnabled(false);
        bStop.setClickable(false);
        bStop.setEnabled(false);
        tvMsg.setText(String.format(getResources().getString(R.string.tvPressP)));

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
        bPause.setClickable(false);
        bPause.setEnabled(false);
        bStop.setClickable(false);
        bStop.setEnabled(false);

        try {
            tvMsg.setText(getResources().getString(R.string.txOpen) + " " + url);
            mediaPlayer.setDataSource(MainActivity.this, myUri);
        } catch (IOException e) {
            Log.e("tag", e.getMessage(), e);
            tvMsg.setText(e.toString());
        }
        tvMsg.setText(getResources().getString(R.string.txBuff) + " " + url);
        mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                bPlay.setClickable(false);
                bPlay.setEnabled(false);
                bPause.setClickable(true);
                bPause.setEnabled(true);
                bStop.setClickable(true);
                bStop.setEnabled(true);
                tvMsg.setText(getResources().getString(R.string.txPlay) + " " + url);
                mediaPlayer.start();
            }

        });
    }


    public void pause(View view) {
        bPlay.setClickable(false);
        bPlay.setEnabled(false);
        bPause.setClickable(true);
        bPause.setEnabled(true);
        bStop.setClickable(true);
        bStop.setEnabled(true);

        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            tvMsg.setText(getResources().getString(R.string.txPause) + " " + url);
        } else {
            mediaPlayer.start();
            tvMsg.setText(getResources().getString(R.string.txPlay) + " " + url);
        }
    }


    public void stop(View view) {
        bPlay.setClickable(true);
        bPlay.setEnabled(true);
        bPause.setClickable(false);
        bPause.setEnabled(false);
        bStop.setClickable(false);
        bStop.setEnabled(false);
        mediaPlayer.stop();
        mediaPlayer.reset();
        tvMsg.setText(String.format(getResources().getString(R.string.tvPressP)));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}
package bloguelinux.sandmarq.ca.bloguelinux;

import android.content.res.Resources;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
    //Uri myUri = Uri.parse(url);
    private Handler myHandler = new Handler();
    Player mediaPlayer = new Player(url);
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
       mediaPlayer.Play();
        myHandler.postDelayed(UpdateInterface, 500);
    }


    public void pause(View view) {
        mediaPlayer.Pause();
    }


    public void stop(View view) {
        mediaPlayer.Stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.Release();
    }

    // 0 : Stop, 1 : opening, 2 : buffering, 3 : paused, 4 : Playing
    private Runnable UpdateInterface = new Runnable() {
        public void run() {
            switch (mediaPlayer.getStatus()){
                case 0: // Stop
                    bPlay.setClickable(true);
                    bPlay.setEnabled(true);
                    bPause.setClickable(false);
                    bPause.setEnabled(false);
                    bStop.setClickable(false);
                    bStop.setEnabled(false);
                    tvMsg.setText(String.format(getResources().getString(R.string.tvPressP)));
                    break;
                case 1: // Opening
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(false);
                    bPause.setEnabled(false);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(String.format(getResources().getString(R.string.txOpen)));
                    break;
                case 2: // buffering
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(false);
                    bPause.setEnabled(false);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(String.format(getResources().getString(R.string.txBuff)));
                    break;
                case 3: // paused
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(true);
                    bPause.setEnabled(true);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(String.format(getResources().getString(R.string.txPause)));
                    break;
                case 4: // playing
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(true);
                    bPause.setEnabled(true);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(String.format(getResources().getString(R.string.txPlay)));
                    break;
            }
            myHandler.postDelayed(this, 500);
        }
    };

}
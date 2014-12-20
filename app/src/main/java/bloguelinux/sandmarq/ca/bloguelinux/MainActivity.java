package bloguelinux.sandmarq.ca.bloguelinux;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "BlogueLinux";
    private static final String KEY_INDEX = "status";

    String url = "http://live.bloguelinux.ca/"; // your URL here
    //Uri myUri = Uri.parse(url);
    private Handler myHandler = new Handler();
    private int statusint;
    Player mediaPlayer = new Player(url);
    Button bPlay;
    Button bPause;
    Button bStop;
    TextView tvMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            statusint = 0;
        }

        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        bPlay = (Button) findViewById(R.id.bPlay);
        bPause = (Button) findViewById(R.id.bPause);
        bStop = (Button) findViewById(R.id.bStop);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        myHandler.postDelayed(UpdateInterface, 100);
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
        statusint = mediaPlayer.getStatus();
        Log.d(TAG, "play() called");
        Log.d(TAG, Integer.toString(statusint));
    }


    public void pause(View view) {
        mediaPlayer.Pause();
        statusint = mediaPlayer.getStatus();
        Log.d(TAG, "pause() called");
        Log.d(TAG, Integer.toString(statusint));
    }


    public void stop(View view) {
        mediaPlayer.Stop();
        statusint = mediaPlayer.getStatus();
        Log.d(TAG, "stop() called");
        Log.d(TAG, Integer.toString(statusint));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        Log.d(TAG, Integer.toString(statusint));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        Log.d(TAG, Integer.toString(statusint));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        Log.d(TAG, Integer.toString(statusint));
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
        Log.d(TAG, Integer.toString(statusint));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            statusint = mediaPlayer.getStatus();
            Log.d(TAG, "onDestroyed() called");
            Log.d(TAG, Integer.toString(statusint));
            mediaPlayer.Release();
        }
    }

    // 0 : Stop, 1 : opening, 2 : buffering, 3 : paused, 4 : Playing
    private Runnable UpdateInterface = new Runnable() {
        public void run() {
            if (mediaPlayer.isPlaying()){
                statusint = 4;
            } else if (mediaPlayer == null){
                statusint = 0;
            } else {
                statusint = mediaPlayer.getStatus();
            }

            switch (statusint) {
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
                    tvMsg.setText(String.format(getResources().getString(R.string.txOpen) + " " + url));
                    break;
                case 2: // buffering
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(false);
                    bPause.setEnabled(false);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(String.format(getResources().getString(R.string.txBuff) + " " + url));
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
                    tvMsg.setText(String.format(getResources().getString(R.string.txPlay) + " " + url));
                    break;
            }
            myHandler.postDelayed(this, 100);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        Log.d(TAG, Integer.toString(statusint));
        savedInstanceState.putInt(KEY_INDEX, statusint);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        statusint = savedInstanceState.getInt(KEY_INDEX,0);
    }
}
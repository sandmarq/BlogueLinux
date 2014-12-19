package bloguelinux.sandmarq.ca.bloguelinux;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


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
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        bPlay = (Button) findViewById(R.id.bPlay);
        bPause = (Button) findViewById(R.id.bPause);
        bStop = (Button) findViewById(R.id.bStop);
        tvMsg = (TextView) findViewById(R.id.tvMsg);

        if (savedInstanceState != null) {
            statusint = savedInstanceState.getInt(KEY_INDEX);
        }

        myHandler.postDelayed(UpdateInterface, 500);
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
        Log.d(TAG, "play() called");
        mediaPlayer.Play();
    }


    public void pause(View view) {
        Log.d(TAG, "pause() called");
        mediaPlayer.Pause();
    }


    public void stop(View view) {
        Log.d(TAG, "stop() called");
        mediaPlayer.Stop();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing()) {
            Log.d(TAG, "onDestroyed() called");
            mediaPlayer.Release();
        }
    }

    // 0 : Stop, 1 : opening, 2 : buffering, 3 : paused, 4 : Playing
    private Runnable UpdateInterface = new Runnable() {
        public void run() {
            if (mediaPlayer != null){
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
            myHandler.postDelayed(this, 500);
        }
    };

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, statusint);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        statusint = savedInstanceState.getInt(KEY_INDEX);
    }

}
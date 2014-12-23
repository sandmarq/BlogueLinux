package bloguelinux.sandmarq.ca.bloguelinux;

import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Sampler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import android.widget.*;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "BlogueLinux";
    private static final String KEY_INDEX = "status";
    private ShowsList[] mlist = new ShowsList[]{
            new ShowsList("Live", "Live feed", "http://live.bloguelinux.ca/", "http://live.bloguelinux.ca/"),
            new ShowsList("Émission #74 du 18 décembre 2014 – Les dents me pètent dans yeule", "Bonne fête bloguelinux a 3 ans", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_74.mp3", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_74.ogg"),
            new ShowsList("L'après CAST – Émission #25 du 14 décembre 2014", "AprèsCast", "http://www.bloguelinux.ca/wp-content/uploads/apres_cast/ap_emission_25.mp3", "http://www.bloguelinux.ca/wp-content/uploads/apres_cast/ap_emission_25.ogg"),
    };
    private Handler myHandler = new Handler();
    private int statusint;
    private String url = "http://live.bloguelinux.ca/"; // your URL here
    private String urlPodcast = "http://feeds.feedburner.com/Bloguelinux_Podcast";
    private String urlAprescast = "http://feeds.feedburner.com/apres_cast";
    private Long timer = 0L;
    private String sTimer;
    private Player mediaPlayer = new Player();
    private Button bPlay;
    private Button bPause;
    private Button bStop;
    private TextView tvMsg;
    private String message;
    private TextView tvTimer;
    private ListView lvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ListAdapter listAdapter = new MyAdapterShowList(this,mlist);

        if (savedInstanceState == null) {
            statusint = 0;
        }

        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);
        bPlay = (Button) findViewById(R.id.bPlay);
        bPause = (Button) findViewById(R.id.bPause);
        bStop = (Button) findViewById(R.id.bStop);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        lvTest = (ListView) findViewById(R.id.listView);
        tvTimer = (TextView) findViewById(R.id.tvTimer);

        lvTest.setAdapter(listAdapter);

        lvTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mediaPlayer.setUrl(mlist[position].getLienOgg());
                mediaPlayer.Play();
                statusint = mediaPlayer.getStatus();
                Log.d(TAG, "playing selection() called");
                Log.d(TAG, Integer.toString(statusint));
                message = String.format(getResources().getString(R.string.txPlay) + " " + mlist[position].getTitre());
            }
        });

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
        mediaPlayer.setUrl(url);
        mediaPlayer.Play();
        statusint = mediaPlayer.getStatus();
        message = String.format(getResources().getString(R.string.txPlay) + " " + url);
        Log.d(TAG, "play() called");
        Log.d(TAG, Integer.toString(statusint));
    }


    public void pause(View view) {
        mediaPlayer.Pause();
        statusint = mediaPlayer.getStatus();
        message = String.format(getResources().getString(R.string.txPause) + " " + url);
        Log.d(TAG, "pause() called");
        Log.d(TAG, Integer.toString(statusint));
    }


    public void stop(View view) {
        mediaPlayer.Stop();
        timer = 0L;
        statusint = mediaPlayer.getStatus();
        message = String.format(getResources().getString(R.string.txOpen) + " " + url);
        Log.d(TAG, "stop() called");
        Log.d(TAG, Integer.toString(statusint));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
        Log.d(TAG, Integer.toString(statusint));
        message = String.format(getResources().getString(R.string.txOpen) + " " + url);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        Log.d(TAG, Integer.toString(statusint));
        message = String.format(getResources().getString(R.string.txPause) + " " + url);
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
            if (mediaPlayer.isPlaying()) {
                statusint = 4;
            } else if (mediaPlayer == null) {
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
                    break;
                case 1: // Opening
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(false);
                    bPause.setEnabled(false);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(message);
                    break;
                case 2: // buffering
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(false);
                    bPause.setEnabled(false);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(message);
                    break;
                case 3: // paused
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(true);
                    bPause.setEnabled(true);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(message);
                    break;
                case 4: // playing
                    bPlay.setClickable(false);
                    bPlay.setEnabled(false);
                    bPause.setClickable(true);
                    bPause.setEnabled(true);
                    bStop.setClickable(true);
                    bStop.setEnabled(true);
                    tvMsg.setText(message);
                    break;
            }

            if (mediaPlayer.isPlaying()) {
                timer = timer + 100L;
                //tvTimer.setText(Long.toString(timer / (1000*60*60)));
            }
            sTimer = String.format("%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(timer),
                    TimeUnit.MILLISECONDS.toMinutes(timer) -
                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timer)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(timer) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timer)));
            tvTimer.setText(sTimer);

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
        Log.i(TAG, "onRestoreInstanceState");
        Log.d(TAG, Integer.toString(statusint));
        statusint = savedInstanceState.getInt(KEY_INDEX, 0);
    }
}

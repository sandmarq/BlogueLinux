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
import android.widget.*;


public class MainActivity extends ActionBarActivity {
    String url = "http://live.bloguelinux.ca/"; // your URL here
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
		tvMsg.setText("Presse play to start");

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
			tvMsg.setText("Opening URL " + url);
            mediaPlayer.setDataSource(url);
			tvMsg.setText("Buffering " + url);
			mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
			tvMsg.setText(e.toString());
        }
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer player) {
					player.start();
					bPlay.setClickable(false);
					bPlay.setEnabled(false);
					bPause.setClickable(true);
					bPause.setEnabled(true);
					bStop.setClickable(true);
					bStop.setEnabled(true);
					tvMsg.setText(player.getTrackInfo().toString());
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
			tvMsg.setText("Paused");
        } else {
            mediaPlayer.start();
			tvMsg.setText("Playing");
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
		tvMsg.setText("Presse play to start");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

}


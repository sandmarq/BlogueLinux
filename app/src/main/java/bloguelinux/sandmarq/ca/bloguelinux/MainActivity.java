package bloguelinux.sandmarq.ca.bloguelinux;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    String url = "http://live.bloguelinux.ca/"; // your URL here
    MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preparePlayer();

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

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void preparePlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mediaPlayer.reset();
                    Log.e("tag", "Media Player onError callback!");
                    return true;
                }
            });
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    ToggleButton playButton = (ToggleButton) findViewById(R.id.playToggleButton);
                    playButton.setClickable(true);
                    mp.start();
                }
            });
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();

        } catch (IllegalArgumentException e) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.erreurIllegalArgument), Toast.LENGTH_LONG).show();
            Log.e("tag", e.getMessage(), e);
        } catch (IllegalStateException e) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.erreurIllegalState), Toast.LENGTH_LONG).show();
            Log.e("tag", e.getMessage(), e);
        } catch (IOException e) {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.erreurIO), Toast.LENGTH_LONG).show();
            Log.e("tag", e.getMessage(), e);
        }
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

}
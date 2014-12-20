package bloguelinux.sandmarq.ca.bloguelinux;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

/**
 * Created by SANDRINE.MARQUIS on 2014-12-19.
 */
public class Player {

    private static final String TAG = "BlogueLinux|Player";

    private String url = null; // your URL here
    private static int status = 0; // 0 : Stop, 1 : opening, 2 : buffering, 3 : paused, 4 : Playing
    private MediaPlayer mediaPlayer = new MediaPlayer();

    public int getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int Play() {
        try {
            mediaPlayer.setDataSource(url);
            status = 1;
        } catch (IOException e) {
            Log.e("tag", e.getMessage(), e);
            status = 0;
        }
        status = 2;
        mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                status = 4;
                Log.d(TAG, "Start playing");
                Log.d(TAG, Integer.toString(status));
                mediaPlayer.start();
            }

        });
        return status;
    }

    public int Stop() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        status = 0;
        return status;
    }

    public int Pause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            status = 3;
        } else {
            mediaPlayer.start();
            status = 4;
        }
        return status;
    }

    public void Release() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public Player(String url) {
        setUrl(url);
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

}
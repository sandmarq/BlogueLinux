package bloguelinux.sandmarq.ca.bloguelinux;

import android.app.DialogFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.widget.*;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "BlogueLinux";
    private static final String KEY_INDEX = "status";

    private ShowsList[] mlist = new ShowsList[]{
            new ShowsList("Live", "Live feed", "http://live.bloguelinux.ca/", "http://live.bloguelinux.ca/"),
            new ShowsList("Émission #74 du 18 décembre 2014 – Les dents me pètent dans yeule", "Bonne fête bloguelinux a 3 ans", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_74.mp3", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_74.ogg"),
            new ShowsList("L'après CAST – Émission #25 du 14 décembre 2014", "AprèsCast", "http://www.bloguelinux.ca/wp-content/uploads/apres_cast/ap_emission_25.mp3", "http://www.bloguelinux.ca/wp-content/uploads/apres_cast/ap_emission_25.ogg"),
            new ShowsList("Émission #73 du 4 décembre 2014 – Monté en cabochon", "Expression Québécoise qui veut dire : Quelques que chose qui est fait de façon maladroite, bâclé rapidement ou tout simplement mal fait.", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_73.mp3", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_73.ogg"),
            new ShowsList("Émission #72 du 20 novembre 2014 – Ça pas d’allure", "Expression Québécoise qui veut dire : Expression utilisée souvent par Éric pour pour d’écrire quelque chose d’impressionnant ou qui n’est pas normal (ça n’a pas de bon sens), c’est n’importe quoi.", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_72.mp3", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_72.ogg"),
            new ShowsList("Émission #71 du 6 novembre 2014 – Y a du monde à messe", "Expression Québécoise qui veut dire : Avoir beaucoup de monde, avoir une foule, ou plus de monde que prévu.", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_71.mp3", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_71.ogg"),
            new ShowsList("Émission #70 du 9 octobre 2014 – Miss calembour 2014", "Il ne s’agit pas d’une expression québécoise, mais il faut écouter l »émission pour comprendre ; l’expression fait référence à Sandrine qui fait souvent des jeux de mots qui nous prends un certains temps à comprendre ", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_70.mp3", "http://www.bloguelinux.ca/wp-content/uploads/podcast/emission_70.ogg"),
    };
	
    private Handler myHandler = new Handler();
    private int statusint;
    private String url = "http://live.bloguelinux.ca/", urlPodcast = "http://feeds.feedburner.com/Bloguelinux_Podcast", urlAprescast = "http://feeds.feedburner.com/apres_cast", sTimer, message;
    private Long timer = 0L;
    private Player mediaPlayer = new Player();
    private Button bPlay, bPause, bStop;
    private TextView tvMsg, tvTimer;
    private ListView lvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            statusint = 0;
        }

        initializeViews();

		initializeListAdapter(); 

        myHandler.postDelayed(UpdateInterface, 100);

    }

    private void initializeListAdapter() {
        final ListAdapter listAdapter = new MyAdapterShowList(this, mlist);

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
    }

    private void initializeViews() {
        bPlay = (Button) findViewById(R.id.bPlay);
        bPause = (Button) findViewById(R.id.bPause);
        bStop = (Button) findViewById(R.id.bStop);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        lvTest = (ListView) findViewById(R.id.listView);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
    }

    public boolean checkConnectionStatus() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    // Status return connection type
    // 0 = none
    // 1 = WIFI
    // 2 = 3G
    // 3 = WIFI,3G
    public int checkConnectionType() {
        int status = 0;
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (checkConnectionStatus()) {
            NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            boolean isWifiConn = false;
            if (networkInfo != null) {
                isWifiConn = networkInfo.isConnected();
            }
            if (isWifiConn) {
                status = 1;
            }

            boolean isMobileConn = false;

            networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (networkInfo != null) {
                isMobileConn = networkInfo.isConnected();
            }
            if (isMobileConn) {
                switch (status) {
                    case 0:
                        status = 2;
                        break;
                    case 1:
                        status = 3;
                        break;
                }
            }
            return status;
        } else {
            return status;
        }
    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<ShowsList>> {
        @Override
        protected List<ShowsList> doInBackground(String... urls) {

            // Debug the task thread name
            Log.d(TAG, Thread.currentThread().getName());

            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);

                // Parse RSS, get items
                return rssReader.getItems();

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }
        @Override
        protected void onPostExecute(List<ShowsList> result) {

            // Get a ListView from main view
            ListView itcItems = (ListView) findViewById(R.id.listView);

            // Create a list adapter
            //ArrayAdapter<ShowsList> adapter = new ArrayAdapter<ShowsList>(local,R.layout.shows_list, result);
            final ListAdapter adapter = new MyAdapterShowList(MainActivity.this, mlist);

            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);

            // Set list view item click listener
            //itcItems.setOnItemClickListener(new ListListener(result, local));
            itcItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        }
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
            } else if (id == R.id.menu_exit) {
                Log.d(TAG, "finish() called");
                if (mediaPlayer != null) {
                    mediaPlayer.Release();
                }
                myHandler.removeCallbacks(UpdateInterface);
                finish();
            } else if (id == R.id.menu_about) {

                DialogFragment myDialogFragmentAbout = new MyDialogFragmentAbout();

                myDialogFragmentAbout.show(getFragmentManager(), "theDialogAbout");

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

        public void setButtons(Boolean setButtonPlay, Boolean setButtonPause, Boolean setButtonStop) {
            bPlay.setClickable(setButtonPlay);
            bPlay.setEnabled(setButtonPlay);
            bPause.setClickable(setButtonPause);
            bPause.setEnabled(setButtonPause);
            bStop.setClickable(setButtonStop);
            bStop.setEnabled(setButtonStop);
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
                if (checkConnectionStatus()) {
                    lvTest.setFocusable(true);
                    if (mediaPlayer.isPlaying()) {
                        statusint = 4;
                    } else if (mediaPlayer == null) {
                        statusint = 0;
                    } else {
                        statusint = mediaPlayer.getStatus();
                    }

                    switch (statusint) {
                        case 0: // Stop
                            setButtons(true, false, false);
                            break;
                        case 1: // Opening
                            setButtons(false, false, true);
                            tvMsg.setText(message);
                            break;
                        case 2: // buffering
                            setButtons(false, false, true);
                            tvMsg.setText(message);
                            break;
                        case 3: // paused
                            setButtons(false, true, false);
                            tvMsg.setText(message);
                            break;
                        case 4: // playing
                            setButtons(false, true, true);
                            tvMsg.setText(message);
                            break;
                    }
                } else {
                    setButtons(false, false, false);
                    lvTest.setFocusable(false);
                    if (mediaPlayer.getStatus() == 4) {
                        mediaPlayer.Pause();
                    }
                }

                if (mediaPlayer.isPlaying()) {
                    timer = timer + 100L;
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

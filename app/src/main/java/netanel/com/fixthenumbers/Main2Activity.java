package netanel.com.fixthenumbers;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {
    int[][] gameState = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    int[][] gameTaeget = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
    int max_Col = gameState.length;
    int max_Row = gameState[0].length;
    int locRow = 0;
    int locCol = 0;
    int locRowOf0 = 3;
    int locColOf0 = 3;
    int tempRow = 0;
    int tempCol = 0;
    float locXof0 = 0;
    float locYof0 = 0;
    boolean startGame = false;
    int counter = 0;
    String counterStr = "Number of tries: ";
    MediaPlayer clickSound;
    MediaPlayer applauseSound;

    public void Shuffle(View view) {
        findViewById(R.id.statusText).setAlpha(0);
        startGame = true;
        int random = (int)(Math.random()+10)*31;
        String randomSTR = Integer.toString(random);
        Log.i("ramdom: ", randomSTR);
//        Toast.makeText(this, random,Toast.LENGTH_LONG).show();
        for (int i = 0; i < random; i++) {
            click(findViewById(R.id.imageView0));
            click(findViewById(R.id.imageView1));
            click(findViewById(R.id.imageView2));
            click(findViewById(R.id.imageView3));
            click(findViewById(R.id.imageView4));
            click(findViewById(R.id.imageView5));
            click(findViewById(R.id.imageView6));
            click(findViewById(R.id.imageView7));
            click(findViewById(R.id.imageView8));
            click(findViewById(R.id.imageView9));
            click(findViewById(R.id.imageView10));
            click(findViewById(R.id.imageView11));
            click(findViewById(R.id.imageView12));
            click(findViewById(R.id.imageView13));
            click(findViewById(R.id.imageView14));
            click(findViewById(R.id.imageView15));
        }
        counter = 0;
        ((TextView) findViewById(R.id.score)).setText(counterStr + "0");

    }


    public void click(View view) {
        ((TextView) findViewById(R.id.score)).setText(counterStr + counter);
        locXof0 = findViewById(R.id.imageView0).getX();
        locYof0 = findViewById(R.id.imageView0).getY();
        String imgSelectedStr = view.getTag().toString(); // tell the method who called it
        int imgSelectedInt = Integer.parseInt(imgSelectedStr); // convert to int
        findInArr(imgSelectedInt); // file the location ov the number in arr

        // 1. if in the same col and 1 row apart   2. if in the same row and 1 col apart
        if (((locRow - locRowOf0 == 1 && locCol - locColOf0 == 0) || (locRow - locRowOf0 == -1 && locCol - locColOf0 == 0)
                || (locCol - locColOf0 == 1 && locRow - locRowOf0 == 0) || locCol - locColOf0 == -1 && locRow - locRowOf0 == 0)) {
            counter++;
            clickSound.start();
            //---------------- update arr --------------------------------------------
            tempRow = locRow;
            tempCol = locCol;
            locRow = locRowOf0;
            locCol = locColOf0;
            locRowOf0 = tempRow;
            locColOf0 = tempCol;
            gameState[locRowOf0][locColOf0] = 0; // update location of 0
            gameState[locRow][locCol] = imgSelectedInt; // update location of selected number
            //-----------------------------------------------------------------------
            updateLocation(view, (ImageView) findViewById(R.id.imageView0)); //update image location on screen
            if (checkIfWon() && startGame == true) {
                findViewById(R.id.statusText).setAlpha(1);
                applauseSound.start();
            }

        }


    }

    public boolean checkIfWon() {
        // i for row, j for col
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[0].length; j++) {
                if (gameState[i][j] != gameTaeget[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateLocation(View selected, ImageView image0) {
        int screenRightLoc0 = image0.getRight();
        int screenLeftLoc0 = image0.getLeft();
        int screenTopLoc0 = image0.getTop();
        int screenBottomLoc0 = image0.getBottom();
        int screenRightLocSelected = selected.getRight();
        int screenLeftLocSelected = selected.getLeft();
        int screenTopLocSelected = selected.getTop();
        int screenBottomLocSelected = selected.getBottom();

        selected.setRight(screenRightLoc0);
        selected.setLeft(screenLeftLoc0);
        selected.setTop(screenTopLoc0);
        selected.setBottom(screenBottomLoc0);
        image0.setRight(screenRightLocSelected);
        image0.setLeft(screenLeftLocSelected);
        image0.setTop(screenTopLocSelected);
        image0.setBottom(screenBottomLocSelected);
    }

    public void resetGame(View view) {
        this.onRestart();
    }

    public void findInArr(int number) {
        // i for row, j for col
        for (int i = 0; i < gameState.length; i++) {
            for (int j = 0; j < gameState[0].length; j++) {
                if (number == gameState[i][j]) {
                    locRow = i;
                    locCol = j;
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        clickSound = MediaPlayer.create(this, R.raw.click);
        applauseSound = MediaPlayer.create(this, R.raw.applause);

        // Load an ad into the AdMob banner view.
        AdView adView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        adView.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "In the future :)", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

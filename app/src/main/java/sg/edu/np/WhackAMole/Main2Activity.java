package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    CountDownTimer cdt;
    CountDownTimer icd;
    final int[] advancedCount = {0};
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */



    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        cdt = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.v(TAG, "Ready Countdown! " + millisUntilFinished/1000);
                String string = "Get Ready in " + millisUntilFinished/1000 + " seconds!";
                Toast.makeText(Main2Activity.this, string, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Log.v(TAG ,"Ready Countdown Complete!");
                Toast.makeText(Main2Activity.this, "GO!", Toast.LENGTH_SHORT).show();
                placeMoleTimer();
                cdt.cancel();
            }
        };
        cdt.start();
    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        icd = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                setNewMole();
                Log.v(TAG,"New Mole Location!");

            }

            @Override
            public void onFinish() {
                icd.start();
            }
        };
        icd.start();

    }
    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
            R.id.left1, R.id.center1, R.id.right1,
            R.id.left2, R.id.center2, R.id.right2,
            R.id.left3, R.id.center3, R.id.right3
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView advancedScore = (TextView)findViewById(R.id.advancedScore);
        Intent receivingEnd = getIntent();
        advancedCount[0] = receivingEnd.getIntExtra("Score", 0);
        Log.v(TAG, "Current User Score: " + advancedScore);
        setNewMole();
        readyTimer();

        for(final int id : BUTTON_IDS){
            /*  HINT:
            This creates a for loop to populate all 9 buttons with listeners.
            You may use if you wish to remove or change to suit your codes.
            */
            final Button b = (Button) findViewById(id);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCheck(b);
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
    }
    private void doCheck(Button checkButton)
    {
        TextView advancedScore = (TextView)findViewById(R.id.advancedScore);
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        if(checkButton.getText() == "*"){
            Log.v(TAG,"Hit, Score added!");
            ++advancedCount[0];
        }
        else{
            Log.v(TAG,"Missed, point deducted!");
            --advancedCount[0];
        }
        advancedScore.setText(String.valueOf(advancedCount[0]));
        setNewMole();
    }

    public void setNewMole()
    {
        /* Hint:
            Clears the previous mole location and gets a new random location of the next mole location.
            Sets the new location of the mole.
         */
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        for (int i: BUTTON_IDS){
            Button b = (Button) findViewById(i);
            b.setText("O");
        }
        Button mole = (Button) findViewById(BUTTON_IDS[randomLocation]);
        mole.setText("*");
    }
}


package sg.edu.np.WhackAMole;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.RestrictionEntry;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    final int[] count = {0};
    final int[] check = {0};

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function doCheck() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.v(TAG, "Finished Pre-Initialisation!");

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        TextView score = (TextView)findViewById(R.id.score);
        if(checkButton.getText().equals("*")){
            Log.v(TAG, "Hit, score added!");
            ++count[0];
            ++check[0];
        }
        else{
            Log.v(TAG, "Missed, point deducted!");
            --count[0];
        }
        score.setText(String.valueOf(count[0]));
        if (check[0]%10 == 0 && check[0]>0){
            nextLevelQuery();
        }
        setNewMole();
    }

    private void nextLevelQuery(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning! Insane Whack-a-Mole Incoming!")
                .setMessage("Would you like to continue?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(TAG, "User accepts!");
                        nextLevel();
                        Log.v(TAG, "Advance option given to user!");
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.v(TAG, "User decline!");
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent in = new Intent(MainActivity.this, Main2Activity.class);
        in.putExtra("Score", count[0]);
        startActivity(in);
    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        Button left = (Button)findViewById(R.id.left);
        Button center = (Button)findViewById(R.id.center);
        Button right = (Button)findViewById(R.id.right);

        if(randomLocation == 0){
            left.setText("*");
            center.setText("O");
            right.setText("O");

        }
        else if(randomLocation == 1){
            left.setText("O");
            center.setText("*");
            right.setText("O");
        }
        else{
            left.setText("O");
            center.setText("O");
            right.setText("*");
        }

    }

    public void click(View v) {
        Button b = (Button)v;
        switch(b.getId()){
            case R.id.left:
                Log.v(TAG, "Left Button Clicked!");
                doCheck(b);
                break;

            case R.id.center:
                Log.v(TAG, "Middle Button Clicked!");
                doCheck(b);
                break;

            case R.id.right:
                Log.v(TAG, "Right Button Clicked!");
                doCheck(b);
                break;
        }

    }
}
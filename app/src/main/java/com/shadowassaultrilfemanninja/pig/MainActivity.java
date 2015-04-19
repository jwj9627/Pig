package com.shadowassaultrilfemanninja.pig;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    private FrameLayout die1, die2;
    private Button roll, hold;
    private int score;
    private int roundScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = getIntent();

        score = intent.getIntExtra("p1score", 0);
        final int p2Score = intent.getIntExtra("p2score", 0);
        Toast.makeText(this, "The score is: " + p2Score, Toast.LENGTH_LONG).show();
        setP1Score(score);
        setP2Score(p2Score);

        //returns object must be cast to framelayout
        die1 = (FrameLayout) findViewById(R.id.die1);
        die2 = (FrameLayout) findViewById(R.id.die2);

        roll = (Button) findViewById(R.id.button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tempScore = rollFoo();
                if (tempScore == 1) {
                    Intent intent = new Intent(MainActivity.this, Player2.class);
                    intent.putExtra("p1score", score);
                    intent.putExtra("p2score", p2Score);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                } else {
                    roundScore += tempScore;
                    TextView tv = (TextView) findViewById(R.id.round);
                    tv.setText("Round " + roundScore);
                }
            }
        });

        hold = (Button) findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                score += roundScore;
                //setP1Score(score);

                if (score >= 100) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("You Won!");
                    alertDialog.setMessage("You are da man!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    score = 0;
                                    roundScore = 0;
                                    intent.putExtra("p1score", score);
                                    intent.putExtra("p2score", score);

                                    setP1Score(0);
                                    setP2Score(0);

                                    TextView tv = (TextView) findViewById(R.id.round);
                                    tv.setText("Round " + roundScore);
                                    startActivity(intent);
                                }
                            });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(MainActivity.this, Player2.class);
                    intent.putExtra("p1score", score);
                    intent.putExtra("p2score", p2Score);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }
            }
        });
    }

    public void setP1Score(int score) {
        TextView tv = (TextView) findViewById(R.id.p1);
        tv.setText("P1: " + score);
    }

    public void setP2Score(int score) {
        TextView tv = (TextView) findViewById(R.id.p2);
        tv.setText("P2: " + score);
    }

    public Intent p2Score() {
        return null;
    }

    //get two random numbers, change the dice to have the appropriate image
    public int rollFoo() {
        int roll1 = 1 + (int) (6 * Math.random());
        int roll2 = 1 + (int) (6 * Math.random());

        setDie(roll1, die1);
        setDie(roll2, die2);

        return (roll1 == 1 || roll2 == 1) ? 1 : roll1 + roll2;
    }

    //set appropriate image to FrameView for an int
    public void setDie(int value, FrameLayout die) {
        Drawable pic = null;

        switch (value) {
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                break;
        }
        die.setBackground(pic);
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
}

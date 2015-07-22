package com.namibsun.android.dice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;


public class RiskDice extends AppCompatActivity {

    private ImageView red1;
    private ImageView red2;
    private ImageView red3;
    private ImageView blue1;
    private ImageView blue2;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_risk_dice);
        this.red1 = (ImageView) findViewById(R.id.risk_r1);
        this.red2 = (ImageView) findViewById(R.id.risk_r2);
        this.red3 = (ImageView) findViewById(R.id.risk_r3);
        this.blue1 = (ImageView) findViewById(R.id.risk_b1);
        this.blue2 = (ImageView) findViewById(R.id.risk_b2);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_risk_dice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_one) {
            Intent menuIntent = new Intent(this, Main.class);
            startActivity(menuIntent);
            return true;
        } else if (id == R.id.action_two) {
            Intent menuIntent = new Intent(this, TwoDiceActivity.class);
            startActivity(menuIntent);
            return true;
        } else if (id == R.id.custom_action) {
            Intent menuIntent = new Intent(this, CustomDice.class);
            startActivity(menuIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void rollRedDice(View view) {
        this.vibrator.vibrate(2000);
        ArrayList<ImageView> dice = new ArrayList<ImageView>();
        dice.add(red1);
        dice.add(red2);
        dice.add(red3);
        new MultiDice(dice, DiceColor.REDWHITE).rollDice();
    }

    public void rollBlueDice(View view) {
        this.vibrator.vibrate(2000);
        ArrayList<ImageView> dice = new ArrayList<ImageView>();
        dice.add(blue1);
        dice.add(blue2);
        new MultiDice(dice, DiceColor.BLUEWHITE).rollDice();
    }
}

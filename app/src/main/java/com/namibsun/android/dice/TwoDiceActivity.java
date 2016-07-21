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

/**
 * Android Activity that enables the rolling of two dice
 * @author  Hermann Krumrey (hermann@krumreyh.com)
 */
public class TwoDiceActivity extends AppCompatActivity {

    private ImageView image1;
    private ImageView image2;
    private Vibrator vibrator;

    /**
     * Method run when created, gets objects in layout and saves them to local variables
     * @param savedInstanceState - android-default-thingamajig
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_dice);
        this.image1 = (ImageView) findViewById(R.id.dice_image);
        this.image2 = (ImageView) findViewById(R.id.dice2_image);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Method run when mnu is being created
     * @param menu - the menu being created
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_two_dice, menu);
        return true;
    }

    /**
     * Method run when an option on the menu is selected
     * @param item - the selected item
     * @return true, I guess?
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_one) {
            Intent menuIntent = new Intent(this, Main.class);
            startActivity(menuIntent);
            return true;
        } else if (id == R.id.risk_action) {
            Intent menuIntent = new Intent(this, RiskDice.class);
            startActivity(menuIntent);
            return true;
        } else if (id == R.id.custom_action) {
            Intent menuIntent = new Intent(this, CustomDice.class);
            startActivity(menuIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Rolls the dice
     * @param view - the active view
     */
    public void rollDice(View view) {
        this.vibrator.vibrate(2000);
        ArrayList<ImageView> dice = new ArrayList<ImageView>();
        dice.add(this.image1);
        dice.add(this.image2);
        new MultiDice(dice, DiceColor.WHITEBLACK).rollDice();
    }
}
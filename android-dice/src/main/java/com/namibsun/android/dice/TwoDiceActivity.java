package com.namibsun.android.dice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class TwoDiceActivity extends Activity {

    private ImageView image1;
    private ImageView image2;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_dice);
        this.image1 = (ImageView) findViewById(R.id.dice_image);
        this.image2 = (ImageView) findViewById(R.id.dice2_image);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_two_dice, menu);
        return true;
    }

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
        }
        return super.onOptionsItemSelected(item);
    }

    public void rollDice(View view) {
        this.vibrator.vibrate(2000);
        new TwoDice(this.image1, this.image2).rollDice();
    }
}

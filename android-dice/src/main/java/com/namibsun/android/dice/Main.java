package com.namibsun.android.dice;

import android.content.Context;
import android.opengl.Matrix;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Main Activity class of the android app
 * @author  Hermann Krumrey (hermann@krumreyh.com)
 */
public class Main extends ActionBarActivity {

    private ImageView image;
    private Vibrator vibrator;
    private int animationCount = 0;

    /**
     * Method run when created
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.image = (ImageView) findViewById(R.id.dice_image);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }


    /**
     * Method run when created
     * @param menu -
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Method run when created
     * @param item -
     * @return -
     */
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

    public void rollDice(View view) {
        this.image.setEnabled(false);
        this.vibrator.vibrate(2000);

        new CountDownTimer(2000, 5) {
            public void onTick(long millisUntilFinished) {
                rotateImage(Main.this.image);
                if (Main.this.animationCount == 10) {
                    Main.this.image.setImageResource(getRandomImage());
                    Main.this.animationCount = 0;
                } else {
                    Main.this.animationCount++;
                }
            }

            public void onFinish() {
                straightenImage(Main.this.image);
                Main.this.image.setEnabled(true);
            }
        }.start();
    }

    /**
     * Method to get a random dice image
     * @return
     */
    private int getRandomImage() {
        int random = (int) (Math.random() * 6 + 1);
        System.out.print(random);
        switch(random) {
            case 1: return R.drawable.diceone500;
            case 2: return R.drawable.dicetwo500;
            case 3: return R.drawable.dicethree500;
            case 4: return R.drawable.dicefour500;
            case 5: return R.drawable.dicefive500;
            case 6: return R.drawable.dicesix500;
            default: return R.drawable.diceone500;
        }
    }

    private int getSemiRandomImage(int last) {
        int random = getRandomImage();
        while(random == last) {
            random = getRandomImage();
        }
        return random;
    }

    private void rotateImage(ImageView image) {

        float rotation = image.getRotation();
        if (rotation == 0.0f) {
            image.setRotation(1.1f);
        } else if (rotation > 0.0f && rotation < 10.0f) {
            if ((rotation - (int)rotation) < 0.5f) {
                image.setRotation(rotation + 1.0f);
            } else {
                image.setRotation(rotation - 1.0f);
            }
        } else if (rotation >= 10.0f) {
            image.setRotation(9.9f);
        } else if (rotation < 0.0f && rotation > -10.0f) {
            if (((-1.0f * rotation) + (int)rotation) < 0.5f) {
                image.setRotation(rotation - 1.0f);
            } else {
                image.setRotation(rotation + 1.0f);
            }
        } else if (rotation <= -10.0f) {
            image.setRotation(-9.9f);
        }

    }

    private void straightenImage(ImageView image) {
        image.setRotation(0);
    }
}

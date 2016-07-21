package com.namibsun.android.dice;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.TypedValue;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import org.w3c.dom.Text;


public class CustomDice extends AppCompatActivity {

    private EditText seed;
    private TextView result;
    private Vibrator vibrator;
    private Display getOrient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dice);
        this.seed = (EditText) findViewById(R.id.randomDiceSeed);
        this.result = (TextView) findViewById(R.id.custom_result);
        this.vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        this.getOrient = getWindowManager().getDefaultDisplay();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_dice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_two) {
            Intent menuIntent = new Intent(this, TwoDiceActivity.class);
            startActivity(menuIntent);
            return true;
        } else if (id == R.id.risk_action) {
            Intent menuIntent = new Intent(this, RiskDice.class);
            startActivity(menuIntent);
            return true;
        } else if (id == R.id.action_one) {
            Intent menuIntent = new Intent(this, Main.class);
            startActivity(menuIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void rollDice(View view) {
        final int seedNo;
        try {
            seedNo = Integer.parseInt(this.seed.getText().toString());
        } catch (NumberFormatException e) {
            return;
        }
        this.vibrator.vibrate(2000);
        result.setEnabled(false);

        new CountDownTimer(2000, 10) {

            int timer = 0;
            int key = 0;

            public void onTick(long millisUntilFinished) {
                wiggleNumber(result);

                if (timer == 5) {
                    changeNumber(result, seedNo);
                    timer = 0;
                } else {
                    timer++;
                }
            }

            public void onFinish() {
                result.setRotation(0.0f);
                changeNumber(result, seedNo);
                result.setEnabled(true);
            }
        }.start();



        int random = (int) (Math.random() * seedNo + 1);
        this.result.setText(random + "");
    }

    private void wiggleNumber(TextView text) {
        float rotation = text.getRotation();
        if (rotation == 0.0f) {
            text.setRotation(1.000001f);
        } else if (rotation > 0.0f && rotation < 11.0f) {
            if ((rotation - Math.floor(rotation)) < 0.5f) {
                text.setRotation(rotation + 2.0f);
            } else {
                text.setRotation(rotation - 2.0f);
            }
        } else if (rotation >= 10.0f) {
            text.setRotation(9.999999f);
        } else if (rotation < 0.0f && rotation > -11.0f) {
            if (((-1.0f * rotation) + Math.ceil(rotation)) < 0.5f) {
                text.setRotation(rotation - 2.0f);
            } else {
                text.setRotation(rotation + 2.0f);
            }
        } else if (rotation <= -10.0f) {
            text.setRotation(-8.999999f);
        }
    }

    private void changeNumber(TextView text, int seedNo) {
        int random = (int) (Math.random() * seedNo + 1);
        float fontSize;
        if (getOrient.getRotation() == Surface.ROTATION_0 ) {
            if (random < 100) {
                fontSize = 250.0f;
                text.setPadding(0, 0, 0, 0);
            } else if (random >= 100 && random < 1000) {
                fontSize = 150.0f;
                text.setPadding(0, 100, 0, 0);
            } else if (random >= 1000 && random < 10000) {
                fontSize = 100.0f;
                text.setPadding(0, 150, 0, 0);
            } else if (random >= 10000 && random < 100000) {
                fontSize = 50.0f;
                text.setPadding(0, 200, 0, 0);
            } else {
                fontSize = 30.0f;
                text.setPadding(0, 220, 0, 0);
            }
        } else {
            if (random < 10000000) {
                fontSize = 150.0f;
                text.setPadding(0, 0, 0, 0);
            } else {
                fontSize = 100.0f;
                text.setPadding(0, 50, 0, 0);
            }
        }
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        text.setText("" + random);
    }
}

package com.namibsun.android.dice;

import android.media.Image;
import android.os.CountDownTimer;
import android.widget.ImageView;

/**
 * Created by hermann on 7/21/15.
 */
public class SingleDice {

    private ImageView dice;

    public SingleDice(ImageView dice) {
        this.dice = dice;
    }

    public void rollDice() {

        this.dice.setEnabled(false);

        new CountDownTimer(2000, 10) {

            int timer = 0;
            int key = 0;

            public void onTick(long millisUntilFinished) {
                wiggleDice();
                if (timer == 5) {
                    changeDice(getPseudoRandom(key));
                    if (key >= 20) {
                        key = 0;
                    } else {
                        key++;
                    }
                    timer = 0;
                } else {
                    timer++;
                }
            }

            public void onFinish() {
                straightenDice();
                SingleDice.this.dice.setImageResource(getRandom());
                SingleDice.this.dice.setEnabled(true);
            }
        }.start();

    }

    private int getRandom() {
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

    private int getPseudoRandom(int key) {
        switch (key) {
            case 1: return R.drawable.diceone500;
            case 2: return R.drawable.dicethree500;
            case 3: return R.drawable.dicetwo500;
            case 4: return R.drawable.dicesix500;
            case 5: return R.drawable.dicefour500;
            case 6: return R.drawable.dicefive500;
            case 7: return R.drawable.dicesix500;
            case 8: return R.drawable.dicethree500;
            case 9: return R.drawable.diceone500;
            case 10: return R.drawable.dicethree500;
            case 11: return R.drawable.dicetwo500;
            case 12: return R.drawable.dicefour500;
            case 13: return R.drawable.dicefive500;
            case 14: return R.drawable.diceone500;
            case 15: return R.drawable.dicetwo500;
            case 16: return R.drawable.dicethree500;
            case 17: return R.drawable.dicesix500;
            case 18: return R.drawable.dicefour500;
            case 19: return R.drawable.dicefive500;
            case 20: return R.drawable.diceone500;
            default: return R.drawable.dicethree500;
        }
    }

    private void wiggleDice() {
        float rotation = dice.getRotation();
        if (rotation == 0.0f) {
            dice.setRotation(1.000001f);
        } else if (rotation > 0.0f && rotation < 11.0f) {
            if ((rotation - Math.floor(rotation)) < 0.5f) {
                dice.setRotation(rotation + 2.0f);
            } else {
                dice.setRotation(rotation - 2.0f);
            }
        } else if (rotation >= 10.0f) {
            dice.setRotation(9.999999f);
        } else if (rotation < 0.0f && rotation > -11.0f) {
            if (((-1.0f * rotation) + Math.ceil(rotation)) < 0.5f) {
                dice.setRotation(rotation - 2.0f);
            } else {
                dice.setRotation(rotation + 2.0f);
            }
        } else if (rotation <= -10.0f) {
            dice.setRotation(-8.999999f);
        }
    }

    private void straightenDice() {
        this.dice.setRotation(0);
    }

    private void changeDice(int id) {
        this.dice.setImageResource(id);
    }

}

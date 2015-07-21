package com.namibsun.android.dice;

import android.os.CountDownTimer;
import android.widget.ImageView;

/**
 * Created by hermann on 7/21/15.
 */
public class TwoDice {

    private ImageView dice1;
    private ImageView dice2;

    public TwoDice(ImageView dice1, ImageView dice2) {
        this.dice1 = dice1;
        this.dice2 = dice2;
    }

    public void rollDice() {

        this.dice1.setEnabled(false);
        this.dice2.setEnabled(false);

        final int dice2Result = getRandom();

        new CountDownTimer(2000, 10) {

            int timer = 0;
            int key = 0;

            public void onTick(long millisUntilFinished) {
                wiggleDice();
                if (timer == 5) {
                    changeDice(getPseudoRandom(key, 0), dice1);
                    changeDice(getPseudoRandom(key, 100), dice2);
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
                straightenDice(dice1);
                straightenDice(dice2);
                dice1.setImageResource(getRandom());
                dice2.setImageResource(dice2Result);
                dice1.setEnabled(true);
                dice2.setEnabled(true);
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

    private int getPseudoRandom(int key, int diceKey) {
        switch (key + diceKey) {
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
            case 101: return R.drawable.dicesix500;
            case 102: return R.drawable.dicefour500;
            case 103: return R.drawable.dicefive500;
            case 104: return R.drawable.diceone500;
            case 105: return R.drawable.dicetwo500;
            case 106: return R.drawable.dicethree500;
            case 107: return R.drawable.diceone500;
            case 108: return R.drawable.dicefive500;
            case 109: return R.drawable.dicesix500;
            case 110: return R.drawable.dicetwo500;
            case 111: return R.drawable.dicethree500;
            case 112: return R.drawable.dicesix500;
            case 113: return R.drawable.diceone500;
            case 114: return R.drawable.dicefive500;
            case 115: return R.drawable.dicetwo500;
            case 116: return R.drawable.dicefour500;
            case 117: return R.drawable.diceone500;
            case 118: return R.drawable.dicefour500;
            case 119: return R.drawable.dicetwo500;
            case 120: return R.drawable.dicefive500;
            default: return R.drawable.dicethree500;
        }
    }

    private void wiggleDice() {
            float rotation = dice1.getRotation();
            if (rotation == 0.0f) {
                dice1.setRotation(1.000001f);
                dice2.setRotation(-1.000001f);
            } else if (rotation > 0.0f && rotation < 11.0f) {
                if ((rotation - Math.floor(rotation)) < 0.5f) {
                    dice1.setRotation(rotation + 2.0f);
                    dice2.setRotation((-1.0f * rotation) - 2.0f);
                } else {
                    dice1.setRotation(rotation - 2.0f);
                    dice2.setRotation((-1.0f * rotation) + 2.0f);
                }
            } else if (rotation >= 10.0f) {
                dice1.setRotation(9.999999f);
                dice2.setRotation(-9.999999f);
            } else if (rotation < 0.0f && rotation > -11.0f) {
                if (((-1.0f * rotation) + Math.ceil(rotation)) < 0.5f) {
                    dice1.setRotation(rotation - 2.0f);
                    dice2.setRotation((-1.0f * rotation) + 2.0f);
                } else {
                    dice1.setRotation(rotation + 2.0f);
                    dice2.setRotation((-1.0f * rotation) - 2.0f);
                }
            } else if (rotation <= -11.0f) {
                dice1.setRotation(-9.999999f);
                dice2.setRotation(9.999999f);
            }
    }

    private void straightenDice(ImageView dice) {
        dice.setRotation(0);
    }

    private void changeDice(int id, ImageView dice) {
        dice.setImageResource(id);
    }

}

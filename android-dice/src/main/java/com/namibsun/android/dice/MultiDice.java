package com.namibsun.android.dice;

import android.os.CountDownTimer;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Class that is used to simulate an indefinite amount of dice.
 * @author Hermann Krumrey (hermann@krumreyh.com)
 */
public class MultiDice {

    private ArrayList<ImageView> dice;

    /**
     * Constructor that loads an Arraylist of dice elements to the class's private variables
     * @param dice - the dice to be loaded
     */
    public MultiDice(ArrayList<ImageView> dice) {
        this.dice = dice;
    }

    /**
     * Rolls all dice for 2 seconds, while wiggling them
     */
    public void rollDice() {

        for (int i = 0; i < dice.size(); i++) {
            final ImageView die = dice.get(i);
            final int current = i;
            die.setEnabled(false);

            new CountDownTimer(2000, 10) {

                int timer = 0;
                int key = 0;
                int randomTimer = 0;
                int randomNumber = 0;

                public void onTick(long millisUntilFinished) {
                    wiggleDice(die, current);
                    if (randomTimer == current) {
                        randomNumber = getRandom();
                    } else {
                        randomTimer++;
                    }
                    if (timer == 5) {
                        die.setImageResource(getPseudoRandom(key, current));
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
                    die.setRotation(0.0f);
                    die.setImageResource(getRandom());
                    die.setEnabled(true);
                }
            }.start();
        }
    }

    /**
     * Gets a pseudo-random dice image
     * @param key - key used in calculation
     * @param counter - counter used in calculatio
     * @return the id of a pseudo random die
     */
    private int getPseudoRandom(int key, int counter) {
        switch ((((key + counter) * 2) % 6) + 1) {
            case 1: return R.drawable.diceone500;
            case 2: return R.drawable.dicetwo500;
            case 3: return R.drawable.dicethree500;
            case 4: return R.drawable.dicefour500;
            case 5: return R.drawable.dicefive500;
            case 6: return R.drawable.dicesix500;
            default: return R.drawable.dicesix500;
        }
    }

    /**
     * Gets a random dice image
     * @return a random dice image's id
     */
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

    /**
     * Wiggles a Dice
     * @param dice - the dice to be wiggled
     * @param position - the dice's position in the array list
     */
    private void wiggleDice(ImageView dice, int position) {
        float rotation = dice.getRotation();
        if (position % 2 == 0) { //even => Starts to the right
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
        } else {
            if (rotation == 0.0f) {
                dice.setRotation(-1.000001f);
            } else if (rotation < 0.0f && rotation > -11.0f) {
                if (((-1.0f * rotation) + Math.ceil(rotation)) < 0.5f) {
                    dice.setRotation(rotation - 2.0f);
                } else {
                    dice.setRotation(rotation + 2.0f);
                }
            } else if (rotation <= -11.0f) {
                dice.setRotation(-9.999999f);
            } else if (rotation > 0.0f && rotation < 10.0f) {
                if ((rotation - Math.floor(rotation)) < 0.5f) {
                    dice.setRotation(rotation + 2.0f);
                } else {
                    dice.setRotation(rotation - 2.0f);
                }
            } else if (rotation >= 10.0f) {
                dice.setRotation(8.999999f);
            }
        }
    }
}

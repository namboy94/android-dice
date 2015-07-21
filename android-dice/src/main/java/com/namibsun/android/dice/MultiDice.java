package com.namibsun.android.dice;

import android.os.CountDownTimer;
import android.widget.ImageView;

import java.util.ArrayList;

import static com.namibsun.android.dice.DiceColor.WHITEBLACK;

/**
 * Class that is used to simulate an indefinite amount of dice.
 * @author Hermann Krumrey (hermann@krumreyh.com)
 */
public class MultiDice {

    private ArrayList<ImageView> dice;
    private DiceColor color;


    /**
     * Constructor that loads an Arraylist of dice elements to the class's private variables
     * @param dice - the dice to be loaded
     */
    public MultiDice(ArrayList<ImageView> dice, DiceColor color) {
        this.dice = dice;
        this.color = color;
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
        switch (this.color) {
            case WHITEBLACK:    switch ((((key + counter) * 2) % 6) + 1) {
                                case 1: return R.drawable.dice1;
                                case 2: return R.drawable.dice2;
                                case 3: return R.drawable.dice3;
                                case 4: return R.drawable.dice4;
                                case 5: return R.drawable.dice5;
                                case 6: return R.drawable.dice6;
                                }
            case REDWHITE:      switch ((((key + counter) * 2) % 6) + 1) {
                                case 1: return R.drawable.dice1red;
                                case 2: return R.drawable.dice2red;
                                case 3: return R.drawable.dice3red;
                                case 4: return R.drawable.dice4red;
                                case 5: return R.drawable.dice5red;
                                case 6: return R.drawable.dice6red;
                                }
            case BLUEWHITE:     switch ((((key + counter) * 2) % 6) + 1) {
                                case 1: return R.drawable.dice1blue;
                                case 2: return R.drawable.dice2blue;
                                case 3: return R.drawable.dice3blue;
                                case 4: return R.drawable.dice4blue;
                                case 5: return R.drawable.dice5blue;
                                case 6: return R.drawable.dice6blue;
                                }
        }
        return 0;
    }

    /**
     * Gets a random dice image
     * @return a random dice image's id
     */
    private int getRandom() {
        int random = (int) (Math.random() * 6 + 1);
        switch (this.color) {
            case WHITEBLACK:    switch (random) {
                case 1: return R.drawable.dice1;
                case 2: return R.drawable.dice2;
                case 3: return R.drawable.dice3;
                case 4: return R.drawable.dice4;
                case 5: return R.drawable.dice5;
                case 6: return R.drawable.dice6;
            }
            case REDWHITE:      switch (random) {
                case 1: return R.drawable.dice1red;
                case 2: return R.drawable.dice2red;
                case 3: return R.drawable.dice3red;
                case 4: return R.drawable.dice4red;
                case 5: return R.drawable.dice5red;
                case 6: return R.drawable.dice6red;
            }
            case BLUEWHITE:     switch (random) {
                case 1: return R.drawable.dice1blue;
                case 2: return R.drawable.dice2blue;
                case 3: return R.drawable.dice3blue;
                case 4: return R.drawable.dice4blue;
                case 5: return R.drawable.dice5blue;
                case 6: return R.drawable.dice6blue;
            }
        }
        return 0;
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

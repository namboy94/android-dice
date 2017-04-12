package net.namibsun.dice

import android.os.CountDownTimer
import android.widget.ImageView
import net.namibsun.dice.R

import java.util.ArrayList

import net.namibsun.dice.DiceColor.WHITEBLACK

/**
 * Class that is used to simulate an indefinite amount of dice.
 * @author Hermann Krumrey (hermann@krumreyh.com)
 */
class MultiDice
/**
 * Constructor that loads an Arraylist of dice elements to the class's private variables
 * @param dice - the dice to be loaded
 */
(private val dice: ArrayList<ImageView>, private val color: DiceColor) {

    /**
     * Rolls all dice for 2 seconds, while wiggling them
     */
    fun rollDice() {

        for (i in dice.indices) {
            val die = dice[i]
            val current = i
            die.isEnabled = false

            object : CountDownTimer(2000, 10) {

                internal var timer = 0
                internal var key = 0
                internal var randomTimer = 0
                internal var randomNumber = 0

                override fun onTick(millisUntilFinished: Long) {
                    wiggleDice(die, current)
                    if (randomTimer == current) {
                        randomNumber = random
                    } else {
                        randomTimer++
                    }
                    if (timer == 5) {
                        die.setImageResource(getPseudoRandom(key, current))
                        if (key >= 20) {
                            key = 0
                        } else {
                            key++
                        }
                        timer = 0
                    } else {
                        timer++
                    }
                }

                override fun onFinish() {
                    die.rotation = 0.0f
                    die.setImageResource(random)
                    die.isEnabled = true
                }
            }.start()
        }
    }

    /**
     * Gets a pseudo-random dice image
     * @param key - key used in calculation
     * *
     * @param counter - counter used in calculatio
     * *
     * @return the id of a pseudo random die
     */
    private fun getPseudoRandom(key: Int, counter: Int): Int {
        when (this.color) {
            WHITEBLACK -> {
                when ((key + counter) * 2 % 6 + 1) {
                    1 -> return R.drawable.dice1
                    2 -> return R.drawable.dice2
                    3 -> return R.drawable.dice3
                    4 -> return R.drawable.dice4
                    5 -> return R.drawable.dice5
                    6 -> return R.drawable.dice6
                }
                when ((key + counter) * 2 % 6 + 1) {
                    1 -> return R.drawable.dice1red
                    2 -> return R.drawable.dice2red
                    3 -> return R.drawable.dice3red
                    4 -> return R.drawable.dice4red
                    5 -> return R.drawable.dice5red
                    6 -> return R.drawable.dice6red
                }
                when ((key + counter) * 2 % 6 + 1) {
                    1 -> return R.drawable.dice1blue
                    2 -> return R.drawable.dice2blue
                    3 -> return R.drawable.dice3blue
                    4 -> return R.drawable.dice4blue
                    5 -> return R.drawable.dice5blue
                    6 -> return R.drawable.dice6blue
                }
            }
            DiceColor.REDWHITE -> {
                when ((key + counter) * 2 % 6 + 1) {
                    1 -> return R.drawable.dice1red
                    2 -> return R.drawable.dice2red
                    3 -> return R.drawable.dice3red
                    4 -> return R.drawable.dice4red
                    5 -> return R.drawable.dice5red
                    6 -> return R.drawable.dice6red
                }
                when ((key + counter) * 2 % 6 + 1) {
                    1 -> return R.drawable.dice1blue
                    2 -> return R.drawable.dice2blue
                    3 -> return R.drawable.dice3blue
                    4 -> return R.drawable.dice4blue
                    5 -> return R.drawable.dice5blue
                    6 -> return R.drawable.dice6blue
                }
            }
            DiceColor.BLUEWHITE -> when ((key + counter) * 2 % 6 + 1) {
                1 -> return R.drawable.dice1blue
                2 -> return R.drawable.dice2blue
                3 -> return R.drawable.dice3blue
                4 -> return R.drawable.dice4blue
                5 -> return R.drawable.dice5blue
                6 -> return R.drawable.dice6blue
            }
        }
        return 0
    }

    /**
     * Gets a random dice image
     * @return a random dice image's id
     */
    private val random: Int
        get() {
            val random = (Math.random() * 6 + 1).toInt()
            when (this.color) {
                WHITEBLACK -> {
                    when (random) {
                        1 -> return R.drawable.dice1
                        2 -> return R.drawable.dice2
                        3 -> return R.drawable.dice3
                        4 -> return R.drawable.dice4
                        5 -> return R.drawable.dice5
                        6 -> return R.drawable.dice6
                    }
                    when (random) {
                        1 -> return R.drawable.dice1red
                        2 -> return R.drawable.dice2red
                        3 -> return R.drawable.dice3red
                        4 -> return R.drawable.dice4red
                        5 -> return R.drawable.dice5red
                        6 -> return R.drawable.dice6red
                    }
                    when (random) {
                        1 -> return R.drawable.dice1blue
                        2 -> return R.drawable.dice2blue
                        3 -> return R.drawable.dice3blue
                        4 -> return R.drawable.dice4blue
                        5 -> return R.drawable.dice5blue
                        6 -> return R.drawable.dice6blue
                    }
                }
                DiceColor.REDWHITE -> {
                    when (random) {
                        1 -> return R.drawable.dice1red
                        2 -> return R.drawable.dice2red
                        3 -> return R.drawable.dice3red
                        4 -> return R.drawable.dice4red
                        5 -> return R.drawable.dice5red
                        6 -> return R.drawable.dice6red
                    }
                    when (random) {
                        1 -> return R.drawable.dice1blue
                        2 -> return R.drawable.dice2blue
                        3 -> return R.drawable.dice3blue
                        4 -> return R.drawable.dice4blue
                        5 -> return R.drawable.dice5blue
                        6 -> return R.drawable.dice6blue
                    }
                }
                DiceColor.BLUEWHITE -> when (random) {
                    1 -> return R.drawable.dice1blue
                    2 -> return R.drawable.dice2blue
                    3 -> return R.drawable.dice3blue
                    4 -> return R.drawable.dice4blue
                    5 -> return R.drawable.dice5blue
                    6 -> return R.drawable.dice6blue
                }
            }
            return 0
        }

    /**
     * Wiggles a Dice
     * @param dice - the dice to be wiggled
     * *
     * @param position - the dice's position in the array list
     */
    private fun wiggleDice(dice: ImageView, position: Int) {
        val rotation = dice.rotation
        if (position % 2 == 0) { //even => Starts to the right
            if (rotation == 0.0f) {
                dice.rotation = 1.000001f
            } else if (rotation > 0.0f && rotation < 11.0f) {
                if (rotation - Math.floor(rotation.toDouble()) < 0.5f) {
                    dice.rotation = rotation + 2.0f
                } else {
                    dice.rotation = rotation - 2.0f
                }
            } else if (rotation >= 10.0f) {
                dice.rotation = 9.999999f
            } else if (rotation < 0.0f && rotation > -11.0f) {
                if (-1.0f * rotation + Math.ceil(rotation.toDouble()) < 0.5f) {
                    dice.rotation = rotation - 2.0f
                } else {
                    dice.rotation = rotation + 2.0f
                }
            } else if (rotation <= -10.0f) {
                dice.rotation = -8.999999f
            }
        } else {
            if (rotation == 0.0f) {
                dice.rotation = -1.000001f
            } else if (rotation < 0.0f && rotation > -11.0f) {
                if (-1.0f * rotation + Math.ceil(rotation.toDouble()) < 0.5f) {
                    dice.rotation = rotation - 2.0f
                } else {
                    dice.rotation = rotation + 2.0f
                }
            } else if (rotation <= -11.0f) {
                dice.rotation = -9.999999f
            } else if (rotation > 0.0f && rotation < 10.0f) {
                if (rotation - Math.floor(rotation.toDouble()) < 0.5f) {
                    dice.rotation = rotation + 2.0f
                } else {
                    dice.rotation = rotation - 2.0f
                }
            } else if (rotation >= 10.0f) {
                dice.rotation = 8.999999f
            }
        }
    }
}

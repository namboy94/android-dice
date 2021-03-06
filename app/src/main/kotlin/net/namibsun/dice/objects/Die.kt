/*
Copyright 2015 Hermann Krumrey

This file is part of dice-roller.

dice-roller is an Android app that allows a user to roll a virtual
die. Multiple configurations are supported

dice-roller is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

dice-roller is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with dice-roller. If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.dice.objects

import android.content.Context
import android.os.Vibrator
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import net.namibsun.dice.R
import net.namibsun.dice.activities.BaseActivity
import java.security.SecureRandom
import kotlin.concurrent.thread

/**
 * Abstract class that defines common Dice behaviour for different dice types
 * @param context: The Activity using the Die
 * @param view: The View displaying the die
 * @param theme: The theme that defines the look of the View
 * @param storedValueKey: The key string used to store the current value in the shared preferences
 * @param limit: The maximum value of the Die
 * @param minimum: The minimum value of the Die
 * @param initialValue: The initial value of the Die
 * @param wiggleAnimationResource: Overrides the default wiggle animation if set
 */
abstract class Die(
    protected val context: BaseActivity,
    val view: View,
    protected var theme: Theme,
    protected val storedValueKey: String,
    protected var limit: Int = 6,
    protected var minimum: Int = 1,
    initialValue: Int = 5,
    protected val wiggleAnimationResource: Int = R.anim.wiggle
) {

    /**
     * Draws the Current Information as the View
     */
    abstract fun draw()

    /**
     * The current value of the die
     */
    protected var currentValue: Int = initialValue

    /**
     * The vibrator is used to vibrate the device while the animation is running,
     * if the theme allows for this.
     */
    private val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    /**
     * An object that can generate random numbers
     */
    protected val random = SecureRandom()

    /**
     * Indicates if the change animation is currently active
     */
    private var changeAnimating = false

    /**
     * Indicates if the wiggle animation is currently active
     */
    private var wiggleAnimating = false

    /**
     * Sets the OnClickListener for the image view
     */
    init {
        this.view.setOnClickListener { this.roll() }
        this.currentValue = this.context.prefs!!.getInt(this.storedValueKey, initialValue)
        this.draw()
    }

    /**
    * Displays the next value in the view
     * @param specificNumber: Instead of a random number, display a specific number
    */
    open fun next(specificNumber: Int? = null) {
        this.currentValue = specificNumber ?: this.nextRandomNumber()
        this.context.prefs!!.edit().putInt(this.storedValueKey, this.currentValue).apply()
        this.draw()
    }

    /**
     * Updates the theme of the Die
     * @param theme: The theme to change to
     */
    fun updateTheme(theme: Theme) {
        this.theme = theme
        this.draw()
    }

    /**
     * Rolls the Die according to the settings provided by the theme
     */
    fun roll() {

        if (this.theme.vibrate && !this.theme.wiggleAnimation && !this.theme.changeAnimation) {
            this.vibrator.vibrate(100)
            this.next()
        } else if (this.theme.wiggleAnimation) {
            this.animate(AnimationUtils.loadAnimation(this.context, this.wiggleAnimationResource))
        } else if (this.theme.changeAnimation) {
            this.changeAnimation(1000)
        } else {
            this.next()
        }
    }

    /**
     * @return The current value of the die
     */
    fun getValue(): Int {
        return this.currentValue
    }

    /**
     * @return true if the die is currently being animated, false otherwise
     */
    fun isAnimating(): Boolean {
        return this.changeAnimating || this.wiggleAnimating
    }

    /**
     * Generates a new random number within the limits
     * @return The generated random number
     */
    protected fun nextRandomNumber(): Int {
        return this.random.nextInt(this.limit - this.minimum + 1) + this.minimum
    }

    /**
     * Vibrates the device for a set amount of time, but only if vibrating is enabled
     * in the settings.
     * @param duration: The duration of the vibration
     */
    private fun vibrate(duration: Long) {
        if (this.theme.vibrate) {
            this.vibrator.vibrate(duration)
        }
    }

    /**
     * Starts animating the die
     * @param animation: The animation to use
     */
    private fun animate(animation: Animation) {

        if (this.theme.vibrate) {
            this.vibrate(animation.duration)
        }

        if (this.theme.changeAnimation) {
            this.changeAnimation(animation.duration * 10)
        }

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) { }
            override fun onAnimationStart(animation: Animation?) { }
            override fun onAnimationEnd(animation: Animation?) {
                this@Die.next()
                this@Die.wiggleAnimating = false
            }
        })

        this.vibrate(animation.duration * 10)
        this.wiggleAnimating = true
        this.view.startAnimation(animation)
    }

    /**
     * Changes the currently displayed value of the Die for a specified period of time
     */
    private fun changeAnimation(duration: Long, pause: Int = 100) {

        this.changeAnimating = true
        if (this.theme.vibrate) {
            this.vibrate(duration)
        }

        thread(start = true) {
            var runtime = 0
            while (runtime < duration) {
                this.context.runOnUiThread { this.next() }
                Thread.sleep(pause.toLong())
                runtime += pause
            }
            this.changeAnimating = false
        }
    }
}
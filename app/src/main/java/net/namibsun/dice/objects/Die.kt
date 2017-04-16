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
 * @param limit: The maximum value of the Die
 * @param minimum: The minimum value of the Die
 * @param initialValue: The initial value of the Die
 * @param wiggleAnimationResource: Overrides the default wiggle animation if set
 */
abstract class Die(protected val context: BaseActivity,
                   val view: View,
                   protected var theme: Theme,
                   protected var limit: Int = 6,
                   protected var minimum: Int = 1,
                   initialValue: Int = 5,
                   protected val wiggleAnimationResource: Int = R.anim.wiggle) {

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
    protected val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    /**
     * An object that can generate random numbers
     */
    protected val random = SecureRandom()

    /**
     * Sets the OnClickListener for the image view
     */
    init {
        this.view.setOnClickListener { this.roll() }
        this.draw()
    }

    /**
    * Displays the next value in the view
    */
    fun next() {
        this.currentValue = this.next_random_number()
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
     * Vibrates the device for a set amount of time, but only if vibrating is enabled
     * in the settings.
     * @param duration: The duration of the vibration
     */
    fun vibrate(duration: Long) {
        if (this.theme.vibrate) {
            this.vibrator.vibrate(duration)
        }
    }

    /**
     * Generates a new random number within the limits
     * @return The generated random number
     */
    fun next_random_number() : Int {
        return this.random.nextInt(this.limit - this.minimum + 1) + this.minimum
    }

    /**
     * Rolls the Die according to the settings provided by the theme
     */
    fun roll() {

        if (this.theme.vibrate && !this.theme.wiggleAnimation && !this.theme.changeAnimation) {
            this.vibrator.vibrate(100)
            this.next()
        }
        else if (this.theme.wiggleAnimation) {
            this.animate(AnimationUtils.loadAnimation(this.context, this.wiggleAnimationResource))
        }
        else if (this.theme.changeAnimation) {
            this.changeAnimation(1000)
        }
        else {
            this.next()
        }
    }

    /**
     * Starts animating the die
     * @param animation: The animation to use
     */
    fun animate(animation: Animation) {

        if (this.theme.vibrate) {
            this.vibrate(animation.duration)
        }

        if (this.theme.changeAnimation) {
            this.changeAnimation(animation.duration * 10)
        }
        else {
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) { }
                override fun onAnimationStart(animation: Animation?) { }
                override fun onAnimationEnd(animation: Animation?) { this@Die.next() }
            })
        }

        this.vibrate(animation.duration * 10)
        this.view.startAnimation(animation)
    }

    /**
     * Changes the currently displayed value of the Die for a specified period of time
     */
    fun changeAnimation(duration: Long, pause: Int = 100) {

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
        }
    }
}
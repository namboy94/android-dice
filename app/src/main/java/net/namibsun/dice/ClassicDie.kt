package net.namibsun.dice

import android.content.Context
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.security.SecureRandom
import kotlin.concurrent.thread

/**
 * This is a class that simulates a classic 6-sided die.
 * @param context: The context/Activity for this die.
 * @param view: The view that represents this die
 * @param theme: The theme to be applied to the die
 * @param current: Optional parameter that can specify which image of the theme's
 *                 permutations should be the default image. By default this is the 5th element.
 */
class ClassicDie(private val context: AppCompatActivity,
                 val view: ImageView, private val theme: Theme, private var current: Int = 4) {

    /**
     * The vibrator is used to vibrate the device while the animation is running,
     * if the theme allows for this.
     */
    val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    /**
     * An object that can generate random numbers
     */
    val random = SecureRandom()

    /**
     * Sets the OnClickListener for the image view
     */
    init {
        this.view.setImageResource(this.theme.permutations[this.current])
        this.view.setOnClickListener { this.roll() }
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
     * Switches the Image resource to the next image.
     * While doing so, it is ensured that two consecutive images are always different
     * from each other
     */
    fun nextImage() {
        var next = this.current
        while (next == this.current) {
            next = this.random.nextInt(this.theme.permutations.size)
        }
        this.current = next
        this.view.setImageResource(this.theme.permutations[this.current])
    }

    /**
     * Starts the die roll. Depending on the settings stored in the theme variable,
     * animations and vibrations are started
     */
    fun roll() {

        if (this.theme.vibrate && !this.theme.wiggleAnimation && !this.theme.changeAnimation) {
            this.vibrator.vibrate(100)
            this.nextImage()
        }
        else if (this.theme.wiggleAnimation) {
            this.animate()
        }
        else if (this.theme.changeAnimation) {
            this.vibrate(1000)
            thread(start = true) {
                var runtime = 0
                while (runtime < 1000) {
                    this.context.runOnUiThread { this.nextImage() }
                    Thread.sleep(200)
                    runtime += 200
                }
            }
        }
        else {
            this.nextImage()
        }
    }

    /**
     * Animates the die roll. If vibration is activated, the device will vibrate during the
     * animation.
     *
     * An AnimationListener is required, it is used to change the picture of the
     * die while the animation is running
     */
    fun animate() {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.wiggle)

        if (this.theme.changeAnimation) {
            animation.setAnimationListener(Animator())
        }

        this.vibrate(animation.duration * 10)
        this.view.startAnimation(animation)
    }

    /**
     * Implementation of the AnimationListener that changes the background image
     * of the die to one of the permutations stored in the themes object while an animation
     * is running
     */
    inner class Animator : Animation.AnimationListener {

        //Not Needed
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {}

        /**
         * When the animation starts, a new thread is created that periodically changes the
         * die image.
         */
        override fun onAnimationStart(animation: Animation) {

            thread(start = true) {
                var sleep = 0
                while (sleep < animation.duration * 10) {
                    this@ClassicDie.context.runOnUiThread { this@ClassicDie.nextImage() }
                    Thread.sleep(200)
                    sleep += 200
                }
            }
        }
    }
}
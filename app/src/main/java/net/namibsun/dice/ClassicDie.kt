package net.namibsun.dice

import android.content.Context;
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.security.SecureRandom
import kotlin.concurrent.thread

/**
 * This is a class that simulates a classic 6-sided die.
 */
class ClassicDie(private val context: AppCompatActivity,
                 val view: ImageView, private val theme: Theme) {

    val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    init {
        this.view.setOnClickListener { this.roll() }
    }

    /**
     *
     */
    val random = SecureRandom()

    /**
     *
     */
    fun getRandom() : Int {
        return this.random.nextInt(this.theme.permutations.size)
    }

    fun nextImage() {
        val next = this.getRandom()
        this.view.setImageResource(this.theme.permutations[next])
    }

    fun roll() {

        if (this.theme.vibrate && !this.theme.animate) {
            this.vibrator.vibrate(100)
        }
        else if (this.theme.animate) {
            this.animate()
        }
    }

    fun animate() {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.wiggle)

        animation.setAnimationListener(Animator(animation))

        if (this.theme.vibrate) {
            this.vibrator.vibrate(animation.duration * 10)
        }

        this.view.startAnimation(animation)
    }

    inner class Animator(val animation: Animation) : Animation.AnimationListener {

        override fun onAnimationRepeat(animation: Animation?) {
            // Nothing
        }

        override fun onAnimationEnd(animation: Animation?) {
            // Nothing
        }

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
package net.namibsun.dice

import android.content.Context;
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.ImageView
import java.security.SecureRandom

/**
 * This is a class that simulates a classic 6-sided die.
 */
class ClassicDie(private val context: AppCompatActivity,
                 val view: ImageView, private val theme: Theme) {

    val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

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

    fun roll() {

        if (this.theme.vibrate && !this.theme.animate) {
            this.vibrator.vibrate(100)
        }
        else if (this.theme.animate) {
            this.animate()
        }

        val next = this.getRandom()
        this.view.setImageResource(this.theme.permutations[next])
    }

    fun animate() {
        val animation = AnimationUtils.loadAnimation(this.context, R.anim.wiggle)

        if (this.theme.vibrate) {
            this.vibrator.vibrate(animation.duration * 10)
        }

        this.view.startAnimation(animation)
    }

}
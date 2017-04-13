package net.namibsun.dice

import android.widget.ImageView
import java.security.SecureRandom

/**
 * This is a class that simulates a classic 6-sided die.
 */
class ClassicDie(val view: ImageView, private val theme: Theme) {

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
        val next = this.getRandom()
        this.view.setImageResource(this.theme.permutations[next])
    }

}
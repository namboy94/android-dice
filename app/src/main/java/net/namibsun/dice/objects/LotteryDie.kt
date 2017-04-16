package net.namibsun.dice.objects

import android.util.Log
import android.widget.TextView
import android.widget.ToggleButton
import net.namibsun.dice.R
import net.namibsun.dice.activities.BaseActivity
import net.namibsun.dice.data.lotteryOccurences

/**
 * Class that models a Lottery die with the ability to generate weighted values
 * @param context: The Activity that uses this die
 * @param view: The View of the Die, which is a TextView
 * @param theme: The theme to be applied to the die
 * @param toggle: The toggle used to identify if weight values should be generated
 * @param wiggleAnimationResource: Overrides the wiggle animation if set
 */
open class LotteryDie(context: BaseActivity,
                   view: TextView,
                   theme: Theme,
                   private val toggle: ToggleButton,
                   wiggleAnimationResource: Int = R.anim.wiggle) :
        TextDie(context, view, theme, initialValue=1, limit=49, minimum=1,
                wiggleAnimationResource=wiggleAnimationResource) {

    /**
     * The Weighted list of numbers in case weighted values should be generated
     */
    val weighted: MutableList<Int> = mutableListOf()

    /**
     * Initialize the weighted values
     */
    init {
        for ((key, value) in lotteryOccurences) {
            for (x in 0..value)
            this.weighted.add(key)
        }
    }

    /**
     * In case the weighted values should be used, they are used to generate the
     * next values.
     */
    override fun next() {
        if (this.toggle.isChecked) {
            this.currentValue = this.weighted[this.random.nextInt(this.weighted.size)]
            this.draw()
        }
        else {
            super.next()
        }
    }

}
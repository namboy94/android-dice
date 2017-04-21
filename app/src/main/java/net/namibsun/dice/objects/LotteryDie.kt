/*
Copyright 2015-2017 Hermann Krumrey

This file is part of android-dice.

    android-dice is an Android app that allows a user to roll a virtual
    die. Multiple configurations are supported

    android-dice is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    android-dice is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with android-dice. If not, see <http://www.gnu.org/licenses/>.
*/

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
 * @param storedValueKey: The key string used to store the current value in the shared preferences
 * @param toggle: The toggle used to identify if weight values should be generated
 * @param wiggleAnimationResource: Overrides the wiggle animation if set
 */
class LotteryDie(context: BaseActivity,
                 view: TextView,
                 theme: Theme,
                 storedValueKey: String,
                 private val toggle: ToggleButton,
                 wiggleAnimationResource: Int = R.anim.wiggle) :
        TextDie(context, view, theme, storedValueKey, initialValue=1, limit=49, minimum=1,
                wiggleAnimationResource=wiggleAnimationResource) {

    /**
     * The Weighted list of numbers in case weighted values should be generated
     */
    val weighted: MutableList<Int> = mutableListOf()

    /**
     * The other lottery dice linked with this Die
     */
    var neighbourDice: List<LotteryDie> = listOf()

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
     * Sets the neighbour lottery dice.
     * @param neighbours The neighbours to set
     */
    fun setNeighbours(neighbours: List<LotteryDie>) {
        this.neighbourDice = neighbours
    }

    /**
     * In case the weighted values should be used, they are used to generate the
     * next values.
     */
    override fun next() {

        do {
            if (this.toggle.isChecked) {
                this.currentValue = this.weighted[this.random.nextInt(this.weighted.size)]
            }
            else {
                this.currentValue = this.next_random_number()
            }
            Log.e("Test", "$this.currentValue")
        }
        while (this.currentValue in this.neighbourDice.map(LotteryDie::currentValue))

        this.context.prefs!!.edit().putInt(this.storedValueKey, this.currentValue).apply()
        this.draw()
    }

}
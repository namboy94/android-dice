/*
Copyright 2015-2018 Hermann Krumrey<hermann@krumreyh.com>

This file is part of android-dice.

android-dice is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

android-dice is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with android-dice.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.dice.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.ToggleButton
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.LotteryDie
import net.namibsun.dice.objects.TextDie
import net.namibsun.dice.objects.loadTheme

/**
 * An activity that allows a user to generate a lottery number
 */
class LotteryActivity : BaseActivity() {

    /**
     * The Lottery number UI elements, which are represented by TextDies
     */
    private val lotteryNumbers: MutableList<LotteryDie> = mutableListOf()

    /**
     * Initializes the lottery TextDies and sets their limit to a value
     * between 1 and 49
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.lottery)
        initializeBottomMenuBar(this)
        initializeSettingsButton(this)

        val toggle = this.findViewById(R.id.weighted_lottery_toggle) as ToggleButton
        toggle.isChecked = this.prefs!!.getBoolean("" + R.id.weighted_lottery_toggle, false)

        toggle.setOnCheckedChangeListener {
            _, isChecked -> this.prefs!!.edit()
                .putBoolean("" + R.id.weighted_lottery_toggle, isChecked).apply()
        }

        listOf(R.id.lottery_die_one, R.id.lottery_die_two, R.id.lottery_die_three,
                R.id.lottery_die_four, R.id.lottery_die_five, R.id.lottery_die_six)
                .mapTo(lotteryNumbers) {
            LotteryDie(this,
                    this.findViewById(it) as TextView,
                    loadTheme(this.prefs!!),
                    "" + it,
                    toggle)
                }

        this.lotteryNumbers.map { die ->
            die.view.setOnClickListener {
                this.lotteryNumbers.map(TextDie::roll)
            }
        }

        for (i in 0 until this.lotteryNumbers.size) {
            val neighbours = this.lotteryNumbers.toMutableList()
            neighbours.removeAt(i)
            this.lotteryNumbers[i].setNeighbours(neighbours)
        }
    }

    /**
     * Applies the current theme to the lottery numbers
     */
    override fun onResume() {
        super.onResume()
        this.lotteryNumbers.map { die -> die.updateTheme(loadTheme(this.prefs!!)) }
    }
}
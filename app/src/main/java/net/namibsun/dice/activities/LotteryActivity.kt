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

package net.namibsun.dice.activities

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.LotteryDie
import net.namibsun.dice.objects.LotteryDieType
import net.namibsun.dice.objects.loadTheme
import org.jetbrains.anko.doAsync

/**
 * An activity that allows a user to generate a lottery number
 */
class LotteryActivity : BaseActivity() {

    /**
     * The normal Lottery number UI elements, which are represented by TextDies
     * Their values range from 1 to 49
     */
    private val normalLotteryDies: MutableList<LotteryDie> = mutableListOf()

    /**
     * The five "normal" Eurojackpot numbers ranging from 1 to 50
     */
    private val normalEuroJackpotDies: MutableList<LotteryDie> = mutableListOf()

    /**
     * The bonus Eurojackpot numbers ranging from 1 to 10
     */
    private val bonusEuroJackpotDies: MutableList<LotteryDie> = mutableListOf()

    /**
     * The IDs for the normal lottery numbers
     */
    private val normalLotteryIds = listOf(
            R.id.lottery_die_one, R.id.lottery_die_two, R.id.lottery_die_three,
            R.id.lottery_die_four, R.id.lottery_die_five, R.id.lottery_die_six
    )

    /**
     * The IDs for the normal Eurojackpot numbers
     */
    private val normalEuroJackpotIds = listOf(
            R.id.eurojackpot_die_one, R.id.eurojackpot_die_two, R.id.eurojackpot_die_three,
            R.id.eurojackpot_die_four, R.id.eurojackpot_die_five
    )

    /**
     * The IDs for the bonus Eurojackpot numbers
     */
    private val bonusEuroJackpotIds = listOf(
            R.id.eurojackpot_bonus_die_one, R.id.eurojackpot_bonus_die_two
    )

    /**
     * Initializes the lottery TextDies and sets their limits to the applicable values
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.lottery)
        initializeBottomMenuBar(this)
        initializeSettingsButton(this)
        this.findViewById(R.id.lottery_activity).setOnClickListener { }

        for (toggle in listOf(R.id.weighted_lottery_toggle, R.id.eurojackpot_toggle)) {
            val toggleButton = this.findViewById(toggle) as ToggleButton
            toggleButton.isChecked = this.prefs!!.getBoolean("$toggle", false)
            toggleButton.setOnCheckedChangeListener {
                _, isChecked -> this.prefs!!.edit()
                    .putBoolean("$toggle", isChecked).apply()

                if (toggle == R.id.eurojackpot_toggle) {
                    this@LotteryActivity.switchlotteryType(isChecked)
                }
            }
        }

        this.switchlotteryType(
                (this.findViewById(R.id.eurojackpot_toggle) as ToggleButton).isChecked
        )

        for (combination in listOf(
                Triple(this.normalLotteryIds, this.normalLotteryDies,
                        LotteryDieType.NORMAL),
                Triple(this.normalEuroJackpotIds, this.normalEuroJackpotDies,
                        LotteryDieType.EURO_JACKPOT),
                Triple(this.bonusEuroJackpotIds, this.bonusEuroJackpotDies,
                        LotteryDieType.EURO_JACKPOT_BONUS)
        )) {
            combination.first.mapTo(combination.second) {
                LotteryDie(
                        this,
                        this.findViewById(it) as TextView,
                        loadTheme(this.prefs!!),
                        "$it",
                        combination.third,
                        this.findViewById(R.id.weighted_lottery_toggle) as ToggleButton
                )
            }
        }

        for (dieSet in listOf(
                this.normalLotteryDies,
                this.normalEuroJackpotDies + this.bonusEuroJackpotDies
        )) {
            dieSet.map { die ->
                die.view.setOnClickListener {
                    dieSet.map {
                        it.roll()
                    }
                    this@LotteryActivity.doAsync {
                        dieSet.map {
                            while (it.isAnimating()) {}
                        }
                        this@LotteryActivity.runOnUiThread {
                            this@LotteryActivity.sortDies()
                        }
                    }
                }
            }
            for (i in 0 until dieSet.size) {
                val neighbours = dieSet.toMutableList()
                neighbours.removeAt(i)
                dieSet[i].setNeighbours(neighbours)
            }
        }
    }

    /**
     * Sorts the lottery dies
     */
    private fun sortDies() {
        for (dies in listOf(
                this.normalLotteryDies,
                this.normalEuroJackpotDies,
                this.bonusEuroJackpotDies
        )) {
            val numbers = dies.map { it.getValue() }.sorted()
            for (i in 0 until numbers.size) {
                dies[i].next(numbers[i])
            }
        }
    }

    /**
     * Switches between the normal lottery style and the eurojackpot style
     * @param euroJackpotToggleState: The state of the eurojackpot toggle button
     */
    private fun switchlotteryType(euroJackpotToggleState: Boolean) {
        val normal = this.findViewById(R.id.lottery_numbers)
        val eurojackpot =
                this.findViewById(R.id.eurojackpot_lottery_numbers)
        if (euroJackpotToggleState) {
            normal.visibility = View.INVISIBLE
            eurojackpot.visibility = View.VISIBLE
        } else {
            normal.visibility = View.VISIBLE
            eurojackpot.visibility = View.INVISIBLE
        }
    }

    /**
     * Applies the current theme to the lottery numbers
     */
    override fun onResume() {
        super.onResume()
        (this.normalLotteryDies + this.normalEuroJackpotDies + this.bonusEuroJackpotDies).map {
            die -> die.updateTheme(loadTheme(this.prefs!!))
        }
    }
}
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

package net.namibsun.dice.helpers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import net.namibsun.dice.R
import net.namibsun.dice.activities.LotteryActivity
import net.namibsun.dice.activities.MainActivity
import net.namibsun.dice.activities.RiskDiceActivity
import net.namibsun.dice.activities.SettingsActivity
import net.namibsun.dice.activities.TextDieActivity
import net.namibsun.dice.activities.TwoDiceActivity

/**
 * Initializes the OnClickListener of the Settings button
 * @param context: The activity that calls this method
 */
fun initializeSettingsButton(context: AppCompatActivity) {
    context.findViewById(R.id.settings).setOnClickListener {
        context.startActivity(Intent(context, SettingsActivity::class.java))
    }
}

/**
 * Initializes the OnClickListeners for the individual buttons in the bottom
 * mode switcher bar
 * @param context: The activity that calls this method
 */
fun initializeBottomMenuBar(context: AppCompatActivity) {
    context.findViewById(R.id.single_die_activity).setOnClickListener {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
    context.findViewById(R.id.two_dice_activity).setOnClickListener {
        context.startActivity(Intent(context, TwoDiceActivity::class.java))
    }
    context.findViewById(R.id.risk_dice_activity).setOnClickListener {
        context.startActivity(Intent(context, RiskDiceActivity::class.java))
    }
    context.findViewById(R.id.text_dice_activity).setOnClickListener {
        context.startActivity(Intent(context, TextDieActivity::class.java))
    }
    context.findViewById(R.id.lottery_activity).setOnClickListener {
        context.startActivity(Intent(context, LotteryActivity::class.java))
    }
}
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

package net.namibsun.dice.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.TextDie
import net.namibsun.dice.objects.loadTheme

/**
 * The Text Die Activity Allows a user to specify a range of numbers of which he/she will
 * then be provided a die with a number written on it which may be rolled like any other die.
 */
class TextDieActivity : BaseActivity() {

    /**
     * The Text Die shown on the activity
     */
    var textDie : TextDie? = null

    /**
     * Initializes the Text Die and its OnClickListener
     * @param savedInstanceState: The saved instance state of the Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.textdie)

        initializeBottomMenuBar(this)
        initializeSettingsButton(this)

        this.textDie = TextDie(
                this,
                this.findViewById(R.id.textdie) as TextView,
                loadTheme(this.prefs!!),
                "" + R.id.textdie
        )

        // Check for values in the range edits
        this.textDie!!.view.setOnClickListener {
            val startEdit = this.findViewById(R.id.text_die_range_start_edit) as EditText
            val endEdit = this.findViewById(R.id.text_die_range_end_edit) as EditText
            this.textDie!!.updateRange(startEdit, endEdit)
            this.textDie!!.roll()
        }

    }

    /**
     * Updated the Theme of the Die when the activity is resume
     */
    override fun onResume() {
        super.onResume()
        this.textDie!!.updateTheme(loadTheme(this.prefs!!))
    }

}
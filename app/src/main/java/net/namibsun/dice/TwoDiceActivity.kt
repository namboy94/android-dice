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

package net.namibsun.dice

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView


/**
 */
class TwoDiceActivity : AppCompatActivity() {

    var topDie : ClassicDie? = null
    var bottomDie : ClassicDie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.twodice)

        this.topDie = ClassicDie(this, this.findViewById(R.id.topdie) as ImageView, this.loadTheme())
        this.bottomDie = ClassicDie(this, this.findViewById(R.id.bottomdie) as ImageView, this.loadTheme())

        this.topDie!!.view.setOnClickListener { this.topDie!!.roll(); this.bottomDie!!.roll() }
        this.bottomDie!!.view.setOnClickListener { this.topDie!!.roll(); this.bottomDie!!.roll() }
    }

    /**
     * Loads the theme whenever the Activity resumes, in case these values have changed
     */
    override fun onResume() {
        super.onResume()
        this.topDie!!.updateTheme(this.loadTheme())
        this.bottomDie!!.updateTheme(this.loadTheme())
    }

    /**
     * Loads the theme from the shared preferences file
     * @return The theme created from the shared preferences
     */
    fun loadTheme() : Theme {
        val prefs = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
        return Theme(
                ThemeStyles.valueOf(prefs.getString("style", "CLASSIC")),
                prefs.getBoolean("vibrate", true),
                prefs.getBoolean("wiggleAnimation", true),
                prefs.getBoolean("changeAnimation", true)
        )
    }

}
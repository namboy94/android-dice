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

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import net.namibsun.dice.objects.ClassicDie
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.loadTheme


/**
 * An Activity that alows
 */
class TwoDiceActivity : AppCompatActivity() {

    var topDie : ClassicDie? = null
    var bottomDie : ClassicDie? = null

    /**
     * A shared preferences object used to store and load settings
     */
    var prefs: SharedPreferences? = null

    /**
     * Sets the layout, creates the dice objects and overrides their default
     * OnClickListeners so that both are rolled simultaneously
     * @param savedInstanceState: The saved instance state of the App
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.twodice)
        this.prefs = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)

        this.topDie = ClassicDie(
                this,
                this.findViewById(net.namibsun.dice.R.id.topdie) as ImageView,
                loadTheme(this.prefs!!)
        )
        this.bottomDie = ClassicDie(
                this,
                this.findViewById(net.namibsun.dice.R.id.bottomdie) as ImageView,
                loadTheme(this.prefs!!),
                animation = R.anim.wigglereverse
        )

        this.topDie!!.view.setOnClickListener { this.topDie!!.roll(); this.bottomDie!!.roll() }
        this.bottomDie!!.view.setOnClickListener { this.topDie!!.roll(); this.bottomDie!!.roll() }

        // Define the OnClickListeners for the menu buttons
        initializeSettingsButton(this)
        initializeBottomMenuBar(this)
    }

    /**
     * Loads the theme whenever the Activity resumes, in case these values have changed
     */
    override fun onResume() {
        super.onResume()
        this.topDie!!.updateTheme(loadTheme(this.prefs!!))
        this.bottomDie!!.updateTheme(loadTheme(this.prefs!!))
    }

}
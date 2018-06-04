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

import android.content.Context
import android.os.Bundle
import android.view.View
import net.namibsun.dice.objects.ClassicDie
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.loadTheme

/**
 * An Activity that alows
 */
class TwoDiceActivity : BaseActivity() {

    /**
     * The Die on top
     */
    private var topDie: ClassicDie? = null

    /**
     * The Die on the bottom
     */
    private var bottomDie: ClassicDie? = null

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
                this.findViewById(R.id.topdie),
                loadTheme(this.prefs!!),
                "" + R.id.topdie
        )
        this.bottomDie = ClassicDie(
                this,
                this.findViewById(R.id.bottomdie),
                loadTheme(this.prefs!!),
                "" + R.id.bottomdie,
                wiggleAnimationResource = R.anim.wigglereverse
        )

        this.topDie!!.view.setOnClickListener { this.topDie!!.roll(); this.bottomDie!!.roll() }
        this.bottomDie!!.view.setOnClickListener { this.topDie!!.roll(); this.bottomDie!!.roll() }

        // Define the OnClickListeners for the menu buttons
        initializeSettingsButton(this)
        initializeBottomMenuBar(this)
        this.findViewById<View>(R.id.two_dice_activity).setOnClickListener { }
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
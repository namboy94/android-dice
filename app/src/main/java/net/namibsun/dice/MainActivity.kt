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

import android.content.Intent
import android.util.Log
import android.os.Bundle
import android.widget.ImageView
import android.support.v7.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    var die: ClassicDie? = null
    val theme = Theme(ThemeStyles.CLASSIC, true, false, true)

    /**
     * Initializes the App's Main Activity View.
     * @param savedInstanceState: The Instance Information of the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.i("MainActivity", "onCreate")

        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.main)

        // Get the die views and assign OnClickListeners, as well as initialize the die
        this.die = ClassicDie(
                this, this.findViewById(R.id.die) as ImageView, this.theme
        )

        // Define the OnClickListeners for the menu buttons
        this.findViewById(R.id.settings).setOnClickListener {
            this.startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

}

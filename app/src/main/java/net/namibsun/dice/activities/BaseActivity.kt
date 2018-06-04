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

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    /**
    * A shared preferences object used to store and load settings
    */
    var prefs: SharedPreferences? = null

    /**
     * Initializes the App's Main Activity View.
     * Initializes the Firebase Tracker object
     * @param savedInstanceState: The Instance Information of the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.prefs = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
    }
}

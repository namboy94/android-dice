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
import android.support.v7.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics

open class BaseActivity : AppCompatActivity() {
    /**
     * The Analytics Tracker
     */
    var analytics: FirebaseAnalytics? = null

    /**
     * Initializes the App's Main Activity View.
     * Initializes the Firebase Tracker object
     * @param savedInstanceState: The Instance Information of the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.analytics = FirebaseAnalytics.getInstance(this)

    }

    /**
     * Logs an event with the Firebase Tracker
     */
    fun logEvent(type: String, message: String) {
        val event = Bundle()
        event.putString(type, message)
        this.analytics!!.logEvent("Event", event)
    }
}

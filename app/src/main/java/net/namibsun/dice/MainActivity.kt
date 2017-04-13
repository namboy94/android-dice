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

import net.namibsun.dice.R
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import kotlin.system.measureTimeMillis


class MainActivity : AppCompatActivity() {

    var die: ClassicDie? = null
    val theme = Theme(ThemeStyles.CLASSIC, true, true)

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
                this.findViewById(R.id.die) as ImageView, this.theme
        )
        this.die!!.view.setOnClickListener { this.die!!.roll() }

        // Define the OnClickListener for the menu buttons
        this.findViewById(R.id.settings).setOnClickListener {  }
    }

    fun wiggle(view: ImageView) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.wiggle)

        //anim.setAnimationListener(BackgroundChanger(view, listOf(R.drawable.dice1, R.drawable.dice2)))


        view.startAnimation(anim)

        Log.e("aa", "" + anim.hasEnded())

        view.setImageResource(R.drawable.dice1)


        Log.e("Start", "" + measureTimeMillis{})
        //while (!anim.hasEnded()) {
        //}
        Log.e("Done", "" + measureTimeMillis{})
    }



}

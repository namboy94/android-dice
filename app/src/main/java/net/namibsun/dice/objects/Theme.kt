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

package net.namibsun.dice.objects

import android.content.SharedPreferences
import net.namibsun.dice.R

/**
 * Class that defines the attributes for a theme.
 * For example, the permutations for a normal 6-sided die
 * @param style: The style of the theme. Used to define which images to use for the die
 * @param vibrate: Can be set to true to enable vibrating
 * @param wiggleAnimation: Can be set to true to enable a wiggle animation
 * @param changeAnimation: Can be set to true to enable an animation that
 *                         changes the eyes of the die while it is rolling
 */
class Theme(style: ThemeStyles, val vibrate: Boolean,
            val wiggleAnimation: Boolean, val changeAnimation: Boolean) {

    /**
     * The permutations for a 6-sided die
     */
    val permutations = this.findPermutations(style)

    /**
     * Defines the permutations for a 6-sided die based on the provided style value
     */
    fun findPermutations(style: ThemeStyles) : List<Int> {

        // Add new styles here!
        return when(style) {
            ThemeStyles.CLASSIC -> listOf(
                    R.drawable.classic_1, R.drawable.classic_2, R.drawable.classic_3,
                    R.drawable.classic_4, R.drawable.classic_5, R.drawable.classic_6
            )
            ThemeStyles.RED -> listOf(
                    R.drawable.red_1, R.drawable.red_2, R.drawable.red_3,
                    R.drawable.red_4, R.drawable.red_5, R.drawable.red_6
            )
            ThemeStyles.BLUE -> listOf(
                    R.drawable.blue_1, R.drawable.blue_2, R.drawable.blue_3,
                    R.drawable.blue_4, R.drawable.blue_5, R.drawable.blue_6
            )
        }
    }
}


/**
 * An enum to help define different Theme style types
 */
enum class ThemeStyles {
    CLASSIC, RED, BLUE
}


/**
 * Loads a theme object from a shared preferences object
 * @param prefs: The Shared Preferences to use
 * @return The generated Theme object
 */
fun loadTheme(prefs: SharedPreferences) : Theme {
    return Theme(
            ThemeStyles.valueOf(prefs.getString("style", "CLASSIC")),
            prefs.getBoolean("vibrate", true),
            prefs.getBoolean("wiggleAnimation", true),
            prefs.getBoolean("changeAnimation", true)
    )
}
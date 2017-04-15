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

import android.content.Context
import android.content.SharedPreferences
import android.support.v4.content.ContextCompat
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
class Theme(val style: ThemeStyles, val vibrate: Boolean,
            val wiggleAnimation: Boolean, val changeAnimation: Boolean) {

    /**
     * Calculates the Theme's colour values
     * @param context: The currently active activity
     * @return A hashmap of colour values
     */
    fun getThemeColors(context: Context) : HashMap<String, Int> {
        val colors = when (this.style) {
            ThemeStyles.CLASSIC -> hashMapOf(
                    "die_base" to R.color.classic_base, "die_eye" to R.color.classic_eye
            )
            ThemeStyles.RED -> hashMapOf(
                    "die_base" to R.color.red_base, "die_eye" to R.color.red_eye
            )
            ThemeStyles.BLUE -> hashMapOf(
                    "die_base" to R.color.blue_base, "die_eye" to R.color.blue_eye
            )
        }
        for ((key, value) in colors) {
            colors[key] = ContextCompat.getColor(context, value)
        }
        return colors
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
 * @param styleOverride: Can be used to override the style of the Theme
 * @return The generated Theme object
 */
fun loadTheme(prefs: SharedPreferences, styleOverride: ThemeStyles? = null) : Theme {

    val style: ThemeStyles
    if (styleOverride == null) {
        style = ThemeStyles.valueOf(prefs.getString("style", "CLASSIC"))
    }
    else {
        style = styleOverride
    }

    return Theme(
            style,
            prefs.getBoolean("vibrate", true),
            prefs.getBoolean("wiggleAnimation", true),
            prefs.getBoolean("changeAnimation", true)
    )
}
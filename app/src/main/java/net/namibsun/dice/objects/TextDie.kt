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

package net.namibsun.dice.objects

import android.graphics.drawable.GradientDrawable
import android.widget.EditText
import android.widget.TextView
import net.namibsun.dice.R
import net.namibsun.dice.activities.BaseActivity

/**
 * Class that models a Text Die. It can display arbitrary numbers instead of a finite amount
 * of eyes
 * @param context: The Activity that uses this die
 * @param view: The View of the Die, which is a TextView
 * @param theme: The theme to be applied to the die
 * @param storedValueKey: The key string used to store the current value in the shared preferences
 * @param limit: The upper limit of the die
 * @param minimum: The lower limit of the die
 * @param wiggleAnimationResource: Overrides the wiggle animation if set
 */
open class TextDie(context: BaseActivity,
                   view: TextView,
                   theme: Theme,
                   storedValueKey: String,
                   initialValue: Int = 0,
                   limit: Int = 0,
                   minimum: Int = 0,
                   wiggleAnimationResource: Int = R.anim.wiggle) :
        Die(context, view, theme, storedValueKey, initialValue = initialValue, limit = limit,
                minimum = minimum, wiggleAnimationResource = wiggleAnimationResource) {

    /**
     * Draws the current value of the Die as a TextView, applying the current theme
     */
    override fun draw() {

        val text = this.view as TextView
        val background = text.background as GradientDrawable
        val colors = this.theme.getThemeColors(this.context)

        background.setColor(colors["die_base"]!!)
        text.setTextColor(colors["die_eye"]!!)

        text.background = background
        text.text = this.currentValue.toString()
    }

    /**
     * Updates the Range of the die. If the end value is smaller than the start value,
     * the values are flipped around.
     * @param startEdit: The Start Range EditText
     * @param endEdit: The End Range EditText
     */
    fun updateRange(startEdit: EditText, endEdit: EditText) {

        val values: MutableList<Int> = mutableListOf()
        listOf(startEdit, endEdit).mapTo(values) {
            if (it.text.isEmpty()) {
                0
            } else {
                it.text.toString().toInt()
            }
        }

        val editor = this.context.prefs!!.edit()
        editor.putInt(this.storedValueKey + "startvalue", values[0])
        editor.putInt(this.storedValueKey + "endvalue", values[1])
        editor.apply()

        if (values[0] <= values[1]) {
            this.minimum = values[0]
            this.limit = values[1]
        } else {
            this.minimum = values[1]
            this.limit = values[0]
        }
    }
}
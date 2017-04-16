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

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.widget.ImageView
import net.namibsun.dice.R
import net.namibsun.dice.activities.BaseActivity

/**
 * This is a class that simulates a classic 6-sided die.
 * @param context: The context/Activity for this die.
 * @param view: The view that represents this die
 * @param theme: The starting theme to be applied to the die
 * @param initialValue: The initial value of the die
 * @param wiggleAnimationResource: Can be used to override the default wiggle animation
 */
open class ClassicDie(context: BaseActivity,
                      view: ImageView,
                      theme: Theme,
                      initialValue: Int = 5,
                      wiggleAnimationResource: Int = R.anim.wiggle) :
        Die(context, view, theme, initialValue=initialValue, limit = 6, minimum = 1,
                wiggleAnimationResource=wiggleAnimationResource) {

    /**
     * Draws the currently selected image resource
     */
    override fun draw() {

        val image = this.view as ImageView

        val dieFaces : HashMap<Int, Int> = hashMapOf(
                1 to R.drawable.die_1, 2 to R.drawable.die_2, 3 to R.drawable.die_3,
                4 to R.drawable.die_4, 5 to R.drawable.die_5, 6 to R.drawable.die_6
        )

        image.setImageResource(dieFaces[this.currentValue]!!)

        val colors : HashMap<String, Int> = this.theme.getThemeColors(this.context)
        val layer = image.drawable as LayerDrawable
        layer.mutate()

        for (eye in listOf(
                R.id.middle_eye, R.id.left_top_eye, R.id.left_middle_eye, R.id.left_bottom_eye,
                R.id.right_top_eye, R.id.right_middle_eye, R.id.right_bottom_eye)) {

            var eyeDrawable = layer.findDrawableByLayerId(eye)
            val base = layer.findDrawableByLayerId(R.id.die_base) as GradientDrawable

            base.setColor(colors["die_base"]!!)

            if (eyeDrawable != null) {
                eyeDrawable = eyeDrawable as GradientDrawable
                eyeDrawable.setColor(colors["die_eye"]!!)
            }
        }

        val sdk = android.os.Build.VERSION.SDK_INT
        if (sdk <= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            @Suppress("DEPRECATION")
            image.setBackgroundDrawable(layer)
        }
        else {
            image.setImageDrawable(layer)
        }
    }
}

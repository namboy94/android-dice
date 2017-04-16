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
 * @param limit: The upper limit of the die
 * @param minimum: The lower limit of the die
 * @param wiggleAnimationResource: Overrides the wiggle animation if set
 */
class TextDie(context: BaseActivity,
              view: TextView,
              theme: Theme,
              initialValue: Int = 0,
              limit: Int = 0,
              minimum: Int = 0,
              wiggleAnimationResource: Int = R.anim.wiggle) :
        Die(context, view, theme, initialValue=initialValue, limit=limit, minimum=minimum,
                wiggleAnimationResource=wiggleAnimationResource) {

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
            }
            else {
                it.text.toString().toInt()
            }
        }

        if (values[0] <= values[1]) {
            this.minimum = values[0]
            this.limit = values[1]
        }
        else {
            this.minimum = values[1]
            this.limit = values[0]
        }
    }
}
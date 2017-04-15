package net.namibsun.dice.objects

import android.graphics.drawable.GradientDrawable
import android.widget.TextView
import net.namibsun.dice.R
import net.namibsun.dice.activities.BaseActivity

class TextDie(context: BaseActivity, view: TextView, theme: Theme, initialValue: Int = 0, limit: Int = 0, minimum: Int = 0, wiggleAnimationResource: Int = R.anim.wiggle) :
        Die(context, view, theme, initialValue=initialValue, limit=limit, minimum=minimum, wiggleAnimationResource=wiggleAnimationResource) {

    override fun draw() {

        val text = this.view as TextView
        val background = text.background as GradientDrawable
        val colors = this.theme.getThemeColors(this.context)

        background.setColor(colors["die_base"]!!)
        text.setTextColor(colors["die_eye"]!!)

        text.background = background
        text.text = this.currentValue.toString()
    }

    fun updateRange(start: Int, end: Int) {

        if (this.minimum <= end) {
            this.minimum = start
            this.limit = end
        }
        else {
            this.minimum = end
            this.limit = start
        }

    }

}
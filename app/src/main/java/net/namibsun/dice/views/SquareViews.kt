/*
Copyright 2015-2018 Hermann Krumrey<hermann@krumreyh.com>

This file is part of android-dice.

android-dice is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

android-dice is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with android-dice.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.namibsun.dice.views

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView

/**
 * A Text View that Always has the same height as its width
 */
class SquareWidthTextView @JvmOverloads constructor(context: Context,
                                                    attrs: AttributeSet? = null,
                                                    defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

}
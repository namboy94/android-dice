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
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Vibrator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import net.namibsun.dice.R
import net.namibsun.dice.activities.BaseActivity
import java.security.SecureRandom
import kotlin.concurrent.thread

/**
 * This is a class that simulates a classic 6-sided die.
 * @param context: The context/Activity for this die.
 * @param view: The view that represents this die
 * @param theme: The starting theme to be applied to the die
 * @param current: Optional parameter that can specify which image of the theme's
 *                 permutations should be the default image. By default this is the 5th element.
 * @param animation: Can be used to override the default wiggle animation
 */
class ClassicDie(private val context: BaseActivity,
                 val view: ImageView,
                 private var theme: Theme,
                 private var current: Int = 4,
                 private val animation: Int = R.anim.wiggle) {

    val dieFaces = listOf(
            R.drawable.die_1, R.drawable.die_2, R.drawable.die_3,
            R.drawable.die_4, R.drawable.die_5, R.drawable.die_6
    )

    /**
     * The vibrator is used to vibrate the device while the animation is running,
     * if the theme allows for this.
     */
    val vibrator = this.context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    /**
     * An object that can generate random numbers
     */
    val random = SecureRandom()

    /**
     * Sets the OnClickListener for the image view
     */
    init {
        this.view.setOnClickListener { this.roll() }
        this.draw()
    }

    /**
     * Draws the currently selected image resource
     */
    fun draw() {
        this.view.setImageResource(this.dieFaces[this.current])

        val colors : HashMap<String, Int> = this.theme.getThemeColors(this.context)
        val layer = this.view.drawable as LayerDrawable
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
        this.view.setImageDrawable(layer)
    }

    /**
     * Updates the theme of the Die
     * @param theme: The theme to change to
     */
    fun updateTheme(theme: Theme) {
        this.theme = theme
        this.draw()
    }

    /**
     * Vibrates the device for a set amount of time, but only if vibrating is enabled
     * in the settings.
     * @param duration: The duration of the vibration
     */
    fun vibrate(duration: Long) {
        if (this.theme.vibrate) {
            this.vibrator.vibrate(duration)
        }
    }

    /**
     * Switches the Image resource to the next image.
     * While doing so, it is ensured that classic_2 consecutive images are always different
     * from each other
     */
    fun nextImage() {
        var next = this.current
        while (next == this.current) {
            next = this.random.nextInt(6)
        }
        this.current = next
        this.draw()
    }

    /**
     * Starts the die roll. Depending on the settings stored in the theme variable,
     * animations and vibrations are started
     */
    fun roll() {

        this.context.logEvent("Diceroll", "Start")

        if (this.theme.vibrate && !this.theme.wiggleAnimation && !this.theme.changeAnimation) {
            this.vibrator.vibrate(100)
            this.nextImage()
        }
        else if (this.theme.wiggleAnimation) {
            this.animate()
        }
        else if (this.theme.changeAnimation) {
            this.vibrate(1000)
            thread(start = true) {
                var runtime = 0
                while (runtime < 1000) {
                    this.context.runOnUiThread { this.nextImage() }
                    Thread.sleep(200)
                    runtime += 200
                }
            }
        }
        else {
            this.nextImage()
        }
    }

    /**
     * Animates the die roll. If vibration is activated, the device will vibrate during the
     * animation.
     *
     * An AnimationListener is required, it is used to change the picture of the
     * die while the animation is running
     */
    fun animate() {
        val animation = AnimationUtils.loadAnimation(this.context, this.animation)

        if (this.theme.changeAnimation) {
            animation.setAnimationListener(ChangeAnimator())
        }
        else {
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) { }
                override fun onAnimationStart(animation: Animation?) { }
                override fun onAnimationEnd(animation: Animation?) { this@ClassicDie.nextImage() }
            })
        }

        this.vibrate(animation.duration * 10)
        this.view.startAnimation(animation)
    }

    /**
     * Implementation of the AnimationListener that changes the background image
     * of the die to one of the permutations stored in the themes objects while an animation
     * is running
     */
    inner class ChangeAnimator : Animation.AnimationListener {

        //Not Needed
        override fun onAnimationRepeat(animation: Animation?) {}
        override fun onAnimationEnd(animation: Animation?) {}

        /**
         * When the animation starts, a new thread is created that periodically changes the
         * die image.
         */
        override fun onAnimationStart(animation: Animation) {

            thread(start = true) {
                var sleep = 0
                while (sleep < animation.duration * 10) {
                    this@ClassicDie.context.runOnUiThread { this@ClassicDie.nextImage() }
                    Thread.sleep(200)
                    sleep += 200
                }
            }
        }
    }
}
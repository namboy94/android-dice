package net.namibsun.dice

import android.support.v7.app.AppCompatActivity
import android.view.animation.Animation
import android.widget.ImageView
import java.security.SecureRandom


class BackgroundChanger(val context: AppCompatActivity, val view: ImageView, val resources: List<Int>)
    : Animation.AnimationListener {

    var finished = false

    override fun onAnimationRepeat(animation: Animation?) {
        //
    }

    override fun onAnimationEnd(animation: Animation?) {
        this.finished = false
    }

    override fun onAnimationStart(animation: Animation?) {

        val random = SecureRandom()
        var last = -1
        var next = -1

        this.finished = true
        while (this.finished) {
            while(next == last) {
                next = random.nextInt(this.resources.size)
            }
            this.view.setImageResource(this.resources[next])
            last = next
        }
    }

}
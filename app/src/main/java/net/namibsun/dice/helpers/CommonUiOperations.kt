package net.namibsun.dice.helpers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import net.namibsun.dice.R
import net.namibsun.dice.activities.MainActivity
import net.namibsun.dice.activities.SettingsActivity
import net.namibsun.dice.activities.TwoDiceActivity

/**
 * Created by hermann on 4/13/17.
 */


fun initializeSettingsButton(context: AppCompatActivity) {
    context.findViewById(R.id.settings).setOnClickListener {
        context.startActivity(Intent(context, SettingsActivity::class.java))
    }
}

fun initializeBottomMenuBar(context: AppCompatActivity) {
    context.findViewById(R.id.single_die_activity).setOnClickListener {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
    context.findViewById(R.id.two_dice_activity).setOnClickListener {
        context.startActivity(Intent(context, TwoDiceActivity::class.java))
    }
}
package net.namibsun.dice

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.CheckBox
import android.widget.RadioGroup

/**
 * The Settings Activity for the Android App. It enables the user to define certain settings
 * to his/her liking.
 */
class SettingsActivity : AppCompatActivity() {

    var vibrateCheck : CheckBox? = null
    var wiggleAnimationCheck : CheckBox? = null
    var changeAnimationCheck : CheckBox? = null
    var styleGroup : RadioGroup? = null

    /**
     * Initializes the layout and the OnClickListeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.settings)
        this.findViewById(R.id.ok_button).setOnClickListener { this.storeAndReturn() }

        this.vibrateCheck = this.findViewById(R.id.vibrate_check) as CheckBox
        this.wiggleAnimationCheck = this.findViewById(R.id.wiggle_animation_check) as CheckBox
        this.changeAnimationCheck = this.findViewById(R.id.change_animation_check) as CheckBox
        this.styleGroup = this.findViewById(R.id.style_select_group) as RadioGroup

        val prefs = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
        val style = ThemeStyles.valueOf(prefs.getString("style", "CLASSIC"))
        val vibrate = prefs.getBoolean("vibrate", true)
        val wiggleAnimation = prefs.getBoolean("wiggleAnimation", true)
        val changeAnimation = prefs.getBoolean("changeAnimation", true)

        this.vibrateCheck!!.isChecked = vibrate
        this.wiggleAnimationCheck!!.isChecked = wiggleAnimation
        this.changeAnimationCheck!!.isChecked = changeAnimation

        when(style) {
            ThemeStyles.CLASSIC -> this.styleGroup!!.check(R.id.classic_style)
            ThemeStyles.RED -> this.styleGroup!!.check(R.id.red_style)
        }

    }

    /**
     * Stores the current input and returns to the previous activity
     */
    @SuppressLint("CommitPrefEdits")
    fun storeAndReturn() {
        val editor = this.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE).edit()

        val radioGroup = this.findViewById(R.id.style_select_group) as RadioGroup
        val selected = this.findViewById(radioGroup.checkedRadioButtonId)
        val styleName: String = selected.tag as String

        editor.putString("style", styleName)
        editor.putBoolean("vibrate", this.vibrateCheck!!.isChecked)
        editor.putBoolean("wiggleAnimation", this.wiggleAnimationCheck!!.isChecked)
        editor.putBoolean("changeAnimation", this.changeAnimationCheck!!.isChecked)

        editor.commit() // We need to commit to ensure that other activities are updated ASAP
        this.finish()
    }

}
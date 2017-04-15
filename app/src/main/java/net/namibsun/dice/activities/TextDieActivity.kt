package net.namibsun.dice.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.TextDie
import net.namibsun.dice.objects.loadTheme

/**
 * The Text Die Activity Allows a user to specify a range of numbers of which he/she will
 * then be provided a die with a number written on it which may be rolled like any other die.
 */
class TextDieActivity : BaseActivity() {

    /**
     * The Text Die shown on the activity
     */
    var textDie : TextDie? = null

    /**
     * Initializes the Text Die and its OnClickListener
     * @param savedInstanceState: The saved instance state of the Activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.textdie)

        initializeBottomMenuBar(this)
        initializeSettingsButton(this)

        this.textDie = TextDie(
                this, this.findViewById(R.id.textdie) as TextView, loadTheme(this.prefs!!)
        )

        // Check for values in the range edits
        this.textDie!!.view.setOnClickListener {
            val startEdit = this.findViewById(R.id.text_die_range_start_edit) as EditText
            val endEdit = this.findViewById(R.id.text_die_range_end_edit) as EditText
            val startValue = startEdit.text.toString().toInt()
            val endValue = endEdit.text.toString().toInt()
            this.textDie!!.updateRange(startValue, endValue)
            this.textDie!!.roll()
        }

    }

    /**
     * Updated the Theme of the Die when the activity is resume
     */
    override fun onResume() {
        super.onResume()
        this.textDie!!.updateTheme(loadTheme(this.prefs!!))
    }

}
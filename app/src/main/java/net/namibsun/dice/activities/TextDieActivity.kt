package net.namibsun.dice.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.EditText
import android.widget.TextView
import net.namibsun.dice.R
import net.namibsun.dice.helpers.initializeBottomMenuBar
import net.namibsun.dice.helpers.initializeSettingsButton
import net.namibsun.dice.objects.TextDie
import net.namibsun.dice.objects.loadTheme

class TextDieActivity : BaseActivity() {

    var textDie : TextDie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.textdie)
        initializeBottomMenuBar(this)
        initializeSettingsButton(this)
        this.textDie = TextDie(this, this.findViewById(R.id.textdie) as TextView, loadTheme(this.prefs!!))

        this.textDie!!.view.setOnClickListener {
            val startEdit = this.findViewById(R.id.text_die_range_start_edit) as EditText
            val endEdit = this.findViewById(R.id.text_die_range_end_edit) as EditText
            this.textDie!!.updateRange(startEdit.text.toString().toInt(), endEdit.text.toString().toInt())
            this.textDie!!.roll()
        }

    }


    override fun onResume() {
        super.onResume()
        this.textDie!!.updateTheme(loadTheme(this.prefs!!))
    }

}
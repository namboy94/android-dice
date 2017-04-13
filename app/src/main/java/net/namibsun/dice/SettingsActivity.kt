package net.namibsun.dice

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * The Settings Activity for the Android App. It enables the user to define certain settings
 * to his/her liking.
 */
class SettingsActivity : AppCompatActivity() {

    /**
     * Initializes the layout and the OnClickListeners
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(R.layout.settings)
        this.findViewById(R.id.ok_button).setOnClickListener { this.storeAndReturn() }
    }

    /**
     * Stores the current input and returns to the previous activity
     */
    fun storeAndReturn() {
        this.finish()
    }

}
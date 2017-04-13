package net.namibsun.dice.activities


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.analytics.FirebaseAnalytics


open class BaseActivity : AppCompatActivity() {
    /**
     * The Analytics Tracker
     */
    var analytics: FirebaseAnalytics? = null


    /**
     * Initializes the App's Main Activity View.
     * Initializes the Firebase Tracker object
     * @param savedInstanceState: The Instance Information of the app.
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.analytics = FirebaseAnalytics.getInstance(this)

    }

    /**
     * Logs an event with the Firebase Tracker
     */
    fun logEvent(type: String, message: String) {
        val event = Bundle()
        event.putString(type, message)
        this.analytics!!.logEvent("Event", event)
    }
}

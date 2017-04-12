package net.namibsun.dice

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.opengl.Matrix
import android.os.CountDownTimer
import android.os.Vibrator
import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import net.namibsun.dice.R

import java.util.ArrayList

/**
 * Main Activity class of the android app
 * It allows to roll a single dice
 * @author  Hermann Krumrey (hermann@krumreyh.com)
 */
class Main : AppCompatActivity() {

    private var image: ImageView? = null
    private var vibrator: Vibrator? = null
    private val animationCount = 0

    /**
     * Method run when created, gets objects in layout and saves them to local variables
     * @param savedInstanceState - android-default-thingamajig
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.image = findViewById(R.id.dice_image) as ImageView
        this.vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }


    /**
     * Method run when mnu is being created
     * @param menu - the menu being created
     * *
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Method run when an option on the menu is selected
     * @param item - the selected item
     * *
     * @return true, I guess?
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId

        if (id == R.id.action_two) {
            val menuIntent = Intent(this, TwoDiceActivity::class.java)
            startActivity(menuIntent)
            return true
        } else if (id == R.id.risk_action) {
            val menuIntent = Intent(this, RiskDice::class.java)
            startActivity(menuIntent)
            return true
        } else if (id == R.id.custom_action) {
            val menuIntent = Intent(this, CustomDice::class.java)
            startActivity(menuIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Rolls the dice
     * @param view - the active view
     */
    fun rollDice(view: View) {
        this.vibrator!!.vibrate(2000)
        val dice = ArrayList<ImageView>()
        dice.add(this.image!!)
        MultiDice(dice, DiceColor.WHITEBLACK).rollDice()
    }
}

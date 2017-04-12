package net.namibsun.dice

import android.app.Activity
import android.content.Context
import android.content.Intent
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


class RiskDice : AppCompatActivity() {

    private var red1: ImageView? = null
    private var red2: ImageView? = null
    private var red3: ImageView? = null
    private var blue1: ImageView? = null
    private var blue2: ImageView? = null
    private var vibrator: Vibrator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_risk_dice)
        this.red1 = findViewById(R.id.risk_r1) as ImageView
        this.red2 = findViewById(R.id.risk_r2) as ImageView
        this.red3 = findViewById(R.id.risk_r3) as ImageView
        this.blue1 = findViewById(R.id.risk_b1) as ImageView
        this.blue2 = findViewById(R.id.risk_b2) as ImageView
        this.vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_risk_dice, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_one) {
            val menuIntent = Intent(this, Main::class.java)
            startActivity(menuIntent)
            return true
        } else if (id == R.id.action_two) {
            val menuIntent = Intent(this, TwoDiceActivity::class.java)
            startActivity(menuIntent)
            return true
        } else if (id == R.id.custom_action) {
            val menuIntent = Intent(this, CustomDice::class.java)
            startActivity(menuIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun rollRedDice(view: View) {
        this.vibrator!!.vibrate(2000)
        val dice = ArrayList<ImageView>()
        dice.add(red1!!)
        dice.add(red2!!)
        dice.add(red3!!)
        MultiDice(dice, DiceColor.REDWHITE).rollDice()
    }

    fun rollBlueDice(view: View) {
        this.vibrator!!.vibrate(2000)
        val dice = ArrayList<ImageView>()
        dice.add(blue1!!)
        dice.add(blue2!!)
        MultiDice(dice, DiceColor.BLUEWHITE).rollDice()
    }
}

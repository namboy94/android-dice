package net.namibsun.dice

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.Vibrator
import android.support.v7.app.ActionBarActivity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.util.TypedValue
import android.view.*
import android.widget.EditText
import android.widget.TextView
import net.namibsun.dice.R
import org.w3c.dom.Text


class CustomDice : AppCompatActivity() {

    private var seed: EditText? = null
    private var result: TextView? = null
    private var vibrator: Vibrator? = null
    private var getOrient: Display? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_dice)
        this.seed = findViewById(R.id.randomDiceSeed) as EditText
        this.result = findViewById(R.id.custom_result) as TextView
        this.vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        this.getOrient = windowManager.defaultDisplay
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_custom_dice, menu)
        return true
    }

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
        } else if (id == R.id.action_one) {
            val menuIntent = Intent(this, Main::class.java)
            startActivity(menuIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun rollDice(view: View) {
        val seedNo: Int
        try {
            seedNo = Integer.parseInt(this.seed!!.text.toString())
        } catch (e: NumberFormatException) {
            return
        }

        this.vibrator!!.vibrate(2000)
        result!!.isEnabled = false

        object : CountDownTimer(2000, 10) {

            internal var timer = 0
            internal var key = 0

            override fun onTick(millisUntilFinished: Long) {
                wiggleNumber(result!!)

                if (timer == 5) {
                    changeNumber(result!!, seedNo)
                    timer = 0
                } else {
                    timer++
                }
            }

            override fun onFinish() {
                result!!.rotation = 0.0f
                changeNumber(result!!, seedNo)
                result!!.isEnabled = true
            }
        }.start()


        val random = (Math.random() * seedNo + 1).toInt()
        this.result!!.text = random.toString() + ""
    }

    private fun wiggleNumber(text: TextView) {
        val rotation = text.rotation
        if (rotation == 0.0f) {
            text.rotation = 1.000001f
        } else if (rotation > 0.0f && rotation < 11.0f) {
            if (rotation - Math.floor(rotation.toDouble()) < 0.5f) {
                text.rotation = rotation + 2.0f
            } else {
                text.rotation = rotation - 2.0f
            }
        } else if (rotation >= 10.0f) {
            text.rotation = 9.999999f
        } else if (rotation < 0.0f && rotation > -11.0f) {
            if (-1.0f * rotation + Math.ceil(rotation.toDouble()) < 0.5f) {
                text.rotation = rotation - 2.0f
            } else {
                text.rotation = rotation + 2.0f
            }
        } else if (rotation <= -10.0f) {
            text.rotation = -8.999999f
        }
    }

    private fun changeNumber(text: TextView, seedNo: Int) {
        val random = (Math.random() * seedNo + 1).toInt()
        val fontSize: Float
        if (getOrient!!.rotation == Surface.ROTATION_0) {
            if (random < 100) {
                fontSize = 250.0f
                text.setPadding(0, 0, 0, 0)
            } else if (random >= 100 && random < 1000) {
                fontSize = 150.0f
                text.setPadding(0, 100, 0, 0)
            } else if (random >= 1000 && random < 10000) {
                fontSize = 100.0f
                text.setPadding(0, 150, 0, 0)
            } else if (random >= 10000 && random < 100000) {
                fontSize = 50.0f
                text.setPadding(0, 200, 0, 0)
            } else {
                fontSize = 30.0f
                text.setPadding(0, 220, 0, 0)
            }
        } else {
            if (random < 10000000) {
                fontSize = 150.0f
                text.setPadding(0, 0, 0, 0)
            } else {
                fontSize = 100.0f
                text.setPadding(0, 50, 0, 0)
            }
        }
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize)
        text.text = "" + random
    }
}

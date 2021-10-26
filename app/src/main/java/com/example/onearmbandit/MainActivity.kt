package com.example.onearmbandit

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.onearmbandit.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d("OnCreate called", "success")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //SLOTS FROM LAYOUT
        val slot1 = binding.slot1
        val slot2 = binding.slot2
        val slot3 = binding.slot3
        val creditPlus = binding.creditPlus
        val winning = binding.creditPlus2

        val winner = binding.winner
        val badluck = binding.badluck

        val topUP = binding.topUP
        val button = binding.button
        val scoreText = binding.score
        val winsView = binding.wins
        val ratioView = binding.ratio
        val spins = binding.spins

        topUP.visibility = View.INVISIBLE
        creditPlus.visibility = View.INVISIBLE
        winning.visibility = View.INVISIBLE


        //Returns drawable resource on random generated integer
        fun randomImg(): Int {

            val randomInt = Random.nextInt(3) + 1

            Log.d("Random Generated", randomInt.toString())

            val imgSrc = when (randomInt) {
                1 -> R.drawable.lemon
                2 -> R.drawable.seven
                else -> R.drawable.cherry
            }
            return imgSrc
        }

        fun visibility(num: Int) {
            if (num == 1) {
                badluck.visibility = View.INVISIBLE
                winner.visibility = View.VISIBLE
                winning.visibility = View.VISIBLE
            } else {

                badluck.visibility = View.VISIBLE
                winner.visibility = View.INVISIBLE
                winning.visibility = View.INVISIBLE
            }
        }

        //Sets images to Slots
        fun setImages() {

            val image1 = randomImg()
            val image2 = randomImg()
            val image3 = randomImg()

            slot1.setImageResource(image1)
            slot2.setImageResource(image2)
            slot3.setImageResource(image3)


            fun logic() {
                if (image1.equals(image2) && image2.equals(image3)) {
                    Utils.score += 20
                    scoreText.text = Utils.score.toString()
                    Utils.numWins = Utils.numWins + 1
                    visibility(1)


                } else {
                    Utils.score -= 10
                    scoreText.text = Utils.score.toString()
                    visibility(2)
                    Utils.lost = Utils.lost + 1
                }
            }
            logic()
        }

        //Displays the ratio, win and spins statistics
        fun displayStats() {
            Utils.numSpins++
            spins.text = Utils.numSpins.toString()
            winsView.text = Utils.numWins.toString()

            // Calculates ratio only once number of spins and number of losses is >0
            // can't divide by zero
            if (Utils.numSpins > 0 && Utils.lost > 0) {
                Utils.ratio = Utils.numWins / Utils.lost
                val formatRatio = String.format("%.3f", Utils.ratio).toDouble()
                ratioView.text = formatRatio.toString()
            }
        }

        //Top up button
        topUP.setOnClickListener()
        {
            Utils.score += 50
            scoreText.text = Utils.score.toString()
            topUP.visibility = View.INVISIBLE
            creditPlus.visibility = View.VISIBLE
        }

        //Checks credit if its less than or equal to 0
        fun checkCredit(): Boolean {
            if (Utils.score <= 0) {
                Toast.makeText(this, "TOP UP", Toast.LENGTH_SHORT).show()
                topUP.visibility = View.VISIBLE
                return false
            } else {
                topUP.visibility = View.INVISIBLE
                creditPlus.visibility = View.INVISIBLE
                return true
            }
        }

        //Starts the game
        button.setOnClickListener()
        {
            //Checks if user has credit, then starts the game
            if (checkCredit())
            {
                setImages()
                displayStats()
            }
        }
        badluck.visibility = View.INVISIBLE
        winner.visibility = View.INVISIBLE
    }
}
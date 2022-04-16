package com.example.spinninggame

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinButton: ImageView = findViewById(R.id.spinButton)
        val wheel: ImageView = findViewById(R.id.wheel)
        val points: TextView = findViewById(R.id.points)

        getDegreeForSectors()

        spinButton.setOnClickListener {
            if(!isSpinning) {
                spin(wheel, points)
                isSpinning = true
            }
        }
    }

    private val sectors: List<String> = listOf("10", "20", "30", "40", "50", "60", "70", "80", "90", "100", "110", "120")
    private val sectorDegrees: MutableList<Int> = mutableListOf()
    private var degree: Int = 0
    private var isSpinning: Boolean = false

    private fun usePoint(points: TextView) {
        var currentPoint: Int = points.text.toString().toInt()
        if (currentPoint > 40)
            currentPoint -= 40
        else
            currentPoint = 0

        points.text = currentPoint.toString()
    }

    private fun updatePoint(newPoint: Int, points: TextView) {
        var currentPoint: Int = points.text.toString().toInt()
        currentPoint += newPoint

        points.text = currentPoint.toString()

    }

    private fun getDegreeForSectors() {
        val sectorDegree: Int = 360 / sectors.size
        for(num in 0..sectors.size) {
            sectorDegrees.add((num + 1) * sectorDegree)
        }

    }

    private fun spin(imageView: ImageView, points: TextView) {
        val sectorLen: Int = sectors.size - 1
        degree = (0..sectorLen).random()

        val rotateAnimation = RotateAnimation(0F,
            (360 * sectors.size + sectorDegrees[degree]).toFloat(),
            RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)

        rotateAnimation.duration = 3600
        rotateAnimation.fillAfter = true
        rotateAnimation.interpolator = DecelerateInterpolator()
        rotateAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                usePoint(points)
            }

            override fun onAnimationEnd(p0: Animation?) {
                isSpinning = false
                val reward = sectors[sectors.size - (degree + 1)]
                Toast.makeText(applicationContext, "You've got $reward points.", Toast.LENGTH_SHORT).show()
                updatePoint(reward.toInt(), points)
            }

            override fun onAnimationRepeat(p0: Animation?) {
            }
        })
        imageView.startAnimation(rotateAnimation)
    }
}
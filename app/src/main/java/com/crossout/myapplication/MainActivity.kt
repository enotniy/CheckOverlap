package com.crossout.myapplication

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.crossout.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->  checkOverlap() }

    }

    fun checkOverlap() {
        binding.run {
            Log.d("DEBUGTAG","""
                blueLabel ${ IntArray(2).apply { blueLabel.getLocationOnScreen(this) }.toList() }
                greenLabel ${ IntArray(2).apply { greenLabel.getLocationOnScreen(this) }.toList()  }
                yellowLabel ${ IntArray(2).apply { yellowLabel.getLocationOnScreen(this) }.toList()  }
            """.trimIndent() )

            if (greenLabel.isOverlap(blueLabel)) {
                Log.d("DEBUGTAG","greenLabel.isOverlap(blueLabel)" )

                binding.greenLabel.visibility = View.GONE
                yellowLabel.requestLayout()
            }

            yellowLabel.post {
                if (yellowLabel.isOverlap(blueLabel)) {
                    Log.d("DEBUGTAG","yellowLabel.isOverlap(blueLabel)" )
                    yellowLabel.isVisible = false
                }
            }
        }
    }


    private fun View.isOverlap(other: View, deltaX: Int = 0, deltaY: Int = 0): Boolean {
        if (!this.isVisible || !other.isVisible) return false
        val thisXY = IntArray(2).apply { getLocationOnScreen(this) }
        val otherXY = IntArray(2).apply {
            other.getLocationOnScreen(this)
            this[0] += deltaX
            this[1] += deltaY
        }
        return thisXY.let { Rect(it[0], it[1], it[0] + width, it[1] + height) }
            .intersect(otherXY.let {
                Rect(it[0], it[1], it[0] + other.width, it[1] + other.height)
            })
    }

}
package com.example.android.threekingdomschess

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.android.threekingdomschess.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

    }

//    fun gameEndDialog(winner: String) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("")
//        builder.setMessage("$winner WON!")
//        builder.setPositiveButton("GG") { dialogInterface: DialogInterface, i: Int ->
//            ChessGame.reset()
//        }
//        builder.setNegativeButton("Dismiss") { dialogInterface: DialogInterface, i: Int ->
//
//        }
//        builder.show()
//    }
}

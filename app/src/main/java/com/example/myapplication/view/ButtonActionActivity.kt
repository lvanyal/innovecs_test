package com.example.myapplication.view

import android.app.NotificationChannel
import android.app.NotificationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.button_action_activity.*
import android.util.DisplayMetrics
import android.widget.Toast
import com.example.myapplication.domain.Action
import android.content.Intent
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat


class ButtonActionActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.myapplication.R.layout.button_action_activity)

        val model = ViewModelProvider(this)[ButtonActionViewModel::class.java]
        model.currentAction.observe(this, Observer {
            when (it) {
                Action.ANIMATION -> animateButton()
                Action.TOAST -> showToast()
                Action.CALL -> call()
                Action.NOTIFICATION -> showNotification()
                Action.NONE -> doNothing()
            }
        })

        btn_action.setOnClickListener { model.onActionButtonClick() }
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("sf", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun doNothing() {
        //todo implement
    }

    private fun showNotification() {
        val callIntent = createCallIntent()
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, callIntent, 0)
        val builder = NotificationCompat.Builder(this, "innovecs")
            .setSmallIcon(com.example.myapplication.R.drawable.ic_launcher_foreground)
            .setContentTitle("Action is Notification!")
            .setChannelId("sf")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(234, builder.build())
        }
    }

    private fun call() {
        val callIntent = createCallIntent()
        startActivity(callIntent)
    }

    private fun createCallIntent() = Intent(Intent.ACTION_DIAL)

    private fun showToast() {
        Toast.makeText(this, "Action is Toast!", Toast.LENGTH_SHORT).show()
    }

    private fun animateButton() {
        btn_action.animate().rotation(360f).start()
    }
}

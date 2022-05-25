package com.example.videoapp.presentation.mainActivity

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.example.videoapp.R
import org.koin.android.ext.android.get
import org.koin.androidx.scope.ScopeActivity


class MainActivity : ScopeActivity() {

    private var airplaneModeChangeReceiver: AirplaneModeChangeReceiver = get()

    override fun onStart() {
        super.onStart()
        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
            registerReceiver(airplaneModeChangeReceiver, it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(airplaneModeChangeReceiver)
    }
}
package com.fury.debuggingtile

import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.service.quicksettings.TileService
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi

fun printLog(tag: String, message: String) {
    Log.d(tag, message)
}


@RequiresApi(Build.VERSION_CODES.N)
class DebuggingTileService : TileService() {

    private val tag = DebuggingTileService::class.java.simpleName
    private val enabled = 1
    private val disabled = 0

    override fun onTileAdded() {
        super.onTileAdded()
        printLog(tag, "OnTileAdded")
    }

    override fun onTileRemoved() {
        super.onTileRemoved()
        printLog(tag, "OnTileRemoved")
    }

    override fun onStartListening() {
        super.onStartListening()
        printLog(tag, "OnTileStartListening")
    }

    override fun onStopListening() {
        super.onStopListening()
        printLog(tag, "OnTileStopListening")
    }


    override fun onClick() {
        super.onClick()
        printLog(tag, "OnTileClick ".plus(qsTile.state))

        val isDeveloperOptionsEnabled = Settings.Global.getInt(
            applicationContext.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
            disabled
        ) == enabled
        if (isDeveloperOptionsEnabled) {
            navigateUsbDebugging()
        }else{
            Toast.makeText(
                applicationContext,
                "Please Turn On Developer Options",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun navigateUsbDebugging() {
        Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }.also {
            startActivityAndCollapse(it)
        }
    }
}
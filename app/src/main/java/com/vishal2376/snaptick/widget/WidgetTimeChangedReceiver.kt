package com.vishal2376.snaptick.widget

import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.updateAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Handles the time change broadcast.
 */
class WidgetTimeChangedReceiver : android.content.BroadcastReceiver() {
		override fun onReceive(context: Context, intent: Intent) {
			if (intent.action == Intent.ACTION_TIME_CHANGED) {
				// Update all instances of the widget.
				try {
					CoroutineScope(Dispatchers.IO).launch {
						SnaptickWidget.updateAll(context)
					}
				} catch (e: Exception) {
					// "Failed to update widget
				}
			}
		}
	}
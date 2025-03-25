package com.vishal2376.snaptick.widget

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceComposable
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.state.GlanceStateDefinition
import com.vishal2376.snaptick.R
import com.vishal2376.snaptick.widget.components.SnaptickWidgetTheme
import com.vishal2376.snaptick.widget.components.WidgetTasks
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

val parameterTaskId = ActionParameters.Key<Int>("task_id")

object SnaptickWidget : GlanceAppWidget() {

	override val stateDefinition: GlanceStateDefinition<SnaptickWidgetState>
		get() = SnaptickWidgetStateDefinition

	override suspend fun provideGlance(context: Context, id: GlanceId) {

		provideContent {
			Content()
		}
	}

	@GlanceComposable
	@Composable
	private fun Content() {
		val state = currentState<SnaptickWidgetState>()

		val context = LocalContext.current

		// Observe system time format changes
		val is24HourFormat = DateFormat.is24HourFormat(context)

		val tasks by remember(state) {
			derivedStateOf{ state.tasks }
		}

		SnaptickWidgetTheme {
			WidgetTasks(
				tasks = tasks,
				is24HourFormat = is24HourFormat,
				onTaskClick = { taskId ->
					actionRunCallback<OnTaskClickedCallback>(
						parameters = actionParametersOf(parameterTaskId to taskId)
					)
				},
				modifier = GlanceModifier
					.appWidgetBackground()
					.fillMaxSize()
					.background(ImageProvider(R.drawable.bg_round_primary))
					.padding(16.dp)
			)
		}
	}
}

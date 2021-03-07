package com.eltonkola.komposekonfetti

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import nl.dionsegijn.konfetti.ParticleSystem

class TimerIntegration {
    private var previousTime: Long = -1L

    fun reset() {
        previousTime = -1L
    }

    fun getDeltaTime(): Float {
        if (previousTime == -1L) previousTime = System.nanoTime()

        val currentTime = System.nanoTime()
        val dt = (currentTime - previousTime) / 1000000f
        previousTime = currentTime
        return dt / 1000
    }

    fun getTotalTimeRunning(startTime: Long): Long {
        val currentTime = System.currentTimeMillis()
        return (currentTime - startTime)
    }
}

@ExperimentalAnimationApi
@Composable
fun KonfettiKompose(
    modifier : Modifier = Modifier,
    config: ParticleSystem
) {

    val infiniteTransition = rememberInfiniteTransition()

    val value by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 100),
            repeatMode = RepeatMode.Reverse
        )
    )

    val timer by remember { mutableStateOf(TimerIntegration()) }

    Canvas(modifier = modifier

    ) {

        drawRect(
            Color.White,
            topLeft = Offset(0f, 0f),
            size = Size(value, value)
        )

        val deltaTime = timer.getDeltaTime()
        config.systems.forEach { particleSystem->

            val totalTimeRunning =
                timer.getTotalTimeRunning(particleSystem.renderSystem.createdAt)
            if (totalTimeRunning >= particleSystem.getDelay()) {
                particleSystem.renderSystem.render(this@Canvas, deltaTime)
            }

            if (particleSystem.doneEmitting()) {
                config.systems.remove(particleSystem)
            }
        }

        if (config.systems.isEmpty()) {
            timer.reset()
        }

    }

}

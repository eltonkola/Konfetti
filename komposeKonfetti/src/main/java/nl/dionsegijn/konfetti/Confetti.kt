package nl.dionsegijn.konfetti

import android.content.res.Resources
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.SizeZ
import nl.dionsegijn.konfetti.models.Vector
import kotlin.math.abs
import kotlin.random.Random

class Confetti(
    var location: Vector,
    val color: Color,
    val size: SizeZ,
    val shape: Shape,
    var lifespan: Long = -1L,
    val fadeOut: Boolean = true,
    private var acceleration: Vector = Vector(0f, 0f),
    var velocity: Vector = Vector(),
    val rotate: Boolean = true,
    val accelerate: Boolean = true,
    val maxAcceleration: Float = -1f,
    val rotationSpeedMultiplier: Float = 1f
) {

    private val mass = size.mass
    private var width = size.sizeInPx
    private val paint: Paint = Paint()

    private var rotationSpeed = 0f
    private var rotation = 0f
    private var rotationWidth = width

    // Expected frame rate
    private var speedF = 60f

    private var alpha: Int = 255

    init {
        val minRotationSpeed = 0.29f * Resources.getSystem().displayMetrics.density
        val maxRotationSpeed = minRotationSpeed * 3
        if (rotate) {
            rotationSpeed = (maxRotationSpeed * Random.nextFloat() + minRotationSpeed) * rotationSpeedMultiplier
        }
        paint.color = color
    }

    private fun getSize(): Float = width

    fun isDead(): Boolean = alpha <= 0

    fun applyForce(force: Vector) {
        acceleration.addScaled(force, 1f / mass)
    }

    fun render(drawScope: DrawScope, deltaTime: Float) {
        update(deltaTime)
        display(drawScope)
    }

    private fun update(deltaTime: Float) {
        if (accelerate && (acceleration.y < maxAcceleration || maxAcceleration == -1f)) {
            velocity.add(acceleration)
        }

        location.addScaled(velocity, deltaTime * speedF)

        if (lifespan <= 0) updateAlpha(deltaTime)
        else lifespan -= (deltaTime * 1000).toLong()

        val rSpeed = (rotationSpeed * deltaTime) * speedF
        rotation += rSpeed
        if (rotation >= 360) rotation = 0f

        rotationWidth -= rSpeed
        if (rotationWidth < 0) rotationWidth = width
    }

    private fun updateAlpha(deltaTime: Float) {
        alpha = if (fadeOut) {
            val interval = 5 * deltaTime * speedF
            (alpha - interval.toInt()).coerceAtLeast(0)
        } else {
            0
        }
    }

    private fun display(drawScope: DrawScope) {
        // if the particle is outside the bottom of the view the lifetime is over.
        if (location.y > drawScope.size.height) {
            lifespan = 0
            return
        }

        // Do not draw the particle if it's outside the canvas view
        if (location.x > drawScope.size.width || location.x + getSize() < 0 || location.y + getSize() < 0) {
            return
        }

        paint.color = color.copy(alpha = alpha.toComposeAlpha())

        val scaleX = abs(rotationWidth / width - 0.5f) * 2
        val centerX = scaleX * width / 2

        drawScope.drawContext.canvas.let{ canvas ->
            canvas.save()
            canvas.translate(location.x - centerX, location.y)
            canvas.rotate(rotation)
            canvas.scale(scaleX, 1f)

            shape.draw(drawScope, paint, width)

            canvas.restore()
        }

    }
}

fun Int.toComposeAlpha() : Float {
    return (this.toFloat() / 255)
}

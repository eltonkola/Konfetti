package com.eltonkola.konfettidemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eltonkola.komposekonfetti.KonfettiKompose
import com.eltonkola.konfettidemo.ui.theme.KonfettiTheme
import nl.dionsegijn.konfetti.ParticleSystem
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.SizeZ

class MainActivity : AppCompatActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KonfettiTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Demo()
                }
            }
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun Demo() {

    var confettiConfig by remember { mutableStateOf(ParticleSystem()) }

    Column(modifier = Modifier.fillMaxSize()){

        Button(onClick = {
                confettiConfig = ParticleSystem()
                    .addColors(listOf(Color.Yellow, Color.Green, Color.Magenta))
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 2f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.Square, Shape.Circle)
                    .addSizes(SizeZ(22.dp))
                    .setPosition(-50f, 1000 + 50f, -50f, -50f)
                    .streamFor(300, 5000L)
        }) {
            Text(text = "From top")
        }

        Button(onClick = {
            confettiConfig = ParticleSystem()
                .addColors(listOf(Color.Yellow, Color.Green, Color.Magenta))
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.Square, Shape.Circle, Shape.Rectangle(0.5f))
                .addSizes(SizeZ(18.dp, 6f), SizeZ(22.dp))
                .setPosition(500f, 500f)
                .burst(500)
        }) {
            Text(text = "Konfetti 2!")
        }

        //KonfettiKompose(showConfetti)

        KonfettiKompose(
            config = confettiConfig
        )


    }


}


@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KonfettiTheme {
        Demo()
    }
}
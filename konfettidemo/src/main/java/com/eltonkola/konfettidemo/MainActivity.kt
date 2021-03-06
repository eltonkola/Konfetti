package com.eltonkola.konfettidemo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import com.eltonkola.komposekonfetti.KonfettiKompose
import com.eltonkola.konfettidemo.ui.theme.KonfettiTheme

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

    var showConfetti by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()){

        KonfettiKompose(showConfetti)

        Button(onClick = {
            showConfetti = !showConfetti
        }) {
            Text(text = "Konfetti: $showConfetti!")
        }

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
package com.eunicehong.readkotlincoroutine

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eunicehong.readkotlincoroutine.chapter03.Chapter03Activity
import com.eunicehong.readkotlincoroutine.ui.theme.ReadKotlinCoroutineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadKotlinCoroutineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                    )
                    Button(
                        modifier = Modifier.padding(innerPadding),
                        onClick = {
                            val chapter03Intent = Intent(this@MainActivity, Chapter03Activity::class.java)
                            startActivity(chapter03Intent)
                        },
                    ) {
                        Text("Chapter03Activity")
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ReadKotlinCoroutineTheme {
        Greeting("Android")
    }
}

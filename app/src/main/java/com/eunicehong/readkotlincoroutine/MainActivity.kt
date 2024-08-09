package com.eunicehong.readkotlincoroutine

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eunicehong.readkotlincoroutine.chapter03.Chapter03Activity
import com.eunicehong.readkotlincoroutine.ui.theme.Purple80
import com.eunicehong.readkotlincoroutine.ui.theme.ReadKotlinCoroutineTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val chapterActivityList =
        listOf(
            Chapter03Activity::class.java,
        )

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadKotlinCoroutineTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            modifier =
                                Modifier
                                    .background(color = Purple80),
                            title = { Text("코틀린 코루틴의 정석") },
                        )
                    },
                ) { innerPadding ->
                    LazyColumn(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        items(chapterActivityList.size) { index ->
                            Button(
                                onClick = {
                                    val chapter03Intent =
                                        Intent(this@MainActivity, Chapter03Activity::class.java)
                                    startActivity(chapter03Intent)
                                },
                            ) {
                                Text(
                                    String.format(
                                        locale = Locale.ROOT,
                                        "Chapter %02d 예제 보기",
                                        index + 3,
                                    ),
                                )
                            }
                        }
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
private fun GreetingPreview() {
    ReadKotlinCoroutineTheme {
        Greeting("Android")
    }
}

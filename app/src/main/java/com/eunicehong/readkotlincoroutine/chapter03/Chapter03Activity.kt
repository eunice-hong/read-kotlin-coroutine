@file:OptIn(ExperimentalMaterial3Api::class)

package com.eunicehong.readkotlincoroutine.chapter03

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.eunicehong.readkotlincoroutine.ui.ChapterContent
import com.eunicehong.readkotlincoroutine.ui.theme.Purple80
import com.eunicehong.readkotlincoroutine.ui.theme.ReadKotlinCoroutineTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * 1부터 100_000_000까지의 합을 구할 때 사용합니다.
 */
private const val BIG_NUMBER = 100_000_000

/**
 * 결과 출력에 사용할 접두사입니다.
 */
private const val PREFIX_RESULT = "Result:\t"

/**
 * 처리 시간 출력에 사용할 접두사 입니다.
 */
private const val PREFIX_PROCESSING_TIME = "Processing Time:\t"

/**
 * Chapter 03: CoroutineDispatcher
 *
 * CPU 집약적인 데이터 처리로 인한 애플리케이션 성능 저하를 방지하기 위해 백그라운드 스레드에서 작업을 실행하는 방법을 알아봅니다.
 *
 * @see [processData]
 */
class Chapter03Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ReadKotlinCoroutineTheme {
                var counter by remember { mutableIntStateOf(0) }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            modifier =
                                Modifier
                                    .background(color = Purple80),
                            title = { Text("Chapter 03") },
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                counter++
                            },
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    },
                ) { innerPadding ->
                    ChapterContent(
                        situation =
                            "한 데이터 분석 애플리케이션에서 사용자로부터 입력된 대량의 데이터를 처리해야 하는 " +
                                "기능이 있었습니다. 초기 구현에서는 이 데이터 처리 작업이 메인 스레드에서 " +
                                "동기적으로 수행되었습니다. 결과적으로, 데이터 처리 중 애플리케이션이 " +
                                "느려지고, 다른 작업을 수행할 수 없는 상황이 발생했습니다.",
                        solution =
                            "이 문제를 해결하기 위해 Dispatchers.Default를 사용하여 " +
                                "CPU 집약적인 데이터 처리를 백그라운드 스레드에서 수행하도록 " +
                                "코드를 리팩토링했습니다. 이 디스패처는 기본적으로 " +
                                "여러 코어를 활용하여 CPU 집약적인 작업을 병렬로 처리하기 때문에, " +
                                "메인 스레드의 부하를 줄이면서도 효율적인 작업 처리가 가능해졌습니다.",
                        innerPadding = innerPadding,
                    ) {
                        Text(
                            text = "Counter: $counter",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        SumTo100Million(
                            modifier =
                                Modifier
                                    .padding(innerPadding)
                                    .height(500.dp),
                        ) { lifecycleScope.launch { it() } }
                    }
                }
            }
        }
    }
}

/**
 * 1부터 100_000_000까지의 합을 구하는 버튼을 표시합니다.
 */
@Composable
private fun SumTo100Million(
    modifier: Modifier = Modifier,
    launchInScope: (suspend () -> Unit) -> Unit,
) {
    var result by remember { mutableStateOf(PREFIX_RESULT) }
    var timeTaken by remember { mutableStateOf(PREFIX_PROCESSING_TIME) }

    Column(
        modifier =
            modifier
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "버튼을 클릭하여,\n1부터 100_000_000 까지 합을 구하세요.",
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = result,
                style = MaterialTheme.typography.bodyLarge,
            )

            Text(
                text = timeTaken,
                style = MaterialTheme.typography.bodyLarge,
            )
        }

        SumTo100MillionButton(
            buttonText = "Dispatchers.IO",
            onClick = {
                result = PREFIX_RESULT
                timeTaken = PREFIX_PROCESSING_TIME
                launchInScope {
                    processDataCheckingTimeTaken(Dispatchers.IO).let {
                        result = it.first
                        timeTaken = it.second
                    }
                }
            },
        )

        SumTo100MillionButton(
            buttonText = "Dispatchers.Default",
            onClick = {
                result = PREFIX_RESULT
                timeTaken = PREFIX_PROCESSING_TIME
                launchInScope {
                    processDataCheckingTimeTaken(Dispatchers.Default).let {
                        result = it.first
                        timeTaken = it.second
                    }
                }
            },
        )

        SumTo100MillionButton(
            buttonText = "Dispatchers.Main",
            onClick = {
                result = PREFIX_RESULT
                timeTaken = PREFIX_PROCESSING_TIME
                launchInScope {
                    processDataCheckingTimeTaken(Dispatchers.Main).let {
                        result = it.first
                        timeTaken = it.second
                    }
                }
            },
        )

        SumTo100MillionButton(
            buttonText = "Dispatchers.Unconfined",
            onClick = {
                result = PREFIX_RESULT
                timeTaken = PREFIX_PROCESSING_TIME
                launchInScope {
                    processDataCheckingTimeTaken(Dispatchers.Unconfined).let {
                        result = it.first
                        timeTaken = it.second
                    }
                }
            },
        )

        SumTo100MillionButton(
            buttonText = "동기 처리",
            onClick = {
                result = PREFIX_RESULT
                timeTaken = PREFIX_PROCESSING_TIME
                launchInScope {
                    processDataCheckingTimeTaken(null).let {
                        result = it.first
                        timeTaken = it.second
                    }
                }
            },
        )
    }
}

/**
 * 1부터 100_000_000까지의 합을 구하는 버튼.
 *
 * @param buttonText 버튼 텍스트
 *
 * @param onClick 버튼 클릭 이벤트
 */
@Composable
private fun SumTo100MillionButton(
    buttonText: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
    ) {
        Text(text = buttonText)
    }
}

/**
 * 1부터 100_000_000까지의 합을 구하며, 처리 시간을 반환합니다.
 *
 * @param context 코루틴 디스패처
 *
 * @return Pair<String, String> 결과 및 처리 시간
 */
private suspend fun processDataCheckingTimeTaken(context: CoroutineContext? = null): Pair<String, String> {
    val data = 1..BIG_NUMBER // 큰 데이터 리스트 생성
    val startTime = System.currentTimeMillis()
    val sum =
        try {
            processData(context = context, data = data) // 백그라운드 스레드에서 작업 실행
        } catch (e: Exception) {
            e.message
        }
    val endTime = System.currentTimeMillis()
    return Pair("$PREFIX_RESULT$sum", "$PREFIX_PROCESSING_TIME${endTime - startTime} ms")
}

/**
 * 데이터를 처리합니다.
 *
 * @param context 코루틴 디스패처
 *
 * @param data 데이터
 *
 * @return Long 데이터 합계
 */
private suspend fun processData(
    context: CoroutineContext? = null,
    data: Iterable<Int>,
): Long =
    if (context == null) {
        data.sumOf { it.toLong() }
    } else {
        withContext(context) {
            data.sumOf { it.toLong() }
        }
    }

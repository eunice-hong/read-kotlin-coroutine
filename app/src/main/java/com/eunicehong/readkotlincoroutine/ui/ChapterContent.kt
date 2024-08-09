package com.eunicehong.readkotlincoroutine.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp

/**
 * 챕터 내용을 표시하는 컴포저입니다.
 *
 * @param situation 상황 설명
 *
 * @param solution 해결책 설명
 *
 * @param innerPadding 내부 패딩
 *
 * @param content 챕터 내용. [ColumnScope]를 사용하여 구성합니다.
 */
@Composable
fun ChapterContent(
    situation: String,
    solution: String,
    innerPadding: PaddingValues,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement =
            Arrangement.spacedBy(
                16.dp,
                Alignment.CenterVertically,
            ),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .shadow(4.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
        ) {
            Text(
                text = "상황:",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = situation,
                style = MaterialTheme.typography.bodyMedium,
            )
            Text(
                text = "해결책:",
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = solution,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        val scrollState = rememberScrollState()
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content,
        )
    }
}

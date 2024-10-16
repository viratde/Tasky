package com.tasky.agenda.presentation.agenda.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.tasky.agenda.presentation.R
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.UiText
import com.tasky.core.presentation.ui.toFormatUiDate
import java.time.LocalDate


@Composable
fun AgendaDateLabel(
    selectedDate: LocalDate,
    modifier: Modifier = Modifier
) {

    val label = remember(selectedDate) {
        when (selectedDate) {
            LocalDate.now() -> UiText.StringResource(R.string.today)
            LocalDate.now().plusDays(1L) -> UiText.StringResource(R.string.tomorrow)
            LocalDate.now().minusDays(1) -> UiText.StringResource(R.string.yesterday)
            else -> UiText.DynamicString(selectedDate.toFormatUiDate())
        }
    }

    Text(
        text = label.asText(),
        style = MaterialTheme.typography.bodyMedium.copy(
            color = TaskyBlack,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = inter
        ),
        modifier = modifier
    )


}

@Preview(
    showBackground = true
)
@Composable
private fun AgendaDateLabelPreview() {
    TaskyTheme {
        AgendaDateLabel(selectedDate = LocalDate.now())
    }
}
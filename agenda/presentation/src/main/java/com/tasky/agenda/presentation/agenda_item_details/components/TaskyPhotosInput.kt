package com.tasky.agenda.presentation.agenda_item_details.components

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowOverflow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tasky.agenda.presentation.R
import com.tasky.agenda.presentation.agenda_item_details.model.AgendaPhoto
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyLightBlue
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.inter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskyPhotosInput(
    photos: List<AgendaPhoto>,
    onAddPhoto: (AgendaPhoto) -> Unit,
    onDeletePhoto: (AgendaPhoto) -> Unit,
    onOpenPhoto: (AgendaPhoto) -> Unit,
    modifier: Modifier = Modifier,
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->

    }

    AnimatedContent(
        targetState = photos.size,
        label = "Empty Photos",
        modifier = modifier
            .background(TaskyLight2)
    ) { size ->
        if (size == 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = TaskyGrey
                    )
                    Text(
                        text = stringResource(id = R.string.add_photos),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TaskyGrey,
                            fontSize = 16.sp,
                            fontFamily = inter,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
            ) {

                Text(
                    text = stringResource(id = R.string.photos),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = TaskyBlack,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = inter,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    overflow = FlowRowOverflow.Clip
                ) {
                    photos.map { photo ->
                        TaskyPhoto(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .border(
                                    2.dp,
                                    TaskyLightBlue,
                                    RoundedCornerShape(6.dp)
                                )
                                .border(
                                    2.dp,
                                    Color.Transparent,
                                    RoundedCornerShape(6.dp)
                                ),
                            photo = photo,
                            onClick = {

                            },
                            enabled = true
                        )
                    }
                }

            }
        }
    }


}

@Composable
private fun TaskyPhoto(
    enabled: Boolean,
    onClick: () -> Unit,
    photo: AgendaPhoto,
    modifier: Modifier = Modifier,
) {
    when (photo) {
        is AgendaPhoto.LocalPhoto -> {
            val bitmap = remember(photo) {
                BitmapFactory.decodeByteArray(photo.photo, 0, photo.photo.size)
            }
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = modifier
                    .clickable(enabled) {
                        onClick()
                    }
            )
        }

        is AgendaPhoto.RemotePhoto -> {
            AsyncImage(
                model = photo.url,
                contentDescription = null,
                modifier = modifier
                    .clickable(
                        enabled
                    ) {
                        onClick()
                    }
            )
        }
    }
}


@Preview
@Composable
private fun TaskyPhotosEmptyPreview() {
    TaskyTheme {
        TaskyPhotosInput(
            photos = listOf(),
            onAddPhoto = {

            },
            onDeletePhoto = {

            },
            onOpenPhoto = {
                
            }
        )
    }
}

@Preview
@Composable
private fun TaskyPhotosNonEmptyPreview() {
    TaskyTheme {
        TaskyPhotosInput(
            photos = listOf(
                AgendaPhoto.RemotePhoto(
                    id = "",
                    url = ""
                ),
                AgendaPhoto.RemotePhoto(
                    id = "",
                    url = ""
                ),
                AgendaPhoto.RemotePhoto(
                    id = "",
                    url = ""
                ),
                AgendaPhoto.RemotePhoto(
                    id = "",
                    url = ""
                ),
                AgendaPhoto.RemotePhoto(
                    id = "",
                    url = ""
                )
            ),
            onAddPhoto = {

            },
            onDeletePhoto = {

            },
            onOpenPhoto = {

            }
        )
    }
}
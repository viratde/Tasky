package com.tasky.agenda.presentation.agenda_item_details.components

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tasky.agenda.presentation.R
import com.tasky.agenda.domain.model.AgendaPhoto
import com.tasky.core.presentation.designsystem.ui.CrossIcon
import com.tasky.core.presentation.designsystem.ui.TaskyBlack
import com.tasky.core.presentation.designsystem.ui.TaskyGrey
import com.tasky.core.presentation.designsystem.ui.TaskyLight2
import com.tasky.core.presentation.designsystem.ui.TaskyLightBlue
import com.tasky.core.presentation.designsystem.ui.TaskyTheme
import com.tasky.core.presentation.designsystem.ui.TaskyWhite
import com.tasky.core.presentation.designsystem.ui.inter
import com.tasky.core.presentation.ui.toByteArray
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskyPhotosInput(
    photos: List<AgendaPhoto>,
    enabled: Boolean,
    onAddPhoto: (photo: AgendaPhoto.LocalPhoto, mimeType: String?) -> Unit,
    onDeletePhoto: (AgendaPhoto) -> Unit,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        coroutineScope.launch {
            uri?.toByteArray(context)?.let { photo ->
                onAddPhoto(
                    AgendaPhoto.LocalPhoto(
                        photo = photo.first,
                        id = UUID.randomUUID().toString()
                    ),
                    photo.second
                )
            }
        }
    }

    var selectedPhoto: AgendaPhoto? by remember(photos) {
        mutableStateOf(null)
    }

    val onOpen = remember(launcher) {
        {
            launcher.launch(PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }


    if (selectedPhoto != null) {
        TaskySelectedPhoto(
            selectedPhoto = selectedPhoto!!,
            onDelete = { onDeletePhoto(selectedPhoto!!) },
            onClose = { selectedPhoto = null }
        )
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
                    .height(100.dp)
                    .clickable(enabled) {
                        onOpen()
                    },
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
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally
                    ),
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
                                selectedPhoto = photo
                            },
                        )
                    }
                    TaskyPhotoAdder(
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
                        enabled = enabled,
                        onClick = onOpen
                    )

                }

            }
        }
    }


}


@Composable
private fun TaskyPhotoAdder(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = null,
        modifier = modifier
            .clickable(enabled) {
                onClick()
            }
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        tint = TaskyGrey,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TaskySelectedPhoto(
    selectedPhoto: AgendaPhoto,
    onDelete: () -> Unit,
    onClose: () -> Unit
) {

    val bottomSheet = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
    )


    ModalBottomSheet(
        onDismissRequest = onClose,
        containerColor = TaskyBlack,
        sheetState = bottomSheet
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    ),
                contentAlignment = Alignment.Center
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {

                    Text(
                        text = stringResource(id = R.string.photo),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TaskyWhite,
                            fontSize = 16.sp,
                            fontFamily = inter,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    IconButton(onClick = onClose) {
                        Icon(
                            imageVector = CrossIcon,
                            contentDescription = null,
                            tint = TaskyWhite
                        )
                    }

                    IconButton(
                        onClick = {
                            onDelete()
                            onClose()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = TaskyWhite
                        )
                    }

                }

            }


            AsyncImage(
                model = when (val photo = selectedPhoto!!) {
                    is AgendaPhoto.LocalPhoto -> photo.photo
                    is AgendaPhoto.RemotePhoto -> photo.url
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 24.dp
                    )
            )


        }

    }
}

@Composable
private fun TaskyPhoto(
    onClick: () -> Unit,
    photo: AgendaPhoto,
    modifier: Modifier = Modifier,
) {
    when (photo) {
        is AgendaPhoto.LocalPhoto -> {
            AsyncImage(
                model = photo.photo,
                contentDescription = null,
                modifier = modifier
                    .clickable {
                        onClick()
                    }
            )
        }

        is AgendaPhoto.RemotePhoto -> {
            AsyncImage(
                model = photo.url,
                contentDescription = null,
                modifier = modifier
                    .clickable {
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
            onAddPhoto = { photo: AgendaPhoto, mimeType: String? ->

            },
            onDeletePhoto = {

            },
            enabled = false
        )
    }
}

@Preview
@Composable
private fun TaskyPhotosNonEmptyPreview() {
    TaskyTheme {
        TaskyPhotosInput(
            photos = List(10) {
                AgendaPhoto.RemotePhoto(
                    id = "",
                    url = ""
                )
            },
            onAddPhoto = { a, b ->

            },
            onDeletePhoto = {

            },
            enabled = true
        )
    }
}
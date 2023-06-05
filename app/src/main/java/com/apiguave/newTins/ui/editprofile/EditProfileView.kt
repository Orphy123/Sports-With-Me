package com.apiguave.newTins.ui.editprofile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.newTins.R
import com.apiguave.newTins.domain.profilecard.entity.CurrentProfile
import com.apiguave.newTins.domain.profile.entity.UserPicture
import com.apiguave.newTins.ui.components.*
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun EditProfileView(
    uiState: EditProfileUiState,
    signOut: () -> Unit,
    addPicture: () -> Unit,
    onSignedOut: () -> Unit,
    onProfileEdited: () -> Unit,
    removePictureAt: (Int) -> Unit,
    updateProfile: (currentProfile: CurrentProfile, bio: String, genderIndex: Int, orientationIndex: Int, pictures: List<UserPicture>) -> Unit,
    action: SharedFlow<EditProfileAction>,
) {

    var showErrorDialog by remember { mutableStateOf(false) }
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var deleteConfirmationPictureIndex by remember { mutableStateOf(-1) }

    var bioText by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue(uiState.currentProfile.bio)) }
    var selectedGenderIndex by rememberSaveable { mutableStateOf(uiState.currentProfile.genderIndex) }
    var selectedOrientationIndex by rememberSaveable { mutableStateOf(uiState.currentProfile.orientationIndex) }

    LaunchedEffect(key1 = Unit, block = {
        action.collect {
            when(it){
                EditProfileAction.ON_SIGNED_OUT -> onSignedOut()
                EditProfileAction.ON_PROFILE_EDITED -> onProfileEdited()
            }
        }
    })

    LaunchedEffect(key1 = uiState.errorMessage, block = {
        if(uiState.errorMessage != null){
            showErrorDialog = true
        }
    })

    if (showDeleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            onConfirm = {
                showDeleteConfirmationDialog = false
                removePictureAt(deleteConfirmationPictureIndex)
            },
            onDismiss = { showDeleteConfirmationDialog = false })
    }

    if(showErrorDialog){
        ErrorDialog(
            errorDescription = uiState.errorMessage,
            onDismissRequest = { showErrorDialog = false },
            onConfirm = { showErrorDialog = false}
        )
    }

    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colors.primary,
            MaterialTheme.colors.secondary
        )
    )



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.edit_profile),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                backgroundColor = MaterialTheme.colors.primary,
                actions = {
                    TextButton(
                        onClick = {
                            updateProfile(
                                uiState.currentProfile,
                                bioText.text,
                                selectedGenderIndex,
                                selectedOrientationIndex,
                                uiState.pictures
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.done),
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            )
        }
    ) { padding ->
        Surface(
            modifier = Modifier.padding(padding),
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                repeat(RowCount) { rowIndex ->
                    PictureGridRow(
                        rowIndex = rowIndex,
                        pictures = uiState.pictures,
                        onAddPicture = addPicture,
                        onAddedPictureClicked = {
                            showDeleteConfirmationDialog = true
                            deleteConfirmationPictureIndex = it
                        }
                    )
                }

                Spacer(Modifier.height(24.dp))

                Column(Modifier.fillMaxWidth()) {
                    SectionTitle(title = stringResource(id = R.string.about_me))
                    OutlinedTextField(
                        value = bioText,
                        onValueChange = { bioText = it },
                        label = { Text(stringResource(id = R.string.write_something_interesting)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(24.dp))

                    SectionTitle(title = stringResource(id = R.string.gender))
                    Box(modifier = Modifier.padding(horizontal = 8.dp)) {
                        HorizontalPicker(
                            id = R.array.genders,
                            selectedIndex = selectedGenderIndex,
                            onOptionClick = { selectedGenderIndex = it }
                        )
                    }

                    Spacer(Modifier.height(24.dp))

                    SectionTitle(title = stringResource(id = R.string.i_am_interested_in))
                    HorizontalPicker(
                        id = R.array.interests,
                        selectedIndex = selectedOrientationIndex,
                        onOptionClick = { selectedOrientationIndex = it }
                    )

                    Spacer(Modifier.height(24.dp))

                    SectionTitle(title = stringResource(id = R.string.personal_information))
                    FormDivider()
                    TextRow(title = stringResource(id = R.string.name), text = uiState.currentProfile.name)
                    FormDivider()
                    TextRow(title = stringResource(id = R.string.birth_date), text = uiState.currentProfile.birthDate)
                    FormDivider()

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp)),
                        onClick = signOut,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colors.onError,
                            backgroundColor = MaterialTheme.colors.error
                        ),
                        border = BorderStroke(1.dp, MaterialTheme.colors.onError)
                    ) {
                        Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(
                            stringResource(id = R.string.sign_out),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }


    if(uiState.isLoading){
        LoadingView()
    }
}}


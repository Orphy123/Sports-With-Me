package com.apiguave.newTins.ui.login

import android.net.Uri
import com.apiguave.newTins.R



import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import com.apiguave.newTins.ui.components.AnimatedLogo
import com.apiguave.newTins.ui.theme.Orange
import com.apiguave.newTins.ui.theme.Pink
import com.apiguave.newTins.ui.theme.Purple
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun LoginView(
    signInClient: GoogleSignInClient,
    uiState: LoginViewState,
    onNavigateToSignUp: () -> Unit,
    onNavigateToHome: () -> Unit,
    onSignIn: (ActivityResult) -> Unit,
) {
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = onSignIn
    )

    LaunchedEffect(key1 = uiState, block = {
        if (uiState.isUserSignedIn) {
            onNavigateToHome()
        }
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(factory = { context ->
            FullScreenVideoView(context).apply {
                setVideoURI(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.vid))
                start()
                setOnPreparedListener { mp -> mp.isLooping = true }
            }
        }, modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()

                .background(Color.White.copy(alpha = 0.1f)), // Set a semi-transparent black background
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.weight(1.0f))

            AnimatedLogo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                isAnimating = uiState.isLoading
            )

            Column(modifier = Modifier.weight(1.0f)) {
                Spacer(modifier = Modifier.weight(1f))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .alpha(if (uiState.isLoading) 0f else 1f),
                    onClick = onNavigateToSignUp,
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        top = 12.dp,
                        end = 20.dp,
                        bottom = 12.dp
                    ),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text(
                            stringResource(id = R.string.create_account).uppercase(),
                            color = Purple // Updated color for a catchy look
                        )
                    }
                }
            }

        Spacer(Modifier.height(6.dp))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .alpha(if (uiState.isLoading) 0f else 1f),
            onClick = {
                startForResult.launch(signInClient.signInIntent)
            },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            ),
            border = BorderStroke(2.dp, Color.White),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent)
        ) {
            Box(Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier.align(Alignment.CenterStart),
                    painter = painterResource(id = R.drawable.google_icon),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    text = stringResource(id = R.string.sign_in_with_google).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold // Added bold weight for a catchy look
                )
            }
        }
        Spacer(modifier = Modifier.height(44.dp))
    }
}}



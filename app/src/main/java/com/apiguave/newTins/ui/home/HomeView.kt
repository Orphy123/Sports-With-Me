package com.apiguave.newTins.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.apiguave.newTins.R
import com.apiguave.newTins.domain.profile.entity.CreateUserProfile
import com.apiguave.newTins.domain.profilecard.entity.CurrentProfile
import com.apiguave.newTins.domain.profilecard.entity.NewMatch
import com.apiguave.newTins.domain.profilecard.entity.Profile
import com.apiguave.newTins.extensions.allowProfileGeneration
import com.apiguave.newTins.extensions.getRandomProfile
import com.apiguave.newTins.ui.components.*
import com.apiguave.newTins.ui.theme.Green1
import com.apiguave.newTins.ui.theme.Green2
import com.apiguave.newTins.ui.theme.Orange
import com.apiguave.newTins.ui.theme.Pink
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharedFlow


@Composable
fun HomeView(
    uiState: HomeUiState,
    navigateToEditProfile: () -> Unit,
    navigateToMatchList: () -> Unit,
    navigateToNewMatch: () -> Unit,
    setLoading: () -> Unit,
    removeLastProfile: () -> Unit,
    fetchProfiles: () -> Unit,
    swipeUser: (Profile, Boolean) -> Unit,
    createProfiles: (List<CreateUserProfile>) -> Unit,
    newMatch: SharedFlow<NewMatch>,
    currentProfile: SharedFlow<CurrentProfile>,
    setMatch: (NewMatch) -> Unit,
    setCurrentProfile: (CurrentProfile) -> Unit
) {
//    I changed this from FALSE
    var showGenerateProfilesDialog by remember { mutableStateOf(true) }



    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit, block = {
        newMatch.collect {
            setMatch(it)
            navigateToNewMatch()
        }
    })
    LaunchedEffect(key1 = Unit, block = {
        currentProfile.collect{
            setCurrentProfile(it)
        }
    })



    val gradientColors = listOf(Color(0xFF98DB4A), Color(0xFFC29C2B))

    Scaffold(
        backgroundColor = Color(0xFFF0F0F0),
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.primary,
                elevation = 4.dp,
                title = { Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(R.drawable.tinder_logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(50.dp)
                    )
                }
                },
                navigationIcon = {
                    IconButton(onClick = navigateToEditProfile) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "Edit Profile")
                    }
                },
                actions = {
                    IconButton(onClick = navigateToMatchList) {
                        Icon(Icons.Default.MailOutline, contentDescription = "Match List")
                    }
                }
            )
        },
        floatingActionButton = {
            if (allowProfileGeneration) {
                FloatingActionButton(
                    onClick = { showGenerateProfilesDialog = true }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Generate Profiles")
                }
            } else Unit
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.tin),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )


            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when(uiState){
                    is HomeUiState.Error-> {
                        Spacer(Modifier.weight(1f))
                        Text(modifier = Modifier.padding(horizontal = 8.dp),
                            text = uiState.message ?: "", color = Color.Gray, fontSize = 16.sp, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(12.dp))
                        GradientButton(onClick = {
                            scope.launch {
                                delay(200)
                                fetchProfiles()
                            }
                        }) {
                            Text(stringResource(id = R.string.retry))
                        }
                        Spacer(Modifier.weight(1f))
                    }
                    HomeUiState.Loading -> {
                        Spacer(Modifier.weight(1f))
                        AnimatedGradientLogo(Modifier.fillMaxWidth(.4f))
                        Spacer(Modifier.weight(1f))
                    }
                    is HomeUiState.Success -> {

                        Spacer(Modifier.weight(1f))
                        Box(Modifier.padding(horizontal = 12.dp)) {
                            Text(
                                text = stringResource(id = R.string.no_more_profiles),
                                color = Color.Gray,
                                fontSize = 20.sp
                            )
                            val localDensity = LocalDensity.current
                            var buttonRowHeightDp by remember { mutableStateOf(0.dp) }

                            val swipeStates = uiState.profileList.map { rememberSwipeableCardState() }
                            uiState.profileList.forEachIndexed { index, profile ->
                                ProfileCardView(profile,
                                    modifier = Modifier.swipableCard(
                                        state = swipeStates[index],
                                        onSwiped = {
                                            swipeUser(
                                                profile,
                                                it == SwipingDirection.Right
                                            )
                                            removeLastProfile()
                                        }
                                    ),
                                    contentModifier = Modifier.padding(bottom = buttonRowHeightDp.plus(8.dp))
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.BottomCenter)
                                    .padding(vertical = 10.dp)
                                    .onGloballyPositioned { coordinates ->
                                        buttonRowHeightDp =
                                            with(localDensity) { coordinates.size.height.toDp() }
                                    },
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Spacer(Modifier.weight(1f))
                                RoundGradientButton(
                                    onClick = {
                                        scope.launch {
                                            swipeUser(uiState.profileList.last(), false)
                                            swipeStates.last().swipe(SwipingDirection.Left)
                                            removeLastProfile()
                                        }
                                    },
                                    enabled = swipeStates.isNotEmpty(),
                                    imageVector = Icons.Filled.Close, color1 = Pink, color2 = Orange
                                )
                                Spacer(Modifier.weight(.5f))
                                RoundGradientButton(
                                    onClick = {
                                        scope.launch {
                                            swipeUser(uiState.profileList.last(), true)
                                            swipeStates.last().swipe(SwipingDirection.Right)
                                            removeLastProfile()
                                        }
                                    },
                                    enabled = swipeStates.isNotEmpty(),
                                    imageVector = Icons.Filled.Favorite,
                                    color1 = Green1,
                                    color2 = Green2
                                )

                                Spacer(Modifier.weight(1f))
                            }
                        }

                        Spacer(Modifier.weight(1f))
                    }
                    else -> {}
                }
            }

            if (showGenerateProfilesDialog) {
                val context = LocalContext.current
                GenerateProfilesDialog(
                    onDismissRequest = { showGenerateProfilesDialog = false },
                    onGenerate = { profileCount ->
                        showGenerateProfilesDialog = false
                        scope.launch(Dispatchers.Main) {
                            setLoading()
                            val profiles = withContext(Dispatchers.IO) {
                                (0 until profileCount).map {
                                    async {
                                        getRandomProfile(context)
                                    }
                                }.awaitAll()
                            }
                            createProfiles(profiles)
                        }
                    }
                )
            }


        }
    }}

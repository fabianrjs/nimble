package com.example.nimble.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.nimble.NimbleRoutes
import com.example.nimble.R
import com.example.nimble.model.RequestState
import com.example.nimble.model.Survey
import com.example.ui_components.LoadingComponentCircle
import com.example.ui_components.LoadingComponentRectangle

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    userViewModel: UserViewModel,
    navController: NavController
) {
    val surveys = homeViewModel.surveys.collectAsState()
    val userInfo = userViewModel.userInfo.collectAsState()
    val userInfoState = userViewModel.userInfoState.collectAsState()
    val surveysState = homeViewModel.surveysState.collectAsState()

    val isLoading = remember { mutableStateOf(true)}

    LaunchedEffect(key1 = userInfoState.value, surveysState.value) {
        isLoading.value =
            userInfoState.value is RequestState.Loading || surveysState.value is RequestState.Loading
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurveysPager(
            surveys = surveys,
            isLoading = isLoading.value,
            navController = navController
        )
        DayInformationAndUserAvatarSection(
            userAvatarUrl = userInfo.value?.avatarUrl,
            isLoading = isLoading.value,
            navController = navController
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveysPager(
    surveys: State<List<Survey>?>,
    isLoading: Boolean,
    navController: NavController
) {
    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black),
            contentAlignment = Alignment.BottomStart
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 60.dp)
            ) {
                LoadingComponentRectangle(width = 50)
                LoadingComponentRectangle(modifier = Modifier.padding(top = 18.dp),width = 253)
                LoadingComponentRectangle(modifier = Modifier.padding(top = 10.dp),width = 130)
                LoadingComponentRectangle(modifier = Modifier.padding(top = 18.dp),width = 318)
                LoadingComponentRectangle(modifier = Modifier.padding(top = 10.dp), width = 200)
            }
        }
    } else {
        surveys.value?.let {
            val pagerState = rememberPagerState{ it.size }

            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagerState
            ) {page ->
                SurveyPage(
                    survey = it[page],
                    pagerState = pagerState,
                    navController = navController,
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyPage(
    survey: Survey,
    pagerState: PagerState,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .background(Brush.verticalGradient(listOf(Color.White, Color.Black)))
            .fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = survey.coverImageUrl, contentDescription = "Survey Image",
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(Color.Black.copy(alpha = 0.1f), Color.Black)
                    ),
                    alpha = 0.6f
                )
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
        ) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val dotColor =
                        if (pagerState.currentPage == iteration) Color.White
                        else Color.White.copy(alpha = 0.2f)
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .clip(CircleShape)
                            .background(dotColor)
                            .size(8.dp)
                    )
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                text = survey.title,
                maxLines = 2,
                fontSize = 28.sp,
                color = Color.White,
                fontWeight = FontWeight.W800,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = survey.description,
                    maxLines = 2,
                    fontSize = 17.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W400,
                )

                Button(
                    modifier = Modifier
                        .size(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    contentPadding = PaddingValues(0.dp),
                    onClick = {
                        navController.navigate(route = NimbleRoutes.SurveyDetailScreen.route)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(45.dp),
                        painter = painterResource(id = R.drawable.arrow_right_ic),
                        contentDescription = "Go to Survey Button",
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun DayInformationAndUserAvatarSection(
    userAvatarUrl: String?,
    isLoading: Boolean,
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 60.dp,
                start = 20.dp,
                end = 20.dp
            )
    ) {
        val (date, day, userImage) = createRefs()

        if (isLoading) {
            LoadingComponentRectangle(
                modifier = Modifier
                    .constrainAs(date) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                width = 120,
            )

            LoadingComponentRectangle(
                modifier = Modifier
                    .constrainAs(day) {
                        start.linkTo(parent.start)
                        top.linkTo(date.bottom, 15.dp)
                    },
                width = 100,
            )
            LoadingComponentCircle(
                modifier = Modifier
                    .constrainAs(userImage) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                size = 36.dp
            )
        } else {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(date) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                text = "MONDAY, JUNE 15",
                fontSize = 13.sp,
                color = Color.White
            )

            Text(
                modifier = Modifier
                    .constrainAs(day) {
                        start.linkTo(parent.start)
                        top.linkTo(date.bottom)
                    },
                text = "Today",
                color = Color.White,
                fontSize = 34.sp
            )

            AsyncImage(
                modifier = Modifier
                    .size(36.dp)
                    .constrainAs(userImage) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
                    .clip(CircleShape)
                    .clickable {
                        navController.navigate(NimbleRoutes.LogOutScreen.route)
                    },
                model = userAvatarUrl,
                contentDescription = "User Profile Image"
            )
        }
    }
}

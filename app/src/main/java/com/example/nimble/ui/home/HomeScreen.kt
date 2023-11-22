package com.example.nimble.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.nimble.R
import com.example.nimble.model.Survey
import com.example.nimble.model.UserToken
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.GlobalContext

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    userViewModel: UserViewModel
) {
    val surveys = homeViewModel.surveys.collectAsState()
    val userInfo = userViewModel.userInfo.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SurveysPager(surveys)
        DayInformationAndUserAvatarSection(userAvatarUrl = userInfo.value?.avatarUrl)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveysPager(
    surveys: State<List<Survey>?>
) {
    surveys.value?.let {
        val pagerState = rememberPagerState{ it.size }

        HorizontalPager(
            modifier = Modifier.fillMaxSize(),
            state = pagerState
        ) {page ->
            SurveyUI(survey = it[page], pagerState = pagerState)
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyUI(
    survey: Survey,
    pagerState: PagerState
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
                    onClick = { /*TODO*/ }
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
    userAvatarUrl: String?
) {
    ConstraintLayout(
        modifier = Modifier.padding(
            top = 60.dp,
            start = 20.dp,
            end = 20.dp
        )
    ) {
        val (date, day, userImage) = createRefs()
        val userAvatarPainter = rememberAsyncImagePainter(userAvatarUrl)
        val painter =
            if (userAvatarPainter.state is AsyncImagePainter.State.Success) userAvatarPainter
            else painterResource(id = R.drawable.user_default_image)

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

        Image(
            modifier = Modifier
                .size(36.dp)
                .constrainAs(userImage) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
            painter = painter,
            contentDescription = "User Profile Image"
        )
    }
}

@Preview
@Composable
fun PreviewSurveys() {
    HomeScreen(userViewModel = UserViewModel(UserToken(
        id = "123456",
        accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9",
        tokenType = "Bearer",
        expiresIn = 3600,
        refreshToken = "abcdefgh",
        createdAt = System.currentTimeMillis()
    ), GlobalContext.get().get()))
}

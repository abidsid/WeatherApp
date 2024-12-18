package com.aliabid.weather.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.aliabid.weather.R
import com.aliabid.weather.models.WeatherResponse
import kotlinx.coroutines.flow.collectLatest

@Composable
@ExperimentalMaterial3Api
fun BasicTextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "Search Location"
) {
    Column(modifier = Modifier.padding(8.dp)) {
        val maxLength = 110

        OutlinedTextField(
            modifier = Modifier
                .defaultMinSize(minHeight = 8.dp)
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFF2F2F2),
                focusedBorderColor = Color(0xFFF2F2F2),
                focusedContainerColor = Color(0xFFF2F2F2),
                unfocusedContainerColor = Color(0xFFF2F2F2),
                disabledContainerColor = Color(0xFFF2F2F2)
            ),
            value = value,
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color(0xFF9BA1A8),
                    modifier = Modifier.wrapContentSize(unbounded = true),
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            },
            onValueChange = {
                if (it.length <= maxLength) onValueChange(it)
            },
            shape = RoundedCornerShape(18.dp),
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        painterResource(R.drawable.search_24px),
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            },
        )
    }
}

@Composable()
fun WeatherSearchCard(weather: WeatherResponse, onLocationSelected: () -> Unit) {
    Row(
        Modifier
            .height(150.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFFF2F2F2))
            .padding(16.dp)
            .clickable {
                onLocationSelected()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Text(
                "${weather.location.name}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                "${weather.current.temp_c}°",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Image(
                painter = rememberImagePainter("https:${weather.current.condition.icon}"),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable()
fun WeatherCard(weather: WeatherResponse) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    )
    {
        Image(
            painter = rememberImagePainter("https:${weather.current.condition.icon}"),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(150.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${weather.location.name}",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.padding(16.dp))

            Image(
                painter = painterResource(R.drawable.location),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(32.dp)
            )
        }

        Text(
            "${weather.current.temp_c}\u00B0",
            fontSize = 70.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
        )

        Row(
            Modifier
                .height(100.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xFFF2F2F2)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                Text(
                    "Humidity",
                    fontSize = 18.sp,
                    color = Color(0xFFC4C4C4)
                )

                Text(
                    "${weather.current.humidity}",
                    fontSize = 20.sp,
                    color = Color(0xFF9A9A9A)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            {
                Text(
                    "UV",
                    fontSize = 18.sp,
                    color = Color(0xFFC4C4C4)
                )
                Text(
                    "${weather.current.uv}",
                    fontSize = 20.sp,
                    color = Color(0xFF9A9A9A)
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    "Feels Like",
                    fontSize = 18.sp,
                    color = Color(0xFFC4C4C4)
                )
                Text(
                    "${weather.current.feelslike_c}°",
                    fontSize = 20.sp,
                    color = Color(0xFF9A9A9A)
                )
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = hiltViewModel()) {
    val selectedWeather by viewModel.selectedWeather.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchResults by viewModel.locations.collectAsState()

    val isConnected by viewModel.isConnected.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(isConnected) {
        snapshotFlow { isConnected }.collectLatest { connected ->
            if (!connected) {
                snackbarHostState.showSnackbar("No internet connection")
            }
        }
    }

    Column(modifier = Modifier.fillMaxHeight()) {
        SnackbarHost(hostState = snackbarHostState)

        WeatherContent(
            selectedWeather = selectedWeather,
            searchQuery = searchQuery,
            searchResults = searchResults,
            onSearchQueryChange = { query ->
                viewModel.updateSearchQuery(query)
            },
            onLocationSelected = { current ->
                viewModel.selectWeather(current)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherContent(
    selectedWeather: WeatherResponse?,
    searchQuery: String,
    searchResults: List<WeatherResponse>,
    onSearchQueryChange: (String) -> Unit,
    onLocationSelected: (WeatherResponse) -> Unit
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(32.dp))

        BasicTextInputField(value = searchQuery,
            onValueChange = { query ->
                onSearchQueryChange(query)
            })

        if (searchResults.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(searchResults.size) { index ->
                    WeatherSearchCard(searchResults[index], onLocationSelected = {
                        onLocationSelected(searchResults[index])
                    })
                }
            }

        } else {
            if (selectedWeather == null) {
                Column(
                    Modifier
                        .wrapContentSize()
                        .fillMaxSize()
                        .padding(start = 26.dp, end = 26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "No City Selected",
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = "Please Search For A City",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            } else {
                Column(
                    Modifier
                        .wrapContentSize()
                        .fillMaxSize()
                        .padding(start = 26.dp, end = 26.dp, top = 26.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    WeatherCard(weather = selectedWeather)
                }
            }
        }
    }
}

package com.example.blankprojectapp.ux

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blankprojectapp.data.MessageApiJsonData
import com.example.blankprojectapp.ui.theme.BlankProjectAppTheme


// https://dog.ceo/api/breeds/list/all


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlankProjectAppTheme {
                val viewModel : MessageViewModel = viewModel()

                MessageScreen(viewModel)
                // MessageScreen(state, viewModel)
                /**
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                */

            }
        }
    }
}



@Composable
fun MessageScreen(viewModel: MessageViewModel,
                  modifier: Modifier = Modifier) {
    val state = viewModel.uiState.collectAsState()
    when (state.value) {
        is UiState.Loading -> LoadingScreen()
        is UiState.Error -> ErrorScreen(message = (state.value as UiState.Error).toString())
        is UiState.Success -> MainMessageList(messageApiJsonData = (state.value as UiState.Success).data)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box (modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Box (modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text(text = message)
    }
}

@Composable
fun MainMessageList(messageApiJsonData: MessageApiJsonData, modifier: Modifier = Modifier) {
    Column (modifier = modifier.fillMaxWidth().padding(20.dp)) {
        Spacer(modifier = Modifier.padding(40.dp))
        Text(text = "List of Breeds",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(messageApiJsonData.message.keys.toList()) { breed ->
                val subBreeds = messageApiJsonData.message[breed]
                Text(text = if (subBreeds.isNullOrEmpty())
                                breed
                            else
                                "$breed --> ${subBreeds.joinToString(", ")}",
                    fontSize = 20.sp
                )
                HorizontalDivider(thickness = 5.dp)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BlankProjectAppTheme {
        Greeting("Android")
    }
}

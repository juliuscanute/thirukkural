package com.juliuscanute.tkural

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.juliuscanute.tkural.ui.ThirukuralScreen
import com.juliuscanute.tkural.ui.ThirukuralViewModel
import com.juliuscanute.tkural.ui.theme.TKuralTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: ThirukuralViewModel by viewModels()

    private val activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.extras?.getInt("KURAL_NUMBER")?.let {
                    viewModel.loadKural(it)
                }
            }
        }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TKuralTheme {
                val navigationViewState = viewModel.navigationViewState.collectAsState().value
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "Thirukural")
                        }
                    }, actions = {
                        Image(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .padding(16.dp)
                                .clickable {
                                    activityResultLauncher.launch(SearchActivity.newIntent(this@MainActivity))
                                }
                        )
                    })
                }, bottomBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        navigationViewState?.let {
                            Button(
                                enabled = navigationViewState.previous.enabled,
                                onClick = navigationViewState.previous.onClick,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Previous")
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Button(
                                enabled = navigationViewState.next.enabled,
                                onClick = navigationViewState.next.onClick,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Next")
                            }
                        }
                    }
                }) { innerPadding ->
                    val viewState = viewModel.kuralViewState.collectAsState().value

                    viewState?.let {
                        ThirukuralScreen(innerPadding, viewState = it)
                    }
                }
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
    TKuralTheme {
        Greeting("Android")
    }
}
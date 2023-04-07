package jp.speakbuddy.edisonandroidexercise.ui.fact

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.speakbuddy.edisonandroidexercise.R
import jp.speakbuddy.edisonandroidexercise.network.Status
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.CAT_STRING
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.FACT_STRING
import jp.speakbuddy.edisonandroidexercise.utils.AppConstants.LENGTH

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FactScreen(
    mContext: Context,
    viewModel: FactViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(mContext.getString(R.string.app_name)) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            )
        ) {
            var fact by remember { mutableStateOf("") }
            var length by remember { mutableStateOf(0) }
            var isMultipleCatsTextVisible by remember { mutableStateOf(false) }
            var isLengthTextVisible by remember { mutableStateOf(false) }
            var isLoaderShown by remember { mutableStateOf(false) }

            LaunchedEffect(key1 = "key") {
                viewModel.viewState.collect { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { factData ->
                                fact = factData.fact
                                length = factData.length
                                isMultipleCatsTextVisible = fact.contains(CAT_STRING, true)
                                isLengthTextVisible = fact.length > LENGTH
                            }
                            isLoaderShown = false
                        }
                        Status.ERROR -> {
                            isLoaderShown = false
                            resource.message?.let { error ->
                                fact = error
                            }
                            isMultipleCatsTextVisible = false
                            isLengthTextVisible = false
                        }
                        Status.LOADING -> {
                            isLoaderShown = true
                            isMultipleCatsTextVisible = false
                            isLengthTextVisible = false
                        }
                    }
                }
            }

            AnimatedVisibility(visible = isLoaderShown) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            Text(
                text = FACT_STRING,
                style = MaterialTheme.typography.headlineLarge
            )
            AnimatedVisibility(visible = isMultipleCatsTextVisible) {
                Text(
                    text = mContext.getString(R.string.multiple_cats),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = fact,
                style = MaterialTheme.typography.bodyLarge
            )
            AnimatedVisibility(visible = isLengthTextVisible) {
                Text(
                    text = mContext.getString(R.string.length, length.toString()),
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            val onClick = {
                viewModel.updateFact { print("done") }
            }

            Button(onClick = onClick) {
                Text(text = mContext.getString(R.string.update_fact))
            }
        }
    }
}

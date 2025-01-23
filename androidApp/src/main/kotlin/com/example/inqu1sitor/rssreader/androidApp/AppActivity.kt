package com.example.inqu1sitor.rssreader.androidApp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import com.example.inqu1sitor.rssreader.androidApp.composeui.AppTheme
import com.example.inqu1sitor.rssreader.androidApp.composeui.MainScreen
import com.example.inqu1sitor.rssreader.app.FeedSideEffect
import com.example.inqu1sitor.rssreader.app.FeedStore
import kotlinx.coroutines.flow.filterIsInstance
import org.koin.android.ext.android.inject

class AppActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val store: FeedStore by inject()
                val scaffoldState = rememberScaffoldState()
                val error = store.observeSideEffect()
                    .filterIsInstance<FeedSideEffect.Error>()
                    .collectAsState(null)
                LaunchedEffect(error.value) {
                    error.value?.let {
                        scaffoldState.snackbarHostState.showSnackbar(
                            it.error.message.toString()
                        )
                    }
                }
                Box(
                    Modifier.padding(
                        WindowInsets.systemBars
                            .only(WindowInsetsSides.Start + WindowInsetsSides.End)
                            .asPaddingValues()
                    )
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = { hostState ->
                            SnackbarHost(
                                hostState = hostState,
                                modifier = Modifier.padding(
                                    WindowInsets.systemBars
                                        .only(WindowInsetsSides.Bottom)
                                        .asPaddingValues()
                                )
                            )
                        }
                    ) {
                        Navigator(MainScreen())
                    }
                }
            }
        }
    }
}

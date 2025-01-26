package com.gmjproductions.project

import App
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import model.WordlerTopBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Preview
@Composable
fun TopBarPreview() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            Scaffold(topBar = { WordlerTopBar({}) { } }) {
                Box(Modifier.fillMaxSize()) {

                }

            }


        }
    }

}



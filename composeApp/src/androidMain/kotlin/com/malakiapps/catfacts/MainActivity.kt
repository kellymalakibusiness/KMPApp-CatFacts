package com.malakiapps.catfacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.malakiapps.catfacts.ui.screens.main_screen.FactsTopAppBar
import com.malakiapps.catfacts.ui.theme.CatFactsMaterialTheme

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

@Composable
@Preview
fun TopAppBarPreview(){
    CatFactsMaterialTheme {
        FactsTopAppBar(Modifier)
    }
}
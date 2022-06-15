package com.example.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.basicscodelab.ui.Main
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                Main()
            }
        }
    }

    @Preview(showBackground = true, name = "defaultPreview", widthDp = 320)
    @Composable
    fun DefaultPreview() {
        BasicsCodelabTheme {
            Main()
        }
    }
}

package com.coderroots.deepakcomposeclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.coderroots.deepakcomposeclass.ui.theme.DeepakComposeClassTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirstUIScreen()
        }

    }
}


@Preview(showSystemUi = true)
@Composable
fun FirstUIScreen(){
// fillMaxSize() -> width and height ke liye Match Parent
    // fillMaxWidth -> only for width Match Parent
    //fillMaxHeight -> only for height Match parent
 Box(Modifier.fillMaxSize(),
     contentAlignment = Alignment.Center
     ){
     Text("Hello Deepak Kumar",
         fontSize = 20.sp)
//     Text("Hello Deepak Kumar , I'm from Ludhiana",
//         fontSize = 20.sp)
 }
}

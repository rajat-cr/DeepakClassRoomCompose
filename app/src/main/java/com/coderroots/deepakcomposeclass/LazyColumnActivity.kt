package com.coderroots.deepakcomposeclass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class LazyColumnActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LazyColumnScreen()
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun LazyColumnScreen(){
    val context = LocalContext.current
    val nameList = remember { mutableStateListOf<String>("Deepak Kumar","Abhishek Kumar","Mukesh Kumar","Rajat Singh","Deepika") }

    LazyColumn(Modifier.fillMaxSize().padding(horizontal = 10.dp)) {
        items(nameList.size){index->
            Card(Modifier.fillMaxWidth().padding(top = 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 3.dp
                )
                ) {
                Text(nameList[index], modifier = Modifier.padding(15.dp))
            }
        }
    }
}
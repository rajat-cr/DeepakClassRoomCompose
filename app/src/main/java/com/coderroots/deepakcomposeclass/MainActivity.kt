package com.coderroots.deepakcomposeclass

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.coderroots.deepakcomposeclass.ui.theme.DeepakComposeClassTheme
import androidx.core.content.edit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
          SecondUIScreen()
        }

    }
}


@Preview(showSystemUi = true) // -> Notation user for View the Design in Real time
@Composable   //-> Notation @Composable use for UI content
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


@Preview(showSystemUi = true)
@Composable
fun SecondUIScreen(){

    var name by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Text("Hello Deepak, this is Row",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
            )
        Spacer(Modifier.height(10.dp))
        TextField(
            value = name,
            onValueChange = {
//                println("Get Vlaue form TextField: $it")
                name = it

            },
            placeholder = {
                Text("Enter Your Name")
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp)
        )

        Spacer(Modifier.height(10.dp))
        //ElevatedButton
        // OutlinedButton
        //TextButton
        ElevatedButton(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.purple_200),
                contentColor = colorResource(R.color.black)
            ),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text("Submit Data")
        }


    }


}

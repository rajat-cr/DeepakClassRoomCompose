package com.coderroots.deepakcomposeclass.bottomnavigation

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class BottomNavActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            BottomNavScreen()
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
fun BottomNavScreen(){

    var navController = rememberNavController()
    var selectedIndex by remember { mutableStateOf(0) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Dashboard",
                        fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.holo_purple),
                    titleContentColor = Color.White
                )
            )

        },
        bottomBar = {
            BottomAppBar(Modifier.fillMaxWidth(), containerColor = Color.White){

                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    Icon( if(selectedIndex == 0)
                        Icons.Filled.Home
                                else
                        Icons.Outlined.Home,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp).clickable{
                            selectedIndex = 0
                            navController.navigate("home"){
                                popUpTo(navController.graph.startDestinationId){
                                  saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        } )
                    Icon(if(selectedIndex == 1)
                        Icons.Filled.AccountCircle
                    else
                        Icons.Outlined.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp).clickable{
                            selectedIndex = 1
                            navController.navigate("profile"){
                                popUpTo(navController.graph.startDestinationId){
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        })

                    Icon(if(selectedIndex == 2)
                        Icons.Filled.Settings
                    else
                        Icons.Outlined.Settings,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp).clickable{
                            selectedIndex = 2
                            navController.navigate("setting"){
                                popUpTo(navController.graph.startDestinationId){
                                    saveState = true
                                }
                                restoreState = true
                                launchSingleTop = true
                            }
                        })
                }
            }
        }
    ) { innerPadding->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            composable("home") {
                HomeScreen()
            }
            composable("profile") {
                ProfileScreen()
            }
            composable("setting") {
                SettingScreen()
            }
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    var expend by remember { mutableStateOf(false) }
    var showName by  remember { mutableStateOf("") }
    var list = listOf<String>("Deepak","Mukesh","Abhishek","Rajat Singh")
    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){

        ExposedDropdownMenuBox(
            expanded = expend,
            onExpandedChange = {
                expend = !expend
            }
        ) {
            OutlinedTextField(
                value = showName,
                onValueChange = { },
                singleLine = true,
                readOnly = true,
                label = {
                    Text("Select Name")
                },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expend,
                onDismissRequest = { expend = false}
            ) {
                list.forEach { item->
                    DropdownMenuItem(
                        text = {
                            Text(item)
                        },
                        onClick = {
                            showName = item
                            expend = false
                        }
                    )
                }
            }

        }
    }
}

@SuppressLint("CommitPrefEdits")
@Composable
fun ProfileScreen(){
    var name by remember { mutableStateOf("") }
    var context = LocalContext.current
    val options = listOf("Male","Female", "Other")
    var selectedValue by remember { mutableStateOf(options[0]) }

    Row(Modifier.fillMaxSize(),
       verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        options.forEach { text->
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                RadioButton(
                    selected = (text == selectedValue),
                    onClick = {
                        selectedValue = text
                    }
                )
                Text(text)
            }
        }
    }





}

@Composable
fun SettingScreen(){
    Box(Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center){
        Text("Setting Screen")
    }

}


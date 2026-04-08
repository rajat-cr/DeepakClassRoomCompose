package com.coderroots.deepakcomposeclass.bottomnavigation

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

                    Icon(if(selectedIndex == 3)
                        Icons.Filled.ShoppingCart
                    else
                        Icons.Outlined.ShoppingCart,
                        contentDescription = "",
                        modifier = Modifier.size(30.dp).clickable{
                            selectedIndex = 2
                            navController.navigate("firebase"){
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
            composable("firebase") {
                FirebaseScreen()
            }
        }


    }

}

@Preview(showSystemUi = true)
@Composable
fun FirebaseScreen() {
    var showDialog by remember { mutableStateOf(false) }
    val db = Firebase.firestore

   Box(Modifier.fillMaxSize()){
       FloatingActionButton(
           onClick = {
               showDialog = true
           },
           modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp)
       ) {
           Icon(Icons.Default.Add,
               contentDescription = "")
       }
   }
    if(showDialog){
        FirebaseDialog(
            onDismiss = {showDialog = false}
        )
    }
}

@Composable
fun FirebaseDialog(onDismiss: () -> Unit) {
    val db = Firebase.firestore
    val context = LocalContext.current
    var name by  remember { mutableStateOf("") }
    var rollNo by remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = { onDismiss()},
        content = {
            Box(Modifier.fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(7.dp))){
                Column(Modifier.fillMaxWidth().padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text("ADD Student Detail's")
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        placeholder = {
                            Text("Enter Name")
                        },
                        label = {
                            Text("Enter Name")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = rollNo,
                        onValueChange = {
                            rollNo = it
                        },
                        placeholder = {
                            Text("Enter Roll No.")
                        },
                        label = {
                            Text("Enter Roll No.")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(10.dp))
                    ElevatedButton(
                        onClick = {
                            if(name.isEmpty()){
                                Toast.makeText(context, "Enter Your Name", Toast.LENGTH_SHORT).show()
                            }   else  if(rollNo.isEmpty()){
                            Toast.makeText(context, "Enter Your Roll No.", Toast.LENGTH_SHORT).show()
                        } else {
                            val studentModel = StudentModel()
                                studentModel.name = name
                                studentModel.rollNo = rollNo.toInt()
                            db.collection("UserDetails").add(studentModel).addOnCompleteListener {
                                if(it.isSuccessful){

                                    Toast.makeText(context,"Data Store", Toast.LENGTH_SHORT).show()
                                }else {
                                    Toast.makeText(context,"${it.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }.addOnFailureListener {
                                println("Check Exception Firestore: ${it.message}")
                            }

                                onDismiss()
                            }
                        },

                    ) {
                        Text("ADD DETAIL'S")
                    }
                }

            }
        }
    )
}


data class StudentModel(
    var id: String? =null,
    var name: String? = null,
    var rollNo: Int = 0
)

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

@Preview(showSystemUi = true)
@Composable
fun SettingScreen(){
    val context = LocalContext.current
    val userDatabase = UserDatabase.getInstance(context)
    val userList = userDatabase?.userDao()?.getUsers()?.collectAsState(emptyList())?.value
    var showDialog by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(-1) }


    Box(Modifier.fillMaxSize(),
        ){
        if(userList?.isNotEmpty() == true){
        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 10.dp)) {
            items(userList.size) { index ->
                val model = userList[index]
                Card(
                    Modifier.fillMaxWidth().padding(top = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Row(Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Column(Modifier.fillMaxWidth().weight(1f).padding(10.dp),
                            verticalArrangement = Arrangement.Center
                            ) {
                            Text(model.userName.toString())
                            Text(model.contactNumber.toString())
                        }

                        Icon(Icons.Default.Edit,
                            contentDescription = "",
                            modifier = Modifier.clickable{
                                showDialog = true
                                selectedIndex = index

                            })
                        Spacer(Modifier.height(5.dp))
                        Icon(Icons.Default.Delete,
                            contentDescription = "",
                            modifier = Modifier.clickable{

                            })
                        Spacer(Modifier.width(5.dp))
                    }
                }
            }
        }
        }

        FloatingActionButton(
            onClick = {
                showDialog = true
                selectedIndex = -1
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(10.dp)
        ) {
            Icon(Icons.Default.Add,
                contentDescription = null)
        }

    }

    if(showDialog){
        ShowDialog(
            userList = userList,
            dismiss = {showDialog = false},
            selectedIndex = selectedIndex
        )
    }

}

@Composable
fun ShowDialog(dismiss: () -> Unit, selectedIndex: Int, userList: List<UserEntity>?) {
    var name by  remember { mutableStateOf("") }
    var contact by  remember { mutableStateOf("") }
    val context = LocalContext.current
    val userDatabase = UserDatabase.getInstance(context)
    val scope = rememberCoroutineScope()
    val userDao = userDatabase?.userDao()
      // 0  !=-1
    if(selectedIndex!=-1)
    {
        name = userList!![selectedIndex].userName.toString()
        contact = userList[selectedIndex].contactNumber.toString()
    }


    Dialog(
        onDismissRequest = {
            dismiss()
        },
        content = {
            Box(Modifier.fillMaxWidth().background(color = colorResource(R.color.white))){
                Column(Modifier.fillMaxWidth().padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                    )
                {
                    Text(if(selectedIndex == -1)
                        "Add User"
                    else
                    "Update User"
                    )
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = {
                            name = it
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        singleLine = true,
                        maxLines = 1,
                        label = {
                            Text("Name")
                        }
                    )
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = contact,
                        onValueChange = {
                            contact = it
                        },
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                        singleLine = true,
                        maxLines = 1,
                        label = {
                            Text("Contact")
                        }
                    )
                    Spacer(Modifier.height(10.dp))

                    ElevatedButton(
                        onClick = {
                            if(name.isEmpty()){
                                Toast.makeText(context,"Enter Name", Toast.LENGTH_SHORT).show()
                            }else if(contact.isEmpty()){
                                Toast.makeText(context,"Enter Contact", Toast.LENGTH_SHORT).show()
                            }else {
                                val userEntity = UserEntity(userName = name, contactNumber = contact)
                                if (selectedIndex == -1) {
                                    scope.launch(Dispatchers.IO) {
                                        userDao?.addUser(userEntity)
                                        dismiss()
                                    }
                                    Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show()
                                }else{
                                    userEntity.id = userList?.get(selectedIndex)?.id?:0
                                    scope.launch(Dispatchers.IO) {
                                        userDao?.updateUser(userEntity)
                                        dismiss()
                                    }
                                    Toast.makeText(context, "Data Update", Toast.LENGTH_SHORT).show()

                                }
                            }
                        },
                        shape = RoundedCornerShape(7.dp)
                    ) {
                        Text(
                            if(selectedIndex == -1)
                            "ADD USER"
                        else
                        "UPDATE USER")
                    }
                }
            }
        }
    )
}


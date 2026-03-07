package com.coderroots.deepakcomposeclass

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

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
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedIndex by remember { mutableIntStateOf(-1) }
    val nameList = remember { mutableStateListOf<String>("Deepak Kumar","Abhishek Kumar","Mukesh Kumar","Rajat Singh","Deepika") }

    Box(Modifier.fillMaxSize()) {

        LazyColumn(Modifier.fillMaxSize().padding(horizontal = 10.dp)) {
            items(nameList.size) { index ->
                Card(
                    Modifier.fillMaxWidth().padding(top = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {
                    Row(Modifier.fillMaxWidth().padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(nameList[index])
                        Spacer(Modifier.weight(1f))
                        Icon(Icons.Default.Edit,
                            contentDescription ="edit",
                            modifier = Modifier.clickable{
                                showDialog = true
                                selectedIndex = index
                            })

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
        OpenDialog(
            nameList = nameList,
            onDismiss = { showDialog = false },
            selectedIndex = selectedIndex
        )
    }
}

@Composable
fun OpenDialog(onDismiss: () -> Unit, nameList: SnapshotStateList<String>, selectedIndex: Int) {

    var name by remember { mutableStateOf("") }
    val context = LocalContext.current

    if(selectedIndex!=-1){
        name = nameList[selectedIndex]
    }



    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        content = {
            Column(Modifier.fillMaxWidth().background(color = Color.White, shape = RoundedCornerShape(10.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Spacer(Modifier.height(20.dp))
                Text(
                    if(selectedIndex == -1)
                    "Add Customers"
                       else
                        "Update Customer",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = name,
                    onValueChange = { name = it},
                    placeholder = {
                        Text("Enter Your Name")
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(Modifier.height(10.dp))
                ElevatedButton(
                    onClick = {
                        if(name.isEmpty()){
                            Toast.makeText(context, "Enter Your Name", Toast.LENGTH_SHORT).show()
                        }else{
                            //           0   == -1
                            if(selectedIndex == -1) {
                                nameList.add(name)
                                onDismiss()
                            }else{
                                nameList[selectedIndex] = name
                                onDismiss()
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.purple_200)
                    ),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Text(if(selectedIndex == -1)
                        "Add Customers"
                    else
                        "Update Customer")
                }
                Spacer(Modifier.height(10.dp))
            }
        }
    )
}
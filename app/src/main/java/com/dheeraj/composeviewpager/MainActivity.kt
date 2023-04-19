package com.dheeraj.composeviewpager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dheeraj.composeviewpager.ui.theme.ComposeviewpagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeviewpagerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val fragments = listOf(
                        FragmentData(title = "Chat"),
                        FragmentData(title = "Status"),
                        FragmentData(title = "Calls")
                    )
                    ViewPagerExample(fragments = fragments)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeviewpagerTheme {
        Greeting("Android")
    }
}

// Data class representing the fragments
data class FragmentData(val title: String)
data class ChatItem(val name: String, val message: String, val time: String, val avatar: Int)


val chatItems = listOf(
    ChatItem("John", "Hey, how's it going?", "10:30 AM", R.drawable.avtar),
    ChatItem("Sarah", "What are you up to?", "9:45 AM", R.drawable.avtar),
    ChatItem("Michael", "Got the tickets!", "Yesterday", R.drawable.avtar),
    ChatItem("Olivia", "Sure, let's meet up!", "3/4/23", R.drawable.avtar),
    ChatItem("David", "Missed call", "3/3/23", R.drawable.avtar),
    // Add more chat items as needed
)

@Composable
fun ViewPagerExample(fragments: List<FragmentData>) {
    val selectedTabIndex = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                //backgroundColor = /* Set desired background color */,
                //contentColor = /* Set desired text color */
            ) {
                fragments.forEachIndexed { index, fragment ->
                    Tab(
                        text = { Text(text = fragment.title) },
                        selected = selectedTabIndex.value == index,
                        onClick = { selectedTabIndex.value = index }
                    )
                }
            }
        }
    ) {
        LazyColumn {
            itemsIndexed(fragments) { index, fragment ->
                if (selectedTabIndex.value == index) {
                    FragmentContent(index)
                }
            }
        }
    }
}

@Composable
fun FragmentContent(index: Int) {
    // Content of the fragment
    ChatList()
}

@Preview
@Composable
fun ViewPagerExamplePreview() {
    val fragments = listOf(
        FragmentData(title = "Fragment 1"),
        FragmentData(title = "Fragment 2"),
        FragmentData(title = "Fragment 3")
    )
    ViewPagerExample(fragments = fragments)
}



@Composable
fun ChatList() {
    Column(modifier = Modifier.fillMaxSize()) {
        chatItems.forEach { chatItem ->
            ChatItemRow(chatItem)
            Divider(color = Color.LightGray, thickness = 0.5.dp)
        }
    }
}

@Composable
fun ChatItemRow(chatItem: ChatItem) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click on chat item */ }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = chatItem.avatar),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = chatItem.name,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold
                )
                Text(text = chatItem.message, style = MaterialTheme.typography.body1)
            }
            Text(text = chatItem.time, style = MaterialTheme.typography.body2)
        }
    }
}
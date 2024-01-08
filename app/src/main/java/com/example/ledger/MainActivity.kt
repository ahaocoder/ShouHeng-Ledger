package com.example.ledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ledger.data.database.LedgerDatabase
import com.example.ledger.data.database.LedgerDatabase.Companion.getDatabase
import com.example.ledger.ui.theme.LedgerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LedgerTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainPage(getDatabase(applicationContext))
                }
            }
        }
    }
}

@Composable
fun MainPage(db: LedgerDatabase){
    Routes(db)
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    LedgerTheme {
//        Routes()
//    }
//}
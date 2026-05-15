package com.rafaelleal.kotlinandroidfundamentals

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.rafaelleal.kotlinandroidfundamentals.architectures.views.MVIActivity
import com.rafaelleal.kotlinandroidfundamentals.architectures.views.MVVMActivity
import com.rafaelleal.kotlinandroidfundamentals.ui.theme.KotlinAndroidFundamentalsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinAndroidFundamentalsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavigationList(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun NavigationList(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            MVVMNavigateButon()
            MVINavigateButon()
        }
    }
}

@Composable
fun MVVMNavigateButon() {
    val context = LocalContext.current
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = {
            val intent = Intent(context, MVVMActivity::class.java)
            context.startActivity(intent)
        }
    ) {
        Text(text = "Ir para MVVM")
    }
}
@Composable
fun MVINavigateButon() {
    val context = LocalContext.current
    Button(
        modifier = Modifier.padding(16.dp),
        onClick = {
            val intent = Intent(context, MVIActivity::class.java)
            context.startActivity(intent)
        }
    ) {
        Text(text = "Ir para MVI")
    }
}
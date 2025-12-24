package kr.co.roysoft.biblereadingchecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kr.co.roysoft.biblereadingchecker.ui.navigation.NavGraph
import kr.co.roysoft.biblereadingchecker.ui.theme.BibleReadingCheckerTheme
import kr.co.roysoft.biblereadingchecker.ui.viewmodel.ReadingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val application = application as BibleReadingApplication
        val viewModel = ReadingViewModel(application.repository, application.preferences)
        
        setContent {
            BibleReadingCheckerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavGraph(
                        navController = navController,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
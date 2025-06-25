package uk.co.weightwars.challenges

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent

@Composable
fun ChallengeListScreen() {
    Column {
        Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            Text("No Sugar")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Card(modifier = Modifier.fillMaxWidth().height(200.dp)) {
            Text("No Sugar Hardcore")
        }
    }
}
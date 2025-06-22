package uk.co.weightwars.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.time.LocalDate

@Composable
fun OverviewScreen() {
    val now = remember { mutableStateOf(LocalDate.now()) }
    val days = mutableListOf<LocalDate>()


//    LifecycleResumeEffect(Unit) {
//
//        onPauseOrDispose {
//            // do any needed clean up here
//        }
//    }
//    Column {
//        Button(onClick = {
//
//            for(x in 0..3) {
//                val date = LocalDate.now().plusDays(x.toLong() + 1)
//                days.add(date)
//            }
//        }) {
//            Text("do somethign")
//        }
//    }
}
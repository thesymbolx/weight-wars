package uk.co.weightwars.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    userCreated: () -> Unit
) {
    LaunchedEffect(userViewModel.userCreated) {
        if(userViewModel.userCreated) {
            userViewModel.userCreated()
            userCreated()
        }
    }

    CurrentUserName(onSaveUserName = userViewModel::saveCurrentUserName)
}


@Composable
private fun CurrentUserName(
    onSaveUserName: (String) -> Unit
) {
    var userName by remember {
        mutableStateOf("")
    }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Enter your name") }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = userName.isNotEmpty(),
            onClick = { onSaveUserName(userName) }
        ) {
            Text("Save")
        }
    }
}
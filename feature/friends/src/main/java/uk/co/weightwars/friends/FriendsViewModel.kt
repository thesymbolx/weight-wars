package uk.co.weightwars.friends

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import uk.co.weightwars.data.repository.FriendsRepo
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepo: FriendsRepo
) : ViewModel() {
    // TODO: Add state and logic for FriendsScreen
} 
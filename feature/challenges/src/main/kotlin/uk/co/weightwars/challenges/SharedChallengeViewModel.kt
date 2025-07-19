package uk.co.weightwars.challenges

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SharedChallengeViewModel @Inject constructor() : ViewModel() {
    private var challengeId: Long? = null

    fun getChallenge(): Long? {
        val challenge = this.challengeId
        this.challengeId = null
        return challenge
    }

    fun setChallenge(challengeId: Long) {
        this.challengeId = challengeId
    }
}

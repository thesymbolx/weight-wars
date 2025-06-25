package uk.co.weightwars.overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.co.weightwars.data.database.dao.ChallengeCategoryDao
import uk.co.weightwars.data.database.entities.ChallengeCategory
import javax.inject.Inject

data class OverviewUiState(
    val category: List<ChallengeCategory> = emptyList()
)

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val challengeCategoryDao: ChallengeCategoryDao
) : ViewModel() {
    var uiState by mutableStateOf(OverviewUiState())

    fun getCategories() = viewModelScope.launch {
        val i = challengeCategoryDao.getAll()

        uiState.copy(
            category = i
        )
    }
}
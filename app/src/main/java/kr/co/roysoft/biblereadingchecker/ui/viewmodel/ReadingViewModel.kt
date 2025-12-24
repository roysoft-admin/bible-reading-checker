package kr.co.roysoft.biblereadingchecker.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kr.co.roysoft.biblereadingchecker.data.model.ReadingDay
import kr.co.roysoft.biblereadingchecker.data.model.ReadingPlanType
import kr.co.roysoft.biblereadingchecker.data.preferences.AppPreferences
import kr.co.roysoft.biblereadingchecker.data.repository.ReadingRepository

data class ReadingUiState(
    val selectedPlan: String? = null,
    val todayReading: ReadingDay? = null,
    val allReadings: List<ReadingDay> = emptyList(),
    val completedCount: Int = 0,
    val totalDays: Int = 365,
    val currentStreak: Int = 0,
    val isLoading: Boolean = false,
    val isPremium: Boolean = false,
    val reminderEnabled: Boolean = false,
    val reminderTime: Pair<Int, Int> = 8 to 0
)

class ReadingViewModel(
    private val repository: ReadingRepository,
    private val preferences: AppPreferences
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ReadingUiState())
    val uiState: StateFlow<ReadingUiState> = _uiState.asStateFlow()
    
    private var currentPlanType: String? = null
    
    init {
        viewModelScope.launch {
            combine(
                preferences.selectedPlan,
                preferences.isPremium,
                preferences.reminderEnabled,
                preferences.reminderTime
            ) { plan, premium, reminderEnabled, reminderTime ->
                Triple(plan, premium, Pair(reminderEnabled, reminderTime))
            }.collect { (plan, premium, reminderPair) ->
                val selectedPlan = plan ?: ""
                val (reminderEnabled, reminderTime) = reminderPair
                
                _uiState.update { it.copy(
                    selectedPlan = selectedPlan,
                    isPremium = premium,
                    reminderEnabled = reminderEnabled,
                    reminderTime = reminderTime
                ) }
                
                if (selectedPlan.isNotEmpty() && selectedPlan != currentPlanType) {
                    currentPlanType = selectedPlan
                    loadReadingData(selectedPlan)
                }
            }
        }
    }
    
    private fun loadReadingData(planType: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            try {
                combine(
                    repository.getTodayReading(planType),
                    repository.getReadingDays(planType),
                    repository.getCompletedCount(planType),
                    repository.getCurrentStreak(planType)
                ) { today, all, completed, streak ->
                    _uiState.value.copy(
                        selectedPlan = planType,
                        todayReading = today,
                        allReadings = all,
                        completedCount = completed,
                        totalDays = repository.getTotalDays(planType),
                        currentStreak = streak,
                        isLoading = false
                    )
                }.collect { state ->
                    _uiState.value = state
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
    
    fun initializePlan(planType: ReadingPlanType) {
        viewModelScope.launch {
            try {
                repository.initializePlan(planType)
                preferences.setSelectedPlan(planType.id)
                preferences.setPlanStartDate(System.currentTimeMillis().toString())
                loadReadingData(planType.id)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    fun markAsRead(day: Int, completed: Boolean) {
        val planType = _uiState.value.selectedPlan ?: return
        viewModelScope.launch {
            try {
                repository.markAsRead(planType, day, completed)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    fun setReminderEnabled(enabled: Boolean) {
        viewModelScope.launch {
            preferences.setReminderEnabled(enabled)
        }
    }
    
    fun setReminderTime(hour: Int, minute: Int) {
        viewModelScope.launch {
            preferences.setReminderTime(hour, minute)
        }
    }
    
    fun setIsPremium(isPremium: Boolean) {
        viewModelScope.launch {
            preferences.setIsPremium(isPremium)
        }
    }
}


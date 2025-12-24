package kr.co.roysoft.biblereadingchecker.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

class AppPreferences(private val context: Context) {
    companion object {
        private val SELECTED_PLAN_KEY = stringPreferencesKey("selected_plan")
        private val IS_PREMIUM_KEY = booleanPreferencesKey("is_premium")
        private val REMINDER_ENABLED_KEY = booleanPreferencesKey("reminder_enabled")
        private val REMINDER_HOUR_KEY = stringPreferencesKey("reminder_hour")
        private val REMINDER_MINUTE_KEY = stringPreferencesKey("reminder_minute")
        private val PLAN_START_DATE_KEY = stringPreferencesKey("plan_start_date")
    }
    
    val selectedPlan: Flow<String?> = context.dataStore.data.map { it[SELECTED_PLAN_KEY] }
    
    val isPremium: Flow<Boolean> = context.dataStore.data.map { it[IS_PREMIUM_KEY] ?: false }
    
    val reminderEnabled: Flow<Boolean> = context.dataStore.data.map { it[REMINDER_ENABLED_KEY] ?: false }
    
    val reminderTime: Flow<Pair<Int, Int>> = context.dataStore.data.map {
        val hour = it[REMINDER_HOUR_KEY]?.toIntOrNull() ?: 8
        val minute = it[REMINDER_MINUTE_KEY]?.toIntOrNull() ?: 0
        hour to minute
    }
    
    val planStartDate: Flow<String?> = context.dataStore.data.map { it[PLAN_START_DATE_KEY] }
    
    suspend fun setSelectedPlan(plan: String) {
        context.dataStore.edit { it[SELECTED_PLAN_KEY] = plan }
    }
    
    suspend fun setIsPremium(isPremium: Boolean) {
        context.dataStore.edit { it[IS_PREMIUM_KEY] = isPremium }
    }
    
    suspend fun setReminderEnabled(enabled: Boolean) {
        context.dataStore.edit { it[REMINDER_ENABLED_KEY] = enabled }
    }
    
    suspend fun setReminderTime(hour: Int, minute: Int) {
        context.dataStore.edit {
            it[REMINDER_HOUR_KEY] = hour.toString()
            it[REMINDER_MINUTE_KEY] = minute.toString()
        }
    }
    
    suspend fun setPlanStartDate(date: String) {
        context.dataStore.edit { it[PLAN_START_DATE_KEY] = date }
    }
}


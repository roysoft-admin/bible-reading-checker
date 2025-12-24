package kr.co.roysoft.biblereadingchecker.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kr.co.roysoft.biblereadingchecker.data.dao.ReadingProgressDao
import kr.co.roysoft.biblereadingchecker.data.entity.ReadingProgressEntity
import kr.co.roysoft.biblereadingchecker.data.model.ReadingDay
import kr.co.roysoft.biblereadingchecker.data.model.ReadingPlanType
import java.util.Calendar

class ReadingRepository(
    private val dao: ReadingProgressDao
) {
    fun getReadingDays(planType: String): Flow<List<ReadingDay>> {
        return dao.getAllByPlanType(planType).map { entities ->
            val today = getTodayDayNumber(planType)
            entities.map { entity ->
                ReadingDay(
                    day = entity.day,
                    passage = entity.passage,
                    completed = entity.completed,
                    isToday = entity.day == today
                )
            }
        }
    }
    
    fun getTodayReading(planType: String): Flow<ReadingDay?> {
        return dao.getAllByPlanType(planType).map { entities ->
            val today = getTodayDayNumber(planType)
            entities.find { it.day == today }?.let { entity ->
                ReadingDay(
                    day = entity.day,
                    passage = entity.passage,
                    completed = entity.completed,
                    isToday = true
                )
            }
        }
    }
    
    suspend fun initializePlan(planType: ReadingPlanType) {
        val plan = ReadingPlanGenerator.generatePlan(planType)
        val entities = plan.map { (day, passage) ->
            ReadingProgressEntity(
                day = day,
                planType = planType.id,
                passage = passage,
                completed = false,
                completedDate = null
            )
        }
        dao.insertAll(entities)
    }
    
    suspend fun markAsRead(planType: String, day: Int, completed: Boolean) {
        val completedDate = if (completed) System.currentTimeMillis() else null
        dao.updateCompletion(planType, day, completed, completedDate)
    }
    
    fun getCompletedCount(planType: String): Flow<Int> {
        return dao.getCompletedCount(planType)
    }
    
    fun getTotalDays(planType: String): Int {
        return ReadingPlanType.values().find { it.id == planType }?.days ?: 365
    }
    
    fun getCurrentStreak(planType: String): Flow<Int> {
        return dao.getAllByPlanType(planType).map { entities ->
            var streak = 0
            val sorted = entities.sortedByDescending { it.day }
            val today = getTodayDayNumber(planType)
            
            for (entity in sorted) {
                if (entity.day <= today && entity.completed) {
                    streak++
                } else if (entity.day <= today && !entity.completed) {
                    break
                }
            }
            streak
        }
    }
    
    private fun getTodayDayNumber(planType: String): Int {
        // 계획 시작일을 기준으로 오늘 날짜 계산
        // 간단히 구현: 2024년 1월 1일을 시작일로 가정
        val startDate = Calendar.getInstance().apply {
            set(2024, Calendar.JANUARY, 1)
        }
        val today = Calendar.getInstance()
        val diffInMillis = today.timeInMillis - startDate.timeInMillis
        val diffInDays = (diffInMillis / (1000 * 60 * 60 * 24)).toInt() + 1
        
        val totalDays = ReadingPlanType.values().find { it.id == planType }?.days ?: 365
        return minOf(diffInDays, totalDays)
    }
}


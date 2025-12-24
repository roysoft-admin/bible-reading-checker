package kr.co.roysoft.biblereadingchecker.data.dao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kr.co.roysoft.biblereadingchecker.data.entity.ReadingProgressEntity

@Dao
interface ReadingProgressDao {
    @Query("SELECT * FROM reading_progress WHERE planType = :planType ORDER BY day ASC")
    fun getAllByPlanType(planType: String): Flow<List<ReadingProgressEntity>>
    
    @Query("SELECT * FROM reading_progress WHERE planType = :planType AND day = :day")
    suspend fun getByDay(planType: String, day: Int): ReadingProgressEntity?
    
    @Query("SELECT COUNT(*) FROM reading_progress WHERE planType = :planType AND completed = 1")
    fun getCompletedCount(planType: String): Flow<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(progress: ReadingProgressEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(progresses: List<ReadingProgressEntity>)
    
    @Update
    suspend fun update(progress: ReadingProgressEntity)
    
    @Query("UPDATE reading_progress SET completed = :completed, completedDate = :completedDate WHERE planType = :planType AND day = :day")
    suspend fun updateCompletion(planType: String, day: Int, completed: Boolean, completedDate: Long?)
    
    @Query("DELETE FROM reading_progress WHERE planType = :planType")
    suspend fun deleteByPlanType(planType: String)
}


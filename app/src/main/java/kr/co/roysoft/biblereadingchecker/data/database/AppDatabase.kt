package kr.co.roysoft.biblereadingchecker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kr.co.roysoft.biblereadingchecker.data.dao.ReadingProgressDao
import kr.co.roysoft.biblereadingchecker.data.entity.ReadingProgressEntity

@Database(
    entities = [ReadingProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun readingProgressDao(): ReadingProgressDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bible_reading_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


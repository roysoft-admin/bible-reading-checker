package kr.co.roysoft.biblereadingchecker

import android.app.Application
import kr.co.roysoft.biblereadingchecker.data.database.AppDatabase
import kr.co.roysoft.biblereadingchecker.data.preferences.AppPreferences
import kr.co.roysoft.biblereadingchecker.data.repository.ReadingRepository

class BibleReadingApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val preferences by lazy { AppPreferences(this) }
    val repository by lazy { ReadingRepository(database.readingProgressDao()) }
}


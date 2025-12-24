package kr.co.roysoft.biblereadingchecker.data.repository

import kr.co.roysoft.biblereadingchecker.data.model.ReadingPlanType

object ReadingPlanGenerator {
    
    // 성경 66권의 장 수 데이터 (구약 39권 + 신약 27권)
    private val oldTestamentBooks = listOf(
        "Genesis" to 50,
        "Exodus" to 40,
        "Leviticus" to 27,
        "Numbers" to 36,
        "Deuteronomy" to 34,
        "Joshua" to 24,
        "Judges" to 21,
        "Ruth" to 4,
        "1 Samuel" to 31,
        "2 Samuel" to 24,
        "1 Kings" to 22,
        "2 Kings" to 25,
        "1 Chronicles" to 29,
        "2 Chronicles" to 36,
        "Ezra" to 10,
        "Nehemiah" to 13,
        "Esther" to 10,
        "Job" to 42,
        "Psalms" to 150,
        "Proverbs" to 31,
        "Ecclesiastes" to 12,
        "Song of Songs" to 8,
        "Isaiah" to 66,
        "Jeremiah" to 52,
        "Lamentations" to 5,
        "Ezekiel" to 48,
        "Daniel" to 12,
        "Hosea" to 14,
        "Joel" to 3,
        "Amos" to 9,
        "Obadiah" to 1,
        "Jonah" to 4,
        "Micah" to 7,
        "Nahum" to 3,
        "Habakkuk" to 3,
        "Zephaniah" to 3,
        "Haggai" to 2,
        "Zechariah" to 14,
        "Malachi" to 4
    )
    
    private val newTestamentBooks = listOf(
        "Matthew" to 28,
        "Mark" to 16,
        "Luke" to 24,
        "John" to 21,
        "Acts" to 28,
        "Romans" to 16,
        "1 Corinthians" to 16,
        "2 Corinthians" to 13,
        "Galatians" to 6,
        "Ephesians" to 6,
        "Philippians" to 4,
        "Colossians" to 4,
        "1 Thessalonians" to 5,
        "2 Thessalonians" to 3,
        "1 Timothy" to 6,
        "2 Timothy" to 4,
        "Titus" to 3,
        "Philemon" to 1,
        "Hebrews" to 13,
        "James" to 5,
        "1 Peter" to 5,
        "2 Peter" to 3,
        "1 John" to 5,
        "2 John" to 1,
        "3 John" to 1,
        "Jude" to 1,
        "Revelation" to 22
    )
    
    fun generatePlan(planType: ReadingPlanType): List<Pair<Int, String>> {
        return when (planType) {
            ReadingPlanType.ONE_YEAR -> generateOneYearPlan()
            ReadingPlanType.NINETY_DAYS -> generateNinetyDaysPlan()
            ReadingPlanType.NEW_TESTAMENT -> generateNewTestamentPlan()
        }
    }
    
    private fun generateOneYearPlan(): List<Pair<Int, String>> {
        val allBooks = oldTestamentBooks + newTestamentBooks
        return generateReadingPlan(allBooks, 365)
    }
    
    private fun generateNinetyDaysPlan(): List<Pair<Int, String>> {
        val allBooks = oldTestamentBooks + newTestamentBooks
        return generateReadingPlan(allBooks, 90)
    }
    
    private fun generateNewTestamentPlan(): List<Pair<Int, String>> {
        return generateReadingPlan(newTestamentBooks, 260)
    }
    
    /**
     * 책 목록과 총 일수를 받아서 일별 읽기표를 생성합니다.
     * 각 날짜마다 적절한 분량의 장을 할당합니다.
     */
    private fun generateReadingPlan(
        books: List<Pair<String, Int>>,
        totalDays: Int
    ): List<Pair<Int, String>> {
        val plan = mutableListOf<Pair<Int, String>>()
        
        // 전체 장 수 계산
        val totalChapters = books.sumOf { it.second }
        val averageChaptersPerDay = totalChapters.toDouble() / totalDays
        
        // 각 책의 장을 리스트로 변환
        val allChapters = mutableListOf<Pair<String, Int>>()
        books.forEach { (bookName, chapterCount) ->
            repeat(chapterCount) { chapter ->
                allChapters.add(bookName to (chapter + 1))
            }
        }
        
        // 일별로 장 할당
        var chapterIndex = 0
        for (day in 1..totalDays) {
            if (chapterIndex >= allChapters.size) {
                plan.add(day to "Review Day")
                continue
            }
            
            val chaptersForToday = mutableListOf<Pair<String, Int>>()
            var remainingChapters = averageChaptersPerDay
            
            // 오늘 읽을 장들 수집
            while (remainingChapters > 0.1 && chapterIndex < allChapters.size) {
                chaptersForToday.add(allChapters[chapterIndex])
                chapterIndex++
                remainingChapters -= 1.0
            }
            
            if (chaptersForToday.isEmpty()) {
                plan.add(day to "Review Day")
                continue
            }
            
            // 같은 책의 연속된 장들을 그룹화
            val readingText = formatReadingText(chaptersForToday)
            plan.add(day to readingText)
        }
        
        return plan
    }
    
    /**
     * 같은 책의 연속된 장들을 "Book 1-5" 형식으로 포맷팅합니다.
     */
    private fun formatReadingText(chapters: List<Pair<String, Int>>): String {
        if (chapters.isEmpty()) return "Review Day"
        
        val grouped = mutableListOf<Pair<String, List<Int>>>()
        var currentBook = chapters[0].first
        var currentChapters = mutableListOf(chapters[0].second)
        
        for (i in 1 until chapters.size) {
            val (book, chapter) = chapters[i]
            if (book == currentBook) {
                currentChapters.add(chapter)
            } else {
                grouped.add(currentBook to currentChapters.toList())
                currentBook = book
                currentChapters = mutableListOf(chapter)
            }
        }
        grouped.add(currentBook to currentChapters.toList())
        
        // 여러 책이 섞여있으면 첫 번째와 마지막만 표시
        if (grouped.size == 1) {
            val (book, chapterList) = grouped[0]
            val start = chapterList.minOrNull() ?: 1
            val end = chapterList.maxOrNull() ?: 1
            return if (start == end) {
                "$book $start"
            } else {
                "$book $start–$end"
            }
        } else {
            // 여러 책이 섞여있는 경우
            val firstBook = grouped[0]
            val lastBook = grouped[grouped.size - 1]
            val firstStart = firstBook.second.minOrNull() ?: 1
            val firstEnd = firstBook.second.maxOrNull() ?: 1
            val lastStart = lastBook.second.minOrNull() ?: 1
            val lastEnd = lastBook.second.maxOrNull() ?: 1
            
            val firstText = if (firstStart == firstEnd) {
                "${firstBook.first} $firstStart"
            } else {
                "${firstBook.first} $firstStart–$firstEnd"
            }
            
            val lastText = if (lastStart == lastEnd) {
                "${lastBook.first} $lastStart"
            } else {
                "${lastBook.first} $lastStart–$lastEnd"
            }
            
            return if (grouped.size == 2) {
                "$firstText, $lastText"
            } else {
                "$firstText ... $lastText"
            }
        }
    }
}

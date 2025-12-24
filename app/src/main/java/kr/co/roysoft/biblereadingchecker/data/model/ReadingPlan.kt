package kr.co.roysoft.biblereadingchecker.data.model

enum class ReadingPlanType(val id: String, val displayName: String, val days: Int) {
    ONE_YEAR("1year", "1 Year Plan", 365),
    NINETY_DAYS("90days", "90 Days", 90),
    NEW_TESTAMENT("newtestament", "New Testament", 260)
}

data class ReadingDay(
    val day: Int,
    val passage: String,
    val completed: Boolean = false,
    val isToday: Boolean = false
)


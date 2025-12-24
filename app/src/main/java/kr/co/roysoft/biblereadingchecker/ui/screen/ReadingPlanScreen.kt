package kr.co.roysoft.biblereadingchecker.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kr.co.roysoft.biblereadingchecker.data.model.ReadingDay
import kr.co.roysoft.biblereadingchecker.ui.components.AdMobBanner
import kr.co.roysoft.biblereadingchecker.ui.theme.*

@Composable
fun ReadingPlanScreen(
    readings: List<ReadingDay>,
    planName: String,
    isPremium: Boolean,
    onBack: () -> Unit,
    onReadingClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(BackgroundLight, BackgroundDark)
                )
            )
    ) {
        // Header
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = SurfaceWhite.copy(alpha = 0.5f),
            shadowElevation = 1.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 56.dp, 24.dp, 24.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable(onClick = onBack)
                        .padding(bottom = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryMedium,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Back",
                        fontSize = 14.sp,
                        color = PrimaryMedium
                    )
                }
                Text(
                    text = "Reading Plan",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = planName,
                    fontSize = 14.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
        
        // Reading List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(readings) { reading ->
                ReadingDayItem(
                    reading = reading,
                    onClick = { onReadingClick(reading.day) }
                )
            }
        }
        
        // Banner Ad Space (Free Version)
        if (!isPremium) {
            AdMobBanner(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp, 16.dp)
            )
        }
    }
}

@Composable
fun ReadingDayItem(
    reading: ReadingDay,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                reading.isToday -> PrimaryLight.copy(alpha = 0.1f)
                reading.completed -> SurfaceWhite.copy(alpha = 0.4f)
                else -> SurfaceWhite.copy(alpha = 0.6f)
            }
        ),
        border = if (reading.isToday) {
            androidx.compose.foundation.BorderStroke(2.dp, PrimaryLight.copy(alpha = 0.3f))
        } else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Check Icon
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        if (reading.completed) PrimaryLight.copy(alpha = 0.2f)
                        else BorderLight.copy(alpha = 0.5f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (reading.completed) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = PrimaryMedium,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Day and Passage
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Day ${reading.day}",
                    fontSize = 14.sp,
                    color = TextSecondary,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = reading.passage,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (reading.completed) TextSecondary else TextPrimary
                )
            }
            
            // Today Badge
            if (reading.isToday) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = PrimaryLight.copy(alpha = 0.1f)
                ) {
                    Text(
                        text = "Today",
                        fontSize = 12.sp,
                        color = PrimaryLight,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}


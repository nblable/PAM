package com.example.myfirstkmpapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myfirstkmpapp.model.NutritionInfo

@Composable
fun NutritionCard(info: NutritionInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = info.name.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE91E63)
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 1.dp, color = Color(0xFFFCE4EC))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                NutrientItem("Kalori", "${info.calories}", "kcal")
                NutrientItem("Protein", "${info.protein}", "g")
                NutrientItem("Karbo", "${info.carbs}", "g")
                NutrientItem("Lemak", "${info.fat}", "g")
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), thickness = 1.dp, color = Color(0xFFFCE4EC))

            Text(
                text = "💡 Saran Kesehatan:",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFFC2185B),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            info.healthTips.forEach { tip ->
                Text(
                    text = "• $tip",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 4.dp),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun NutrientItem(label: String, value: String, unit: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold, color = Color.Black)
        Text(text = unit, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
    }
}
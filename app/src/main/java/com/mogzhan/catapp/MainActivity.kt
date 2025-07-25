package com.mogzhan.catapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mogzhan.catapp.ui.theme.CatAppTheme

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var themeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }
            val systemInDarkTheme = isSystemInDarkTheme()

            val isDarkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> systemInDarkTheme
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            CatAppTheme(darkTheme = isDarkTheme) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        currentThemeMode = themeMode,
                        onThemeModeChange = { themeMode = it },
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    currentThemeMode: ThemeMode,
    onThemeModeChange: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Choose Theme",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        ThemeSelector(
            selectedTheme = currentThemeMode,
            onThemeSelected = onThemeModeChange
        )

        Text(
            text = "Current: ${currentThemeMode.name}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Composable
fun ThemeSelector(
    selectedTheme: ThemeMode,
    onThemeSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier
) {
    val themes = listOf(
        ThemeMode.SYSTEM to "System",
        ThemeMode.LIGHT to "Light",
        ThemeMode.DARK to "Dark"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(4.dp)
    ) {
        themes.forEachIndexed { index, (theme, label) ->
            ThemeSelectorItem(
                text = label,
                isSelected = selectedTheme == theme,
                onClick = { onThemeSelected(theme) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ThemeSelectorItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Transparent
                }
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            ),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true, name = "Light Theme")
@Composable
fun MainScreenLightPreview() {
    CatAppTheme(darkTheme = false) {
        MainScreen(
            currentThemeMode = ThemeMode.LIGHT,
            onThemeModeChange = {}
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun MainScreenDarkPreview() {
    CatAppTheme(darkTheme = true) {
        MainScreen(
            currentThemeMode = ThemeMode.DARK,
            onThemeModeChange = {}
        )
    }
}

@Preview(showBackground = true, name = "System Theme")
@Composable
fun MainScreenSystemPreview() {
    CatAppTheme(darkTheme = false) {
        MainScreen(
            currentThemeMode = ThemeMode.SYSTEM,
            onThemeModeChange = {}
        )
    }
}
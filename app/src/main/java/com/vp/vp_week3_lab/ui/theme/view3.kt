package com.vp.vp_week3_lab.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.setValue
import com.vp.vp_week3_lab.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import kotlin.math.ceil



enum class Mode {
    COLOR, TEXT
}

enum class Choice {
    INK, WORD
}

enum class ColorName {
    RED, BLUE, GREEN, YELLOW
}

// Keep numbered naming to avoid conflict with view1.kt
enum class GameState2 {
    INITIAL, GAME_OVER, COUNTDOWN, RUNNING
}

@Composable
fun ColorWordMatchingGame() {
    var gameState2 by rememberSaveable { mutableStateOf(GameState2.INITIAL) }
    var currentMode by rememberSaveable { mutableStateOf(Mode.COLOR) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var mistakes by rememberSaveable { mutableIntStateOf(0) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }
    var timeLeft by rememberSaveable { mutableIntStateOf(5) }
    var countdownNumber by rememberSaveable { mutableIntStateOf(3) }
    var isButtonLeftInk by rememberSaveable { mutableStateOf(true) }
    var isCountingDown by rememberSaveable { mutableStateOf(false) }

    var currentWord by rememberSaveable { mutableStateOf(ColorName.RED) }
    var currentInkColor by rememberSaveable { mutableStateOf(ColorName.BLUE) }

    fun getColorValue(colorName: ColorName): Color {
        if (colorName == ColorName.RED) {
            return Color.Red
        }
        if (colorName == ColorName.BLUE) {
            return Color.Blue
        }
        if (colorName == ColorName.GREEN) {
            return Color.Green
        }
        if (colorName == ColorName.YELLOW) {
            return Color.Yellow
        }
        return Color.Black
    }

    fun generateNewQuestion() {
        val colors = ColorName.values()
        val randomIndex1 = (0 until colors.size).random()
        val randomIndex2 = (0 until colors.size).random()
        currentWord = colors[randomIndex1]
        currentInkColor = colors[randomIndex2]

        val modeRandom = (0..1).random()
        if (modeRandom == 0) {
            currentMode = Mode.COLOR
        } else {
            currentMode = Mode.TEXT
        }

        val buttonRandom = (0..1).random()
        if (buttonRandom == 0) {
            isButtonLeftInk = true
        } else {
            isButtonLeftInk = false
        }

        timeLeft = 5
    }

    fun checkAnswer(choice: Choice) {
        var isCorrect = false

        if (currentMode == Mode.COLOR && choice == Choice.INK) {
            isCorrect = true
        }
        if (currentMode == Mode.TEXT && choice == Choice.WORD) {
            isCorrect = true
        }

        if (isCorrect == true) {
            score = score + 1
            generateNewQuestion()
        } else {
            mistakes = mistakes + 1
            if (mistakes >= 3) {
                if (score > bestScore) {
                    bestScore = score
                }
                gameState2 = GameState2.GAME_OVER
            } else {
                generateNewQuestion()
            }
        }
    }

    LaunchedEffect(gameState2) {
        if (gameState2 == GameState2.COUNTDOWN) {
            isCountingDown = true
            countdownNumber = 3
            delay(700)
            countdownNumber = 2
            delay(700)
            countdownNumber = 1
            delay(700)
            countdownNumber = 0
            delay(700)
            gameState2 = GameState2.RUNNING
            generateNewQuestion()
            isCountingDown = false
        }
    }

    LaunchedEffect(gameState2, timeLeft) {
        if (gameState2 == GameState2.RUNNING) {
            if (timeLeft > 0) {
                delay(1000)
                timeLeft = timeLeft - 1
            } else {
                mistakes = mistakes + 1
                if (mistakes >= 3) {
                    if (score > bestScore) {
                        bestScore = score
                    }
                    gameState2 = GameState2.GAME_OVER
                } else {
                    generateNewQuestion()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        when (gameState2) {
            GameState2.INITIAL -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Color Word Matching",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Button(
                        onClick = {
                            gameState2 = GameState2.COUNTDOWN
                            score = 0
                            mistakes = 0
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Start Game",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            GameState2.COUNTDOWN -> {
                when (countdownNumber) {
                    3, 2, 1 -> {
                        Text(
                            text = countdownNumber.toString(),
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }
                    0 -> {
                        Text(
                            text = "Start!",
                            fontSize = 80.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            GameState2.RUNNING -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Top row with score and mode info
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var modeText = "Mode: COLOR"
                        if (currentMode == Mode.TEXT) {
                            modeText = "Mode: TEXT"
                        }

                        Text(
                            text = modeText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Text(
                            text = "Benar: " + score.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        )

                        Text(
                            text = "Kesalahan: " + mistakes.toString() + "/3",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Red
                        )
                    }

                    // Timer
                    Text(
                        text = timeLeft.toString(),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (timeLeft <= 2) Color.Red else Color.Black,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )

                    // Clue text
                    var clueText = "Pilih WARNA TINTA"
                    if (currentMode == Mode.TEXT) {
                        clueText = "Pilih NAMA KATA"
                    }

                    Text(
                        text = clueText,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue,
                        modifier = Modifier
                            .background(
                                Color.LightGray.copy(alpha = 0.2f),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    // Word display
                    var wordText = "RED"
                    if (currentWord == ColorName.BLUE) {
                        wordText = "BLUE"
                    }
                    if (currentWord == ColorName.GREEN) {
                        wordText = "GREEN"
                    }
                    if (currentWord == ColorName.YELLOW) {
                        wordText = "YELLOW"
                    }

                    Text(
                        text = wordText,
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = getColorValue(currentInkColor),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    // Answer buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        if (isButtonLeftInk == true) {
                            // Left button: INK color
                            var inkText = "RED"
                            if (currentInkColor == ColorName.BLUE) {
                                inkText = "BLUE"
                            }
                            if (currentInkColor == ColorName.GREEN) {
                                inkText = "GREEN"
                            }
                            if (currentInkColor == ColorName.YELLOW) {
                                inkText = "YELLOW"
                            }

                            Button(
                                onClick = { checkAnswer(Choice.INK) },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(70.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = getColorValue(currentInkColor)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = inkText,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                            )
                            }

                            // Right button: WORD color
                            var wordButtonText = "RED"
                            if (currentWord == ColorName.BLUE) {
                                wordButtonText = "BLUE"
                            }
                            if (currentWord == ColorName.GREEN) {
                                wordButtonText = "GREEN"
                            }
                            if (currentWord == ColorName.YELLOW) {
                                wordButtonText = "YELLOW"
                            }

                            Button(
                                onClick = { checkAnswer(Choice.WORD) },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(70.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = getColorValue(currentWord)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = wordButtonText,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                            )
                            }
                        } else {
                            // Left button: WORD color
                            var wordButtonText = "RED"
                            if (currentWord == ColorName.BLUE) {
                                wordButtonText = "BLUE"
                            }
                            if (currentWord == ColorName.GREEN) {
                                wordButtonText = "GREEN"
                            }
                            if (currentWord == ColorName.YELLOW) {
                                wordButtonText = "YELLOW"
                            }

                            Button(
                                onClick = { checkAnswer(Choice.WORD) },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(70.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = getColorValue(currentWord)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = wordButtonText,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                            )
                            }

                            // Right button: INK color
                            var inkText = "RED"
                            if (currentInkColor == ColorName.BLUE) {
                                inkText = "BLUE"
                            }
                            if (currentInkColor == ColorName.GREEN) {
                                inkText = "GREEN"
                            }
                            if (currentInkColor == ColorName.YELLOW) {
                                inkText = "YELLOW"
                            }

                            Button(
                                onClick = { checkAnswer(Choice.INK) },
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(70.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = getColorValue(currentInkColor)),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = inkText,
                                    fontSize = 18.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                            )
                            }
                        }
                    }
                }
            }

            GameState2.GAME_OVER -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Game Over",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(30.dp))

                    Text(
                        text = "Skor Akhir: " + score.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Best Score: " + bestScore.toString(),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Button(
                            onClick = {
                                gameState2 = GameState2.COUNTDOWN
                                score = 0
                                mistakes = 0
                            },
                            modifier = Modifier
                                .width(120.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Restart",
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = {
                                gameState2 = GameState2.INITIAL
                            },
                            modifier = Modifier
                                .width(120.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(
                                text = "Exit",
                                fontSize = 16.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ColorWordMatchingGamePreview() {
    ColorWordMatchingGame()

}
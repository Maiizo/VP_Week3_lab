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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.setValue



enum class Mode {
    COLOR, TEXT
}

enum class Choice {
    INK, WORD
}

enum class ColorName {
    RED, BLUE, GREEN, YELLOW
}

// soalnya sdh ada gamestate di view1
enum class gameState2 {
    INITIAL, GAME_OVER, COUNTDOWN, RUNNING
}

@Composable
fun View3() {
    var gameState by rememberSaveable { mutableStateOf(gameState2.INITIAL) }
    var currentMode by rememberSaveable { mutableStateOf(Mode.COLOR) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var mistakes by rememberSaveable { mutableIntStateOf(0) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }
    var timeLeft by rememberSaveable { mutableIntStateOf(5) }
    var countdownNumber by rememberSaveable { mutableIntStateOf(3) }
    var leftButtonCorrect by rememberSaveable { mutableStateOf(true) }
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

    fun getColorText(colorName: ColorName): String {
        if (colorName == ColorName.RED) {
            return "RED"
        }
        if (colorName == ColorName.BLUE) {
            return "BLUE"
        }
        if (colorName == ColorName.GREEN) {
            return "GREEN"
        }
        if (colorName == ColorName.YELLOW) {
            return "YELLOW"
        }
        return "RED"
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
            leftButtonCorrect = true
        } else {
            leftButtonCorrect = false
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
                gameState = gameState2.GAME_OVER
            } else {
                generateNewQuestion()
            }
        }
    }

    @Composable
    fun gameButton(colorName: ColorName, choice: Choice) {
        Button(
            onClick = { checkAnswer(choice) },
            modifier = Modifier
                .width(150.dp)
                .height(70.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(
                text = getColorText(colorName),
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }

    LaunchedEffect(gameState) {
        if (gameState == gameState2.COUNTDOWN) {
            isCountingDown = true
            countdownNumber = 3
            delay(700)
            countdownNumber = 2
            delay(700)
            countdownNumber = 1
            delay(700)
            countdownNumber = 0
            delay(700)
            gameState = gameState2.RUNNING
            generateNewQuestion()
            isCountingDown = false
        }
    }

    LaunchedEffect(gameState, timeLeft) {
        if (gameState == gameState2.RUNNING) {
            if (timeLeft > 0) {
                delay(1000)
                timeLeft = timeLeft - 1
            } else {
                mistakes = mistakes + 1
                if (mistakes >= 3) {
                    if (score > bestScore) {
                        bestScore = score
                    }
                    gameState = gameState2.GAME_OVER
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
        if (gameState == gameState2.INITIAL) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to",
                    fontSize = 30.sp,
                    color = Color.Black,
                )


                Text(
                    text = "Color Word Matching",
                    fontSize = 30.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(50.dp))

                Button(
                    onClick = {
                        gameState = gameState2.COUNTDOWN
                        score = 0
                        mistakes = 0
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "Start Game",
                        fontSize = 15.sp,
                        color = Color.Black,
                    )
                }
            }
        }

        if (gameState == gameState2.COUNTDOWN) {
            if (countdownNumber == 3) {
                Text(
                    text = "3",
                    fontSize = 30.sp,
                    color = Color.Black,
                )
            }
            if (countdownNumber == 2) {
                Text(
                    text = "2",
                    fontSize = 30.sp,
                    color = Color.Black,
                )
            }
            if (countdownNumber == 1) {
                Text(
                    text = "1",
                    fontSize = 30.sp,
                    color = Color.Black,
                )
            }
            if (countdownNumber == 0) {
                Text(
                    text = "Start!",
                    fontSize = 30.sp,
                    color = Color.Black,
                )
            }
        }

        if (gameState == gameState2.RUNNING) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                        fontSize = 10.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "✅ " + score.toString(),
                        fontSize = 10.sp,
                        color = Color.Black
                    )

                    Text(
                        text = "❌ " + mistakes.toString() + "/3",
                        fontSize = 10.sp,
                        color = Color.Black
                    )
                }

                Text(
                    text = timeLeft.toString() + " s",
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                var clueText = "Pilih WARNA TINTA"
                if (currentMode == Mode.TEXT) {
                    clueText = "Pilih NAMA KATA"
                }

                Text(
                    text = clueText,
                    fontSize = 20.sp,
                    color = Color.Black,
                )

                Spacer(modifier = Modifier.height(30.dp))

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
                    fontSize = 50.sp,
                    color = getColorValue(currentInkColor),
                )

                Spacer(modifier = Modifier.height(50.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (leftButtonCorrect == true) {
                        gameButton(currentInkColor, Choice.INK)
                        gameButton(currentWord, Choice.WORD)
                    } else {
                        gameButton(currentWord, Choice.WORD)
                        gameButton(currentInkColor, Choice.INK)
                    }
                }
            }
        }

        if (gameState == gameState2.GAME_OVER) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Game Over",
                    fontSize = 40.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(30.dp))

                Text(
                    text = "Your score: " + score.toString(),
                    fontSize = 25.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Best Score: " + bestScore.toString(),
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Button(
                        onClick = {
                            gameState = gameState2.COUNTDOWN
                            score = 0
                            mistakes = 0
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "Restart Game",
                            fontSize = 15.sp,
                            color = Color.Black,
                            textAlign = TextAlign.Center

                        )
                    }

                    Button(
                        onClick = {
                            gameState = gameState2.INITIAL
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        shape = RoundedCornerShape(50.dp)
                    ) {
                        Text(
                            text = "Exit",
                            fontSize = 15.sp,
                            color = Color.Black,
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun VIew3Preview() {
    View3()

}
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

enum class rps {
    rock, scissor, paper
}

enum class state4 {
    INITIAL, PICK, REVEAL, FINISHED
}

enum class Result {
    win, lose, draw
}


@Composable
fun View4() {
    var gameState by rememberSaveable { mutableStateOf(state4.INITIAL) }
    var yourScore by rememberSaveable { mutableIntStateOf(0) }
    var cpuScore by rememberSaveable { mutableIntStateOf(0) }
    var bestScore by rememberSaveable { mutableIntStateOf(0) }
    var yourChoice by remember { mutableStateOf<rps?>(null) }
    var cpuChoice by remember { mutableStateOf<rps?>(null) }
    var roundResult by remember { mutableStateOf<Result?>(null) }
    var buttonOrder by remember { mutableStateOf(listOf(rps.rock, rps.paper, rps.scissor)) }

    val BO = 3
    val neededScore = BO/2 + 1

    fun choiceEmoji(choice: rps): String {
        if (choice == rps.rock) {
            return "✊"
        }
        if (choice == rps.paper) {
            return "✋"
        }
        if (choice == rps.scissor) {
            return "✌"
        }
        return "✊"
    }

    fun choiceText(choice: rps): String {
        if (choice == rps.rock) {
            return "✊ Rock"
        }
        if (choice == rps.paper) {
            return "✋ Paper"
        }
        if (choice == rps.scissor) {
            return "✌ Scissor"
        }
        return " "
    }
    fun winnerResult(your: rps, cpu: rps): Result {
        if (your == cpu) {
            return Result.draw
        }

        var yourWins = false
        if (your == rps.rock && cpu == rps.scissor) {
            yourWins = true
        }
        if (your == rps.paper && cpu == rps.rock) {
            yourWins = true
        }
        if (your == rps.scissor && cpu == rps.paper) {
            yourWins = true
        }

        if (yourWins) {
            return Result.win
        } else {
            return Result.lose
        }
    }

    fun shuffleButtons() {
        buttonOrder = listOf(rps.rock, rps.paper, rps.scissor).shuffled()
    }

    fun start() {
        yourScore = 0
        cpuScore = 0
        gameState = state4.PICK
        shuffleButtons()
    }

    fun restart() {
        yourScore = 0
        cpuScore = 0
        gameState = state4.PICK
        shuffleButtons()
    }

    fun exit() {
        yourScore = 0
        cpuScore = 0
        gameState = state4.INITIAL
    }

    LaunchedEffect(gameState) {
        if (gameState == state4.REVEAL) {
            delay(700)
            if (yourScore >= neededScore || cpuScore >= neededScore) {
                val finalScore = yourScore - cpuScore
                if (finalScore > bestScore) {
                    bestScore = finalScore
                }
                gameState = state4.FINISHED
            } else {
                gameState = state4.PICK
                shuffleButtons()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (gameState == state4.INITIAL) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("🧑", fontSize = 25.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$yourScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("—", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$cpuScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("🤖", fontSize = 25.sp)


                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Best of $BO",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                "Rock • Paper • Scissors",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { start() },
                shape = RoundedCornerShape(25.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB0BEC5)
                ),
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
            ) {
                Text(
                    "Start",
                    fontSize = 15.sp,
                    color = Color.Black
                )
            }
        }

        if (gameState == state4.PICK) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("🧑", fontSize = 25.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$yourScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("—", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$cpuScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("🤖", fontSize = 25.sp)


                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Best of $BO",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                "Pick your move!",
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("?", fontSize = 50.sp)
                Spacer(modifier = Modifier.width(15.dp))
                Text("VS", fontSize = 50.sp)
                Spacer(modifier = Modifier.width(15.dp))
                Text("?", fontSize = 50.sp)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                buttonOrder.forEach { choice ->
                    Button(
                        onClick = {
                            yourChoice = choice
                            cpuChoice = listOf(rps.rock, rps.paper, rps.scissor).random()
                            roundResult = winnerResult(choice, cpuChoice!!)

                            if (roundResult == Result.win) {
                                yourScore++
                            }
                            if (roundResult == Result.lose) {
                                cpuScore++
                            }
                            if (roundResult == Result.draw) {

                            }

                            gameState = state4.REVEAL
                        },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB0BEC5)
                        ),
                        modifier = Modifier
                            .width(100.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            choiceText(choice),
                            fontSize = 10.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }

        if (gameState == state4.REVEAL) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("🧑", fontSize = 25.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$yourScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("—", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$cpuScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("🤖", fontSize = 25.sp)
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Best of $BO",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(100.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(choiceEmoji(yourChoice!!), fontSize = 35.sp)
                Spacer(modifier = Modifier.width(15.dp))
                Text("VS", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(15.dp))
                Text(choiceEmoji(cpuChoice!!), fontSize = 35.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))
            var resultText = ""
            if (roundResult == Result.win) {
                resultText = "You Win!"
            }
            if (roundResult == Result.lose) {
                resultText = "You Lose!"
            }
            if (roundResult == Result.draw) {
                resultText = "Draw"
            }

            Text(
                resultText,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
        }

        if (gameState == state4.FINISHED) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text("🧑", fontSize = 25.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$yourScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("—", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text("$cpuScore", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(10.dp))
                Text("🤖", fontSize = 25.sp)


                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "Best of $BO",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(100.dp))

            var matchResult = ""
            if (yourScore > cpuScore) {
                matchResult = "You Win the Match!"
            } else {
                matchResult = "You Lose the Match!"
            }

            Text(
                matchResult,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                "Best Score: $bestScore",
                fontSize = 15.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { restart() },
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0BEC5)
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                ) {
                    Text(
                        "Restart",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }

                Button(
                    onClick = { exit() },
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB0BEC5)
                    ),
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                ) {
                    Text(
                        "Exit",
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
            }

        }
    }


}

@Composable
@Preview(showBackground = true, showSystemUi = true)
    fun View4Preview(){
        View4()
    }

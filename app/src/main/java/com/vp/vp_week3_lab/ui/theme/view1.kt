package com.vp.vp_week3_lab.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import com.vp.vp_week3_lab.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip

enum class GameState {
    start, wait, go, trial, failed, final
}

@Composable
fun View1() {

    var gameState by remember { mutableStateOf(GameState.start) }
    var currentTrial by remember { mutableIntStateOf(1) }
    var trial1Time by remember { mutableLongStateOf(0L) }
    var trial2Time by remember { mutableLongStateOf(0L) }
    var trial3Time by remember { mutableLongStateOf(0L) }
    var trial1Failed by remember { mutableStateOf(false) }
    var trial2Failed by remember { mutableStateOf(false) }
    var trial3Failed by remember { mutableStateOf(false) }
    var startTime by remember { mutableLongStateOf(0L) }

    val bgColor = if (gameState == GameState.start) Color(0xFF6ECDDC)
    else if (gameState == GameState.wait) Color(0xFFD7D7D7)
    else if (gameState == GameState.go) Color(0xFF4CAF50)
    else if (gameState == GameState.trial) Color(0xFF4CAF50)
    else if (gameState == GameState.failed) Color(0xFFF44336)
    else Color(0xFF6ECDDC)

    LaunchedEffect(gameState) {
        if (gameState == GameState.wait) {
            val randomDelay = (500..4500).random()
            delay(randomDelay.toLong())
            startTime = System.currentTimeMillis()
            gameState = GameState.go
        }
    }

    fun aveTime(): Long {
        var validTrials = 0
        var totalTime = 0L

        if (!trial1Failed) {
            validTrials++
            totalTime += trial1Time
        }
        if (!trial2Failed) {
            validTrials++
            totalTime += trial2Time
        }
        if (!trial3Failed) {
            validTrials++
            totalTime += trial3Time
        }

        if (validTrials == 0) return 0L
        return totalTime / validTrials
    }

    fun catMessage(average: Long): String {
        if (average < 180) return "DANG YOU ARE SO FAST BRO!"
        else if (average < 280) return "YOUR REFLEX IS GOOD"
        else if (average < 450) return "MEH LIKE OTHER PERSON"
        else return "YOU LIKE A SNAIL BRO"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = bgColor)
    ) {



        if (gameState == GameState.start) {
            Text(
                text = "Reaction",
                color = Color(0xFFFFFFFF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Icon(
                imageVector = Icons.Filled.Bolt,
                contentDescription = "Add",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "Test\n",
                color = Color(0xFFFFFFFF),
            )

            Button(
                onClick = {
                    gameState = GameState.wait
                    currentTrial = 1
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6ECDDC)),
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Text(
                    text = "Click to start",
                    color = Color(0xFFFFFFFF)
                )
            }
        }




        if (gameState == GameState.wait) {
            Text(
                text = "Get Ready",
                color = Color(0xFFFFFFFF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Add",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "Wait for greem light ...\n",
                color = Color(0xFFFFFFFF),
            )

            Button(
                onClick = {
                    if (currentTrial == 1) {
                        trial1Failed = true
                    } else if (currentTrial == 2) {
                        trial2Failed = true
                    } else if (currentTrial == 3) {
                        trial3Failed = true
                    }
                    gameState = GameState.failed
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD7D7D7)),
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Text(
                    text = "DON'T CLICK YET!",
                    color = Color(0xFFFFFFFF)
                )
            }
        }



        if (gameState == GameState.go) {
            Text(
                text = "GO!",
                color = Color(0xFFFFFFFF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Icon(
                imageVector = Icons.Filled.DirectionsRun,
                contentDescription = "Go",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "TAP AS FAST AS YOU CAN!\n",
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )

            Button(
                onClick = {
                    val reactionTime = System.currentTimeMillis() - startTime

                    if (currentTrial == 1) {
                        trial1Time = reactionTime
                    } else if (currentTrial == 2) {
                        trial2Time = reactionTime
                    } else if (currentTrial == 3) {
                        trial3Time = reactionTime
                    }

                    gameState = GameState.trial
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Text(
                    text = "CLICK NOW!",
                    color = Color(0xFFFFFFFF)
                )
            }
        }




        if (gameState == GameState.trial) {
            Text(
                text = "Trial $currentTrial Complete!",
                color = Color(0xFFFFFFFF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = "Complete",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )

            val currentTime = if (currentTrial == 1) trial1Time
            else if (currentTrial == 2) trial2Time
            else trial3Time

            Text(
                text = "Time: ${currentTime}ms\n",
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )

            Button(
                onClick = {
                    if (currentTrial < 3) {
                        currentTrial++
                        gameState = GameState.wait
                    } else {
                        gameState = GameState.final
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.padding(top = 5.dp)
            ) {
                val buttonText = if (currentTrial < 3) "Continue to Trial ${currentTrial + 1}"
                else "Show Results"
                Text(
                    text = buttonText,
                    color = Color(0xFFFFFFFF),
                    fontSize = 20.sp,
                )
            }

            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.White)
                    .padding(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Trial Results",
                        color = Color(0xFF2196F3),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 50.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.padding(top = 30.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "1",
                            color = if (currentTrial >= 1) Color(0xFF4CAF50) else Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (currentTrial >= 1 && !trial1Failed) "${trial1Time}ms" else if (trial1Failed) "Failed" else "",
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "2",
                            color = if (currentTrial >= 2) Color(0xFF4CAF50) else Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (currentTrial >= 2 && !trial2Failed) "${trial2Time}ms" else if (trial2Failed) "Failed" else "",
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "3",
                            color = if (currentTrial >= 3) Color(0xFF4CAF50) else Color.Gray,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (currentTrial >= 3 && !trial3Failed) "${trial3Time}ms" else if (trial3Failed) "Failed" else "",
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }






        if (gameState == GameState.failed) {
            Text(
                text = "FAIL!",
                color = Color(0xFFFFFFFF),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Icon(
                imageVector = Icons.Filled.ThumbDown,
                contentDescription = "Fail",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 20.dp)
            )

            Text(
                text = "You clicked too early. TRY TO READ THE RULE BRO!\n",
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.White)
                    .padding(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Trial Results",
                        color = Color(0xFF2196F3),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "1",
                                color = if (currentTrial >= 1) Color(0xFF4CAF50) else Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (currentTrial >= 1 && !trial1Failed) "${trial1Time}ms" else if (trial1Failed) "Failed" else "",
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "2",
                                color = if (currentTrial >= 2) Color(0xFF4CAF50) else Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (currentTrial >= 2 && !trial2Failed) "${trial2Time}ms" else if (trial2Failed) "Failed" else "",
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "3",
                                color = if (currentTrial >= 3) Color(0xFF4CAF50) else Color.Gray,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (currentTrial >= 3 && !trial3Failed) "${trial3Time}ms" else if (trial3Failed) "Failed" else "",
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (currentTrial < 3) {
                        currentTrial++
                        gameState = GameState.wait
                    } else {
                        gameState = GameState.final
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
                modifier = Modifier.padding(top = 5.dp)
            ) {
                val buttonText = if (currentTrial < 3) "Try Again"
                else "Show Results"
                Text(
                    text = buttonText,
                    color = Color(0xFFFFFFFF)
                )
            }
        }




        if (gameState == GameState.final) {
            val AveTime = aveTime()
            val CatMessage = catMessage(AveTime)

            Text(
                text = CatMessage,
                color = Color(0xFFFFFFFF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            if (AveTime < 180) {
                Image(
                    painter = painterResource(id = R.drawable.fast),
                    contentDescription = "fast",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 20.dp)
                )
            } else if (AveTime < 280) {
                Image(
                    painter = painterResource(id = R.drawable.good),
                    contentDescription = "good ",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 20.dp)
                )
            } else if (AveTime < 450) {
                Image(
                    painter = painterResource(id = R.drawable.meh),
                    contentDescription = "Ave",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 20.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.snail),
                    contentDescription = "snaikl",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(bottom = 20.dp)
                )
            }

            Text(
                text = "Average: ${AveTime}ms\n",
                color = Color(0xFFFFFFFF),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 5.dp)
            )

            Button(
                onClick = {
                    gameState = GameState.start
                    currentTrial = 1
                    trial1Time = 0L
                    trial2Time = 0L
                    trial3Time = 0L
                    trial1Failed = false
                    trial2Failed = false
                    trial3Failed = false
                    startTime = 0L
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6ECDDC)),
                modifier = Modifier.padding(top = 5.dp)
            ) {
                Text(
                    text = "Click to Start New Test",
                    color = Color(0xFFFFFFFF)
                )
            }

            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color.White)
                    .padding(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Trial Results",
                        color = Color(0xFF2196F3),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "1",
                                color = if (!trial1Failed) Color(0xFF4CAF50) else Color(0xFFF44336),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (!trial1Failed) "${trial1Time}ms" else "Failed",
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "2",
                                color = if (!trial2Failed) Color(0xFF4CAF50) else Color(0xFFF44336),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (!trial2Failed) "${trial2Time}ms" else "Failed",
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "3",
                                color = if (!trial3Failed) Color(0xFF4CAF50) else Color(0xFFF44336),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (!trial3Failed) "${trial3Time}ms" else "Failed",
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                        }

                    }


                    Text(
                        text = "Average: ${AveTime}ms\n",
                        color = Color(0xFFe49e56),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
        }

    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun View1Preview() {
    View1()
}
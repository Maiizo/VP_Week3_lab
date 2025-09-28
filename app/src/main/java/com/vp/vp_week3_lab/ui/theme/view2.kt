package com.vp.vp_week3_lab.ui.theme


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
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
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import com.vp.vp_week3_lab.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import kotlin.math.ceil


@Composable
fun View2() {
    var coins by remember { mutableStateOf(0) }
    var value by remember { mutableStateOf(1) }
    var upgradePrice by remember { mutableStateOf(10) }
    var catPressed by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bg_2),
            contentDescription = "fast",
            modifier = Modifier
        )


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(15.dp),
        ) {

            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = Color.White)
                    .padding(15.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = "Your Coins:",
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "$coins",
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Green
                    )

                    Text(
                        text = "+$value coin per click",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(60.dp))

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClickLabel = "Click cat"
                    ) {
                        coins = coins + value
                        catPressed = true
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(
                        id = if (catPressed) R.drawable.cat_open else R.drawable.cat_close
                    ),
                    contentDescription = "Cat",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // Reset cat when clicking outside
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        catPressed = false
                    }
            ) {}

            Spacer(modifier = Modifier.height(80.dp))

            // Upgrade Button
            val canAfford = coins >= upgradePrice
            val nextValue = ceil(value * 1.5).toInt()

            Button(
                onClick = {
                    if (canAfford) {
                        coins = coins - upgradePrice
                        value = ceil(value * 1.5).toInt()
                        upgradePrice = upgradePrice * 2
                    }
                },
                enabled = canAfford,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canAfford) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                    contentColor = if (canAfford) Color.White else Color.Gray,
                    disabledContainerColor = Color(0xFFE0E0E0),
                    disabledContentColor = Color.Gray
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "UPGRADE",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    if (canAfford) {
                        Text(
                            text = "Cost: $upgradePrice coins",
                            fontSize = 14.sp
                        )
                    } else {
                        Text(
                            text = "Need ${upgradePrice - coins} more coins",
                            fontSize = 14.sp
                        )
                    }
                    Text(
                        text = "Next: +$nextValue coin per click",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun View2Preview() {
    View2()
}
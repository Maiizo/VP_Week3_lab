package com.vp.vp_week3_lab.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun View1 (){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(color = Color(0xFF6ECDDC))

    ){
        Text (
            text = Text("Reaction")
        )



    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun View1Preview(){
    View1()
}
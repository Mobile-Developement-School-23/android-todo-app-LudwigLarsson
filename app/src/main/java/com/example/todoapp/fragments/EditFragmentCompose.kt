package com.example.todoapp.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.fragments.AppTheme.colors




@Preview(showBackground = true)
@Composable
fun FragmentPage() {
    AppTheme {

        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = back_primary
        ) {
            val openDialog = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier.fillMaxWidth(),

                verticalArrangement = Arrangement.Top
            ) {
                Toolbar()
                EditField()
                ImportanceBlock()
                DoUntilBlock(openDialog)
                DeleteButton()

            }
        }
    }
}

@Composable
fun Toolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.close),
                contentDescription = "Close",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(40.dp)
                    .padding(10.dp)
            )
        }
        Text(
            text = "СОХРАНИТЬ",
            style = TextStyle(
                color = blue,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
            ),
            modifier = Modifier
                .clickable {

                }
                .align(Alignment.CenterVertically)
                .padding(horizontal = 12.dp, vertical = 10.dp)
        )
    }
}

@Composable
fun EditField() {
    val state = remember { mutableStateOf("") }
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = {
            Text(
                "Что надо сделать...",
                style = TextStyle(
                    color = tertiary
                )
            )
        },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = gray_light,
            unfocusedBorderColor = gray_light,
            cursorColor = blue
        ),
        modifier = Modifier
            .sizeIn(minHeight = 56.dp)
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 10.dp)
    )
}

@Composable
fun ImportanceBlock() {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                //click
            }
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Text(
            text = "Важность",
            style = TextStyle(
                fontSize = 16.sp,
                color = colors.primary
            )
        )
        Text(
            text = "Нет",
            style = TextStyle(
                fontSize = 13.sp,
                color = colors.tertiary
            )
        )
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    color = tertiary
                )
        )
    }
}

@Composable
fun DoUntilBlock(openDialog: MutableState<Boolean>) {
    val checkedState = remember { mutableStateOf(true) }
    Column(
        modifier = Modifier
            .height(110.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    checkedState.value = !checkedState.value
                }
                .padding(vertical = 20.dp, horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Сделать до",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = colors.primary
                    )
                )

                if (checkedState.value) {
                    Text(
                        text = "дата здесь",
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = colors.blue
                        ),
                    )
                }
            }
            Switch(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    if (checkedState.value) {
                        openDialog.value = true
                    }
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = colors.blue,
                    uncheckedThumbColor = colors.gray,
                    uncheckedTrackColor = colors.gray_light,
                    checkedTrackColor = colors.gray_light

                ),
            )

        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(
                    color = tertiary
                )
        )
    }
}

@Composable
fun DeleteButton() {
    Row(
        modifier = Modifier
            .padding(start = 20.dp, top = 10.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
                onClick = {}
            )
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {

        Icon(
            painter = painterResource(id = R.drawable.delete),
            contentDescription = "Delete Icon",
            tint = colors.tertiary
        )
        Text(
            text = "Удалить",
            color = colors.tertiary,
            modifier = Modifier
                .padding(start = 5.dp)
        )
    }
}

package com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.DataUser
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.SimpleUser
import com.rafaelleal.kotlinandroidfundamentals.fundamentals.view.models.UserType


@Composable
fun UserBox(modifier: Modifier = Modifier) {

    val list = dataUser3.userTypes
    list.add(UserType.ADMIN)

    val (dataUserName, dataUserAge, dataUserUserTypes) = dataUser1

    val name = dataUser1.name
    val age = dataUser1.age
    val userTypes = dataUser1.userTypes

    Box(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            TextWithBottomPadding(text = "val simpleUser1 = SimpleUser(\"John\", 30, listOf(UserType.COMMON))")
            TextWithBottomPadding(text = "val simpleUser2 = SimpleUser(\"John\", 30, listOf(UserType.COMMON))")
            TextWithBottomPadding(text = "val dataUser1 = DataUser(\"John\", 30, listOf(UserType.COMMON))")
            TextWithBottomPadding(text = "val dataUser2 = DataUser(\"John\", 30, listOf(UserType.COMMON))")

            HorizontalDivider()

            TextWithBottomPadding(text = "SimpleUser 1: $simpleUser1")
            TextWithBottomPadding(text = "SimpleUser 2: $simpleUser2")

            TextWithBottomPadding(text = "simpleUser1 == simpleUser2: ${simpleUser1 == simpleUser2}")


            HorizontalDivider()

            TextWithBottomPadding(text = "DataUser 1: $dataUser1")

            TextWithBottomPadding(text = "DataUser 2: $dataUser2")

            TextWithBottomPadding(text = "dataUser1 == dataUser2: ${dataUser1 == dataUser2}")

            TextWithBottomPadding(text = "dataUser3: ${dataUser3}")

        }
    }
}

@Composable
fun TextWithBottomPadding(
    text: String, modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.padding(bottom = 14.dp),
        text = text
    )

}

@Preview(showBackground = true, showSystemUi = false)
@Composable
private fun UserBoxPreview() {
    UserBox()
}

val simpleUser1 = SimpleUser("John", 30, listOf(UserType.COMMON))
val simpleUser2 = SimpleUser("John", 30, listOf(UserType.COMMON))

val dataUser1 = DataUser("John", 30, mutableListOf(UserType.COMMON))
val dataUser2 = DataUser("John", 30, mutableListOf(UserType.COMMON))

val dataUser3 = dataUser1.copy(userTypes = dataUser1.userTypes.map { it }.toMutableList())

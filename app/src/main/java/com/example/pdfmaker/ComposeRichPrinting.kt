package com.example.pdfmaker

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zachklipp.richtext.ui.printing.Printable
import com.zachklipp.richtext.ui.printing.hideWhenPrinting
import com.zachklipp.richtext.ui.printing.rememberPrintableController
import com.zachklipp.richtext.ui.printing.responsivePadding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ComposeRichPrinting() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DocumentScreen()
    }

}

@Composable
private fun DocumentScreen() {
    var text = remember{
        mutableStateOf("")
    }
    val printableController = rememberPrintableController()
    val document = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    Column(Modifier.verticalScroll(rememberScrollState())) {
        Printable(
            printableController,
            pageDpi = 96,
            modifier = Modifier.responsivePadding(
                600.dp to 32.dp,
                Dp.Infinity to 96.dp
            )
        ) {
            Column {
                Text(
                    stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.Bold
                )
                Image(modifier= Modifier.align(Alignment.CenterHorizontally),
                    painter = painterResource(id = R.drawable.thranduil),
                    contentDescription ="Icon" )
                Canvas(modifier = Modifier.size(90.dp), onDraw = {
                    drawCircle(color = Color(158, 210, 141), center = Offset(size.width/2, size.height/2))
                })
                Spacer(Modifier.size(24.dp))
                TextField(value = text.value, onValueChange = { text.value = it })
                for (i in 1..10) {
                    Section(i)
                }
                Spacer(Modifier.size(16.dp))
                OutlinedButton(
                    onClick = {
                        printableController.print(document)
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .hideWhenPrinting().align(Alignment.CenterHorizontally),
                ) {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = "stringResource(id = R.string.print)",
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colors.primary
                    )
                    Text(
                        text = "Print",
                        modifier = Modifier.padding(8.dp),
                        color = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun Section(
    sectionNumber: Int,
) {
    Text(
        text = "Section $sectionNumber",
        style = MaterialTheme.typography.h6,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    Text(
        text = "This document is ready to be printed",
        style = MaterialTheme.typography.body2,
    )
}
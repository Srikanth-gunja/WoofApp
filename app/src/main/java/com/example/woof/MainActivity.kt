package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.woof.data.Dog
import com.example.woof.data.dogs
import com.example.woof.ui.theme.AbrilFatface
import com.example.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    WoofApp(
                        dogList = dogs,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                    )
                }
            }
        }
    }
}

@Composable
fun WoofApp(
    dogList: List<Dog>,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {

                    Row(horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .size(dimensionResource(id = R.dimen.image_size))
                                .padding(dimensionResource(id = R.dimen.padding_small)),
                            painter = painterResource(R.drawable.ic_woof_logo),
                            contentDescription = null
                        )
                        Text("Woof", style =  TextStyle(
                            fontFamily = AbrilFatface,
                            fontWeight = FontWeight.Normal,
                            fontSize = 36.sp
                        )
                        )
                    }
                },
                modifier = modifier
            )
        }
    ) {
        LazyColumn(modifier = modifier.padding(it)) {
            items(dogList) { dog ->
                DogItem(dog = dog, modifier = modifier)
            }
        }
    }
}

@Composable
fun DogItem(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .background(Color.Transparent)
            .padding(dimensionResource(R.dimen.padding_small))
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DogIcon(img = dog.imageResourceId)
            DogInfo(dog = dog)
        }
    }
}

@Composable
fun DogIcon(
    @DrawableRes img: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(img),
        contentDescription = null,
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun DogInfo(
    dog: Dog,
    modifier: Modifier = Modifier
) {
    var isSelected by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { isSelected = !isSelected }
    ) {
        Column(modifier = modifier.padding(dimensionResource(R.dimen.padding_small))) {
            Text(
                text = stringResource(dog.name),
               fontFamily = FontFamily(
                   Font(R.font.montserrat_regular),
                   Font(R.font.montserrat_bold, FontWeight.Bold)
               ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "${dog.age} years old")
            if (isSelected) {
                Text(text = stringResource(dog.hobbies))
            }
        }
        if (!isSelected) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Preview
@Composable
fun DogPreview() {
    WoofApp(dogList = dogs)
}

@Preview
@Composable
fun DogDarkPreview() {
    WoofTheme(darkTheme = true) {
        WoofApp(dogList = dogs)
    }
}

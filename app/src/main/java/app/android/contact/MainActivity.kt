package app.android.contact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contact = Contact(
            name = "Рик",
            surname = "Дэ́ниел",
            familyName = "Санчес",
            isFavorite = true,
            phone = "+7 495 495 95 95",
            address = "ул. Льва Толстого, 16, Москва, Россия",
            email = "help@yandex-team.ru"
        )
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactDetails(contact)
                }
            }
        }
    }
}

@Composable
fun ContactDetails(contact: Contact, modifier: Modifier = Modifier) {
    val initials = "${contact.name.firstOrNull()}${contact.surname?.firstOrNull() ?: ""}"
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        if (contact.imageRes != null) {
            ProfileImage(imageRes = contact.imageRes)
        } else {
            InitialsCircle(initials = initials)
        }

        Spacer(modifier = Modifier.height(16.dp))

        NameRow(name = contact.name, surname = contact.surname)
        FamilyNameRow(familyName = contact.familyName, isFavorite = contact.isFavorite)

        InfoRow(label = "Телефон", value = contact.phone.ifEmpty { "---" })
        InfoRow(label = "Адрес", value = contact.address)

        contact.email?.let {
            InfoRow(label = "E-mail", value = it)
        }
    }
}

@Composable
fun ProfileImage(imageRes: Int, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = imageRes),
        contentDescription = null,
        modifier = modifier
            .size(120.dp)
            .background(Color.Gray, shape = CircleShape),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun InitialsCircle(initials: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(120.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
                .background(Color.Gray, shape = CircleShape)
        ) {
            Text(
                text = initials,
                color = Color.White,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun NameRow(name: String, surname: String?, modifier: Modifier = Modifier) {
    Row {
        Text(
            text = "$name ",
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
        )
        Text(
            text = surname.orEmpty(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = modifier
        )
    }
}

@Composable
fun FamilyNameRow(familyName: String, isFavorite: Boolean, modifier: Modifier = Modifier) {
    Row {
        Text(
            text = familyName,
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier
        )
        if (isFavorite) {
            Icon(
                painter = painterResource(id = android.R.drawable.star_big_on),
                contentDescription = null,
                tint = Color.Yellow,
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp)
                    .size(24.dp)
            )
        }
    }
}

@Composable
fun InfoRow(label: String, value: String, modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Right
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFavoriteContact() {
    ContactDetails(
        contact = Contact(
            name = "Рик",
            surname = "Дэ́ниел",
            familyName = "Санчес",
            isFavorite = true,
            phone = "+7 495 495 95 95",
            address = "ул. Льва Толстого, 16, Москва, Россия",
            email = "help@yandex-team.ru"
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewNonFavoriteContactWithPhoto() {
    ContactDetails(
        contact = Contact(
            name = "Рик",
            familyName = "Санчес",
            imageRes = R.drawable.sample_photo,
            phone = "",
            address = "ул. Льва Толстого, 16, Москва, Россия"
        )
    )
}

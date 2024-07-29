package app.android.contact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
fun ContactDetails(contact: Contact) {
    val fio = "${contact.name.firstOrNull()}${contact.surname?.firstOrNull() ?: ""}"
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (contact.imageRes != null) {
            Image(
                painter = painterResource(id = contact.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .background(Color.Gray, shape = CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(120.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Gray, shape = CircleShape)
                ) {
                    Text(
                        text = fio,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text(
                text = "${contact.name} ",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = contact.surname.orEmpty(),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        Row {
            Text(
                text = contact.familyName,
                style = MaterialTheme.typography.headlineMedium
            )
            if (contact.isFavorite) {
                Image(
                    painter = painterResource(id = android.R.drawable.star_big_on),
                    contentDescription = null,
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                )
            }
        }

        InfoRow(label = "Телефон", value = contact.phone.ifEmpty { "---" })
        InfoRow(label = "Адрес", value = contact.address)

        contact.email?.let {
            InfoRow(label = "E-mail", value = it)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
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
            phone = "+7 495 495 95 95",
            address = "ул. Льва Толстого, 16, Москва, Россия"
        )
    )
}

package com.example.call2

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity

@Composable
fun Contacts() {
    val context = LocalContext.current
    val contacts = remember {
        listOf(
            Contact("Ethan Matthews", "ethan@example.com", "1234567890", R.drawable.ic_contact),
            Contact("Lucas Carter", "lucas@example.com", "0987654321", R.drawable.ic_contact),
            Contact("Benjamin Adams", "benjamin@example.com", "5555555555", R.drawable.ic_contact),
            Contact("Samuel Thomson", "samuel@example.com", "6666666666", R.drawable.ic_contact),
            Contact("Emma Bennett", "emma@example.com", "7777777777", R.drawable.ic_contact),
            Contact("Liz Bacon", "liz@example.com", "8888888888", R.drawable.ic_contact)
        )
    }
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column {
        Spacer(modifier = Modifier.padding(top = 16.dp))
        SearchBar(searchQuery) {
            searchQuery = it
        }
        ContactList(contacts, searchQuery.text) { phoneNumber ->
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse("tel:${phoneNumber}")
            context.startActivity(intent)
        }
    }
}





@Composable
fun SearchBar(searchQuery: TextFieldValue, onValueChange: (TextFieldValue) -> Unit) {
    TextField(
        value = searchQuery,
        onValueChange = onValueChange,

        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        )
}

@Composable
fun ContactList(contacts: List<Contact>, searchQuery: String, onContactClick: (String) -> Unit) {
    val filteredContacts = contacts.filter {
        it.name.contains(searchQuery, ignoreCase = true)
    }
    LazyColumn {
        items(filteredContacts) { contact ->
            ContactItem(contact, onContactClick)
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onContactClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onContactClick(contact.phoneNumber) }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = contact.imageResId),
            contentDescription = contact.name,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = contact.name)
            Text(text = contact.phoneNumber, color = Color.Gray)
            Text(text = contact.email, color = Color.Gray)
        }
    }
}


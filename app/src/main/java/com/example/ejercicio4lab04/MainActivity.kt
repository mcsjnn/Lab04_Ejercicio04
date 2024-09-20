package com.example.ejercicio4lab04

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ejercicio4lab04.ui.theme.Ejercicio4Lab04Theme

data class Route(val name: String, var isFavorite: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio4Lab04Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TransportRouteApp()
                }
            }
        }
    }
}

@Composable
fun TransportRouteApp(modifier: Modifier = Modifier) {
    var routeName by rememberSaveable { mutableStateOf("") }
    var routes by rememberSaveable { mutableStateOf(listOf<Route>()) }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Rutas agregadas: ${routes.size}")
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = routeName,
            onValueChange = { routeName = it },
            label = { Text("Nombre de la ruta") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (routeName.isNotBlank()) {
                routes = routes + Route(routeName, false)
                routeName = ""
            }
        }) {
            Text("Agregar Ruta")
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar las rutas agregadas en Cards
        Text("Rutas:")
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            routes.forEachIndexed { index, route ->
                RouteCard(route = route, onFavoriteClick = {
                    routes = routes.toMutableList().also { it[index] = route.copy(isFavorite = !route.isFavorite) }
                })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun RouteCard(route: Route, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = route.name,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (route.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Marcar como favorito"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransportRouteApp() {
    TransportRouteApp()
}

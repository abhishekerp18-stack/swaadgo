package com.swaadgo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Restaurant(
    val id: Int,
    val name: String,
    val cuisine: String,
    val rating: String,
    val eta: String
)

data class Food(val id: Int, val name: String, val price: Int, val restaurant: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SwaadgoApp() }
    }
}

@Composable
fun SwaadgoApp() {
    var screen by remember { mutableStateOf("home") }
    var cartCount by remember { mutableIntStateOf(0) }

    val restaurants = listOf(
        Restaurant(1, "SWAADGO Kitchen", "Indian • North Indian", "4.6", "25 min"),
        Restaurant(2, "Royal Tandoor", "Biryani • Mughlai", "4.5", "30 min"),
        Restaurant(3, "Pizza Corner", "Pizza • Fast Food", "4.3", "20 min")
    )

    val foods = listOf(
        Food(1, "Paneer Butter Masala", 180, "SWAADGO Kitchen"),
        Food(2, "Chicken Biryani", 220, "Royal Tandoor"),
        Food(3, "Veg Pizza", 199, "Pizza Corner"),
        Food(4, "Masala Dosa", 120, "SWAADGO Kitchen")
    )

    MaterialTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    NavigationBarItem(screen=="home", { screen="home" }, icon={}, label={Text("Home")})
                    NavigationBarItem(screen=="orders", { screen="orders" }, icon={}, label={Text("Orders")})
                    NavigationBarItem(screen=="cart", { screen="cart" }, icon={}, label={Text("Cart $cartCount")})
                    NavigationBarItem(screen=="profile", { screen="profile" }, icon={}, label={Text("Profile")})
                }
            }
        ) { pad ->
            when(screen) {
                "home" -> HomeScreen(
                    Modifier.padding(pad), restaurants, foods,
                    onAdd = { cartCount++ },
                    onOpenRestaurant = { screen="restaurant" }
                )
                "restaurant" -> RestaurantScreen(
                    Modifier.padding(pad), foods,
                    onAdd = { cartCount++ },
                    onBack = { screen="home" }
                )
                "cart" -> CartScreen(Modifier.padding(pad), cartCount)
                "orders" -> OrdersScreen(Modifier.padding(pad))
                else -> ProfileScreen(Modifier.padding(pad))
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier,
    restaurants: List<Restaurant>,
    foods: List<Food>,
    onAdd: () -> Unit,
    onOpenRestaurant: () -> Unit
) {
    Column(modifier.fillMaxSize().background(Color(0xFFF7F7F7))) {
        Column(Modifier.padding(18.dp)) {
            Text("SWAADGO", fontSize=30.sp, fontWeight=FontWeight.ExtraBold, color=Color(0xFFE23744))
            Text("Delicious food, delivered fast", color=Color.Gray)
            Spacer(Modifier.height(14.dp))
            Card(shape=RoundedCornerShape(18.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("📍 Deliver to", fontWeight=FontWeight.Bold)
                    Text("Select your delivery location", color=Color.Gray)
                }
            }
            Spacer(Modifier.height(14.dp))
            Text("Popular near you", fontSize=20.sp, fontWeight=FontWeight.Bold)
        }
        LazyColumn(contentPadding=PaddingValues(horizontal=18.dp, vertical=8.dp)) {
            items(restaurants) { r ->
                Card(
                    Modifier.fillMaxWidth().padding(bottom=12.dp).clickable { onOpenRestaurant() },
                    shape=RoundedCornerShape(18.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(r.name, fontSize=19.sp, fontWeight=FontWeight.Bold)
                        Text(r.cuisine, color=Color.Gray)
                        Spacer(Modifier.height(8.dp))
                        Row(horizontalArrangement=Arrangement.spacedBy(16.dp)) {
                            Text("★ ${r.rating}")
                            Text("🕒 ${r.eta}")
                        }
                    }
                }
            }
            item {
                Spacer(Modifier.height(4.dp))
                Text("Recommended dishes", fontSize=20.sp, fontWeight=FontWeight.Bold)
            }
            items(foods) { f ->
                FoodCard(f, onAdd)
            }
        }
    }
}

@Composable
fun RestaurantScreen(modifier: Modifier, foods: List<Food>, onAdd: () -> Unit, onBack: () -> Unit) {
    Column(modifier.fillMaxSize().background(Color(0xFFF7F7F7))) {
        Row(Modifier.fillMaxWidth().padding(18.dp), verticalAlignment=Alignment.CenterVertically) {
            Text("‹", fontSize=38.sp, modifier=Modifier.clickable{onBack()})
            Spacer(Modifier.width(12.dp))
            Column {
                Text("SWAADGO Kitchen", fontSize=22.sp, fontWeight=FontWeight.Bold)
                Text("Indian • 4.6 ★ • 25 min", color=Color.Gray)
            }
        }
        LazyColumn(contentPadding=PaddingValues(18.dp)) {
            items(foods) { FoodCard(it, onAdd) }
        }
    }
}

@Composable
fun FoodCard(food: Food, onAdd: () -> Unit) {
    Card(Modifier.fillMaxWidth().padding(bottom=10.dp), shape=RoundedCornerShape(16.dp)) {
        Row(Modifier.padding(15.dp), verticalAlignment=Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(food.name, fontWeight=FontWeight.Bold, fontSize=17.sp)
                Text("₹${food.price}", fontWeight=FontWeight.SemiBold)
                Text(food.restaurant, color=Color.Gray, fontSize=12.sp)
            }
            Button(onClick=onAdd, shape=RoundedCornerShape(12.dp)) { Text("ADD") }
        }
    }
}

@Composable
fun CartScreen(modifier: Modifier, count: Int) {
    Column(modifier.fillMaxSize().padding(20.dp)) {
        Text("Your Cart", fontSize=28.sp, fontWeight=FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        if (count == 0) Text("Your cart is empty.", color=Color.Gray)
        else {
            Text("$count item(s) added")
            Spacer(Modifier.height(20.dp))
            Button(
                onClick = {},
                modifier=Modifier.fillMaxWidth(),
                shape=RoundedCornerShape(14.dp)
            ) { Text("PROCEED TO CHECKOUT") }
        }
    }
}

@Composable
fun OrdersScreen(modifier: Modifier) {
    Column(modifier.fillMaxSize().padding(20.dp)) {
        Text("My Orders", fontSize=28.sp, fontWeight=FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        Card(Modifier.fillMaxWidth(), shape=RoundedCornerShape(18.dp)) {
            Column(Modifier.padding(18.dp)) {
                Text("No live orders", fontWeight=FontWeight.Bold)
                Text("Your active and previous orders will appear here.", color=Color.Gray)
            }
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier) {
    Column(modifier.fillMaxSize().padding(20.dp)) {
        Text("Profile", fontSize=28.sp, fontWeight=FontWeight.Bold)
        Spacer(Modifier.height(20.dp))
        listOf("Login / Register", "Saved Addresses", "Offers & Coupons", "Help & Support").forEach {
            Card(Modifier.fillMaxWidth().padding(bottom=10.dp), shape=RoundedCornerShape(14.dp)) {
                Text(it, Modifier.padding(18.dp), fontWeight=FontWeight.SemiBold)
            }
        }
    }
}

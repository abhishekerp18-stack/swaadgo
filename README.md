# SWAADGO Android App

This is the native Android starter app for SWAADGO.

## Open
Open this folder in Android Studio and let Gradle sync.

## Current UI
- SWAADGO branded home screen
- Restaurant listing
- Restaurant/menu screen
- Add-to-cart counter
- Cart screen
- Orders screen
- Profile screen
- Internet and GPS permissions

## Backend connection
The app is intentionally separated from the PHP backend so your existing SWAADGO database is not changed.

Next integration should expose secure PHP JSON API endpoints for:
- login/register
- restaurants
- menu
- cart
- checkout/order creation
- order status
- delivery partner GPS
- customer order tracking

Do NOT put MySQL credentials inside the Android app. The app must call PHP APIs over HTTPS.

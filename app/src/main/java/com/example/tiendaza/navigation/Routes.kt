package com.example.tiendaza.navigation

object Routes {
    const val HOME = "home"

    const val SEARCH = "search"
    const val PROFILE = "profile"

    const val SELL ="sell"

    const val CART ="cart"


    const val DETAIL = "detail/{itemId}"
    fun detailRoute(itemId: Int) = "detail/$itemId"
}
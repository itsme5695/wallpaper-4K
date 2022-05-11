package com.example.newhdwallpaper.networkhelper
import android.content.Context;
import android.net.ConnectivityManager;
class NetworkHelper() {
        fun isNetworkAvailable(context: Context) :Boolean {
            var connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)
            var activeNetworkInfo = connectivityManager.activeNetworkInfo;
            return activeNetworkInfo != null && activeNetworkInfo.isConnected;
        }
}
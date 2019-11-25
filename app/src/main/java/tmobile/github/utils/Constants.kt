package tmobile.github.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

/**
 * Created by Amanjeet Singh on 29/11/17.
 */
public class Constants {

    companion object {


        fun isOnline(context: Context?): Boolean {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected

        }

        fun toast(msg: String,context: Context?) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

        @JvmField
        val BASE_URL = "https://api.github.com";

    }



}
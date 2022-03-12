package com.app.ravn_challenge.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.app.ravn_challenge.utilities.SERVER_URL
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null

/**
 * Class used to connect Apollo to fetch People of the repository
 */

class Apollo{

    fun getApolloClient(): ApolloClient {
        if (instance != null) {
            return instance!!
        }

        val okHttpClient = OkHttpClient.Builder()
            .build()

        instance = ApolloClient.Builder()
            .serverUrl(SERVER_URL)
            .okHttpClient(okHttpClient)
            .build()

        return instance!!
    }

}



package com.app.ravn_challenge.network

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

private var instance: ApolloClient? = null
private const val SERVER_URL = "https://swapi-graphql.netlify.app/.netlify/functions/index"

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



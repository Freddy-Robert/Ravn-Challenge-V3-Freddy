package com.app.ravn_challenge.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.app.ravn_challenge.AllPeopleQuery
import com.app.ravn_challenge.PeopleQuery

interface PeopleRepository {

    suspend fun getAllPeople(): ApolloResponse<AllPeopleQuery.Data>

    suspend fun getPerson(id: String): ApolloResponse<PeopleQuery.Data>

}
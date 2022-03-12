package com.app.ravn_challenge.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.app.ravn_challenge.AllPeopleQuery
import com.app.ravn_challenge.PeopleQuery
import com.app.ravn_challenge.network.Apollo
import javax.inject.Inject

/**
 * Repository to fetch data from the webService
 */

class PeopleRepositoryImpl @Inject constructor(private val apolloService: Apollo): PeopleRepository {

    override suspend fun getAllPeople(): ApolloResponse<AllPeopleQuery.Data> {
        return apolloService.getApolloClient().query(AllPeopleQuery()).execute()
    }

    override suspend fun getPerson(id: String): ApolloResponse<PeopleQuery.Data> {
        return apolloService.getApolloClient().query(PeopleQuery(Optional.presentIfNotNull(id))).execute()
    }

}

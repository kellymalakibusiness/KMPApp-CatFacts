package com.malakiapps.catfacts.data.fact

import com.malakiapps.catfacts.common.di.handleError
import com.malakiapps.catfacts.data.common.NetworkError
import com.malakiapps.catfacts.data.common.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class HttpFactRepository(
    private val httpClient: HttpClient
): FactRepository {
    override suspend fun getFacts(count: Int): Result<List<String>, NetworkError> {
        val response = try {
            httpClient.get("https://meowfacts.herokuapp.com"){
                parameter("count", count)
            }
        } catch (e: UnresolvedAddressException){
            return Result.ResultError(NetworkError.NO_INTERNET)
        } catch (e: SerializationException){
            return Result.ResultError(NetworkError.SERIALIZATION)
        }

        return when(response.status.value){
            //Success codes
            in 200..299 -> {
                val facts = response.body<CatFactListDOT>()
                Result.ResultSuccess(facts.data)
            }
            else ->{
                response.status.handleError()
            }
        }
    }
}
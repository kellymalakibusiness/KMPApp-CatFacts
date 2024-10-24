package com.malakiapps.catfacts.data.image

import com.malakiapps.catfacts.common.di.handleError
import com.malakiapps.catfacts.data.common.NetworkError
import com.malakiapps.catfacts.data.common.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import okio.IOException

class HttpCatImageRepository(
    private val httpClient: HttpClient
): CatImageRepository {
    override suspend fun getImages(count: Int): Result<List<CatImageDOT>, NetworkError> {
        val response = try {
            httpClient.get("https://api.thecatapi.com/v1/images/search"){
                parameter("limit", count)
            }
        } catch (e: UnresolvedAddressException){
            return Result.ResultError(NetworkError.NO_INTERNET)
        } catch (e: SerializationException){
            return Result.ResultError(NetworkError.SERIALIZATION)
        } catch (e: IOException) {
            return Result.ResultError(NetworkError.NO_INTERNET)
        } catch (e: Exception){
            return Result.ResultError(NetworkError.UNKNOWN)
        }

        return when(response.status.value){
            //Success codes
            in 200..299 -> {
                val facts = response.body<List<CatImageDOT>>()
                Result.ResultSuccess(facts)
            }
            else ->{
                response.status.handleError()
            }
        }
    }
}
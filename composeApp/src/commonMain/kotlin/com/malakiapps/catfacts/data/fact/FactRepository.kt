package com.malakiapps.catfacts.data.fact

import com.malakiapps.catfacts.data.common.NetworkError
import com.malakiapps.catfacts.data.common.Result

interface FactRepository {
    suspend fun getFacts(count: Int): Result<List<String>, NetworkError>
}

class TestFactRepository: FactRepository {
    override suspend fun getFacts(count: Int): Result<List<String>, NetworkError> {
        TODO()
    }

}
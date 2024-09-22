package com.malakiapps.catfacts.data.image

import com.malakiapps.catfacts.data.common.NetworkError
import com.malakiapps.catfacts.data.common.Result

class DummyImageRepository : CatImageRepository {
    private val hardCodedImages = listOf(
        CatImageDOT(
            id = "8vi",
            url = "https://cdn2.thecatapi.com/images/8vi.jpg",
            width = 424,
            height = 720
        ),
        CatImageDOT(
            id = "a33",
            url = "https://cdn2.thecatapi.com/images/a33.jpg",
            width = 480,
            height = 360
        ),
        CatImageDOT(
            id = "ac5",
            url = "https://cdn2.thecatapi.com/images/ac5.jpg",
            width = 565,
            height = 551
        ),
        CatImageDOT(
            id = "amv",
            url = "https://cdn2.thecatapi.com/images/amv.jpg",
            width = 900,
            height = 675
        ),
        CatImageDOT(
            id = "b9b",
            url = "https://cdn2.thecatapi.com/images/b9b.jpg",
            width = 500,
            height = 352
        ),
        CatImageDOT(
            id = "c8e",
            url = "https://cdn2.thecatapi.com/images/c8e.jpg",
            width = 640,
            height = 480
        ),
        CatImageDOT(
            id = "cej",
            url = "https://cdn2.thecatapi.com/images/cej.jpg",
            width = 640,
            height = 427
        ),
        CatImageDOT(
            id = "ea6",
            url = "https://cdn2.thecatapi.com/images/ea6.gif",
            width = 332,
            height = 182
        ),
        CatImageDOT(
            id = "MTkyNDc3MA",
            url = "https://cdn2.thecatapi.com/images/MTkyNDc3MA.jpg",
            width = 640,
            height = 428
        ),
        CatImageDOT(
            id = "stVqmJmi7",
            url = "https://cdn2.thecatapi.com/images/stVqmJmi7.jpg",
            width = 1400,
            height = 1050
        )

    )

    override suspend fun getImages(count: Int): Result<List<CatImageDOT>, NetworkError> {
        return Result.ResultSuccess(
            hardCodedImages.subList(0, count)
        )
    }
}
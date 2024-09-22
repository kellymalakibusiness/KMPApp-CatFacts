package com.malakiapps.catfacts.data.fact

import com.malakiapps.catfacts.data.common.NetworkError
import com.malakiapps.catfacts.data.common.Result
import kotlinx.coroutines.delay

class DummyFactRepository : FactRepository {
    private val hardCodedFacts = listOf(
        "Jaguars are the only big cats that don't roar.",
        "The worlds largest cat measured 48.5 inches long. https://www.youtube.com/watch?v=gc5M0aGc_EI",
        "The Maine Coone is the only native American long haired breed.",
        "Your cat recognizes your voice but just acts too cool to care (probably because they are).",
        "A cat has ran for mayor of Mexico City in 2013.",
        "Cats can get tapeworms from eating fleas. These worms live inside the cat forever, or until they are removed with medication. They reproduce by shedding a link from the end of their long bodies. This link crawls out the cat's anus, and sheds hundreds of eggs. These eggs are injested by flea larvae, and the cycles continues. Humans may get these tapeworms too, but only if they eat infected fleas. Cats with tapeworms should be dewormed by a veterinarian.",
        "A cat has been mayor of Talkeetna, Alaska, for 15 years. His name is Stubbs.",
        "Neutering a cat extends its life span by two or three years.",
        "A cat has more bones than a human; humans have 206, and the cat - 230.",
        "All cats have three sets of long hairs that are sensitive to pressure - whiskers, eyebrows,and the hairs between their paw pads."
    )

    var noOfCalls = 0
    override suspend fun getFacts(count: Int): Result<List<String>, NetworkError> {
        println("DummyFact made an api call:$noOfCalls")
        noOfCalls++
        delay(2000)
        return Result.ResultSuccess(
            hardCodedFacts.subList(0, count)
        )
    }
}
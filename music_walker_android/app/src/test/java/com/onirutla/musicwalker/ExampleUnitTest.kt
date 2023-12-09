package com.onirutla.musicwalker

import com.onirutla.musicwalker.ui.util.toStringTime
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val expectedTimeString = "01:30"
        val actualTime = 90070L
        val actualString = actualTime.toStringTime()
        assertEquals(expectedTimeString, actualString)
    }
}

package me.ddayo.coroutine.functions

import java.time.LocalDateTime

class WaitSeconds(private val time: Double): CoroutineFunctions {
    private val s = LocalDateTime.now()

    override fun next() = LocalDateTime.now().minusNanos((time * 1000000000 + 1).toLong()).isAfter(s)
}
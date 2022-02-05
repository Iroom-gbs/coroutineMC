package com.dayo.coroutine

import com.dayo.coroutine.functions.CoroutineFunctions
import com.dayo.coroutine.utils.Lock
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class Coroutine : JavaPlugin() {
    companion object {
        val functions = emptyList<Iterator<CoroutineFunctions?>>().toMutableList()
        val yieldList = emptyList<CoroutineFunctions?>().toMutableList()
        val rmList = emptyList<Int>().toMutableList()
        val lock = Lock()

        fun startCoroutine(f: Sequence<CoroutineFunctions>) {
            lock.withLock {
                functions.add(f.iterator())
                yieldList.add(null)
            }
            println("Registered")
        }
    }

    override fun onEnable() {
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            lock.withLock {
                rmList.clear()
                for (fi in functions.withIndex()) {
                    val index = fi.index
                    if (yieldList[index] == null || yieldList[index]!!.next()) {
                        val f = fi.value
                        if (f.hasNext())
                            yieldList[index] = f.next()
                        else rmList.add(fi.index)
                    }
                }
                rmList.reverse()
                for (i in rmList)
                    functions.removeAt(i)
            }
        }, 1L, 1L)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
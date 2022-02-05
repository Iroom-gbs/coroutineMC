package com.dayo.coroutine.utils

class Lock {
    private var lockStatus = false
    public fun withLock(f: () -> Unit) {
        while(lockStatus);
        lockStatus = true
        f()
        lockStatus = false
    }
}
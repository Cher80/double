package ru.pubg

class Pinger(private val resolver: Resolver) {
    init {
        val thread = Thread {
            var i =0
            while (true) {
                safePrint("${Thread.currentThread().name} alive ${i++} deltaY = ${resolver.deltaY} enabled = ${resolver.enable}")
                Thread.sleep(1900)
            }
        }
        thread.start()
    }
}
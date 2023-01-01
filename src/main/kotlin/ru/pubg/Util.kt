package ru.pubg

import kotlin.math.abs


/**
 * Use this printer to avoid windows command line selection mode lock
 */
fun safePrint(message: String) {
    val thread = Thread {
        System.out.println(message)
    }
    thread.start()
}

fun inThread(f: ()->Unit) {
    val thread = Thread {
        f.invoke()
    }
    thread.start()
}


inline fun <reified T: Enum<T>> T.next(): T {
    val values = enumValues<T>()
    val nextOrdinal = (ordinal + 1) % values.size
    println("asdsadsa nextOrdinal=$nextOrdinal values.size=${values.size} ordinal=$ordinal this=$this")
    return values[nextOrdinal]
}

inline fun <reified T: Enum<T>> T.prev(): T {
    val values = enumValues<T>()
    val nextOrdinal =if (ordinal - 1 >= 0) ordinal - 1 else values.size - 1
    return values[nextOrdinal]
}
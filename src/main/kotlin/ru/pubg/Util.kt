package ru.pubg


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

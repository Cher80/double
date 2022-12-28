package ru.pubg

import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class Sounds {
    private val clipOn: Clip = AudioSystem.getClip()
    private val clipOff: Clip = AudioSystem.getClip()
    private val clipSaved: Clip = AudioSystem.getClip()
    private val startSave: Clip = AudioSystem.getClip()


    init {
        clipOn.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/on_new.wav")))
        clipOff.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/error.wav")))
        clipSaved.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/saved.wav")))
        startSave.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/startsave.wav")))
    }

    fun playOn() {
        clipOn.stop()
        clipOn.framePosition = 0
        clipOn.start()

    }

    fun playOff() {
        clipOn.stop()
        clipOff.framePosition = 0
        clipOff.start()
    }

    fun playSaved() {
        clipOn.stop()
        clipSaved.framePosition = 0
        clipSaved.start()
    }

    fun playStartSave() {
        clipOn.stop()
        startSave.framePosition = 0
        startSave.start()
    }
}
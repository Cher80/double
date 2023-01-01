package ru.pubg

import ru.pubg.items.Guns
import ru.pubg.items.Nasadki
import ru.pubg.items.Ruschki
import ru.pubg.items.Scopes
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

class Sounds {
    private val clipOn: Clip = AudioSystem.getClip()
    private val clipOff: Clip = AudioSystem.getClip()
    private val clipSaved: Clip = AudioSystem.getClip()
    private val startSave: Clip = AudioSystem.getClip()

    private val clipMini: Clip = AudioSystem.getClip()
    private val clipMk12: Clip = AudioSystem.getClip()

    private val clipNasadkaKompens: Clip = AudioSystem.getClip()
    private val clipNasadkaPlamya: Clip = AudioSystem.getClip()
    private val clipNasadkaNo: Clip = AudioSystem.getClip()

    private val clipRuchkaLegkay: Clip = AudioSystem.getClip()
    private val clipRuchkaVert: Clip = AudioSystem.getClip()
    private val clipRuchkaNo: Clip = AudioSystem.getClip()

    private val clipScope4: Clip = AudioSystem.getClip()
    private val clipScope6: Clip = AudioSystem.getClip()
    private val clipScope8: Clip = AudioSystem.getClip()
    private val clipScope15: Clip = AudioSystem.getClip()


    init {
        clipOn.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/on_new.wav")))
        clipOff.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/error.wav")))
        clipSaved.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/saved.wav")))
        startSave.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/startsave.wav")))

        clipMk12.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/gun_mk12.wav")))
        clipMini.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/gun_mini.wav")))

        clipNasadkaKompens.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/nasadka_kompens.wav")))
        clipNasadkaPlamya.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/nasadka_plamya.wav")))
        clipNasadkaNo.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/nasadka_no.wav")))

        clipRuchkaLegkay.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/ruchka_legkaya.wav")))
        clipRuchkaVert.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/ruchka_vert.wav")))
        clipRuchkaNo.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/ruchka_no.wav")))

        clipScope4.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/scope_4x.wav")))
        clipScope6.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/scope_6x.wav")))
        clipScope8.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/scope_8x.wav")))
        clipScope15.open(AudioSystem.getAudioInputStream(object {}.javaClass.getResource("/scope_15x.wav")))
    }

    fun playOn() {
        clipOn.stop()
        clipOn.framePosition = 0
        clipOn.start()

    }

    fun playOff() {
        clipOff.stop()
        clipOff.framePosition = 0
        clipOff.start()
    }

    fun playSaved() {
        clipSaved.stop()
        clipSaved.framePosition = 0
        clipSaved.start()
    }

    fun playStartSave() {
        startSave.stop()
        startSave.framePosition = 0
        startSave.start()
    }


    fun playRifleMini() = playWith(clipMini)

    fun playRifleMk12() = playWith(clipMk12)

    fun playRuchkaLegkaya() = playWith(clipRuchkaLegkay)

    fun playRuchkaVert() = playWith(clipRuchkaVert)

    fun playRuchkaNo() = playWith(clipRuchkaNo)

    fun playNasadkaKompens() = playWith(clipNasadkaKompens)

    fun playNasadkaPlamya() = playWith(clipNasadkaPlamya)

    fun playNasadkaNo() = playWith(clipNasadkaNo)

    fun playScope4() = playWith(clipScope4)

    fun playScope6() = playWith(clipScope6)

    fun playScope8() = playWith(clipScope8)

    fun playScope15() = playWith(clipScope15)

    fun mapGun(gun: Guns) {
        when (gun) {
            Guns.MK12 -> playRifleMk12()
            Guns.MINI -> playRifleMini()
        }
    }

    fun mapNasadki(nasadki: Nasadki) {
        when (nasadki) {
            Nasadki.PLAMYA -> playNasadkaPlamya()
            Nasadki.KOMPENS -> playNasadkaKompens()
            Nasadki.NO -> playNasadkaNo()
        }
    }

    fun mapRuchki(rucchki: Ruschki) {
        when (rucchki) {
            Ruschki.LEGKAYA -> playRuchkaLegkaya()
            Ruschki.VERT -> playRuchkaVert()
            Ruschki.NO -> playRuchkaNo()
        }
    }

    fun mapScope(scopes: Scopes) {
        when (scopes) {
            Scopes.X4 -> playScope4()
            Scopes.X6 -> playScope6()
            Scopes.X8 -> playScope8()
            Scopes.X15 -> playScope15()
        }
    }

    private fun playWith(clip: Clip) {
        clip.stop()
        clip.framePosition = 0
        clip.start()
    }
}
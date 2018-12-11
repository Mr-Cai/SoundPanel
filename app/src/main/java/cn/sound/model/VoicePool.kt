package cn.sound.model

import android.content.Context
import android.content.res.AssetManager
import android.media.SoundPool

class VoicePool(context: Context) {
    private val assets: AssetManager = context.assets
    val sounds: List<Sound>
    private val soundPool = SoundPool.Builder().setMaxStreams(5).build()!!

    init {
        sounds = assets.list("sounds")!!.map { soundName ->
            val soundPath = "sounds/$soundName"
            val soundId = soundPool.load(assets.openFd(soundPath), 1)
            Sound(soundPath, soundId)
        }
    }

    fun play(sound: Sound) {
        soundPool.play(sound.soundId, 1f, 1f, 1, 0, 1f)
    }

    fun release() = soundPool.release()
}
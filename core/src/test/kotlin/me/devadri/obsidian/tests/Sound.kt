package me.devadri.obsidian.tests

import com.cryptomorin.xseries.XSound
import me.devadri.obsidian.platform.AbstractTestPlatform
import me.devadri.obsidian.sound.Sound
import me.devadri.obsidian.sound.XSoundCategory
import kotlin.test.Test
import kotlin.test.assertEquals

class Sound : AbstractTestPlatform() {

    @Test
    fun `Create Sound`() {
        val sound = Sound.builder()
            .sound(XSound.BLOCK_NOTE_BLOCK_PLING)
            .category(XSoundCategory.BLOCKS)
            .volume(0.5f)
            .pitch(0.5f)
            .build()

        assertEquals(sound.sound, XSound.BLOCK_NOTE_BLOCK_PLING.get(), "Sound should be BLOCK_NOTE_BLOCK_PLING")
        assertEquals(sound.category, XSoundCategory.BLOCKS, "Sound category should be BLOCKS")
        assertEquals(sound.volume, 0.5f, "Sound volume should be 0.5f")
        assertEquals(sound.pitch, 0.5f, "Sound pitch should be 0.5f")
    }
}
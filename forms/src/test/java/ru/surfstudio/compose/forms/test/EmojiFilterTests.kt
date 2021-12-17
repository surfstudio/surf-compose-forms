package ru.surfstudio.compose.forms.test

import org.junit.Test
import ru.surfstudio.compose.forms.emoji.EmojiUtils

class EmojiFilterTests {

    @Test
    fun testEmojiPredicate() {
        filterEmoji {
            !EmojiUtils.containsEmoji(it)
        }
    }

    @Test
    fun testEmojiFilter() {
        filterEmoji {
            EmojiUtils.removeEmoji(it).isNotEmpty()
        }
    }

    private fun filterEmoji(predicate: (String) -> Boolean) {
        val result = ALL_EMOJI_LIST.filter(predicate)
        assert(result.isEmpty()) { "$result is not empty" }
    }
}

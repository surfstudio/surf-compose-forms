package ru.surfstudio.compose.forms.emoji

/**
 * Support Emoji 13.1 2020-09-12
 * @link https://unicode.org/Public/emoji/13.1/emoji-test.txt
 *
 * Util class for filter emoji
 */
object EmojiUtils {

    private const val VARIATION_SELECTOR = "FE0F"
    private const val EMOJI_PARTS_GLUE = "200D"

    private val glueSymbols = arrayOf(
        VARIATION_SELECTOR,
        EMOJI_PARTS_GLUE
    )

    private val emojiUnicodeBlocks = arrayOf(
        Character.UnicodeBlock.EMOTICONS,
        Character.UnicodeBlock.MAHJONG_TILES,
        Character.UnicodeBlock.DOMINO_TILES,
        Character.UnicodeBlock.PLAYING_CARDS,
        Character.UnicodeBlock.ENCLOSED_ALPHANUMERICS,
        Character.UnicodeBlock.ENCLOSED_ALPHANUMERIC_SUPPLEMENT,
        Character.UnicodeBlock.ENCLOSED_IDEOGRAPHIC_SUPPLEMENT,
        Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS,
        Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS,
        Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS,
        Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_ARROWS,
        Character.UnicodeBlock.MISCELLANEOUS_TECHNICAL,
        Character.UnicodeBlock.DINGBATS,
        Character.UnicodeBlock.TRANSPORT_AND_MAP_SYMBOLS,
        Character.UnicodeBlock.GEOMETRIC_SHAPES,
        Character.UnicodeBlock.ALCHEMICAL_SYMBOLS,
        Character.UnicodeBlock.ARROWS,
        Character.UnicodeBlock.SUPPLEMENTAL_ARROWS_A,
        Character.UnicodeBlock.SUPPLEMENTAL_ARROWS_B,
        Character.UnicodeBlock.COMBINING_MARKS_FOR_SYMBOLS,
        Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION,
        Character.UnicodeBlock.LETTERLIKE_SYMBOLS,
        Character.UnicodeBlock.TAGS
    )

    private val emojiUnicodeExtendedBlocks = arrayOf(
        "SUPPLEMENTAL_SYMBOLS_AND_PICTOGRAPHS",
        "GEOMETRIC_SHAPES_EXTENDED"
    )

    /**
     * Checks if the given string has emoji
     */
    fun containsEmoji(str: String): Boolean {
        // unicode can be at most 2 Java Char(utf-16), use code point
        val cpCnt = str.codePointCount(0, str.length)
        for (index in 0 until cpCnt) {
            val codePointIndex = str.offsetByCodePoints(0, index)
            val codePoint = str.codePointAt(codePointIndex)
            val hasEmoji = codePoint.isEmoji() ||
                    codePoint.isGlueSymbol() ||
                    isPartOfComplexEmoji(index, str)
            if (hasEmoji) {
                return true
            }
        }
        return false
    }

    /**
     * Removes emoji from the given string
     */
    fun removeEmoji(str: String): String {
        val sb = StringBuilder()
        // unicode can be at most 2 Java Char(utf-16), use code point
        val codePointsCount = str.codePointCount(0, str.length)
        for (index in 0 until codePointsCount) {
            val indexInString = str.offsetByCodePoints(0, index)
            val codePoint = str.codePointAt(indexInString)
            val hasEmoji = codePoint.isEmoji() ||
                    codePoint.isGlueSymbol() ||
                    isPartOfComplexEmoji(index, str)
            if (!hasEmoji) {
                sb.append(Character.toChars(codePoint))
            }
        }
        return sb.toString()
    }

    /**
     * Gets codePoint which is next to [index]-codepoint in [str]
     */
    private fun getNextCodePoint(index: Int, str: String): Int? {
        val codePointsCount = str.codePointCount(0, str.length)
        val nextCodePointIndex = index + 1
        return if (nextCodePointIndex <= codePointsCount - 1) {
            val indexInString = str.offsetByCodePoints(0, nextCodePointIndex)
            str.codePointAt(indexInString)
        } else {
            null
        }
    }

    /**
     * Checks if the codepoint is included to codepoints' groups,
     * which can build emoji together
     */
    private fun isPartOfComplexEmoji(index: Int, str: String): Boolean {
        val nextCodePoint = getNextCodePoint(index, str)
        return nextCodePoint?.isGlueSymbol() ?: false
    }

    /**
     * Checks if the codepoint is a connecting symbol inside symbols' groups
     */
    private fun Int.isGlueSymbol(): Boolean {
        val nextInHex = toHexStr(this)
        return nextInHex in glueSymbols
    }

    /**
     * Checks if the codepoint is included to symbols' group,
     * which is typical for emoji
     */
    private fun Int.isEmoji(): Boolean {
        val unicodeBlock = Character.UnicodeBlock.of(this)
        return unicodeBlock == null ||
                unicodeBlock in emojiUnicodeBlocks ||
                unicodeBlock.toString() in emojiUnicodeExtendedBlocks
    }

    /**
     * Converts codePoint to uppercase string representation
     *
     * Sample (for 🤾): 129342 -> 1F93E
     */
    private fun toHexStr(c: Int): String {
        return Integer.toHexString(c).uppercase()
    }
}
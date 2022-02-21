package ru.surfstudio.compose.forms.base

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha


/**
 * Modifier check bool is FALSE for set params
 */
internal inline fun Modifier.ifFalse(value: Boolean, block: Modifier.() -> Modifier): Modifier =
    if (!value) block.invoke(this) else this

/**
 * Modifier check bool is TRUE for set params
 */
internal inline fun Modifier.ifTrue(value: Boolean, block: Modifier.() -> Modifier): Modifier =
    if (value) block.invoke(this) else this

/**
 * Controlling element visibility based on transparency
 */
internal fun Modifier.visible(visibility: Boolean): Modifier =
    this.then(alpha(if (visibility) 1f else 0f))
package ru.nikitae57.sandbox

import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.image.KImageView

object FirstScreen : KScreen<FirstScreen>() {
    override val layoutId: Int = R.layout.fragment_first
    override val viewClass: Class<*> = FirstFragment::class.java

    val image = KImageView { withId(R.id.image) }
}
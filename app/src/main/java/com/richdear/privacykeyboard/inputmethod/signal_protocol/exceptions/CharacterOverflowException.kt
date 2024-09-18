package com.richdear.privacykeyboard.inputmethod.signal_protocol.exceptions

import java.io.IOException


class CharacterOverflowException : IOException {
    constructor(message: String?) : super(message)

    constructor(message: String?, e: IOException?) : super(message, e)
}
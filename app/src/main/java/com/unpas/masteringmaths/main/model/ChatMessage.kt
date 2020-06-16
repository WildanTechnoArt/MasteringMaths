package com.unpas.masteringmaths.main.model

import java.util.*

data class ChatMessage (
    var text: String? = null,
    var date: Date? = null,
    var username: String? = null,
    var userId: String? = null,
    var status: String? = null
)
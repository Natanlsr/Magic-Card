package com.magic.application.config

import org.springframework.messaging.Message
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.ChannelInterceptor

class SocketMessageInterceptor: ChannelInterceptor{
    override fun preReceive(channel: MessageChannel): Boolean {
        println(channel.toString())
        return super.preReceive(channel)
    }

    override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
        println(message.toString())
        return super.preSend(message, channel)
    }

}
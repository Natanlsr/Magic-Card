package com.magic.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = [
    "com.magic.repository"
])
open class MagicApplication

fun main(args: Array<String>) {
    runApplication<MagicApplication>(*args)
}

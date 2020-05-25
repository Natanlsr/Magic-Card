package com.magic.application

import com.magic.application.config.WebSocketConfig
import com.magic.application.controller.GameController
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(
    basePackages = [
        "com.magic.service",
        "com.magic.repository"
    ],
    basePackageClasses = [
        GameController::class,
        WebSocketConfig::class
    ]
)
@EnableJpaRepositories("com.magic.repository")
@EntityScan("com.magic.model")
open class MagicApplication

fun main(args: Array<String>) {
    runApplication<MagicApplication>(*args)
}

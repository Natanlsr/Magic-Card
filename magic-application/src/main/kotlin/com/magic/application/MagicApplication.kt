package com.magic.application

import com.magic.application.config.WebCorsConfig
import com.magic.application.config.WebSocketConfig
import com.magic.application.controller.GameController
import org.modelmapper.ModelMapper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@ComponentScan(
        basePackages = [
            "com.magic.service",
            "com.magic.repository"
        ],
        basePackageClasses = [
            GameController::class,
            WebSocketConfig::class,
            WebCorsConfig::class
        ]
)
@EnableJpaRepositories("com.magic.repository")
@EntityScan("com.magic.model")
@EnableAsync
open class MagicApplication

fun main(args: Array<String>) {
    runApplication<MagicApplication>(*args)
}

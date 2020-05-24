package com.magic.repository.config

import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import javax.sql.DataSource

@Configuration
@EnableAutoConfiguration(exclude = [LiquibaseAutoConfiguration::class])
@ComponentScan(basePackages = ["com.magic.repository"])
@EnableJpaRepositories
open class LiquibaseConfig {

    @Bean
    open fun liquibase(dataSource: DataSource): SpringLiquibase{
        val springLiquibase = SpringLiquibase()
        springLiquibase.dataSource = dataSource
        springLiquibase.changeLog  = "classpath*:db/changelog-master.xml"
        return springLiquibase
    }
}
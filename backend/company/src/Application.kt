package com.sedex.connect

import com.fasterxml.jackson.databind.SerializationFeature
import com.sedex.connect.domain.company.Company
import com.sedex.connect.domain.company.toCompanyResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

val companyA = Company(id = UUID.randomUUID(), companyName = "Company A")
val companyB = Company(id = UUID.randomUUID(), companyName = "Company B")

val companies = mutableMapOf(
    companyA.id to companyA,
    companyB.id to companyB
)

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        route("/company") {
            get {
                call.respond(mapOf("items" to synchronized(companies) { companies.values.toList().map { it.toCompanyResponse() } } ))
            }
        }
    }

}



package com.sedex.connect

import com.fasterxml.jackson.databind.SerializationFeature
import com.sedex.connect.domain.company.Company
import com.sedex.connect.domain.company.CompanyRequest
import com.sedex.connect.domain.company.fromCompanyRequest
import com.sedex.connect.domain.company.toCompanyResponse
import com.sedex.connect.repo.CompanyRepo
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*

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
                call.respond(mapOf("items" to synchronized(CompanyRepo.records) {
                    CompanyRepo.records.values.toList().map { it.toCompanyResponse() }
                }))
            }
            post {
                val companyRequest = call.receive<CompanyRequest>()
                val newCompany = Company.fromCompanyRequest(companyRequest)
                newCompany.id = UUID.randomUUID()
                CompanyRepo.records[newCompany.id] = newCompany
                call.respond(newCompany.toCompanyResponse())
            }
            get("/{id}") {
                try {
                    val id = UUID.fromString(call.parameters["id"])
                    val company = CompanyRepo.records[id]
                    if (company != null) {
                        call.respond(synchronized(CompanyRepo.records) { company.toCompanyResponse() })
                    } else {
                        throw NotFoundException("Company Not Found")
                    }
                } catch (e: IllegalArgumentException) {
                    throw NotFoundException("Company Not Found")
                }
            }
        }

        install(StatusPages) {
            exception<BadRequestException> { cause ->
                call.respond(HttpStatusCode.BadRequest, cause)
            }
            exception<NotFoundException> { cause ->
                call.respond(HttpStatusCode.NotFound, "NOT FOUND")
            }
        }

    }

}



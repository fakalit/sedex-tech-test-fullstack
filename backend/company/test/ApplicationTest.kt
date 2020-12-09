package com.sedex.connect

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.sedex.connect.domain.company.CompanyRequest
import com.sedex.connect.domain.company.CompanyResponse
import com.sedex.connect.repo.CompanyRepo
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

class ApplicationTest {

    @Test
    fun `should respond with content when all companies requested`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/company").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertFalse(response.content.isNullOrBlank())
            }
        }
    }

    @Test
    fun `should respond with CompanyResponse when valid CompanyRequest sent as POST`() {
        val mapper = jacksonObjectMapper()

        withTestApplication({ module(testing = true) }) {
            val request = CompanyRequest(companyName = "Company C")
            handleRequest(HttpMethod.Post, "/company") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(mapper.writeValueAsString(request))
            }.apply {
                val companyResponse = mapper.readValue<CompanyResponse>(response.content ?: "")
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(companyResponse.companyName, request.companyName)
                assertNotNull(companyResponse.id)
            }
        }
    }

    @Test
    fun `should respond with BadRequest when invalid CompanyRequest sent as POST`() {
        val mapper = jacksonObjectMapper()

        withTestApplication({ module(testing = true) }) {
            val request = CompanyRequest(companyName = "Company C", incorporatedDate = "asdsa")

            handleRequest(HttpMethod.Post, "/company") {
                addHeader(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                setBody(mapper.writeValueAsString(request))
            }.apply {
                assertEquals(HttpStatusCode.BadRequest, response.status())
            }
        }
    }

    @Test
    fun `should respond with CompanyResponse when given existing Id`() {
        val existingCompany = CompanyRepo.records.entries.first().value
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/company/${existingCompany.id}").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertFalse(response.content.isNullOrBlank())
            }
        }
    }

    @Test
    fun `should respond with NotFound when given bad or nonexisting Id`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/company/cgfvbhjkml").apply {
                assertEquals(HttpStatusCode.NotFound, response.status())
            }
        }
    }

}

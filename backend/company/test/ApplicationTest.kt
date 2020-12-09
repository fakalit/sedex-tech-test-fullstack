package com.sedex.connect

import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class ApplicationTest {

    @Test
    fun `should response with content when all companies requested`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/company").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                assertFalse(response.content.isNullOrBlank())
            }
        }
    }

}

package domain.company

import com.sedex.connect.domain.company.*
import io.ktor.features.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class CompanyResponseTest {

    @Test
    fun `should map from request successfully when data is valid`() {
        val companyRequest = CompanyRequest()
        val expectedCompany = Company()
        val actualCompany = Company.fromCompanyRequest(companyRequest)

        assertEquals(actualCompany.id, expectedCompany.id)
        assertEquals(actualCompany.companyName, expectedCompany.companyName)
        assertEquals(actualCompany.companyType, expectedCompany.companyType)
        assertEquals(actualCompany.natureofBusiness, expectedCompany.natureofBusiness)
        assertEquals(actualCompany.incorporatedDate, expectedCompany.incorporatedDate)
        assertEquals(actualCompany.emailAddress, expectedCompany.emailAddress)
        assertEquals(actualCompany.phoneNumber, expectedCompany.phoneNumber)
        assertEquals(actualCompany.address, expectedCompany.address)
        assertEquals(actualCompany.createdTime, expectedCompany.createdTime)
        assertEquals(actualCompany.updatedTime, expectedCompany.updatedTime)
    }

    @Test
    fun `should throw exception when data is invalid`() {
        val companyRequest = CompanyRequest(incorporatedDate = "asda")
        val exception = assertFailsWith<BadRequestException> { Company.fromCompanyRequest(companyRequest) }
        assertEquals(exception.message, "Text 'asda' could not be parsed at index 0")
    }

    @Test
    fun `should map into response`() {
        val company = Company()
        val expectedResponse = CompanyResponse()
        val actualResponse = company.toCompanyResponse()

        assertEquals(expectedResponse.id, actualResponse.id)
        assertEquals(expectedResponse.companyName, actualResponse.companyName)
        assertEquals(expectedResponse.companyType, actualResponse.companyType)
        assertEquals(expectedResponse.natureofBusiness, actualResponse.natureofBusiness)
        assertEquals(expectedResponse.incorporatedDate, actualResponse.incorporatedDate)
        assertEquals(expectedResponse.emailAddress, actualResponse.emailAddress)
        assertEquals(expectedResponse.phoneNumber, actualResponse.phoneNumber)
        assertEquals(expectedResponse.address, actualResponse.address)
        assertEquals(expectedResponse.createdTime, actualResponse.createdTime)
        assertEquals(expectedResponse.updatedTime, actualResponse.updatedTime)

    }

}

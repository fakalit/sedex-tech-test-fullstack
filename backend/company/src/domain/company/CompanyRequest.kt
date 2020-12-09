package com.sedex.connect.domain.company

import Address
import io.ktor.features.*
import java.time.LocalDate
import java.time.format.DateTimeParseException

data class CompanyRequest(
    val companyName: String = "name",
    val companyType: String = "type",
    val natureofBusiness: String = "nature",
    val incorporatedDate: String = "2020-12-12",
    val emailAddress: String = "email@company.com",
    val phoneNumber: String = "+444444444444",
    val address: Address = Address()
)

fun Company.Companion.fromCompanyRequest(companyRequest: CompanyRequest): Company {
    try{
        return Company(
            companyName = companyRequest.companyName,
            companyType = companyRequest.companyType,
            natureofBusiness = companyRequest.natureofBusiness,
            incorporatedDate = LocalDate.parse(companyRequest.incorporatedDate),
            emailAddress = companyRequest.emailAddress,
            phoneNumber = companyRequest.phoneNumber,
            address = companyRequest.address
        )
    }
    catch (e: DateTimeParseException) {
        throw BadRequestException(e.message ?: "bad date format")
    }
}


package com.sedex.connect.domain.company

import Address
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class CompanyResponse(
    var id: String = "123e4567-e89b-12d3-a456-556642440000",
    var companyName: String = "name",
    var companyType: String = "type",
    var natureofBusiness: String = "nature",
    var incorporatedDate: String = "20201212",
    var emailAddress: String = "email@company.com",
    var phoneNumber: String = "+444444444444",
    var address: Address = Address(),
    var createdTime: String = "2020-12-12T10:10:00",
    var updatedTime: String = "2020-12-12T10:10:00"
)

fun Company.toCompanyResponse() = CompanyResponse(
    id = "$id",
    companyName = companyName,
    companyType = companyType,
    natureofBusiness = natureofBusiness,
    incorporatedDate = incorporatedDate.format(DateTimeFormatter.BASIC_ISO_DATE),
    emailAddress = emailAddress,
    phoneNumber = phoneNumber,
    address = address,
    createdTime = createdTime.format(DateTimeFormatter.ISO_DATE_TIME),
    updatedTime = updatedTime.format(DateTimeFormatter.ISO_DATE_TIME)
)
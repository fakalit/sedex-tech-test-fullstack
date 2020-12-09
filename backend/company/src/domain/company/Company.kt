package com.sedex.connect.domain.company

import Address
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

data class Company(
    var id: UUID = UUID.fromString("123e4567-e89b-12d3-a456-556642440000"),
    var companyName: String = "name",
    var companyType: String = "type",
    var natureofBusiness: String = "nature",
    var incorporatedDate: LocalDate = LocalDate.parse("2020-12-12"),
    var emailAddress: String = "email@company.com",
    var phoneNumber: String = "+444444444444",
    var address: Address = Address(),
    var createdTime: LocalDateTime = LocalDateTime.of(2020,12,12,10,10),
    var updatedTime: LocalDateTime = LocalDateTime.of(2020,12,12,10,10)
){
    companion object {}
}





package com.sedex.connect.repo

import com.sedex.connect.domain.company.Company
import java.util.*

val companyA = Company(id = UUID.randomUUID(), companyName = "Company A")
val companyB = Company(id = UUID.randomUUID(), companyName = "Company B")

object CompanyRepo {
    val records = mutableMapOf(
        companyA.id to companyA,
        companyB.id to companyB
    )
}
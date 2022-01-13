package com.enjo.hoefsmidenjo

import com.enjo.hoefsmidenjo.domain.domaincontroller.DomainController
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*


class DomainControllerTest {
    @Test
    fun domainController_ConvertDate() {

        DomainController()

        var apiDate = "2022-01-14T09:14:33.9978263"
        var result = "14/01/2022 09:14"
        var response = DomainController.instance.getTimeOfString(apiDate)
        assertEquals(result,response)
    }
}
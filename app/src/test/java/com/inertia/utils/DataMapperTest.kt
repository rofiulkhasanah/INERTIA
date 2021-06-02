package com.inertia.utils

import org.junit.Test

import org.junit.Assert.*

class DataMapperTest {

    @Test
    fun `return valid output number with not valid input number`() {
        val number = "082338819564"
        val expectedNumber = "6282338819564"
        assertEquals(expectedNumber, DataMapper.getValidNumber(number))
    }

    @Test
    fun `return valid output number with valid input number`() {
        val number = "6282338819564"
        val expectedNumber = "6282338819564"
        assertEquals(expectedNumber, DataMapper.getValidNumber(number))
    }
}
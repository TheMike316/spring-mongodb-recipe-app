package com.miho.springmongodbrecipeapp.testutils

import org.mockito.Mockito

/**
 * Because of Kotlin's null-safety we cannot use Mockito's any() on non-null parameters to mock our classes.
 * Using this method works around that issue.
 */
fun <T> any(): T {
    Mockito.any<T>()
    return null as T
}



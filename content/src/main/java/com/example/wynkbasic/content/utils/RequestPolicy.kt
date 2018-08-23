package com.example.wynkbasic.content.utils

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class RequestPolicy(val authenticated: Boolean = false)

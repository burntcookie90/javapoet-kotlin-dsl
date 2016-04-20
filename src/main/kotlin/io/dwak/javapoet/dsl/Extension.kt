package io.dwak.javapoet.dsl

import com.squareup.javapoet.ClassName

fun Class<*>.getClassName() = ClassName.get(this)

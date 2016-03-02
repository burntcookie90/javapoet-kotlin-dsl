package com.vishnurajeevan.javapoet.dsl.model

import com.squareup.javapoet.MethodSpec

class JavaPoetControlFlow(val methodSpecBuilder : MethodSpec.Builder) {

  fun statement(statement : String) {
    methodSpecBuilder.addStatement(statement)
  }

  fun statement(format : String, vararg args : Any){
    methodSpecBuilder.addStatement(format, *args)
  }

  fun begin(format : String, args : Array<Any>, init : JavaPoetControlFlow.() -> Unit){
    methodSpecBuilder.beginControlFlow(format, *args)
    val controlFlow = JavaPoetControlFlow(methodSpecBuilder)
    controlFlow.init()
  }

  fun begin(condition : String, init : JavaPoetControlFlow.() -> Unit) {
    methodSpecBuilder.beginControlFlow(condition)
    val controlFlow = JavaPoetControlFlow(methodSpecBuilder)
    controlFlow.init()
  }

  fun next(format : String, args : Array<Any>, init : JavaPoetControlFlow.() -> Unit){
    methodSpecBuilder.nextControlFlow(format, *args)
    val controlFlow = JavaPoetControlFlow(methodSpecBuilder)
    controlFlow.init()
  }

  fun next(condition : String, init : JavaPoetControlFlow.() -> Unit) {
    methodSpecBuilder.nextControlFlow(condition)
    val controlFlow = JavaPoetControlFlow(methodSpecBuilder)
    controlFlow.init()
  }

  fun end(condition : String = "") {
    if (condition.isEmpty()) {
      methodSpecBuilder.endControlFlow()
    }
    else {
      methodSpecBuilder.endControlFlow(condition)
    }
  }

}
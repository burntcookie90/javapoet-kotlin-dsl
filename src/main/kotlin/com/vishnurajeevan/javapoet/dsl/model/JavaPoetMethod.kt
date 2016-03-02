package com.vishnurajeevan.javapoet.dsl.model

import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import com.vishnurajeevan.javapoet.dsl.model.JavaPoetControlFlow
import com.vishnurajeevan.javapoet.dsl.model.JavaPoetValue
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

class JavaPoetMethod(val name : String,
                     val modifiers : Set<Modifier>,
                     val returns : TypeName,
                     val parameters : Set<JavaPoetValue>) {

  val methodSpecBuilder : MethodSpec.Builder
  var javaDoc : String? = null
    set(value) {
      field == value
      methodSpecBuilder.addJavadoc(value)
    }

  init {
    methodSpecBuilder = MethodSpec.methodBuilder(name).addModifiers(modifiers).returns(returns)
    parameters.forEach {
      methodSpecBuilder.addParameter(ParameterSpec.builder(it.typeName, it.name, *it.modifiers.toTypedArray()).build())
    }
  }

  fun statement(statement : String) {
    methodSpecBuilder.addStatement(statement)
  }

  fun statement(format : String, vararg args : Any){
    methodSpecBuilder.addStatement(format, *args)
  }

  fun controlFlow(init : JavaPoetControlFlow.() -> Unit) : JavaPoetControlFlow {
    val controlFlow = JavaPoetControlFlow(methodSpecBuilder)
    controlFlow.init()
    return controlFlow
  }
}
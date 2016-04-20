package io.dwak.javapoet.dsl.model

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.TypeName
import io.dwak.javapoet.dsl.model.JavaPoetControlFlow
import io.dwak.javapoet.dsl.model.JavaPoetValue
import java.lang.reflect.Type
import java.util.*
import javax.lang.model.element.Modifier

class JavaPoetMethod(val name : String,
                     val modifiers : Set<Modifier>,
                     val returns : TypeName,
                     val parameters : Set<JavaPoetValue>) {

  val methodSpecBuilder : MethodSpec.Builder
  var annotations : Set<AnnotationSpec>? = null
    set(value) {
      field == value
      value?.forEach {
        methodSpecBuilder.addAnnotation(it)
      }
    }
  var javaDoc : String? = null
    set(value) {
      field == value
      methodSpecBuilder.addJavadoc(value)
    }

  init {
    methodSpecBuilder = MethodSpec.methodBuilder(name).addModifiers(modifiers).returns(returns)
    parameters.forEach {
      var builder : ParameterSpec.Builder
      if(it.typeName != null){
        builder = ParameterSpec.builder(it.typeName, it.name, *it.modifiers.toTypedArray())
      }
      else {
        builder = ParameterSpec.builder(it.type, it.name, *it.modifiers.toTypedArray())
      }
      methodSpecBuilder.addParameter(builder.build())
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
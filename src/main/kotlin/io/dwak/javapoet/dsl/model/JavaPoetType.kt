package io.dwak.javapoet.dsl.model

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeVariableName
import io.dwak.javapoet.dsl.model.JavaPoetConstructor
import io.dwak.javapoet.dsl.model.JavaPoetMethod
import io.dwak.javapoet.dsl.model.JavaPoetValue
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

class JavaPoetType(val modifiers : Set<Modifier>,
                   val name : String) {
  val constructors = arrayListOf<JavaPoetConstructor>()
  val methods = arrayListOf<JavaPoetMethod>()
  val fields = arrayListOf<JavaPoetValue>()
  var javaDoc : String? = null
  val parameterizedTypes = arrayListOf<TypeVariableName>()
  val extends = arrayListOf<TypeName>()
  val implements = arrayListOf<TypeName>()
  var annotations : Set<AnnotationSpec>? = null

  fun field(modifier : Modifier = Modifier.DEFAULT,
            typeName : TypeName,
            name : String,
            value : Any? = null,
            init : JavaPoetValue.() -> Unit = {})
          = field(setOf(modifier), typeName, name, value, init)

  fun field(modifiers : Set<Modifier> = setOf(Modifier.DEFAULT),
            typeName : TypeName,
            name : String,
            value : Any? = null,
            init : JavaPoetValue.() -> Unit = {}) : JavaPoetValue {

    val jPValue = JavaPoetValue(modifiers = modifiers, typeName = typeName, name = name, value = value)
    jPValue.init()
    fields.add(jPValue)
    return jPValue
  }

  fun field(modifier : Modifier = Modifier.DEFAULT,
            type : Type,
            name : String,
            value : Any? = null,
            init : JavaPoetValue.() -> Unit = {})
          = field(setOf(modifier), type, name, value, init)
  fun field(modifiers : Set<Modifier> = setOf(Modifier.DEFAULT),
            type: Type,
            name : String,
            value : Any? = null,
            init : JavaPoetValue.() -> Unit = {}) : JavaPoetValue {

    val jPValue = JavaPoetValue(modifiers = modifiers, type = type, name = name, value = value)
    jPValue.init()
    fields.add(jPValue)
    return jPValue
  }

  fun constructor(modifier : Modifier = Modifier.DEFAULT,
                  parameters : Set<JavaPoetValue> = emptySet(),
                  init : JavaPoetConstructor.() -> Unit = {})
          = constructor(setOf(modifier), parameters, init)

  fun constructor(modifiers : Set<Modifier> = setOf(Modifier.DEFAULT),
                  parameters : Set<JavaPoetValue> = emptySet(),
                  init : JavaPoetConstructor.() -> Unit = {}) : JavaPoetConstructor {
    val constructor = JavaPoetConstructor(modifiers, parameters)
    constructor.init()
    constructors.add(constructor)
    return constructor
  }

  fun method(modifier : Modifier = Modifier.DEFAULT,
             returns : TypeName = TypeName.VOID,
             name : String,
             parameters : Set<JavaPoetValue> = emptySet(),
             init : JavaPoetMethod.() -> Unit)
          = method(setOf(modifier), returns, name, parameters, init)
  fun method(modifiers : Set<Modifier> = setOf(Modifier.DEFAULT),
             returns : TypeName = TypeName.VOID,
             name : String,
             parameters : Set<JavaPoetValue> = emptySet(),
             init : JavaPoetMethod.() -> Unit) : JavaPoetMethod {

    val javaPoetMethod = JavaPoetMethod(name, modifiers, returns, parameters)
    javaPoetMethod.init()
    methods.add(javaPoetMethod)
    return javaPoetMethod
  }
}
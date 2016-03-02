package com.vishnurajeevan.javapoet.dsl

import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.TypeSpec
import com.vishnurajeevan.javapoet.dsl.model.JavaPoetType
import javax.lang.model.element.Modifier
import javax.lang.model.element.Modifier.DEFAULT

inline fun classType(modifier : Modifier = DEFAULT,
                     name : String,
                     init : JavaPoetType.() -> Unit)
        = classType(setOf(modifier), name, init)
inline fun classType(modifiers : Set<Modifier> = setOf(DEFAULT),
                     name : String,
                     init : JavaPoetType.() -> Unit) : TypeSpec {
  val type = JavaPoetType(modifiers, name)
  type.init()

  val typeSpecBuilder = TypeSpec.classBuilder(type.name).addModifiers(*type.modifiers.toTypedArray())
  type.parameterizedTypes.forEach { typeSpecBuilder.addTypeVariable(it) }
  type.extends.forEach { typeSpecBuilder.superclass(it) }
  type.implements.forEach { typeSpecBuilder.addSuperinterface(it) }

  type.javaDoc?.let { typeSpecBuilder.addJavadoc(it) }
  type.fields.forEach {
    var builder : FieldSpec.Builder
    if(it.typeName != null) {
      builder = FieldSpec.builder(it.typeName, it.name, *it.modifiers.toTypedArray())
    }
    else {
      builder = FieldSpec.builder(it.type, it.name, *it.modifiers.toTypedArray())
    }
    it.value?.let { builder.initializer("\$L", it) }
    it.javaDoc?.let { builder.addJavadoc(it) }
    typeSpecBuilder.addField(builder.build())
  }
  type.constructors.forEach { typeSpecBuilder.addMethod(it.methodSpecBuilder.build()) }
  type.methods.forEach { typeSpecBuilder.addMethod(it.methodSpecBuilder.build()) }
  return typeSpecBuilder.build()
}

inline fun interfaceType(modifier : Modifier = DEFAULT,
                         name : String,
                         init : JavaPoetType.() -> Unit) : TypeSpec {
  return interfaceType(setOf(modifier), name, init)
}

inline fun interfaceType(modifiers : Set<Modifier> = setOf(DEFAULT),
                         name : String,
                         init : JavaPoetType.() -> Unit) : TypeSpec {
  val type = JavaPoetType(modifiers, name)

  val typeSpecBuilder = TypeSpec.interfaceBuilder(type.name).addModifiers(*type.modifiers.toTypedArray())

  return typeSpecBuilder.build()
}

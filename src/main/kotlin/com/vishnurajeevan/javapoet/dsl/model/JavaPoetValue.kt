package com.vishnurajeevan.javapoet.dsl.model

import com.squareup.javapoet.TypeName
import javax.lang.model.element.Modifier

class JavaPoetValue(val modifiers : Set<Modifier>,
                    val type : TypeName,
                    val name : String,
                    val value : Any? = null) {
  constructor(modifier : Modifier, type : TypeName, name : String, value : Any? = null)
  : this(setOf(modifier), type, name, value) {
  }

  var javaDoc : String? = null
}
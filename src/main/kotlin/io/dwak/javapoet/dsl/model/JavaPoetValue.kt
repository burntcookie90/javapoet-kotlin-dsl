package io.dwak.javapoet.dsl.model

import com.squareup.javapoet.TypeName
import java.lang.reflect.Type
import javax.lang.model.element.Modifier

class JavaPoetValue(val modifiers : Set<Modifier>,
                    val typeName : TypeName? = null,
                    val type : Type? = null,
                    val name : String,
                    val value : Any? = null) {
  constructor(modifier : Modifier, typeName : TypeName, name : String, value : Any? = null)
  : this(modifiers = setOf(modifier),
         typeName = typeName,
         name = name,
         value = value) { }

  constructor(modifier : Modifier, type: Type, name : String, value : Any? = null)
  : this(modifiers = setOf(modifier),
         type = type,
         name = name,
         value = value) { }

  constructor(modifiers : Set<Modifier>, type: Type, name : String, value : Any? = null)
  : this(modifiers = modifiers,
         typeName = null,
         type = type,
         name = name,
         value = value) { }

  var javaDoc : String? = null
}
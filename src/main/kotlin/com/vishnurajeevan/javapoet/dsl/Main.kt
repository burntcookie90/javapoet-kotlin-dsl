package com.vishnurajeevan.javapoet.dsl

import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeName.*
import com.vishnurajeevan.javapoet.dsl.classType
import com.vishnurajeevan.javapoet.dsl.model.JavaPoetValue
import java.util.*
import javax.lang.model.element.Modifier.*

fun main(args : Array<String>) {

  JavaFile.builder("com.example", classType(PUBLIC, "TestDsl") {
    javaDoc = "This is a test class for the kotlin javapoet DSL\n"

    field(setOf(PROTECTED, FINAL), BOOLEAN, "isProtected", true) {
      javaDoc = "this is a protected final field\n"
    }

    field(PRIVATE, BOOLEAN, "isPrivate")

    field(PRIVATE, IntArray::class.java, "integerArray")

    constructor(PUBLIC) //no init block gives default empty constructor

    constructor(PUBLIC, setOf(JavaPoetValue(FINAL, BOOLEAN, "isPrivate"))) {
      javaDoc = "constructor that takes a parameter and sets the corresponding field\n"
      statement("this.isPrivate = isPrivate")
    }

    method(PRIVATE, VOID, "testTypes") {
      val dateClass = Date::class.java.getClassName()
      val stringClass = String::class.java.getClassName()
      statement("final \$T date = new \$T()", dateClass, dateClass)
      statement("final \$T testString = \$S", stringClass, "test")
    }

    method(PRIVATE, VOID, "returnsVoid") {
      controlFlow {
        begin("for (int i = 0; i < 10; i++)") {
          statement("println(\"test\")")
        }
        end()
      }
    }

    method(setOf(PRIVATE, FINAL), INT, "returnsInteger") {
      javaDoc = "this method returns an integer\n"

      statement("int total = 0")

      controlFlow() {
        begin("if(total == 0)") {
          statement("return 1")
        }
        next("else") {
          statement("return 2")
        }
        end()
      }
    }

    method(PUBLIC, BOOLEAN, "complexControlFlow", setOf(JavaPoetValue(FINAL, INT, "in"))) {
      javaDoc = "This method shows arbitrary complex control flow\n"
      controlFlow {
        begin("if(in > 0)") {
          controlFlow {
            begin("if(in < 5)") {
              statement("return true")
            }
            next("else if (in < 7)") {
              statement("return false")
            }
            next("else if (in < 10)") {
              statement("return true")
            }
            end()
          }
        }
        next("else") {
          statement("return false")
        }
        end()
      }
    }
  }).build().writeTo(System.out)
}


package com.joel

import io.circe.generic.auto._
import io.circe.parser

case class Form(firstName: String, lastName: String, age: Int, email: Option[String])

/**
 * Automatically decodes into the Form case class.
 */
object Form {
  def main(args: Array[String]): Unit = {
    val inputString =
      """
        |[
        |    {"firstName": "Rose", "lastName":"Jane", "age":20, "email":"roseJane@gmail.com"},
        |    {"firstName": "John", "lastName":"Doe" , "age": 45}
        |]
        |""".stripMargin

    parser.decode[List[Form]](inputString) match {
      case Right(form) => println(form)
      case Left(ex) => println(s"Ooops something happened ${ex}")
    }
  }
}

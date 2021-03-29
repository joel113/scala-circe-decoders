package com.joel

import io.circe.{Decoder, parser}
import io.circe.generic.semiauto.deriveDecoder

// case class is a singleton object
case class Book(book: String)

/**
 * Decoding a list of objects from a JSON List.
 */
object DecodingArraysApp {
  implicit val decoder: Decoder[Book] = deriveDecoder[Book]

  def main(args: Array[String]): Unit = {
    val inputString =
      """
        |[
        | {"book": "Programming in Scala"},
        | {"book": "How to Win Friends and Influence People"},
        | {"book": "HomoSapiens"},
        | {"book": "Scala OOP"}
        |]
        |""".stripMargin

    parser.decode[List[Book]](inputString) match {
      case Right(books) => println(s"Here are the books ${books}")
      case Left(ex) => println(s"Ooops something error ${ex}")
    }
  }
}

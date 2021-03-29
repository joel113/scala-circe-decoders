package com.joel

import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.jawn.decode

case class Product(productId: Long, price: Double, countryCurrency: String, inStock: Boolean)

/**
 * Decodes objects from json using a semi-automatic derived decoder.
 */
object DecodingObjectsApp {
  implicit val decoder: Decoder[Product] = deriveDecoder[Product]
  implicit val encoder: Encoder[Product] = deriveEncoder[Product]

  def main(args: Array[String]): Unit = {
    val inputString: String =
      """
        |{
        |   "productId": 111112222222,
        |   "price": 23.45,
        |   "countryCurrency": "USD",
        |   "inStock": true
        |}
        |""".stripMargin

    decode[Product](inputString) match {
      case Right(productObject) => println(productObject)
      case Left(ex) => println(s"Ooops some errror here ${ex}")
    }
  }
}

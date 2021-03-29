package com.joel

import io.circe.{Decoder, HCursor, parser}

case class Applicant(name: String, age: Int, phoneNumber: String)

/**
 * Manually decodes into the application object.
 */
object DecodingManually {
  // here we are actually casting the return value to Decode
  implicit val decoder: Decoder[Applicant] = (hCursor: HCursor) =>
    for {
      name <- hCursor.get[String]("name")
      age <- hCursor.downField("age").as[Int]
      phoneNumber <- hCursor.get[String]("phoneNumber")
    } yield Applicant(name, age, phoneNumber)

  def main(args: Array[String]): Unit = {
    val inputString =
      """
        |[
        | {"name": "Jane Doe", "age":26, "phoneNumber":"512222222"},
        | {"name": "Petter Pan", "age":55, "phoneNumber":"214553356"},
        | {"name": "Jason Mamoa", "age":33, "phoneNumber":"2111112234", "email":"jasonMamoa@gmail.com"}
        |]
        |""".stripMargin

    parser.decode[List[Applicant]](inputString) match {
      case Right(applicants) => println(applicants)
      case Left(ex) => println(s"Oops something is wrong with decoding value ${ex}")
    }
  }

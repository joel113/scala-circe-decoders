package com.joel

import io.circe.{ACursor, Decoder, Json, parser}
import io.circe.generic.semiauto.deriveDecoder

case class Company(industry: String, year: Int, name: String, public: Boolean)

object PrepareDecodingApp {

  // derives a decoder and provides a prepare statement
  implicit val decoder: Decoder[Company] = deriveDecoder[Company].prepare { (aCursor: ACursor) =>
  {
    aCursor.withFocus(json => {
      json.mapObject(jsonObject => {
        if (jsonObject.contains("public")) {
          jsonObject
        } else {
          jsonObject.add("public", Json.fromBoolean(false))
        }
      })
    })
  }
  }

  def main(args: Array[String]): Unit = {
    val inputString =
      """
        |[
        | {"industry":"tech", "year":1990, "name":"Intel", "public": true},
        | {"industry":"tech", "year":2006, "name":"Netflix"},
        | {"industry":"Consumer Goods", "year":1860, "name":"Pepsoden", "public": true}
        |]
        |""".stripMargin

    parser.decode[List[Company]](inputString) match {
      case Right(companies) => companies.map(println)
      case Left(ex) => println(s"ooops something wrong ${ex}")
    }
  }
}

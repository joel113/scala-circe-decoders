package com.joel

import cats.Traverse
import cats.implicits.{catsStdInstancesForEither, catsStdInstancesForList}
import io.circe.Decoder.Result
import io.circe.{Decoder, HCursor, Json}

case class ProductResources(name: String, campaignResources: List[Int], discountPrice: List[Int])

object DecodingArraysOfObjectsOfArraysApp {

  // creates a new manual decoder
  implicit val decoder: Decoder[ProductResources] = new Decoder[ProductResources] {
    override def apply(hCursor: HCursor): Result[ProductResources] =
      for {
        name <- hCursor.downField("name").as[String]
        orderItemsJson <- hCursor.downField("orderItems").as[List[Json]]
        // uses cats traverse to traverse the orderItemsJson
        // applies a lambda to the elements of orderItemsJson and returns a simple int
        campaignResource <- Traverse[List].traverse(orderItemsJson)(
          itemJson => itemJson.hcursor.downField("voucher").downField("campaignNumber").as[Int]
        )
        // uses cats traverse again to traverse the orderItemsJson
        discountPrice <- Traverse[List].traverse(orderItemsJson)(orderItemsJson => {
          orderItemsJson.hcursor.downField("voucher").downField("discount").as[Int]
        })
      } yield {
        ProductResources(name, campaignResource, discountPrice)
      }
  }

  def main(args: Array[String]): Unit = {
    val inputString =
      """
        |[
        |   {
        |      "name":"productResource",
        |      "orderItems":[
        |         {
        |            "voucher":{
        |               "campaignNumber":12,
        |               "discount":20,
        |               "subscriptionPeriod":"June"
        |            }
        |         },
        |         {
        |            "voucher":{
        |               "campaignNumber":13,
        |               "discount":24
        |            }
        |         }
        |      ]
        |   },
        |   {
        |      "name":"productResource2",
        |      "orderItems":[
        |         {
        |            "voucher":{
        |               "campaignNumber":13,
        |               "discount":24
        |            }
        |         }
        |      ]
        |   },
        |   {
        |      "name":"productResource3",
        |      "orderItems":[
        |         {
        |            "voucher":{
        |               "campaignNumber":15,
        |               "discount":28
        |            }
        |         }
        |      ]
        |   }
        |]
        |""".stripMargin
    parser.decode[List[ProductResources]](inputString) match {
      case Right(vouchers) => vouchers.map(println)
      case Left(ex) => println(s"Something wrong ${ex}")
    }
  }
}

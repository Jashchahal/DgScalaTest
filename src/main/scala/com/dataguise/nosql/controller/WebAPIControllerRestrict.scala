package com.dataguise.nosql.controller

import com.dataguise.nosql.model.{Greeting, Student}
import org.scalatra.{Ok, ScalatraServlet}
import org.scalatra.json._
import org.scalatra.swagger._
import org.slf4j.{Logger, LoggerFactory}
import org.json4s._
import org.json4s.jackson.JsonMethods._

/**
  * Created by root on 3/13/18.
  */
class WebAPIControllerRestrict(implicit val swagger: Swagger) extends ScalatraServlet with JacksonJsonSupport with WebAPIServletSwaggerDefinition with SwaggerSupport {
  private val logger: Logger = LoggerFactory.getLogger(getClass)
  protected implicit val jsonFormats: Formats = DefaultFormats



  before() {
    logger.info(s"$request")


  //  response.headers += ("Access-Control-Allow-Origin" -> "*")
  }


//  val name = (apiOperation[Unit]("dg-name")
//    summary "Name summary"
//    notes "Name notes"
//
//    )






  get("/") {

    halt(400,"Action Not Permitted")
  }

    post("/") {

      halt(400,"Action Not Permitted")
    }


//  get("/:name", operation(name)) {
//    val name = params.getOrElse("name", "world")
//    Greeting ("Hi!! " ,name)
//
//  }



//  post("/object") {
//   val b = parse(request.body)
//   val p = b.extract[Student]
//    Greeting ("Hi!! " ,p.name)
//
//  }



}





//  val getOperation = apiOperation[ExampleItemList]("getAllItems")
//    .parameter(queryParam[String]("type").description("Filter value for item type").optional)
//    .summary("Gets all example items")
//
//  val getDetailsOperation = apiOperation[ExampleItemDetails]("getItemDetails")
//    .parameter(pathParam[String]("id").description("Item identifier").required)
//    .summary("Returns all details of item with given identifier")
//
//  val addItemOperation = apiOperation[ExampleItemDetails]("addNewItem")
//    .parameter(bodyParam[NewItemRequest]("newItem").description("New item definition").required)
//    .summary("Creates a new item and returns its details")



package com.dataguise.nosql.app

import org.scalatra.ScalatraServlet
import org.scalatra.swagger.{ApiInfo, JacksonSwaggerBase, Swagger}
import org.json4s.{DefaultFormats, Formats}
/**
  * Created by root on 3/13/18.
  */
class ResourcesApp (implicit protected val swagger: DgNoSqlSwaggerApp) extends ScalatraServlet with JacksonSwaggerBase
//{
//  before() {
//
//    response.headers += ("Access-Control-Allow-Origin" -> "*")
//  }
//
//}

class DgNoSqlSwaggerApp extends Swagger(apiInfo = DgNoSqlApiSwagger.apiInfo, apiVersion = "1.0.0", swaggerVersion = Swagger.SpecVersion)

//  title: String,
//  description: String,
//  termsOfServiceUrl: String,
//  contact: String,
//  license: String,
//  licenseUrl: String

  object DgNoSqlApiSwagger {
    val apiInfo = new ApiInfo(
      "Dataguise NoSQL Agent API",
      "Docs for NoSQL Agent API",
      "http://dataguise.com",
      "contact@dataguise.com",
      "Commercial",
      "http://dataguise.com")
  }







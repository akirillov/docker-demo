package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

object Service extends App {
    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route =
      path("yo") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>Yo! [${System.currentTimeMillis()}]</h1>"))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)
}

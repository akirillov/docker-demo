Sample Service Application
==========================

# Building and running the service

* build

      sbt docker
* run

      docker run -ti -p8080:8080  demo/akka-sample:1.0
* access via url
      
      curl localhost:8080/yo

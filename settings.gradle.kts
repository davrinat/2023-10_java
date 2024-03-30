rootProject.name = "otusJava"
include("L01-gradle")
include("L02-gradle2")
include("L02-gradle2-libApi")
include("L02-gradle2-libApiUse")
include("L02-logging")
include("L03-qa")
include("L04-generics")
include("L05-collections")
include("L06-annotations")
include("L08-gc:demo")
include("L08-gc:homework")
include("L09-docker")
include("L10-byteCodes")
include("L11-java8")
include("L12-solid")
include("L13-creationalPatterns")
include("L14-behavioralPatterns")
include("L15-structuralPatterns:demo")
include("L15-structuralPatterns:homework")
include("L16-io:demo")
include("L16-io:homework")
include("L17-nio")
include("L18-jdbc:demo")
include("L18-jdbc:homework")
include("L20-hibernate")
include("L21-cache")
include("L22-jpql:class-demo")
include("L22-jpql:homework-template")
include ("L23-noSQL:mongo-db-demo")
include ("L23-noSQL:mongo-db-reactive-demo")
include ("L23-noSQL:neo4j-demo")
include ("L23-noSQL:redis-demo")
include ("L23-noSQL:cassandra-demo")
include("L24-webServer")
include("L25-di")
include("L25-di:homework")
findProject(":L25-di:homework")?.name = "homework"
include("L25-di:class-demo")
findProject(":L25-di:class-demo")?.name = "class-demo"
include("L26-springBootMVC")
include("L27-websocket")
include("L28-springDataJDBC")
include("L29-threads")
include("L27-websocket:application")
findProject(":L27-websocket:application")?.name = "application"
include("L27-websocket:massager")
findProject(":L27-websocket:massager")?.name = "massager"
include("L27-websocket:messager-starter")
findProject(":L27-websocket:messager-starter")?.name = "messager-starter"
include("L27-websocket:websocket")
findProject(":L27-websocket:websocket")?.name = "websocket"
include("L28-springDataJDBC:homework")
findProject(":L28-springDataJDBC:homework")?.name = "homework"
include("L28-homework")
include ("L30-JMM")
include ("L31-executors")
include("L31-executors:homework")
findProject(":L31-executors:homework")?.name = "homework"
include ("L32-concurrentCollections:ConcurrentCollections")
include ("L32-concurrentCollections:QueueDemo")
include ("L33-multiprocess:processes-demo")
include ("L33-multiprocess:sockets-demo")
include ("L33-multiprocess:rmi-demo")
include ("L33-multiprocess:grpc-demo")
include ("L34-rabbitMQ:allServicesModels")
include ("L34-rabbitMQ:approvalService")
include ("L34-rabbitMQ:mainService")
include ("L35-NIO")
include ("L36-netty")
include ("L37-webflux:source")
include ("L37-webflux:processor")
include ("L37-webflux:client")
include ("L37-webflux-chat:client-service")
include ("L37-webflux-chat:datastore-service")

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
    }
}
include("L31-executors:homework")
findProject(":L31-executors:homework")?.name = "homework"
include("L33-multiprocess:homework")
findProject(":L33-multiprocess:homework")?.name = "homework"

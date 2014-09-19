package net.numa08.scala.pixiv

import akka.actor.Actor
import net.numa08.scala.pixiv.PixivDownloader.GetCSV

import scala.io.{Codec, Source}
import scala.util.control.Exception._

class PixivDownloader extends Actor {

  override def receive: Receive = {
    case GetCSV(urls) => {
      val results : List[Either[Throwable, List[String]]] = urls.map { url =>
          allCatch.either {
            Source.fromURL(url, Codec.UTF8.name)
              .mkString
              .split("\n")
              .toList
              .map(_.split(",").lift(PixivDownloader.ILLUST_COLLUMN))
              .collect{case Some(u) => u.replace("\"", "")}
          }
        }
      sender ! results
    }

  }
}

object PixivDownloader {
  case class GetCSV(urls : List[String]) {
    override def toString: String = urls.mkString(",")
  }
  val ILLUST_COLLUMN = 9
}
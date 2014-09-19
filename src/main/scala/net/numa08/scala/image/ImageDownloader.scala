package net.numa08.scala.image

import java.io.File
import java.net.URL

import akka.actor.Actor
import net.numa08.scala.image.ImageDownloader.GetImages
import scala.util.control.Exception._
import scalax.file.Path
import scalax.io.JavaConverters._

class ImageDownloader extends Actor {
  override def receive: Receive = {
    case GetImages(urls, path) => {
      val download = downloadImage(new File(path), _ : String)
      val downloadResults : List[Either[Throwable, String]] = urls.map{download}
      sender ! downloadResults
    }
  }

  def downloadImage(dir : File, stringUrl : String) : Either[Throwable, String] = allCatch either {
    val url = new URL(stringUrl)
    val file = url.getFile.split("/").lastOption.getOrElse(s"file_at_${System.currentTimeMillis.toString}")
    val imagePath = Path.fromString(s"${dir}${File.separator}$file")
      imagePath.write(url.asInput.bytes)
      imagePath.toString()
  }
}

object ImageDownloader {
  case class GetImages(urls : List[String], path : String) {
    override def toString: String = urls.mkString(",")
  }
}
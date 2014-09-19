package net.numa08.scala.pixiv

import java.io.File

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import net.numa08.scala.image.ImageDownloader
import net.numa08.scala.image.ImageDownloader.GetImages
import net.numa08.scala.pixiv.PixivDownloader.GetCSV
import net.numa08.scala.provider.DejikoProvider

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class PixivProvider extends DejikoProvider{

  override def downloadImageAt(path: String): Unit = {
    new File(path).mkdir
    implicit val system = ActorSystem.create("pixivDownloaderByScala")
    // 部分適応と関数合成によって、CSVのURLリストを引数に、画像をダウンロードする関数
    // downloadedImages を生成する
    val downloadAt = downloadImages(path, _ : List[String])
    val downloadedImages = downloadAt.compose(acquireURLsFromURL)

    val urls = (for(i <- 1 to 5) yield {
      s"http://spapi.pixiv.net/iphone/search.php?&s_mode=s_tag&word=%E3%81%A7%E3%81%98%E3%81%93&PHPSESSID=0&p=$i"
    }).toList

    // 画像のダウンロードの実施。関数合成を利用したので
    // csvのダウンロード -> 画像のダウンロードが行われる
    val images = downloadedImages(urls)
    println("downloaded images ars ")
    images.collect{
      case Right(u) => println(s"Successd download at $u")
      case Left(e) => println(s"Failed download because ${e.getMessage}")
    }
  }

  private def acquireURLsFromURL(urls : List[String])(implicit system : ActorSystem) : List[String] = {
    val actor = system.actorOf(Props[PixivDownloader], "pixivDownloader")
    println("download csv ")
    implicit val timeout = Timeout(5 minutes)
    val futures = urls.grouped(5)
                      .toList
                      .map{u => val m = GetCSV(u);println(s"message is $m");m}
                      .map{m => (actor ? m).mapTo[List[Either[Throwable, List[String]]]]}
    val r = Await.result(Future.sequence(futures), timeout.duration)
         .map{lists =>
            lists.collect{case Right(l) => l}
         }.flatten.flatten.toList
    println("complete")
    r
  }

    private def downloadImages(path : String, urls : List[String])(implicit  system : ActorSystem): List[Either[Throwable, String]] = {
      val actor = system.actorOf(Props[ImageDownloader], "imageDownloader")
      println("download images ")
      implicit val timeout = Timeout(5 minutes)
      val futures = urls.grouped(5)
                      .toList
                      .map{u => val m = GetImages(u, path);println(s"message is $m");m}
                      .map{m => (actor ? m).mapTo[List[Either[Throwable, String]]]}
      Await.result(Future.sequence(futures), timeout.duration).flatten
  }

}

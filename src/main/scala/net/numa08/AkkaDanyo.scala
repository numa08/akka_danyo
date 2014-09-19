package net.numa08

import net.numa08.scala.provider.DejikoProviderFactory

object AkkaDanyo {

  def main(args : Array[String]): Unit = {
    args.headOption match {
      case Some(service) => {
        val provider = DejikoProviderFactory.providerByName(service)
        provider.downloadImageAt("dejiko_scala")
      }
      case _ => {
        println(
          """usage: run [provider]
            |download dejiko-chan illust by [provider]
            |
            |provider -- A service which provide illusts. You can choice pixiv
          """.stripMargin)
      }
    }
  }
}

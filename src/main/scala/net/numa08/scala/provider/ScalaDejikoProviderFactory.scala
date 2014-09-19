package net.numa08.scala.provider

import net.numa08.scala.pixiv.PixivProvider

object DejikoProviderFactory {

  val providers = Map("pixiv" -> new PixivProvider)

  class DejikoProviderNotFoundException(m : String) extends Exception(m)

  def providerByName(name : String) : DejikoProvider = {
    providers.get(name) match {
      case Some(x) => x
      case None => throw new DejikoProviderNotFoundException(s"$name Provider is not exists")
    }
  }
}

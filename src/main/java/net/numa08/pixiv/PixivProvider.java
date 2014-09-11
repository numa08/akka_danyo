package net.numa08.pixiv;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Mapper;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import net.numa08.provider.DejikoProvider;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.runtime.AbstractFunction0;

import java.util.concurrent.TimeUnit;

import static akka.pattern.Patterns.ask;

public class PixivProvider implements DejikoProvider{

    @Override
    public int downLoadDejikoAt(String path) throws Exception {
        final ActorSystem actorSystem = ActorSystem.create("pixivDownloder");
        final ActorRef actor = actorSystem.actorOf(Props.create(PixivDownloader.class));

        final PixivDownloader.GetCSV message = new PixivDownloader.GetCSV("http://spapi.pixiv.net/iphone/search.php?&s_mode=s_tag&word=%E3%81%A7%E3%81%98%E3%81%93&PHPSESSID=0&p=1");

        final FiniteDuration duration = Duration.create(5, TimeUnit.MINUTES);
        final Timeout timeout = new Timeout(duration);
        final Future<Object> result = ask(actor, message, timeout);
        int retval = (int)Await.result(result, duration);
        return retval;
    }
}

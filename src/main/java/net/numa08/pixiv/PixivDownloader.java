package net.numa08.pixiv;

import akka.actor.UntypedActor;
import scala.io.Codec;
import scala.io.Source;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PixivDownloader extends UntypedActor {

    private static final int IllustColumn = 9;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof GetCSV) {
            final GetCSV letter = (GetCSV)message;
            final URL url = new URL(letter.url);
            final String document = Source.fromURL(url, Codec.UTF8()).mkString();

            final List<String> urls = new ArrayList<>();
            for (String line : document.split("\\n")) {
                final String u = line.split(",")[IllustColumn].replace("\"", "");
                urls.add(u);
            }
            getSender().tell(urls, getSelf());
        } else {
            unhandled(message);
        }
    }

    public static final class GetCSV {
        private final String url;

        public GetCSV(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }

}

package net.numa08.pixiv;

import akka.actor.UntypedActor;

public class PixivDownloader extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof GetCSV) {
//            System.out.println("connected");
//            final String[] csv = Jsoup.connect(((GetCSV)message).url)
//                                       .get()
//                                       .body()
//                                       .toString()
//                                       .split("\n");
//            System.out.println("on receive");
//
//            for (String line : csv) {
//                final String u = line.split(",")[11];
//                System.out.println(u);
//            }
            getSender().tell(1, getSelf());
        } else if (message instanceof GetImage) {
            getSender().tell("ok", getSelf());
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

    public static final class GetImage {
        private final String url;

        public GetImage(String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
}

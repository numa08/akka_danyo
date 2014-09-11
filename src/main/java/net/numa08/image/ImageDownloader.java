package net.numa08.image;

import akka.actor.UntypedActor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class ImageDownloader extends UntypedActor {


    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof GetImages) {
            GetImages letter = (GetImages)message;
            try {
                final File targetDir = new File(letter.path);
                if (!targetDir.exists()) {
                    throw new NoSuchFieldError(letter.path +  "is not exit!!");
                }
                final URL url = new URL(letter.url);
                final String[] filePathes = url.getFile().split("/");
                final int fileSections = filePathes.length;
                final String fileName = filePathes[fileSections - 1];
                final File targetFile = new File(targetDir, fileName);

                try(InputStream is = url.openStream(); OutputStream os = new FileOutputStream(targetFile)) {
                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }
                }
                sender().tell(0, getSender());
            } catch (Exception e) {
                System.err.println(e.getMessage());
                sender().tell(e, getSelf());
            }
        } else {
            unhandled(message);
        }
    }

    public static class GetImages {
        final private String url;
        final private String path;

        public GetImages(String url, String path) {
            this.url = url;
            this.path = path;
        }
    }

}

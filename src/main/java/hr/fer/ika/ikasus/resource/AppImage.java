package hr.fer.ika.ikasus.resource;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.base64.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author Luka Ćurić
 */
public class AppImage implements Closeable {
    public static final String STATIC_CONTENT_PREFIX = "./static" ;
    public static final String VEHICLE_IMAGE_ROOT = STATIC_CONTENT_PREFIX + "/static-content/images/vehicles/";
    public static final String SIGNATURE_IMAGE_ROOT =
            STATIC_CONTENT_PREFIX + "/static-content/images/private/signatures/";

    private final String imagePath;
    private BufferedImage imageData;

    private AppImage(String imagePath) {
        this.imagePath = Objects.requireNonNull(imagePath);
    }

    public static class AppImageBuilder {
        private String imagePath;
        private String imageDataBase64;
        private final boolean loadFromFile;

        private AppImageBuilder(boolean loadFromFile) {
            this.loadFromFile = loadFromFile;
        }

        public AppImageBuilder withImagePath(String imagePath) {
            this.imagePath = Objects.requireNonNull(imagePath);
            return this;
        }

        public AppImageBuilder withImageData(String base64Data) {
            if (this.loadFromFile) {
                throw new IllegalStateException("This builder is only for loading images from files");
            }

            this.imageDataBase64 = Objects.requireNonNull(base64Data);
            return this;
        }

        public AppImage build() {
            if (this.imagePath == null || this.imageDataBase64 == null && !this.loadFromFile) {
                throw new IllegalStateException("Image path has to be set if loading image and imageData if saving image");
            }

            AppImage appImage = new AppImage(this.imagePath);

            if (this.loadFromFile) {
                try {
                    appImage.load();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            } else {
                appImage.parse(this.imageDataBase64);
            }

            return appImage;
        }
    }

    public static AppImageBuilder fromFileBuilder() {
        return new AppImageBuilder(true);
    }

    public static AppImageBuilder fromBase64Builder() {
        return new AppImageBuilder(false);
    }

    private void parse(String base64Data) {
        try {
            this.imageData = ImageIO.read(new ByteArrayInputStream(Base64.decode(base64Data)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void load() throws IOException {
        URL image = AppImage.class.getClassLoader().getResource(this.imagePath);

        if (image == null) {
            throw new FileNotFoundException("Image doesn't exits");
        }

        this.imageData = ImageIO.read(image);
    }

    public void save() throws IOException {
        URL image = AppImage.class.getClassLoader().getResource(this.imagePath);
        Path imagePath;

        if (image == null) {
            try {
                URL resource = AppImage.class.getClassLoader().getResource("./");
                if (resource == null) {
                    throw new IllegalArgumentException("Invalid image location: " + this.imagePath);
                }

                Path rootPath = Path.of(resource.toURI());
                imagePath = rootPath.resolve(this.imagePath);
                Files.createFile(imagePath);
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid image location: " + this.imagePath);
            }
        } else {
            try {
                imagePath = Path.of(image.toURI());
            } catch (URISyntaxException e) {
                throw new IllegalArgumentException("Invalid image location: " + this.imagePath);
            }
        }

        ImageIO.write(this.imageData, "jpeg", imagePath.toFile());

        this.imageData.flush();
    }

    @Override
    public void close() {
        this.imageData.flush();
        this.imageData = null;
    }
}

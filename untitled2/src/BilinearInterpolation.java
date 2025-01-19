import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BilinearInterpolation {
    public static BufferedImage bilinearResize(BufferedImage original, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB); // Исправили тип изображения
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                float gx = ((float)x) / (newWidth - 1) * (original.getWidth() - 1);
                float gy = ((float)y) / (newHeight - 1) * (original.getHeight() - 1);
                int gxi = Math.min((int)gx, original.getWidth() - 2);
                int gyi = Math.min((int)gy, original.getHeight() - 2);
                int c00 = original.getRGB(gxi, gyi);
                int c10 = original.getRGB(gxi + 1, gyi);
                int c01 = original.getRGB(gxi, gyi + 1);
                int c11 = original.getRGB(gxi + 1, gyi + 1);
                int rgb = bilinearInterpolate(gx - gxi, gy - gyi, c00, c10, c01, c11);
                resizedImage.setRGB(x, y, rgb);
            }
        }
        return resizedImage;
    }

    private static int bilinearInterpolate(float x, float y, int c00, int c10, int c01, int c11) {
        int red = (int)(
                ((1 - x) * (1 - y) * ((c00 >> 16) & 0xFF) +
                        x * (1 - y) * ((c10 >> 16) & 0xFF) +
                        (1 - x) * y * ((c01 >> 16) & 0xFF) +
                        x * y * ((c11 >> 16) & 0xFF))
        );
        int green = (int)(
                ((1 - x) * (1 - y) * ((c00 >> 8) & 0xFF) +
                        x * (1 - y) * ((c10 >> 8) & 0xFF) +
                        (1 - x) * y * ((c01 >> 8) & 0xFF) +
                        x * y * ((c11 >> 8) & 0xFF))
        );
        int blue = (int)(
                ((1 - x) * (1 - y) * (c00 & 0xFF) +
                        x * (1 - y) * (c10 & 0xFF) +
                        (1 - x) * y * (c01 & 0xFF) +
                        x * y * (c11 & 0xFF))
        );
        return (red << 16) | (green << 8) | blue;
    }

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("image.png"));
            if (originalImage == null) {
                System.out.println("Failed to load image. Please check the file path.");
                return;
            }
            System.out.println("Original Image Dimensions: " + originalImage.getWidth() + "x" + originalImage.getHeight());
            BufferedImage resizedImage = bilinearResize(originalImage,
                    originalImage.getWidth()*20,
                    originalImage.getHeight()*20);
            ImageIO.write(resizedImage, "png", new File("bilinear_image.png"));
            System.out.println("Image resizing completed!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

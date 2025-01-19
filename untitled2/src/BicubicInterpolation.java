import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class BicubicInterpolation {
    public static BufferedImage bicubicResize(BufferedImage original, int newWidth, int newHeight) {
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < newHeight; y++) {
            for (int x = 0; x < newWidth; x++) {
                float gx = ((float)x) / (newWidth - 1) * (original.getWidth() - 1);
                float gy = ((float)y) / (newHeight - 1) * (original.getHeight() - 1);
                int gxi = Math.min((int)gx, original.getWidth() - 2);
                int gyi = Math.min((int)gy, original.getHeight() - 2);
                int rgb = bicubicInterpolate(gx - gxi, gy - gyi, gxi, gyi, original);
                resizedImage.setRGB(x, y, rgb);
            }
        }
        return resizedImage;
    }

    private static int bicubicInterpolate(float x, float y, int gxi, int gyi, BufferedImage img) {
        int[] pixels = new int[16];
        for (int dy = -1; dy <= 2; dy++) {
            for (int dx = -1; dx <= 2; dx++) {
                pixels[(dy + 1) * 4 + (dx + 1)] = getRGB(gxi + dx, gyi + dy, img);
            }
        }
        float[] weightsX = getCubicWeights(x);
        float[] weightsY = getCubicWeights(y);

        int[] rgb = new int[3];
        for (int channel = 0; channel < 3; channel++) {
            float value = 0;
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < 4; i++) {
                    value += weightsX[i] * weightsY[j] * ((pixels[j * 4 + i] >> (16 - channel * 8)) & 0xFF);
                }
            }
            rgb[channel] = Math.min(Math.max((int)Math.round(value), 0), 255);
        }
        return (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
    }

    private static int getRGB(int x, int y, BufferedImage img) {
        if (x < 0 || x >= img.getWidth() || y < 0 || y >= img.getHeight()) {
            return new Color(0, 0, 0).getRGB(); // Черный для выхода за границы
        }
        return img.getRGB(x, y);
    }

    private static float[] getCubicWeights(float t) {
        float t2 = t * t;
        float t3 = t2 * t;
        return new float[]{
                0.5f * (-t3 + 2 * t2 - t),
                0.5f * (3 * t3 - 5 * t2 + 2),
                0.5f * (-3 * t3 + 4 * t2 + t),
                0.5f * (t3 - t2)
        };
    }

    public static void main(String[] args) {
        try {
            BufferedImage originalImage = ImageIO.read(new File("image.png"));
            if (originalImage == null) {
                System.out.println("Не удалось загрузить изображение. Проверьте путь к файлу.");
                return;
            }
            System.out.println("Оригинальные размеры изображения: " + originalImage.getWidth() + "x" + originalImage.getHeight());
            BufferedImage resizedImage = bicubicResize(originalImage,
                    originalImage.getWidth()*20,
                    originalImage.getHeight()*20);
            ImageIO.write(resizedImage, "png", new File("bicubic_image.png"));
            System.out.println("Изменение размера изображения завершено!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

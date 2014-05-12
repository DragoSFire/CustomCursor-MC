package net.craftzone.cursor;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(
   modid = "CustomCursor",
   name = "Custom Cursor by DragoSFire",
   version = "1.6.4"
)
public class mod_CustomCursor {
   private void initCursor(final InputStream is) throws LWJGLException, IOException {
       final BufferedImage image = flipImage(ImageIO.read(is));
       final int width = image.getWidth();
       final int height = image.getHeight();
       final int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);
       final IntBuffer buffer = BufferUtils.createIntBuffer(pixels.length);
       buffer.put(pixels);
       buffer.rewind();
       Mouse.setNativeCursor(new Cursor(width, height, 0, height - 1, 1, buffer, null));
   }
   private BufferedImage flipImage(final BufferedImage image) {
       final AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
       tx.translate(0, -image.getHeight());
       final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
       final BufferedImage result = op.filter(image, null);
       return result;
   }
   @EventHandler
   public void load(FMLInitializationEvent event) {
		InputStream is = getClass().getResourceAsStream("cursor.png");
		try {
			initCursor(is);
			is.close();
		} catch (LWJGLException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

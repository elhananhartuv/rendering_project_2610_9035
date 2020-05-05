package unittests;

import renderer.*;
import org.junit.Test;

/**
 * test class for ImageWriter Class
 * 
 * @author E&Y
 *
 */
public class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */
	@Test
	public void testWriteToImage() {
		String imageName = "testImage";// the name of jpeg file
		primitives.Color red = new primitives.Color(1000, 0, 0);
		primitives.Color blue = new primitives.Color(0, 0, 1000);

		int width = 1600;
		int height = 1000;
		int nx = 800;
		int ny = 500;
		// create the image and the view plane
		ImageWriter imageWriter = new ImageWriter(imageName, width, height, nx, ny);

		// write to pixels
		for (int i = 0; i < ny; i++) {
			for (int j = 0; j < nx; j++) {
				if (i % 50 == 0 || j % 50 == 0) {
					imageWriter.writePixel(j, i, red.getColor());
				} else {
					// background color
					imageWriter.writePixel(j, i, blue.getColor());
				}
			}
		}
		imageWriter.writeToImage();
	}
}

/**
 * 
 */
package unittests;

import static org.junit.Assert.*;

import java.awt.Color;

import renderer.*;
import primitives.*;
import org.junit.Test;

/**
 *  test class for ImageWriter Class
 * @author E&Y
 *
 */
public class ImageWriterTest {

	

	/**
	 * Test method for {@link renderer.ImageWriter#writeToImage()}.
	 */
	@Test
	public void testWriteToImage() {
		String imagename = "testImage";
		primitives.Color red=new primitives.Color(1000,0,0);
		primitives.Color blue=new primitives.Color(0,0,1000);

		int width = 1600;
		int height = 1000;
		int nx = 800;
		int ny =500 ;
	
		ImageWriter imageWriter = new ImageWriter(imagename, width, height, nx, ny);

		for (int i = 0; i < ny; i++) {
			for (int j = 0; j < nx; j++) {
				if (i % 50 == 0 || j % 50 == 0) {
					imageWriter.writePixel(j, i, red.getColor());
				} else {
					imageWriter.writePixel(j, i, blue.getColor());
				}
			}
		}
		imageWriter.writeToImage();
	}

}

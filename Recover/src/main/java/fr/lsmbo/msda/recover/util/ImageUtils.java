package fr.lsmbo.msda.recover.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * 
 * @author aromdhani
 *
 */
public class ImageUtils {
    /** get image View from resources */
	public static void newImageView() {
       new ImageView();
	}
   /** get image */
	public  Image image(IconRessource path) {
       return new Image(this.getClass().getResourceAsStream(path.toString()));
	}
}

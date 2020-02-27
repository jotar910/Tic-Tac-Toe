package ui.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Images {
    private static String mImagesPath = "img/";

    private static Map<String, Image> images;

    static {
        images = new HashMap<>();

        try {
            images.put(CONST.TICTACTOE_IMAGE, ImageIO.read(Resources.getResourceFile(mImagesPath +CONST.TICTACTOE_IMAGE)));
            for(String s:CONST.PLAYER_ICONS){
                images.put(s, ImageIO.read(Resources.getResourceFile(mImagesPath +s)));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static Map<String, Image> getImages(){ return images; }
    public static Image getImage(String imageName){ return images.get(imageName); }

}

package ui.gui;

import java.net.URL;

public class Resources {

    public static URL getResourceFile(String name){
        return Resources.class.getResource(name);
    }
}

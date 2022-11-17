package com.group38.prisonbreak;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class Tile implements Drawable {

    private static final HashMap<Integer, Color> colourMap = new HashMap<>() {{
        put(0, Color.rgb(253, 101, 105));
        put(1, Color.rgb(107, 255, 109));
        put(2, Color.rgb(104, 104, 252));
        put(3, Color.rgb(255, 245, 138));
        put(4, Color.rgb(41, 255, 254));
        put(5, Color.rgb(253, 5, 253));
    }};

    @Override
    public void draw(GraphicsContext g) {

    }
}

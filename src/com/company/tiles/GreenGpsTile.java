package com.company.tiles;

import com.company.global_constants.Constants;

import java.awt.*;

public class GreenGpsTile extends Tile {

    public GreenGpsTile(Color color, int x, int y) {
        super(color,x, y);
    }

    @Override
    public String getType() {
        return Constants.GREEN_GPS;
    }
}

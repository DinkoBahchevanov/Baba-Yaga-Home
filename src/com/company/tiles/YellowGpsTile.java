package com.company.tiles;

import com.company.global_constants.Constants;

import java.awt.*;

public class YellowGpsTile extends Tile {

    public YellowGpsTile(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getType() {
        return Constants.YELLOW_GPS;
    }
}

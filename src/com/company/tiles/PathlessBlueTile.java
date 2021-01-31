package com.company.tiles;

import com.company.global_constants.Constants;

import java.awt.*;

public class PathlessBlueTile extends Tile {

    public PathlessBlueTile(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getType() {
        return Constants.BLUE_GPS;
    }
}

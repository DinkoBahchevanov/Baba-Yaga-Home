package com.company.tiles;

import com.company.global_constants.Constants;

import java.awt.*;
import java.lang.constant.Constable;

public class UnexploredGpsTile extends Tile {

    public UnexploredGpsTile(Color color, int x, int y) {
        super(color,x,y);
    }

    @Override
    public String getType() {
        return Constants.RED_GPS;
    }
}

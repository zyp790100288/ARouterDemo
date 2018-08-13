package com.intretech.app.practice;

import com.intretech.app.base.data.BaseData;

/**
 * Created by yq07744 on 2018/7/5 0005.
 */

public class BmpTrack extends BaseData {
    private int xPosition;
    private int yPosition;
    private byte pointValue;

    public BmpTrack() {
        super();
    }

    public BmpTrack(int xPosition,int yPosition,byte isZero){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.pointValue = isZero;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public byte getPointValue() {
        return pointValue;
    }

    public void setPointValue(byte pointValue) {
        this.pointValue = pointValue;
    }
}

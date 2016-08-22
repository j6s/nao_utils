package de.dhbw.wwi13b.shared.util;

import java.util.ArrayList;

/**
 * Created by huck on 22.08.16.
 */
public class LMCoords {
    float alpha,beta,sizeX,sizeY,heading;
    int landmarkID;

    public float getAlpha() {
        return alpha;
    }

    public float getBeta() {
        return beta;
    }

    public float getSizeX() {
        return sizeX;
    }

    public float getSizeY() {
        return sizeY;
    }

    public float getHeading() {
        return heading;
    }

    public int getLandmarkID() {
        return landmarkID;
    }

    LMCoords(float alpha, float beta, float sizeX, float sizeY, float heading, int markID){
        this.alpha = alpha;
        this.beta = beta;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.heading = heading;
        this.landmarkID = markID;
    }

    static LMCoords fromLandmarkInfo(ArrayList<Object> LMInfo){
        ArrayList<Object> list1 =  (ArrayList<Object>) LMInfo.get(0);
        ArrayList<Object> list2 =  (ArrayList<Object>) list1.get(0);
        ArrayList<Object> list3 =  (ArrayList<Object>) list1.get(1);
        return new LMCoords((float)list2.get(1),(float)list2.get(2),(float)list2.get(3),(float)list2.get(4),(int)list2.get(5),(int)list3.get(0));
    }

}


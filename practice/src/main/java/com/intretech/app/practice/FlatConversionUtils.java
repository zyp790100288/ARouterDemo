package com.intretech.app.practice;

import java.util.ArrayList;

/**
 * Created by yq06171 on 2018/7/30.
 * 平包针坐标转换
 */

public class FlatConversionUtils {

    /**
     * 轮廓坐标点转换
     * @param bmpTracks 轮廓坐标点集合
     * @param distance 两点之间的距离
     * @param height 上下边界高度
     * @return
     */
    public static ArrayList<BmpTrack> flatConversion(ArrayList<BmpTrack> bmpTracks, float distance, float height){
        ArrayList<BmpTrack> flatPoints = new ArrayList<>();
        for (int i = 1; i < bmpTracks.size() - 1; i++) {
            // 获取左右两点直线的斜率
            float k = getLineSlope(bmpTracks.get(i-1).getxPosition(),
                    bmpTracks.get(i-1).getxPosition(),
                    bmpTracks.get(i + 1).getxPosition(),
                    bmpTracks.get(i + 1).getxPosition());
            // 互为垂直的两条直线斜率k1*k2 = -1;
            float[] flatPoint = getCeilAndFloorPoint(bmpTracks.get(i).getxPosition(),
                    bmpTracks.get(i).getxPosition(),(-1/k),height/2);
            saveFlatPoint(flatPoints,flatPoint);
            float lastDistance = -1f;
            tag : for (int j = i + 1; j < bmpTracks.size() -1; j++) {
                float currentDistance = getPointDistance(bmpTracks.get(j).getxPosition(),
                        bmpTracks.get(j).getxPosition(),
                        bmpTracks.get(j + 1).getxPosition(),
                        bmpTracks.get(j + 1).getxPosition());
                // 两点之间的距离和规定距离的差距
                float currentAbsDistance = Math.abs(distance - currentDistance);
                // 获取最接近规定距离的点
                if(lastDistance != -1f){
                    if(lastDistance <= currentAbsDistance ){
                        i = j -1;
                        break tag;
                    }else{
                        lastDistance = currentDistance;
                    }
                }else{
                    lastDistance = currentDistance;
                }

            }
        }
        return flatPoints;
    }

    /**
     * 保存转换后的坐标
     * @param flatPoints
     * @param flatPoint
     */
    private static void saveFlatPoint(ArrayList<BmpTrack> flatPoints, float[] flatPoint) {
        BmpTrack bmpTrack1 = new BmpTrack((int)flatPoint[0],(int)flatPoint[1],(byte)0);
        BmpTrack bmpTrack2 = new BmpTrack((int)flatPoint[2],(int)flatPoint[3],(byte)0);
        flatPoints.add(bmpTrack1);
        flatPoints.add(bmpTrack2);

    }


    /**
     * 获取直线斜率
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return 两点之间直线的斜率
     */
    public static float getLineSlope(double x1,double y1,double x2,double y2){
        if(x2 - x1 ==0){
            // 垂直
            return  0;
        }
        return (float) ((y2 - y1)/(x2 - x1));
    }


    /**
     * 获取两点之间的距离
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return float 两点之间的距离
     */
    public static float getPointDistance(double x1,double y1,double x2,double y2){
        return (float) Math.sqrt((Math.pow((x2-x1),2)+Math.pow((y2-y1),2)));
    }

    /**
     * 获取点的上下边界点
     * @param x 当前点x坐标
     * @param y 当前点坐标
     * @param k 斜率
     * @param distance 与点的距离
     * @return
     */
    public static float[] getCeilAndFloorPoint(float x,float y,float k ,float distance){
        // 第二步：求得在直线y=kx+b上，距离当前坐标距离为L的某点
        // 一元二次方程Ax^2+Bx+C=0中,
        // 一元二次方程求根公式：
        // 两根x1,x2= [-B±√(B^2-4AC)]/2A
        // ①(y-y0)^2+(x-x0)^2=L^2;
        // ②y=kx+b;
        // 式中x,y即为根据以上lenthUnit单位长度(这里就是距离L)对应点的坐标
        // 由①②表达式得到:(k^2+1)x^2+2[(b-y0)k-x0]x+[(b-y0)^2+x0^2-L^2]=0
        float[] coodrs= new float[4];
        float b = y - k * x;
        float A = (float) (Math.pow(k, 2) + 1);// A=k^2+1;
        float B = 2 * ((b - y) * k - x);// B=2[(b-y0)k-x0];
        // C=(b-y0)^2+x0^2-L^2
        float C = (float) (Math.pow(b - y, 2) + Math.pow(x, 2)
                        - Math.pow(distance, 2));
        // 两根x1,x2= [-B±√(B^2-4AC)]/2A
        float x1 = (float) ((-B + Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A));
        float x2 = (float) ((-B - Math.sqrt(Math.pow(B, 2) - 4 * A * C)) / (2 * A));
        coodrs[0] = x1;
        coodrs[1] = k*x1 + b;
        coodrs[2] = x2;
        coodrs[3] = k*x2 + b;
        return coodrs;
    }


}

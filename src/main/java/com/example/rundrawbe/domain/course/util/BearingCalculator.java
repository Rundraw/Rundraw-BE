package com.example.rundrawbe.domain.course.util;

import com.example.rundrawbe.domain.course.entity.DraftPoint;

public class BearingCalculator {

    // 두 좌표 사이의 방위각(0~360도) 계산
    public static double calculateBearing(double lat1, double lng1, double lat2, double lng2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double dLngRad = Math.toRadians(lng2 - lng1);

        double y = Math.sin(dLngRad) * Math.cos(lat2Rad);
        double x = Math.cos(lat1Rad) * Math.sin(lat2Rad)
                - Math.sin(lat1Rad) * Math.cos(lat2Rad) * Math.cos(dLngRad);


        double bearing = Math.toDegrees(Math.atan2(y, x));
        return (bearing + 360) % 360; // 0~360 범위로 정규화
    }


    // 두 방위각의 차이를 -180~180 범위로 정규화
    // (예: 359도 → 1도로 바뀌는 경우도 "+2도 회전"으로 정확히 계산되게 함)
    public static double bearingDiff (Double bearing1, Double bearing2) {
        double diff = bearing2 - bearing1;
        return ((diff + 540) % 360) - 180;
    }
}

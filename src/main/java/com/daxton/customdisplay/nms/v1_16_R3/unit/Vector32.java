package com.daxton.customdisplay.nms.v1_16_R3.unit;

import com.daxton.customdisplay.api.other.Vector3f;
import org.bukkit.util.EulerAngle;

public class Vector32 {

    public Vector32(){

    }

    public static Vector3f getVector3f(EulerAngle eulerAngle) {
        return new Vector3f((float)Math.toDegrees(eulerAngle.getX()), (float)Math.toDegrees(eulerAngle.getY()), (float)Math.toDegrees(eulerAngle.getZ()));
    }

}

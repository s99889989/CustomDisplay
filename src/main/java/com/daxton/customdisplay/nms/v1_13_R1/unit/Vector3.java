package com.daxton.customdisplay.nms.v1_13_R1.unit;

import net.minecraft.server.v1_13_R1.Vector3f;
import org.bukkit.util.EulerAngle;

public class Vector3 {

    public Vector3(){

    }

    public static Vector3f getVector3f(EulerAngle eulerAngle) {
        return new Vector3f((float)Math.toDegrees(eulerAngle.getX()), (float)Math.toDegrees(eulerAngle.getY()), (float)Math.toDegrees(eulerAngle.getZ()));
    }

}

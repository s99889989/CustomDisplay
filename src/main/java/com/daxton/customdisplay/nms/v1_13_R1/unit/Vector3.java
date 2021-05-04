package com.daxton.customdisplay.nms.v1_13_R1.unit;

import com.daxton.customdisplay.nms.main.unit.Vector3Main;
import net.minecraft.server.v1_13_R1.Vector3f;
import org.bukkit.util.EulerAngle;

public class Vector3 implements Vector3Main {

    public Vector3(){

    }

    @Override
    public Vector3f getVector3f(EulerAngle eulerAngle) {
        return new Vector3f((float)Math.toDegrees(eulerAngle.getX()), (float)Math.toDegrees(eulerAngle.getY()), (float)Math.toDegrees(eulerAngle.getZ()));
    }

}

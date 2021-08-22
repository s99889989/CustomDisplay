package com.daxton.customdisplay.api.other;

public class Vector3f {

    protected final float x;
    protected final float y;
    protected final float z;

    public Vector3f(float var0, float var1, float var2) {
        this.x = !Float.isInfinite(var0) && !Float.isNaN(var0) ? var0 % 360.0F : 0.0F;
        this.y = !Float.isInfinite(var1) && !Float.isNaN(var1) ? var1 % 360.0F : 0.0F;
        this.z = !Float.isInfinite(var2) && !Float.isNaN(var2) ? var2 % 360.0F : 0.0F;
    }


    public boolean equals(Object var0) {
        if (!(var0 instanceof Vector3f)) {
            return false;
        } else {
            Vector3f var1 = (Vector3f)var0;
            return this.x == var1.x && this.y == var1.y && this.z == var1.z;
        }
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public float getZ() {
        return this.z;
    }

}

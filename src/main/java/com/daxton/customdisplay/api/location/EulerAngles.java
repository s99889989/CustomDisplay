package com.daxton.customdisplay.api.location;

public class EulerAngles {

    public float pitch;
    public float yaw;
    public float roll;

    public EulerAngles(float pitch, float yaw, float roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public EulerAngles(float w, float x, float y, float z) {
        // roll (x-axis rotation)
        float sinr_cosp = 2 * (w * x + y * z);
        float cosr_cosp = 1 - 2 * (x * x + y * y);
        this.roll = (float) Math.atan2(sinr_cosp, cosr_cosp);

        // pitch (y-axis rotation)
        float sinp = 2 * (w * y - z * x);
        if (Math.abs(sinp) >= 1) {
            this.pitch = Math.copySign(1.57075f, sinp); // use 90 degrees if out of range
        } else {
            this.pitch = (float) Math.asin(sinp);
        }

        // yaw (z-axis rotation)
        float siny_cosp = 2 * (w * z + x * y);
        float cosy_cosp = 1 - 2 * (y * y + z * z);
        this.yaw = (float) Math.atan2(siny_cosp, cosy_cosp);
    }

    public Quaternion ToQuaternion() {
        //欧拉角转四元数，角度减半是因为四元数旋转计算时需要旋转两次，具体原理请查看四元数原理
        float cy = (float) Math.cos(yaw * 0.5f);
        float sy = (float) Math.sin(yaw * 0.5f);
        float cp = (float) Math.cos(pitch * 0.5f);
        float sp = (float) Math.sin(pitch * 0.5f);
        float cr = (float) Math.cos(roll * 0.5f);
        float sr = (float) Math.sin(roll * 0.5f);
        Quaternion q = new Quaternion();
        q.w = cy * cp * cr + sy * sp * sr;
        q.x = cy * cp * sr - sy * sp * cr;
        q.y = sy * cp * sr + cy * sp * cr;
        q.z = sy * cp * cr - cy * sp * sr;
        return q;
    }


}

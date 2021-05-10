package com.daxton.customdisplay.api.location;

public class Quaternion {

    public float w;
    public float x;
    public float y;
    public float z;

    public Quaternion() {
    }

    public Quaternion(float w,float x,float y,float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //向量旋转
    static void VectorRotation(float[] vector,Quaternion q) {
        Quaternion qv = new Quaternion(0,vector[0],vector[1],vector[2]);
        //四元数旋转公式q0*qv*（q0逆）s
        qv = Quaternion.Multiplication(Quaternion.Multiplication(q, qv),q.Inverse());
        vector[0] = qv.x;
        vector[1] = qv.y;
        vector[2] = qv.z;
    }


    //返回欧拉角
    public EulerAngles ToEulerAngles() {
        // roll (x-axis rotation)
        return new EulerAngles(this.w,this.x,this.y,this.z);
    }

    //四元数相乘
    static Quaternion Multiplication(Quaternion q0, Quaternion q1) {
        Quaternion ret = new Quaternion();
        ret.w = q0.w * q1.w - q0.x * q1.x - q0.y * q1.y - q0.z * q1.z;
        ret.x = q0.w * q1.x + q0.x * q1.w + q0.y * q1.z - q0.z * q1.y;
        ret.y = q0.w * q1.y + q0.y * q1.w + q0.z * q1.x - q0.x * q1.z;
        ret.z = q0.w * q1.z + q0.z * q1.w + q0.x * q1.y - q0.y * q1.x;
        return ret;
    }

    //四元数求逆
    public Quaternion Inverse() {
        Quaternion ret;
        ret = this;
        ret.x *= -1;
        ret.y *= -1;
        ret.z *= -1;
        return ret;
    }



}

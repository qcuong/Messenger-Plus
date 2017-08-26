package com.quoccuong.model;

import android.util.Log;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by sev_user on 9/7/2015.
 */
public class DataTrain implements Serializable {
    public int NX = 16;
    public int NY = 16;
    public int NZ = 16;
    public int NGX = 16;
    public int NGY = 16;
    public int NGZ = 16;
    public static int M = 33;
    // accelerometer x -----------------------------------------------------------------
    public double[] PiX;
    public double[][] AX;
    public double[][] BX;

    // accelerometer y -----------------------------------------------------------------
    public double[] PiY;
    public double[][] AY;
    public double[][] BY;

    // accelerometer z -----------------------------------------------------------------
    public double[] PiZ;
    public double[][] AZ;
    public double[][] BZ;

    // gyroscopes x -----------------------------------------------------------------
    public double[] PiGX;
    public double[][] AGX;
    public double[][] BGX;

    // gyroscopes y -----------------------------------------------------------------
    public double[] PiGY;
    public double[][] AGY;
    public double[][] BGY;

    // gyroscopes z -----------------------------------------------------------------
    public double[] PiGZ;
    public double[][] AGZ;
    public double[][] BGZ;


    public void setDataTrainX(int nx) {
        // accelerometer x -----------------------------------------------------------------
        this.NX = nx;
        this.PiX = new double[NX + 1];
        this.AX = new double[NX + 1][NX + 1];
        this.BX = new double[NX + 1][M + 1];

        double[] randomPiX = randomArray(NX);
        for (int i = 1; i <= NX; i++) {
            this.PiX[i] = randomPiX[i];

            double[] randomAX = randomArray(NX);
            for (int j = 1; j <= NX; j++) {
                this.AX[i][j] = randomAX[j];
            }

            double[] randomBX = randomArray(M);
            for (int j = 1; j <= M; j++) {
                this.BX[i][j] = randomBX[j];
            }
        }
    }

    public void setDataTrainY(int ny) {
        // accelerometer y -----------------------------------------------------------------
        this.NY = ny;
        this.PiY = new double[NY + 1];
        this.AY = new double[NY + 1][NY + 1];
        this.BY = new double[NY + 1][M + 1];
        double[] randomPiY = randomArray(NY);
        for (int i = 1; i <= NY; i++) {
            this.PiY[i] = randomPiY[i];

            double[] randomAY = randomArray(NY);
            for (int j = 1; j <= NY; j++) {
                this.AY[i][j] = randomAY[j];
            }

            double[] randomBY = randomArray(M);
            for (int j = 1; j <= M; j++) {
                this.BY[i][j] = randomBY[j];
            }
        }
    }

    public void setDataTrainZ(int nz) {
        // accelerometer y -----------------------------------------------------------------
        this.NZ = nz;
        this.PiZ = new double[NZ + 1];
        this.AZ = new double[NZ + 1][NZ + 1];
        this.BZ = new double[NZ + 1][M + 1];
        double[] randomPiZ = randomArray(NZ);
        for (int i = 1; i <= NZ; i++) {
            this.PiZ[i] = randomPiZ[i];

            double[] randomAZ = randomArray(NZ);
            for (int j = 1; j <= NZ; j++) {
                this.AZ[i][j] = randomAZ[j];
            }

            double[] randomBZ = randomArray(M);
            for (int j = 1; j <= M; j++) {
                this.BZ[i][j] = randomBZ[j];
            }
        }
    }

    public void setDataTrainGX(int ngx) {
        // gyroscopes x -----------------------------------------------------------------
        this.NGX = ngx;
        this.PiGX = new double[NGX + 1];
        this.AGX = new double[NGX + 1][NGX + 1];
        this.BGX = new double[NGX + 1][M + 1];

        double[] randomPiGX = randomArray(NGX);
        for (int i = 1; i <= NGX; i++) {
            this.PiGX[i] = randomPiGX[i];

            double[] randomAGX = randomArray(NGX);
            for (int j = 1; j <= NGX; j++) {
                this.AGX[i][j] = randomAGX[j];
            }

            double[] randomBx = randomArray(M);
            for (int j = 1; j <= M; j++) {
                this.BGX[i][j] = randomBx[j];
            }
        }
    }

    public void setDataTrainGY(int ngy) {
        // gyroscopes y -----------------------------------------------------------------
        this.NGY = ngy;
        this.PiGY = new double[NGY + 1];
        this.AGY = new double[NGY + 1][NGY + 1];
        this.BGY = new double[NGY + 1][M + 1];

        double[] randomPiGY = randomArray(NGY);
        for (int i = 1; i <= NGY; i++) {
            this.PiGY[i] = randomPiGY[i];

            double[] randomAGY = randomArray(NGY);
            for (int j = 1; j <= NGY; j++) {
                this.AGY[i][j] = randomAGY[j];
            }

            double[] randomBGY = randomArray(M);
            for (int j = 1; j <= M; j++) {
                this.BGY[i][j] = randomBGY[j];
            }
        }
    }

    public void setDataTrainGZ(int ngz) {
        // gyroscopes z -----------------------------------------------------------------
        this.NGZ = ngz;
        this.PiGZ = new double[NGZ + 1];
        this.AGZ = new double[NGZ + 1][NGZ + 1];
        this.BGZ = new double[NGZ + 1][M + 1];

        double[] randomPiGZ = randomArray(NGZ);
        for (int i = 1; i <= NGZ; i++) {
            this.PiGZ[i] = randomPiGZ[i];

            double[] randomAGZ = randomArray(NGZ);
            for (int j = 1; j <= NGZ; j++) {
                this.AGZ[i][j] = randomAGZ[j];
            }

            double[] randomBGZ = randomArray(M);
            for (int j = 1; j <= M; j++) {
                this.BGZ[i][j] = randomBGZ[j];
            }
        }
    }

    public DataTrain() {
    }

    public DataTrain(int nx, int ny, int nz, int ngx, int ngy, int ngz) {
        // accelerometer x -----------------------------------------------------------------
        setDataTrainX(nx);

        // accelerometer y -----------------------------------------------------------------
        setDataTrainY(ny);

        // accelerometer z -----------------------------------------------------------------
        setDataTrainZ(nz);

        // gyroscopes x -----------------------------------------------------------------
        setDataTrainGX(ngx);

        // gyroscopes y -----------------------------------------------------------------
        setDataTrainGY(ngy);

        // gyroscopes z -----------------------------------------------------------------
        setDataTrainGZ(ngz);
    }

    public void log() {
        Log.e("quoccuong", "PiX ######################################################################################################################");
        String s = "";
        for (int i = 1; i <= NX; i++) {
            s = s + PiX[i] + " ";
        }
        Log.e("quoccuong", s);

        Log.e("quoccuong", "AX ###########################################################");
        for (int i = 1; i <= NX; i++) {
            s = "";
            for (int j = 1; j <= NX; j++) {
                s = s + AX[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "BX ###########################################################");
        for (int i = 1; i <= NX; i++) {
            s = "";
            for (int j = 1; j <= M; j++) {
                s = s + BX[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "PiY ######################################################################################################################");
        s = "";
        for (int i = 1; i <= NY; i++) {
            s = s + PiY[i] + " ";
        }
        Log.e("quoccuong", s);

        Log.e("quoccuong", "AY ###########################################################");
        for (int i = 1; i <= NY; i++) {
            s = "";
            for (int j = 1; j <= NY; j++) {
                s = s + AY[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "BY ###########################################################");
        for (int i = 1; i <= NY; i++) {
            s = "";
            for (int j = 1; j <= M; j++) {
                s = s + BY[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "PiZ ######################################################################################################################");
        s = "";
        for (int i = 1; i <= NZ; i++) {
            s = s + PiZ[i] + " ";
        }
        Log.e("quoccuong", s);

        Log.e("quoccuong", "AZ ###########################################################");
        for (int i = 1; i <= NZ; i++) {
            s = "";
            for (int j = 1; j <= NZ; j++) {
                s = s + AZ[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "BZ ###########################################################");
        for (int i = 1; i <= NZ; i++) {
            s = "";
            for (int j = 1; j <= M; j++) {
                s = s + BZ[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }


        Log.e("quoccuong", "PiGX ######################################################################################################################");
        s = "";
        for (int i = 1; i <= NGX; i++) {
            s = s + PiGX[i] + " ";
        }
        Log.e("quoccuong", s);

        Log.e("quoccuong", "AGX ###########################################################");
        for (int i = 1; i <= NGX; i++) {
            s = "";
            for (int j = 1; j <= NGX; j++) {
                s = s + AGX[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "BGX ###########################################################");
        for (int i = 1; i <= NGX; i++) {
            s = "";
            for (int j = 1; j <= M; j++) {
                s = s + BGX[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "PiGY ######################################################################################################################");
        s = "";
        for (int i = 1; i <= NGY; i++) {
            s = s + PiGY[i] + " ";
        }
        Log.e("quoccuong", s);

        Log.e("quoccuong", "AGY ###########################################################");
        for (int i = 1; i <= NGY; i++) {
            s = "";
            for (int j = 1; j <= NGY; j++) {
                s = s + AGY[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "BGY ###########################################################");
        for (int i = 1; i <= NGY; i++) {
            s = "";
            for (int j = 1; j <= M; j++) {
                s = s + BGY[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "PiGZ ######################################################################################################################");
        s = "";
        for (int i = 1; i <= NGZ; i++) {
            s = s + PiGZ[i] + " ";
        }
        Log.e("quoccuong", s);

        Log.e("quoccuong", "AGZ ###########################################################");
        for (int i = 1; i <= NGZ; i++) {
            s = "";
            for (int j = 1; j <= NGZ; j++) {
                s = s + AGZ[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

        Log.e("quoccuong", "BGZ ###########################################################");
        for (int i = 1; i <= NGZ; i++) {
            s = "";
            for (int j = 1; j <= M; j++) {
                s = s + BGZ[i][j] + " ";
            }
            Log.e("quoccuong", s);
        }

    }


    double[] randomArray(int n) {
        double[] kq = new double[n + 1];
        Random random = new Random();
        double t = 0;
        for (int i = 1; i <= n; i++) {
            kq[i] = random.nextInt(100 * n);
            t = t + kq[i];
        }
        for (int i = 1; i <= n; i++) {
            kq[i] = kq[i] / t;
        }
        return kq;
    }

}
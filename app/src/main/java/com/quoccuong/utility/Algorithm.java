package com.quoccuong.utility;

import android.util.Log;

import com.quoccuong.model.DataTrain;
import com.quoccuong.model.PointOfTime;

import java.util.ArrayList;

/**
 * Created by sev_user on 10/7/2015.
 */
public class Algorithm {
    public static double POMODEL_X(DataTrain train, ArrayList<PointOfTime> input) {
        double[][] alphaX = null;
        int T = input.size();
        int adding = (DataTrain.M / 2) + 1;
        double P = 0;

        // Tinh alphaX  -----------------------------
        alphaX = new double[T + 1][train.NX + 1];

        for (int i = 1; i <= train.NX; i++) {
            alphaX[1][i] = train.PiX[i];
            int q = adding + input.get(0).getAccX();
            alphaX[1][i] = alphaX[1][i] * train.BX[i][q];
        }

        for (int t = 1; t < T; t++) {
            for (int j = 1; j <= train.NX; j++) {
                alphaX[t + 1][j] = 0;
                for (int i = 0; i <= train.NX; i++) {
                    alphaX[t + 1][j] = alphaX[t + 1][j] + alphaX[t][i] * train.AX[i][j];
                }
                alphaX[t + 1][j] = alphaX[t + 1][j] * train.BX[j][adding + input.get(t).getAccX()];   // max index of data is T-1; position = (t+1)-1 = t;
            }
        }

        for (int i = 1; i <= train.NX; i++) {
            P = P + alphaX[T][i];
        }

        return P;
    }

    public static double POMODEL_Y(DataTrain train, ArrayList<PointOfTime> input) {
        double[][] alphaY = null;
        int T = input.size();
        int adding = (DataTrain.M / 2) + 1;
        double P = 0;

        alphaY = new double[T + 1][train.NY + 1];

        for (int i = 1; i <= train.NY; i++) {
            alphaY[1][i] = train.PiY[i] * train.BY[i][adding + input.get(0).getAccY()];
        }

        for (int t = 1; t < T; t++) {
            for (int j = 1; j <= train.NY; j++) {
                alphaY[t + 1][j] = 0;
                for (int i = 0; i <= train.NY; i++) {
                    alphaY[t + 1][j] = alphaY[t + 1][j] + alphaY[t][i] * train.AY[i][j];
                }
                alphaY[t + 1][j] = alphaY[t + 1][j] * train.BY[j][adding + input.get(t).getAccY()];   // max index of data is T-1; position = (t+1)-1 = t;
            }
        }

        for (int i = 1; i <= train.NY; i++) {
            P = P + alphaY[T][i];
        }

        return P;
    }

    public static double POMODEL_Z(DataTrain train, ArrayList<PointOfTime> input) {
        double[][] alphaZ = null;
        int T = input.size();
        int adding = (DataTrain.M / 2) + 1;
        double P = 0;

        alphaZ = new double[T + 1][train.NZ + 1];

        for (int i = 1; i <= train.NZ; i++) {
            alphaZ[1][i] = train.PiZ[i] * train.BZ[i][adding + input.get(0).getAccZ()];
        }

        for (int t = 1; t < T; t++) {
            for (int j = 1; j <= train.NZ; j++) {
                alphaZ[t + 1][j] = 0;
                for (int i = 0; i <= train.NZ; i++) {
                    alphaZ[t + 1][j] = alphaZ[t + 1][j] + alphaZ[t][i] * train.AZ[i][j];
                }
                alphaZ[t + 1][j] = alphaZ[t + 1][j] * train.BZ[j][adding + input.get(t).getAccZ()];   // max index of data is T-1; position = (t+1)-1 = t;
            }
        }
        for (int i = 1; i <= train.NZ; i++) {
            P = P + alphaZ[T][i];
        }

        return P;
    }

    public static double POMODEL_GX(DataTrain train, ArrayList<PointOfTime> input) {
        double[][] alphaGX = null;
        int T = input.size();
        int adding = (DataTrain.M / 2) + 1;
        double P = 0;
        alphaGX = new double[T + 1][train.NGX + 1];

        for (int i = 1; i <= train.NGX; i++) {
            alphaGX[1][i] = train.PiGX[i] * train.BGX[i][adding + input.get(0).getGyrX()];
        }

        for (int t = 1; t < T; t++) {
            for (int j = 1; j <= train.NGX; j++) {
                alphaGX[t + 1][j] = 0;
                for (int i = 0; i <= train.NGX; i++) {
                    alphaGX[t + 1][j] = alphaGX[t + 1][j] + alphaGX[t][i] * train.AGX[i][j];
                }
                alphaGX[t + 1][j] = alphaGX[t + 1][j] * train.BGX[j][adding + input.get(t).getGyrX()];   // max index of data is T-1; position = (t+1)-1 = t;
            }
        }

        for (int i = 1; i <= train.NGX; i++) {
            P = P + alphaGX[T][i];
        }
        return P;
    }

    public static double POMODEL_GY(DataTrain train, ArrayList<PointOfTime> input) {
        double[][] alphaGY = null;
        int T = input.size();
        int adding = (DataTrain.M / 2) + 1;
        double P = 0;
        alphaGY = new double[T + 1][train.NGY + 1];

        for (int i = 1; i <= train.NGY; i++) {
            alphaGY[1][i] = train.PiGY[i] * train.BGY[i][adding + input.get(0).getGyrY()];
        }

        for (int t = 1; t < T; t++) {
            for (int j = 1; j <= train.NGY; j++) {
                alphaGY[t + 1][j] = 0;
                for (int i = 0; i <= train.NGY; i++) {
                    alphaGY[t + 1][j] = alphaGY[t + 1][j] + alphaGY[t][i] * train.AGY[i][j];
                }
                alphaGY[t + 1][j] = alphaGY[t + 1][j] * train.BGY[j][adding + input.get(t).getGyrY()];   // max index of data is T-1; position = (t+1)-1 = t;
            }
        }

        for (int i = 1; i <= train.NGY; i++) {
            P = P + alphaGY[T][i];
        }
        return P;
    }

    public static double POMODEL_GZ(DataTrain train, ArrayList<PointOfTime> input) {
        double[][] alphaGZ = null;
        int T = input.size();
        int adding = (DataTrain.M / 2) + 1;
        double P = 0;

        alphaGZ = new double[T + 1][train.NGZ + 1];

        for (int i = 1; i <= train.NGZ; i++) {
            alphaGZ[1][i] = train.PiGZ[i] * train.BGZ[i][adding + input.get(0).getGyrZ()];
        }

        for (int t = 1; t < T; t++) {
            for (int j = 1; j <= train.NGZ; j++) {
                alphaGZ[t + 1][j] = 0;
                for (int i = 0; i <= train.NGZ; i++) {
                    alphaGZ[t + 1][j] = alphaGZ[t + 1][j] + alphaGZ[t][i] * train.AGZ[i][j];
                }
                alphaGZ[t + 1][j] = alphaGZ[t + 1][j] * train.BGZ[j][adding + input.get(t).getGyrZ()];   // max index of data is T-1; position = (t+1)-1 = t;
            }
        }
        for (int i = 1; i <= train.NGZ; i++) {
            P = P + alphaGZ[T][i];
        }
        return P;
    }

    public static double POMODEL(DataTrain train, ArrayList<PointOfTime> input){
        double p = POMODEL_X(train, input) + POMODEL_Y(train, input) + POMODEL_Z(train, input);
        p = p + POMODEL_GX(train, input) + POMODEL_GY(train, input) + POMODEL_GZ(train, input);
        return p;
    }

    public static class Tranning {
        private ArrayList<PointOfTime> data;
        private final int M = DataTrain.M;

        private int NX = 8;
        private int NY = 8;
        private int NZ = 8;
        private int NGX = 8;
        private int NGY = 8;
        private int NGZ = 8;

        private DataTrain dataTrainX = null;
        private DataTrain dataTrainGY = null;
        private DataTrain dataTrainGX = null;
        private DataTrain dataTrainGZ = null;
        private DataTrain dataTrainY = null;
        private DataTrain dataTrainZ = null;

        public Tranning(final ArrayList<PointOfTime> data) {
            this.data = data;
        }

        private void calculationX() {
            NX = 8;
            double pXMax = -1;
            for (int iNX = 8; iNX <= 16; iNX++) {
                DataTrain olderDataTrainX = new DataTrain();
                olderDataTrainX.setDataTrainX(iNX);
                DataTrain newDataTrainX = new DataTrain();
                newDataTrainX.setDataTrainX(iNX);

                for (int lap = 0; lap < 20; lap++) {
                    double[][] alphaX = null;
                    double[][] betaX = null;
                    double[][][] tetaX = null;
                    double[][] gamaX = null;
                    int T = data.size();
                    int adding = (M / 2) + 1;

                    alphaX = new double[T + 1][iNX + 1];

                    for (int i = 1; i <= iNX; i++) {
                        alphaX[1][i] = olderDataTrainX.PiX[i] * olderDataTrainX.BX[i][adding + data.get(0).getAccX()];
                    }

                    for (int t = 1; t < T; t++) {
                        for (int j = 1; j <= iNX; j++) {
                            alphaX[t + 1][j] = 0;
                            for (int i = 0; i <= iNX; i++) {
                                alphaX[t + 1][j] = alphaX[t + 1][j] + alphaX[t][i] * olderDataTrainX.AX[i][j];
                            }
                            alphaX[t + 1][j] = alphaX[t + 1][j] * olderDataTrainX.BX[j][adding + data.get(t).getAccX()];   // max index of data is T-1; position = (t+1)-1 = t;
                        }
                    }

                    // Tinh betaX  -----------------------------
                    betaX = new double[T + 1][iNX + 1];

                    for (int i = 1; i <= iNX; i++) {
                        betaX[T][i] = 1;
                    }

                    for (int t = T - 1; t >= 1; t--) {
                        for (int i = 1; i <= iNX; i++) {
                            betaX[t][i] = 0;
                            for (int j = 1; j <= iNX; j++) {
                                betaX[t][i] = betaX[t][i] + olderDataTrainX.AX[i][j] * olderDataTrainX.BX[j][adding + data.get(t).getAccX()] * betaX[t + 1][j];
                            }
                        }
                    }

                    // Tinh tetaX  -----------------------------
                    tetaX = new double[T][iNX + 1][iNX + 1];
                    for (int t = 1; t < T; t++) {
                        double p = 0;
                        for (int ii = 1; ii <= iNX; ii++) {
                            for (int jj = 1; jj <= iNX; jj++) {
                                p = p + alphaX[t][ii] * olderDataTrainX.AX[ii][jj] * olderDataTrainX.BX[jj][data.get(t).getAccX() + adding] * betaX[t + 1][jj];
                            }
                        }

                        for (int i = 1; i <= iNX; i++) {
                            for (int j = 1; j <= iNX; j++) {
                                tetaX[t][i][j] = alphaX[t][i] * olderDataTrainX.AX[i][j] * olderDataTrainX.BX[j][data.get(t).getAccX() + adding] * betaX[t + 1][j];
                                tetaX[t][i][j] = tetaX[t][i][j] / p;
                            }
                        }
                    }

                    // Tinh gamaX  -----------------------------
                    gamaX = new double[T][iNX + 1];
                    for (int t = 1; t < T; t++) {
                        for (int i = 1; i <= iNX; i++) {
                            gamaX[t][i] = 0;
                            for (int j = 1; j <= iNX; j++) {
                                gamaX[t][i] = gamaX[t][i] + tetaX[t][i][j];
                            }
                        }
                    }

                    // Tinh _PiX  -----------------------------
                    for (int i = 1; i <= iNX; i++) {
                        newDataTrainX.PiX[i] = gamaX[1][i];
                    }

                    // Tinh _AX  -----------------------------
                    for (int i = 1; i <= iNX; i++) {
                        double a = 0;
                        for (int t = 1; t < T; t++) {
                            a = a + gamaX[t][i];
                        }
                        for (int j = 1; j <= iNX; j++) {
                            newDataTrainX.AX[i][j] = 0;
                            for (int t = 1; t < T; t++) {
                                newDataTrainX.AX[i][j] = newDataTrainX.AX[i][j] + tetaX[t][i][j];
                            }
                            newDataTrainX.AX[i][j] = newDataTrainX.AX[i][j] / a;
                        }
                    }

                    // Tinh _BX  -----------------------------
                    for (int j = 1; j <= iNX; j++) {
                        double b = 0;
                        for (int t = 1; t < T; t++) {
                            b = b + gamaX[t][j];
                        }

                        for (int k = 1; k <= M; k++) {
                            newDataTrainX.BX[j][k] = 0;
                            for (int t = 1; t < T; t++) {
                                if (data.get(t - 1).getAccX() + adding == k) {
                                    newDataTrainX.BX[j][k] = newDataTrainX.BX[j][k] + gamaX[t][j];
                                }
                            }
                            newDataTrainX.BX[j][k] = newDataTrainX.BX[j][k] / b;
                        }
                    }
                    olderDataTrainX = newDataTrainX;
                }
                // tinh p
                double p = Algorithm.POMODEL_X(newDataTrainX, data);
                if (p > pXMax) {
                    NX = iNX;
                    pXMax = p;
                    dataTrainX = newDataTrainX;
                }
                //Log.e("quoccuong", "iNX = " + iNX + " p = " + POMODEL_X(newDataTrainX, data) + " NX = " + NX + " p max = " + POMODEL_X(dataTrainX, data));
            }
            Log.e("quoccuong", "NX = " + NX + " p max = " + Algorithm.POMODEL_X(dataTrainX, data));
        }

        private void calculationY() {
            NY = 8;
            double pYMax = -1;
            for (int iNY = 8; iNY <= 16; iNY++) {
                DataTrain olderDataTrainY = new DataTrain();
                olderDataTrainY.setDataTrainY(iNY);
                DataTrain newDataTrainY = new DataTrain();
                newDataTrainY.setDataTrainY(iNY);

                for (int lap = 0; lap < 20; lap++) {
                    double[][] alphaY = null;
                    double[][] betaY = null;
                    double[][][] tetaY = null;
                    double[][] gamaY = null;
                    int T = data.size();
                    int adding = (M / 2) + 1;

                    // Tinh alphaY  -----------------------------
                    alphaY = new double[T + 1][iNY + 1];

                    for (int i = 1; i <= iNY; i++) {
                        alphaY[1][i] = olderDataTrainY.PiY[i] * olderDataTrainY.BY[i][adding + data.get(0).getAccY()];
                    }

                    for (int t = 1; t < T; t++) {
                        for (int j = 1; j <= iNY; j++) {
                            alphaY[t + 1][j] = 0;
                            for (int i = 0; i <= iNY; i++) {
                                alphaY[t + 1][j] = alphaY[t + 1][j] + alphaY[t][i] * olderDataTrainY.AY[i][j];
                            }
                            alphaY[t + 1][j] = alphaY[t + 1][j] * olderDataTrainY.BY[j][adding + data.get(t).getAccY()];   // max index of data is T-1; position = (t+1)-1 = t;
                        }
                    }

                    // Tinh betaY  -----------------------------
                    betaY = new double[T + 1][iNY + 1];

                    for (int i = 1; i <= iNY; i++) {
                        betaY[T][i] = 1;
                    }

                    for (int t = T - 1; t >= 1; t--) {
                        for (int i = 1; i <= iNY; i++) {
                            betaY[t][i] = 0;
                            for (int j = 1; j <= iNY; j++) {
                                betaY[t][i] = betaY[t][i] + olderDataTrainY.AY[i][j] * olderDataTrainY.BY[j][adding + data.get(t).getAccY()] * betaY[t + 1][j];
                            }
                        }
                    }

                    // Tinh tetaY  -----------------------------
                    tetaY = new double[T][iNY + 1][iNY + 1];
                    for (int t = 1; t < T; t++) {
                        double p = 0;
                        for (int ii = 1; ii <= iNY; ii++) {
                            for (int jj = 1; jj <= iNY; jj++) {
                                p = p + alphaY[t][ii] * olderDataTrainY.AY[ii][jj] * olderDataTrainY.BY[jj][data.get(t).getAccY() + adding] * betaY[t + 1][jj];
                            }
                        }

                        for (int i = 1; i <= iNY; i++) {
                            for (int j = 1; j <= iNY; j++) {
                                tetaY[t][i][j] = alphaY[t][i] * olderDataTrainY.AY[i][j] * olderDataTrainY.BY[j][data.get(t).getAccY() + adding] * betaY[t + 1][j];
                                tetaY[t][i][j] = tetaY[t][i][j] / p;
                            }
                        }
                    }

                    // Tinh gamaY  -----------------------------
                    gamaY = new double[T][iNY + 1];
                    for (int t = 1; t < T; t++) {
                        for (int i = 1; i <= iNY; i++) {
                            gamaY[t][i] = 0;
                            for (int j = 1; j <= iNY; j++) {
                                gamaY[t][i] = gamaY[t][i] + tetaY[t][i][j];
                            }
                        }
                    }

                    // Tinh _PiY  -----------------------------
                    for (int i = 1; i <= iNY; i++) {
                        newDataTrainY.PiY[i] = gamaY[1][i];
                    }

                    // Tinh _AY  -----------------------------
                    for (int i = 1; i <= iNY; i++) {
                        double a = 0;
                        for (int t = 1; t < T; t++) {
                            a = a + gamaY[t][i];
                        }
                        for (int j = 1; j <= iNY; j++) {
                            newDataTrainY.AY[i][j] = 0;
                            for (int t = 1; t < T; t++) {
                                newDataTrainY.AY[i][j] = newDataTrainY.AY[i][j] + tetaY[t][i][j];
                            }
                            newDataTrainY.AY[i][j] = newDataTrainY.AY[i][j] / a;
                        }
                    }

                    // Tinh _BY  -----------------------------
                    for (int j = 1; j <= iNY; j++) {
                        double b = 0;
                        for (int t = 1; t < T; t++) {
                            b = b + gamaY[t][j];
                        }
                        for (int k = 1; k <= M; k++) {
                            newDataTrainY.BY[j][k] = 0;
                            for (int t = 1; t < T; t++) {
                                if (data.get(t - 1).getAccY() + adding == k) {
                                    newDataTrainY.BY[j][k] = newDataTrainY.BY[j][k] + gamaY[t][j];
                                }
                            }
                            newDataTrainY.BY[j][k] = newDataTrainY.BY[j][k] / b;
                        }
                    }
                    olderDataTrainY = newDataTrainY;
                    //Log.e("quoccuong", "iNY = " + iNY + " lap = " + lap +" p = " + POMODEL_Y(newDataTrainY, data));
                }


                // tinh p
                double p = Algorithm.POMODEL_Y(newDataTrainY, data);
                if (p > pYMax) {
                    NY = iNY;
                    pYMax = p;
                    dataTrainY = newDataTrainY;
                }
                //Log.e("quoccuong", "iNY = " + iNY + " p = " + POMODEL_Y(newDataTrainY, data) + " NY = " + NY + " p max = " + POMODEL_Y(dataTrainY, data));

            }
            Log.e("quoccuong", "NY = " + NY + " p max = " + Algorithm.POMODEL_Y(dataTrainY, data));
        }

        private void calculationZ() {
            NZ = 8;
            double pZMax = -1;
            for (int iNZ = 8; iNZ <= 16; iNZ++) {
                DataTrain olderDataTrainZ = new DataTrain();
                olderDataTrainZ.setDataTrainZ(iNZ);
                DataTrain newDataTrainZ = new DataTrain();
                newDataTrainZ.setDataTrainZ(iNZ);

                for (int lap = 0; lap < 20; lap++) {
                    double[][] alphaZ = null;
                    double[][] betaZ = null;
                    double[][][] tetaZ = null;
                    double[][] gamaZ = null;
                    int T = data.size();
                    int adding = (M / 2) + 1;

                    // Tinh alphaZ  -----------------------------
                    alphaZ = new double[T + 1][iNZ + 1];

                    for (int i = 1; i <= iNZ; i++) {
                        alphaZ[1][i] = olderDataTrainZ.PiZ[i] * olderDataTrainZ.BZ[i][adding + data.get(0).getAccZ()];
                    }

                    for (int t = 1; t < T; t++) {
                        for (int j = 1; j <= iNZ; j++) {
                            alphaZ[t + 1][j] = 0;
                            for (int i = 0; i <= iNZ; i++) {
                                alphaZ[t + 1][j] = alphaZ[t + 1][j] + alphaZ[t][i] * olderDataTrainZ.AZ[i][j];
                            }
                            alphaZ[t + 1][j] = alphaZ[t + 1][j] * olderDataTrainZ.BZ[j][adding + data.get(t).getAccZ()];   // max index of data is T-1; position = (t+1)-1 = t;
                        }
                    }


                    // Tinh betaZ  -----------------------------
                    betaZ = new double[T + 1][iNZ + 1];

                    for (int i = 1; i <= iNZ; i++) {
                        betaZ[T][i] = 1;
                    }

                    for (int t = T - 1; t >= 1; t--) {
                        for (int i = 1; i <= iNZ; i++) {
                            betaZ[t][i] = 0;
                            for (int j = 1; j <= iNZ; j++) {
                                betaZ[t][i] = betaZ[t][i] + olderDataTrainZ.AZ[i][j] * olderDataTrainZ.BZ[j][adding + data.get(t).getAccZ()] * betaZ[t + 1][j];
                            }
                        }
                    }

                    // Tinh tetaZ  -----------------------------
                    tetaZ = new double[T][iNZ + 1][iNZ + 1];
                    for (int t = 1; t < T; t++) {
                        double p = 0;
                        for (int ii = 1; ii <= iNZ; ii++) {
                            for (int jj = 1; jj <= iNZ; jj++) {
                                p = p + alphaZ[t][ii] * olderDataTrainZ.AZ[ii][jj] * olderDataTrainZ.BZ[jj][data.get(t).getAccZ() + adding] * betaZ[t + 1][jj];
                            }
                        }

                        for (int i = 1; i <= iNZ; i++) {
                            for (int j = 1; j <= iNZ; j++) {
                                tetaZ[t][i][j] = alphaZ[t][i] * olderDataTrainZ.AZ[i][j] * olderDataTrainZ.BZ[j][data.get(t).getAccZ() + adding] * betaZ[t + 1][j];
                                tetaZ[t][i][j] = tetaZ[t][i][j] / p;
                            }
                        }
                    }

                    // Tinh gamaZ  -----------------------------
                    gamaZ = new double[T][iNZ + 1];
                    for (int t = 1; t < T; t++) {
                        for (int i = 1; i <= iNZ; i++) {
                            gamaZ[t][i] = 0;
                            for (int j = 1; j <= iNZ; j++) {
                                gamaZ[t][i] = gamaZ[t][i] + tetaZ[t][i][j];
                            }
                        }
                    }

                    // Tinh _PiZ  -----------------------------
                    for (int i = 1; i <= iNZ; i++) {
                        newDataTrainZ.PiZ[i] = gamaZ[1][i];
                    }

                    // Tinh _AZ  -----------------------------
                    for (int i = 1; i <= iNZ; i++) {
                        double a = 0;
                        for (int t = 1; t < T; t++) {
                            a = a + gamaZ[t][i];
                        }
                        for (int j = 1; j <= iNZ; j++) {
                            newDataTrainZ.AZ[i][j] = 0;
                            for (int t = 1; t < T; t++) {
                                newDataTrainZ.AZ[i][j] = newDataTrainZ.AZ[i][j] + tetaZ[t][i][j];
                            }
                            newDataTrainZ.AZ[i][j] = newDataTrainZ.AZ[i][j] / a;
                        }
                    }

                    // Tinh _BZ  -----------------------------
                    for (int j = 1; j <= iNZ; j++) {
                        double b = 0;
                        for (int t = 1; t < T; t++) {
                            b = b + gamaZ[t][j];
                        }
                        for (int k = 1; k <= M; k++) {
                            newDataTrainZ.BZ[j][k] = 0;
                            for (int t = 1; t < T; t++) {
                                if (data.get(t - 1).getAccZ() + adding == k) {

                                    newDataTrainZ.BZ[j][k] = newDataTrainZ.BZ[j][k] + gamaZ[t][j];
                                }
                            }
                            newDataTrainZ.BZ[j][k] = newDataTrainZ.BZ[j][k] / b;
                        }
                    }
                    olderDataTrainZ = newDataTrainZ;
                    //Log.e("quoccuong", "iNZ = " + iNZ + " lap = " + lap +" p = " + POMODEL_Z(newDataTrainZ, data));
                }

                // tinh p
                int k = newDataTrainZ.NZ;
                double p = Algorithm.POMODEL_Z(newDataTrainZ, data);
                if (p > pZMax) {
                    NZ = iNZ;
                    pZMax = p;
                    dataTrainZ = newDataTrainZ;
                }
                //Log.e("quoccuong", "iNZ = " + iNZ + " p = " + p + " NZ = " + NZ + " p max = " + POMODEL_Z(dataTrainZ, data));
            }
            Log.e("quoccuong", "NZ = " + NZ + " p max = " + Algorithm.POMODEL_Z(dataTrainZ, data));
        }

        private void calculationGX() {
            NGX = 8;
            double pGXMax = -1;
            for (int iNGX = 8; iNGX <= 16; iNGX++) {
                DataTrain olderDataTrainGX = new DataTrain();
                olderDataTrainGX.setDataTrainGX(iNGX);
                DataTrain newDataTrainGX = new DataTrain();
                newDataTrainGX.setDataTrainGX(iNGX);

                for (int lap = 0; lap < 20; lap++) {
                    double[][] alphaGX = null;
                    double[][] betaGX = null;
                    double[][][] tetaGX = null;
                    double[][] gamaGX = null;
                    int T = data.size();
                    int adding = (M / 2) + 1;

                    // Tinh alphaGX  -----------------------------
                    alphaGX = new double[T + 1][iNGX + 1];

                    for (int i = 1; i <= iNGX; i++) {
                        alphaGX[1][i] = olderDataTrainGX.PiGX[i] * olderDataTrainGX.BGX[i][adding + data.get(0).getGyrX()];
                    }

                    for (int t = 1; t < T; t++) {
                        for (int j = 1; j <= iNGX; j++) {
                            alphaGX[t + 1][j] = 0;
                            for (int i = 0; i <= iNGX; i++) {
                                alphaGX[t + 1][j] = alphaGX[t + 1][j] + alphaGX[t][i] * olderDataTrainGX.AGX[i][j];
                            }
                            alphaGX[t + 1][j] = alphaGX[t + 1][j] * olderDataTrainGX.BGX[j][adding + data.get(t).getGyrX()];   // max index of data is T-1; position = (t+1)-1 = t;
                        }
                    }

                    // Tinh betaGX  -----------------------------
                    betaGX = new double[T + 1][iNGX + 1];

                    for (int i = 1; i <= iNGX; i++) {
                        betaGX[T][i] = 1;
                    }

                    for (int t = T - 1; t >= 1; t--) {
                        for (int i = 1; i <= iNGX; i++) {
                            betaGX[t][i] = 0;
                            for (int j = 1; j <= iNGX; j++) {
                                betaGX[t][i] = betaGX[t][i] + olderDataTrainGX.AGX[i][j] * olderDataTrainGX.BGX[j][adding + data.get(t).getGyrX()] * betaGX[t + 1][j];
                            }
                        }
                    }

                    // Tinh tetaGX  -----------------------------
                    tetaGX = new double[T][iNGX + 1][iNGX + 1];
                    for (int t = 1; t < T; t++) {
                        double p = 0;
                        for (int ii = 1; ii <= iNGX; ii++) {
                            for (int jj = 1; jj <= iNGX; jj++) {
                                p = p + alphaGX[t][ii] * olderDataTrainGX.AGX[ii][jj] * olderDataTrainGX.BGX[jj][data.get(t).getGyrX() + adding] * betaGX[t + 1][jj];
                            }
                        }

                        for (int i = 1; i <= iNGX; i++) {
                            for (int j = 1; j <= iNGX; j++) {
                                tetaGX[t][i][j] = alphaGX[t][i] * olderDataTrainGX.AGX[i][j] * olderDataTrainGX.BGX[j][data.get(t).getGyrX() + adding] * betaGX[t + 1][j];
                                tetaGX[t][i][j] = tetaGX[t][i][j] / p;
                            }
                        }
                    }

                    // Tinh gamaGX  -----------------------------
                    gamaGX = new double[T][iNGX + 1];
                    for (int t = 1; t < T; t++) {
                        for (int i = 1; i <= iNGX; i++) {
                            gamaGX[t][i] = 0;
                            for (int j = 1; j <= iNGX; j++) {
                                gamaGX[t][i] = gamaGX[t][i] + tetaGX[t][i][j];
                            }
                        }
                    }

                    // Tinh _PiGX  -----------------------------
                    for (int i = 1; i <= iNGX; i++) {
                        newDataTrainGX.PiGX[i] = gamaGX[1][i];
                    }

                    // Tinh _AGX  -----------------------------
                    for (int i = 1; i <= iNGX; i++) {
                        double a = 0;
                        for (int t = 1; t < T; t++) {
                            a = a + gamaGX[t][i];
                        }
                        for (int j = 1; j <= iNGX; j++) {
                            newDataTrainGX.AGX[i][j] = 0;
                            for (int t = 1; t < T; t++) {
                                newDataTrainGX.AGX[i][j] = newDataTrainGX.AGX[i][j] + tetaGX[t][i][j];
                            }
                            newDataTrainGX.AGX[i][j] = newDataTrainGX.AGX[i][j] / a;
                        }
                    }

                    // Tinh _BGX  -----------------------------
                    for (int j = 1; j <= iNGX; j++) {
                        double b = 0;
                        for (int t = 1; t < T; t++) {
                            b = b + gamaGX[t][j];
                        }

                        for (int k = 1; k <= M; k++) {
                            newDataTrainGX.BGX[j][k] = 0;
                            for (int t = 1; t < T; t++) {
                                if (data.get(t - 1).getGyrX() + adding == k) {
                                    newDataTrainGX.BGX[j][k] = newDataTrainGX.BGX[j][k] + gamaGX[t][j];
                                }
                            }
                            newDataTrainGX.BGX[j][k] = newDataTrainGX.BGX[j][k] / b;
                        }
                    }

                    olderDataTrainGX = newDataTrainGX;
                    //Log.e("quoccuong", "iNGX = " + iNGX + " lap = " + lap + " p = " + POMODEL_GX(newDataTrainGX, data));
                }

                double p = Algorithm.POMODEL_GX(newDataTrainGX, data);
                if (p > pGXMax) {
                    pGXMax = p;
                    NGX = iNGX;
                    dataTrainGX = newDataTrainGX;
                }
                //Log.e("quoccuong","iNGX = " + iNGX + " p = " + p + " NGX = " + NGX + " p max = "+POMODEL_GX(dataTrainGX, data));

            }
            Log.e("quoccuong", "NGX = " + NGX + " p max = " + Algorithm.POMODEL_GX(dataTrainGX, data));
        }

        private void calculationGY() {
            NGY = 8;
            double pGYMax = -1;
            for (int iNGY = 8; iNGY <= 16; iNGY++) {
                DataTrain olderDataTrainGY = new DataTrain();
                olderDataTrainGY.setDataTrainGY(iNGY);
                DataTrain newDataTrainGY = new DataTrain();
                newDataTrainGY.setDataTrainGY(iNGY);

                for (int lap = 0; lap < 20; lap++) {
                    double[][] alphaGY = null;
                    double[][] betaGY = null;
                    double[][][] tetaGY = null;
                    double[][] gamaGY = null;
                    int T = data.size();
                    int adding = (M / 2) + 1;

                    // Tinh alphaGX  -----------------------------
                    alphaGY = new double[T + 1][iNGY + 1];

                    for (int i = 1; i <= iNGY; i++) {
                        alphaGY[1][i] = olderDataTrainGY.PiGY[i] * olderDataTrainGY.BGY[i][adding + data.get(0).getGyrY()];
                    }

                    for (int t = 1; t < T; t++) {
                        for (int j = 1; j <= iNGY; j++) {
                            alphaGY[t + 1][j] = 0;
                            for (int i = 0; i <= iNGY; i++) {
                                alphaGY[t + 1][j] = alphaGY[t + 1][j] + alphaGY[t][i] * olderDataTrainGY.AGY[i][j];
                            }
                            alphaGY[t + 1][j] = alphaGY[t + 1][j] * olderDataTrainGY.BGY[j][adding + data.get(t).getGyrY()];   // max index of data is T-1; position = (t+1)-1 = t;
                        }
                    }

                    // Tinh betaGX  -----------------------------
                    betaGY = new double[T + 1][iNGY + 1];

                    for (int i = 1; i <= iNGY; i++) {
                        betaGY[T][i] = 1;
                    }

                    for (int t = T - 1; t >= 1; t--) {
                        for (int i = 1; i <= iNGY; i++) {
                            betaGY[t][i] = 0;
                            for (int j = 1; j <= iNGY; j++) {
                                betaGY[t][i] = betaGY[t][i] + olderDataTrainGY.AGY[i][j] * olderDataTrainGY.BGY[j][adding + data.get(t).getGyrY()] * betaGY[t + 1][j];
                            }
                        }
                    }

                    // Tinh tetaGX  -----------------------------
                    tetaGY = new double[T][iNGY + 1][iNGY + 1];
                    for (int t = 1; t < T; t++) {
                        double p = 0;
                        for (int ii = 1; ii <= iNGY; ii++) {
                            for (int jj = 1; jj <= iNGY; jj++) {
                                p = p + alphaGY[t][ii] * olderDataTrainGY.AGY[ii][jj] * olderDataTrainGY.BGY[jj][data.get(t).getGyrY() + adding] * betaGY[t + 1][jj];
                            }
                        }

                        for (int i = 1; i <= iNGY; i++) {
                            for (int j = 1; j <= iNGY; j++) {
                                tetaGY[t][i][j] = alphaGY[t][i] * olderDataTrainGY.AGY[i][j] * olderDataTrainGY.BGY[j][data.get(t).getGyrY() + adding] * betaGY[t + 1][j];
                                tetaGY[t][i][j] = tetaGY[t][i][j] / p;
                            }
                        }
                    }

                    // Tinh gamaGX  -----------------------------
                    gamaGY = new double[T][iNGY + 1];
                    for (int t = 1; t < T; t++) {
                        for (int i = 1; i <= iNGY; i++) {
                            gamaGY[t][i] = 0;
                            for (int j = 1; j <= iNGY; j++) {
                                gamaGY[t][i] = gamaGY[t][i] + tetaGY[t][i][j];
                            }
                        }
                    }

                    // Tinh _PiGX  -----------------------------
                    for (int i = 1; i <= iNGY; i++) {
                        newDataTrainGY.PiGY[i] = gamaGY[1][i];
                    }

                    // Tinh _AGX  -----------------------------
                    for (int i = 1; i <= iNGY; i++) {
                        double a = 0;
                        for (int t = 1; t < T; t++) {
                            a = a + gamaGY[t][i];
                        }
                        for (int j = 1; j <= iNGY; j++) {
                            newDataTrainGY.AGY[i][j] = 0;
                            for (int t = 1; t < T; t++) {
                                newDataTrainGY.AGY[i][j] = newDataTrainGY.AGY[i][j] + tetaGY[t][i][j];
                            }
                            newDataTrainGY.AGY[i][j] = newDataTrainGY.AGY[i][j] / a;
                        }
                    }

                    // Tinh _BGX  -----------------------------
                    for (int j = 1; j <= iNGY; j++) {
                        double b = 0;
                        for (int t = 1; t < T; t++) {
                            b = b + gamaGY[t][j];
                        }

                        for (int k = 1; k <= M; k++) {
                            newDataTrainGY.BGY[j][k] = 0;
                            for (int t = 1; t < T; t++) {
                                if (data.get(t - 1).getGyrY() + adding == k) {
                                    newDataTrainGY.BGY[j][k] = newDataTrainGY.BGY[j][k] + gamaGY[t][j];
                                }
                            }
                            newDataTrainGY.BGY[j][k] = newDataTrainGY.BGY[j][k] / b;
                        }
                    }

                    olderDataTrainGY = newDataTrainGY;
                    //Log.e("quoccuong", "iNGY = " + iNGY + " lap = " + lap + " p = " + POMODEL_GY(newDataTrainGY, data));
                }

                double p = Algorithm.POMODEL_GY(newDataTrainGY, data);
                if (p > pGYMax) {
                    pGYMax = p;
                    NGY = iNGY;
                    dataTrainGY = newDataTrainGY;
                }
                //Log.e("quoccuong","iNGY = " + iNGY + " p = " + p + " NGY = " + NGY + " p max = "+POMODEL_GY(dataTrainGY, data));

            }
            Log.e("quoccuong", "NGY = " + NGY + " p max = " + Algorithm.POMODEL_GY(dataTrainGY, data));
        }

        private void calculationGZ() {
            NGZ = 8;
            double pGZMax = -1;
            for (int iNGZ = 8; iNGZ <= 16; iNGZ++) {
                DataTrain olderDataTrainGZ = new DataTrain();
                olderDataTrainGZ.setDataTrainGZ(iNGZ);
                DataTrain newDataTrainGZ = new DataTrain();
                newDataTrainGZ.setDataTrainGZ(iNGZ);

                for (int lap = 0; lap < 20; lap++) {
                    double[][] alphaGZ = null;
                    double[][] betaGZ = null;
                    double[][][] tetaGZ = null;
                    double[][] gamaGZ = null;
                    int T = data.size();
                    int adding = (M / 2) + 1;

                    // Tinh alphaGZ  -----------------------------
                    alphaGZ = new double[T + 1][iNGZ + 1];

                    for (int i = 1; i <= iNGZ; i++) {
                        alphaGZ[1][i] = olderDataTrainGZ.PiGZ[i] * olderDataTrainGZ.BGZ[i][adding + data.get(0).getGyrZ()];
                    }

                    for (int t = 1; t < T; t++) {
                        for (int j = 1; j <= iNGZ; j++) {
                            alphaGZ[t + 1][j] = 0;
                            for (int i = 0; i <= iNGZ; i++) {
                                alphaGZ[t + 1][j] = alphaGZ[t + 1][j] + alphaGZ[t][i] * olderDataTrainGZ.AGZ[i][j];
                            }
                            alphaGZ[t + 1][j] = alphaGZ[t + 1][j] * olderDataTrainGZ.BGZ[j][adding + data.get(t).getGyrZ()];   // max index of data is T-1; position = (t+1)-1 = t;
                        }
                    }

                    // Tinh betaGZ  -----------------------------
                    betaGZ = new double[T + 1][iNGZ + 1];

                    for (int i = 1; i <= iNGZ; i++) {
                        betaGZ[T][i] = 1;
                    }

                    for (int t = T - 1; t >= 1; t--) {
                        for (int i = 1; i <= iNGZ; i++) {
                            betaGZ[t][i] = 0;
                            for (int j = 1; j <= iNGZ; j++) {
                                betaGZ[t][i] = betaGZ[t][i] + olderDataTrainGZ.AGZ[i][j] * olderDataTrainGZ.BGZ[j][adding + data.get(t).getGyrZ()] * betaGZ[t + 1][j];
                            }
                        }
                    }

                    // Tinh tetaGZ  -----------------------------
                    tetaGZ = new double[T][iNGZ + 1][iNGZ + 1];
                    for (int t = 1; t < T; t++) {
                        double p = 0;
                        for (int ii = 1; ii <= iNGZ; ii++) {
                            for (int jj = 1; jj <= iNGZ; jj++) {
                                p = p + alphaGZ[t][ii] * olderDataTrainGZ.AGZ[ii][jj] * olderDataTrainGZ.BGZ[jj][data.get(t).getGyrZ() + adding] * betaGZ[t + 1][jj];
                            }
                        }

                        for (int i = 1; i <= iNGZ; i++) {
                            for (int j = 1; j <= iNGZ; j++) {
                                tetaGZ[t][i][j] = alphaGZ[t][i] * olderDataTrainGZ.AGZ[i][j] * olderDataTrainGZ.BGZ[j][data.get(t).getGyrZ() + adding] * betaGZ[t + 1][j];
                                tetaGZ[t][i][j] = tetaGZ[t][i][j] / p;
                            }
                        }
                    }

                    // Tinh gamaGZ  -----------------------------
                    gamaGZ = new double[T][iNGZ + 1];
                    for (int t = 1; t < T; t++) {
                        for (int i = 1; i <= iNGZ; i++) {
                            gamaGZ[t][i] = 0;
                            for (int j = 1; j <= iNGZ; j++) {
                                gamaGZ[t][i] = gamaGZ[t][i] + tetaGZ[t][i][j];
                            }
                        }
                    }

                    // Tinh _PiZ  -----------------------------
                    for (int i = 1; i <= iNGZ; i++) {
                        newDataTrainGZ.PiGZ[i] = gamaGZ[1][i];
                    }

                    // Tinh _AZ  -----------------------------
                    for (int i = 1; i <= iNGZ; i++) {
                        double a = 0;
                        for (int t = 1; t < T; t++) {
                            a = a + gamaGZ[t][i];
                        }
                        for (int j = 1; j <= iNGZ; j++) {
                            newDataTrainGZ.AGZ[i][j] = 0;
                            for (int t = 1; t < T; t++) {
                                newDataTrainGZ.AGZ[i][j] = newDataTrainGZ.AGZ[i][j] + tetaGZ[t][i][j];
                            }
                            newDataTrainGZ.AGZ[i][j] = newDataTrainGZ.AGZ[i][j] / a;
                        }
                    }

                    // Tinh _BZ  -----------------------------
                    for (int j = 1; j <= iNGZ; j++) {
                        double b = 0;
                        for (int t = 1; t < T; t++) {
                            b = b + gamaGZ[t][j];
                        }
                        for (int k = 1; k <= M; k++) {
                            newDataTrainGZ.BGZ[j][k] = 0;
                            for (int t = 1; t < T; t++) {
                                if (data.get(t - 1).getAccZ() + adding == k) {

                                    newDataTrainGZ.BGZ[j][k] = newDataTrainGZ.BGZ[j][k] + gamaGZ[t][j];
                                }
                            }
                            newDataTrainGZ.BGZ[j][k] = newDataTrainGZ.BGZ[j][k] / b;
                        }
                    }
                    Log.e("quoccuong", "iNGZ = " + iNGZ + " lap = " + lap + " p = " + POMODEL_GZ(newDataTrainGZ, data));
                    olderDataTrainGZ = newDataTrainGZ;

                }

                int k = newDataTrainGZ.NGZ;
                double p = Algorithm.POMODEL_GZ(newDataTrainGZ, data);
                if (p > pGZMax) {
                    pGZMax = p;
                    NGZ = iNGZ;
                    dataTrainGZ = newDataTrainGZ;
                }
                //Log.e("quoccuong","iNGZ = " + iNGZ + " p = " + p + " NGZ = " + NGZ + " p max = "+POMODEL_GZ(dataTrainGZ, data));

            }
            Log.e("quoccuong", "NGZ = " + NGZ + " p max = " + Algorithm.POMODEL_GZ(dataTrainGZ, data));
        }

        public DataTrain execute() {
            if (data.isEmpty()) {
                return null;
            }
            try {
                calculationX();
                calculationY();
                calculationZ();
                calculationGX();
                calculationGY();
                calculationGZ();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            DataTrain newTrainingData = new DataTrain();

            try {
                newTrainingData.NX = NX;
                newTrainingData.PiX = dataTrainX.PiX;
                newTrainingData.AX = dataTrainX.AX;
                newTrainingData.BX = dataTrainX.BX;

                newTrainingData.NY = NY;
                newTrainingData.PiY = dataTrainY.PiY;
                newTrainingData.AY = dataTrainY.AY;
                newTrainingData.BY = dataTrainY.BY;

                newTrainingData.NZ = NZ;
                newTrainingData.PiZ = dataTrainZ.PiZ;
                newTrainingData.AZ = dataTrainZ.AZ;
                newTrainingData.BZ = dataTrainZ.BZ;

                newTrainingData.NGX = NGX;
                newTrainingData.PiGX = dataTrainGX.PiGX;
                newTrainingData.AGX = dataTrainGX.AGX;
                newTrainingData.BGX = dataTrainGX.BGX;

                newTrainingData.NGY = NGY;
                newTrainingData.PiGY = dataTrainGY.PiGY;
                newTrainingData.AGY = dataTrainGY.AGY;
                newTrainingData.BGY = dataTrainGY.BGY;

                newTrainingData.NGZ = NGZ;
                newTrainingData.PiGZ = dataTrainGZ.PiGZ;
                newTrainingData.AGZ = dataTrainGZ.AGZ;
                newTrainingData.BGZ = dataTrainGZ.BGZ;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return newTrainingData;
        }

    }
}

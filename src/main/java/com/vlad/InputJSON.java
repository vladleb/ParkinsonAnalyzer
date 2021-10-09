package com.vlad;

import java.util.*;

public class InputJSON {

    private String dateNowSending; // дата и время проведенного тестирования
    private double coeffRezist; // коэффициент сопротивления
    private int gameTime; // время игры
    private double dpHeight; // число dp экрана по высоте (по оси Y)
    private double dpWidth; // число dp экрана по ширине (по оси X)
    private int ballSize; // размер шара
    private int pointSize; // размер точки
    private int coordinateBallYMax; // максимальное значение положения шара в dp по высоте (по оси Y)
    private int coordinateBallXMax; // максимальное значение положения шара в dp по ширине (по оси X)
    private int pointLeftDefault; // отступ слева для точки (центр по оси X)
    private int pointTopDefault; // отступ сверху для точки (центр по оси Y)
    private int ballLeftDefault; // отступ слева для шара (центр по оси X)
    private int ballTopDefault; // отступ сверху для шара (центр по оси Y)

    private List<Measurement> Measurement = new ArrayList<Measurement>(); // Измерения

    public String getDateNowSending() {
        return dateNowSending;
    }

    public void setDateNowSending(String dateNowSending) {
        this.dateNowSending = dateNowSending;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getPointLeftDefault() {
        return pointLeftDefault;
    }

    public void setPointLeftDefault(int pointLeftDefault) {
        this.pointLeftDefault = pointLeftDefault;
    }

    public int getPointTopDefault() {
        return pointTopDefault;
    }

    public void setPointTopDefault(int pointTopDefault) {
        this.pointTopDefault = pointTopDefault;
    }

    public int getBallLeftDefault() {
        return ballLeftDefault;
    }

    public void setBallLeftDefault(int ballLeftDefault) {
        this.ballLeftDefault = ballLeftDefault;
    }

    public int getBallTopDefault() {
        return ballTopDefault;
    }

    public void setBallTopDefault(int ballTopDefault) {
        this.ballTopDefault = ballTopDefault;
    }

    public double getCoeffRezist() {
        return coeffRezist;
    }

    public void setCoeffRezist(double coeffRezist) {
        this.coeffRezist = coeffRezist;
    }

    public double getDpHeight() {
        return dpHeight;
    }

    public void setDpHeight(double dpHeight) {
        this.dpHeight = dpHeight;
    }

    public double getDpWidth() {
        return dpWidth;
    }

    public void setDpWidth(double dpWidth) {
        this.dpWidth = dpWidth;
    }

    public int getBallSize() {
        return ballSize;
    }

    public void setBallSize(int ballSize) {
        this.ballSize = ballSize;
    }

    public int getPointSize() {
        return pointSize;
    }

    public void setPointSize(int pointSize) {
        this.pointSize = pointSize;
    }

    public int getCoordinateBallYMax() {
        return coordinateBallYMax;
    }

    public void setCoordinateBallYMax(int coordinateBallYMax) {
        this.coordinateBallYMax = coordinateBallYMax;
    }

    public int getCoordinateBallXMax() {
        return coordinateBallXMax;
    }

    public void setCoordinateBallXMax(int coordinateBallXMax) {
        this.coordinateBallXMax = coordinateBallXMax;
    }

    public List<InputJSON.Measurement> getMeasurement() {
        return Measurement;
    }

    public void setMeasurement(List<InputJSON.Measurement> measurement) {
        Measurement = measurement;
    }

    public class Measurement{

        private int coordinateBallY;
        private int coordinateBallX;
        private double aX;
        private double aY;
        private double vX;
        private double vY;
        private double Time;

        public int getCoordinateBallY() {
            return coordinateBallY;
        }

        public void setCoordinateBallY(int coordinateBallY) {
            this.coordinateBallY = coordinateBallY;
        }

        public int getCoordinateBallX() {
            return coordinateBallX;
        }

        public void setCoordinateBallX(int coordinateBallX) {
            this.coordinateBallX = coordinateBallX;
        }

        public double getaX() {
            return aX;
        }

        public void setaX(int aX) {
            this.aX = aX;
        }

        public double getaY() {
            return aY;
        }

        public void setaY(int aY) {
            this.aY = aY;
        }

        public double getvX() {
            return vX;
        }

        public void setvX(double vX) {
            this.vX = vX;
        }

        public double getvY() {
            return vY;
        }

        public void setvY(double vY) {
            this.vY = vY;
        }

        public double getTime() {
            return Time;
        }

        public void setTime(int time) {
            Time = time;
        }
    }
}

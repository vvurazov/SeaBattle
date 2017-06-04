package ru.myitschool.seabattle;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ru.myitschool.seabattle.utils.Utilities;

/**
 * Created by Vadim on 09.05.2017.
 */
public class Ship {
    private float x, y; // координаты на экране
    private float homeX, homeY; // домашние координаты корабля (где он расположен на правом поле до расстановки)
    private float baseX, baseY; // базовые координаты, куда кораблик возвращается, если его неправильно разместили
    private int xField, yField; // координаты на игровом поле
    private int type; // тип корабля
    private boolean select; // выбран кораблик для вращения или нет
    private int orientation; // ориентация корабля - HORIZONTAL или VERTICAL
    private int sizeX, sizeY; // размер корабля по x и по y
    private boolean moveReturnToBase; // флаг включается, когда корабль перемещается в базовые координаты
    ImageView image;// изображение
    public Ship(MainActivity main, int x, int y, int type){
        this.type = type;
        this.x = x;
        this.y = y;
        homeX = x;
        homeY = y;
        baseX = x;
        baseY = y;
        orientation = Utilities.VERTICAL;
        sizeX = Utilities.size;
        sizeY = Utilities.size*type; // изначально все корабли вертикальные, поэтому умножая на тип, получаем длину
        xField = Utilities.NOT_IN_FIELD; // это означает, что корабль не в поле
        moveReturnToBase = false; // этот флаг нужен для анимации возвращения на базу
        image=new ImageView(main);
        image.setImageResource(selectImageShip(type));
        image.setScaleType(ImageView.ScaleType.FIT_XY); // подгоняет под пропорции Layout, искажая пропорции
        main.addContentView(image, new RelativeLayout.LayoutParams(sizeX, sizeY));
        outXY();
    }
    // вывод кораблика в реальные координаты на экране
    private void outXY(){
        image.setX(x);
        image.setY(y);
    }
    // определяем расположение головы кораблика (первой клетки) в массиве поля
    private void setXYfield(int is_in_field){
        if(is_in_field == Utilities.IN_FIELD) {
            xField = (int) ((baseX - Utilities.marginLeftMySide) / Utilities.size);
            yField = (int) ((baseY - Utilities.marginTop) / Utilities.size);
        }
        else xField = Utilities.NOT_IN_FIELD;
    }

    // выбираем картинку для корабля
    private int selectImageShip(int type){
        switch (type){
            case Utilities.SHIP_1P: return R.drawable.ship1;
            case Utilities.SHIP_2P: return R.drawable.ship2;
            case Utilities.SHIP_3P: return R.drawable.ship3;
            case Utilities.SHIP_4P: return R.drawable.ship4;
        }
        return 0; // невозможно
    }
    // размещение кораблика в клетки поля
    public void setBaseXY(float x, float y, int is_in_field){
        baseX = x;
        baseY = y;
        setXYfield(is_in_field);
        moveReturnToBase = true;
    }
    // кораблик возвращается, если его неправильно разместили
    public void returnToBase(){
        x+=(baseX-x)/5;
        y+=(baseY-y)/5;
        if(Math.abs(baseX-x) < 2){
            x=baseX;
            y=baseY;
            moveReturnToBase = false;
        }
        outXY();
    }

    public void setXY(float x, float y){
        this.x = x;
        this.y = y;
        outXY();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getSizeX() {
        return sizeX;
    }

    int getSizeY() {
        return sizeY;
    }

    public void setMoveReturnToBase(boolean moveReturnToBase) {
        this.moveReturnToBase = moveReturnToBase;
    }

    public boolean isMoveReturnToBase() {
        return moveReturnToBase;
    }

    public float getBaseX() {
        return baseX;
    }

    public float getBaseY() {
        return baseY;
    }

    public int getxField() {

        return xField;
    }
    // красим в красный цвет выделенный кораблик
    private void paintShip(){
        if(select) image.setColorFilter(Color.argb(160, 255, 0, 0));
        else image.setColorFilter(Color.argb(0, 255, 0, 0));
    }
    public void setSelect(boolean select) {
        this.select = select;
        paintShip();
    }


    public boolean isSelect() {
        return select;
    }

    public void setxField(int xField) {
        this.xField = xField;
    }

    public int getyField() {
        return yField;
    }

    public int getType() {
        return type;
    }

    public float getHomeX() {
        return homeX;
    }

    public float getHomeY() {
        return homeY;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return orientation;
    }
    public void rotateShip(){
        if(orientation== Utilities.HORIZONTAL){
            orientation= Utilities.VERTICAL;
        }
        else {
            orientation = Utilities.HORIZONTAL;
        }
        image.setRotation(orientation);
    }

}
package ru.myitschool.seabattle.widgets;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import ru.myitschool.seabattle.MainActivity;
import ru.myitschool.seabattle.utils.Utilities;


public class Buttons {
    private float x, y; // координаты на экране
    private int xField, yField; // координаты на поле
    private int type; // тип корабля
    private int sizeX, sizeY;
    private boolean moveReturnToBase; // флаг включается, когда корабль перемещается в базовые координаты
    ImageView image;
    public Buttons(MainActivity main, int x, int y, int type){
        this.type = type;
        this.x = x;
        this.y = y;
        sizeX = Utilities.size;
        sizeY = Utilities.size*type;
        xField = -1;
        moveReturnToBase = false;
        image=new ImageView(main);
        //  image.setImageResource(selectImageShip(type));
        image.setScaleType(ImageView.ScaleType.FIT_XY); // подгоняет под пропорции Layout, искажая пропорции
        main.addContentView(image, new RelativeLayout.LayoutParams(sizeX, sizeY));
        //outXY();
    }
}

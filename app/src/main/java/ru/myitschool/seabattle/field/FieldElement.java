package ru.myitschool.seabattle.field;

import android.widget.ImageView;
import android.widget.RelativeLayout;

import ru.myitschool.seabattle.MainActivity;
import ru.myitschool.seabattle.utils.Utilities;


public class FieldElement {
    protected int x, y; //координаты в массиве
    protected ImageView image;
    protected int side;
    public FieldElement(MainActivity main, int x, int y, int side, int pic){
        this.x = x;
        this.y = y;
        this.side = side;
        image=new ImageView(main);
        image.setImageResource(pic);
        main.addContentView(image, new RelativeLayout.LayoutParams(Utilities.size, Utilities.size));
        setXY();
    }
    // вывод элемента на экран
    public void setXY(){
        image.setX(x* Utilities.size+getLeftMargin(side));
        image.setY(y* Utilities.size+ Utilities.marginTop);
    }
    // выясняем, чей отступ слева выбрать
    protected int getLeftMargin(int side){
        return side == 0? Utilities.marginLeftMySide: Utilities.marginLeftEnemySide;
    }
}


package ru.myitschool.seabattle.field;

import ru.myitschool.seabattle.MainActivity;
import ru.myitschool.seabattle.utils.Utilities;

/**
 * Created by Vadim on 09.05.2017.
 */

public class Numbers extends FieldElement {
    public Numbers(MainActivity main, int x, int y, int side, int pic){
        super(main, x, y, side, pic);
    }
    // переопределяем родительский метод вывода
    public void setXY(){
        image.setX(x* Utilities.size+getLeftMargin(side)- Utilities.size);
        image.setY(y* Utilities.size+ Utilities.marginTop);
    }
}


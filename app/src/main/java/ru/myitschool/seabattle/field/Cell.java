package ru.myitschool.seabattle.field;


import ru.myitschool.seabattle.MainActivity;
import ru.myitschool.seabattle.R;

/**
 * Клетка любого игрового поля. На этих клетках будут корабли.
 */
public class Cell extends FieldElement{
    public Cell(MainActivity main, int x, int y, int side){
        super(main, x, y, side, R.drawable.kletka_sea);
    }
}

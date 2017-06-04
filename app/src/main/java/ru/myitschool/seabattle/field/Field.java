package ru.myitschool.seabattle.field;

import ru.myitschool.seabattle.MainActivity;
import ru.myitschool.seabattle.R;
import ru.myitschool.seabattle.utils.Utilities;

/**
 * Created by Vadim on 09.05.2017.
 */

public class Field {
    private int mas[][];
    private Cell cell[][];
    private Letters letters[];
    private Numbers numbers[];
    public Field(MainActivity main, int m[][], int side){
        mas=m;
        cell = new Cell[Utilities.DIMENSION_FIELD][Utilities.DIMENSION_FIELD];
        for(int i = 0; i< Utilities.DIMENSION_FIELD; i++)
            for(int j = 0; j< Utilities.DIMENSION_FIELD; j++)
                cell[i][j] = new Cell(main, i, j, side);

        letters = new Letters[Utilities.DIMENSION_FIELD];
        for(int i = 0; i< Utilities.DIMENSION_FIELD; i++)
            letters[i] = new Letters(main, i, 0, side, R.drawable.letter01+i);

        numbers = new Numbers[Utilities.DIMENSION_FIELD];
        for(int i = 0; i< Utilities.DIMENSION_FIELD; i++)
            numbers[i] = new Numbers(main, 0, i, side, R.drawable.number01+i);

    }
}
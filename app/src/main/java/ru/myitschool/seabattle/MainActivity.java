package ru.myitschool.seabattle;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import ru.myitschool.seabattle.field.Field;
import ru.myitschool.seabattle.utils.Utilities;


public class MainActivity extends AppCompatActivity {
    // два поля - наше, и вражеское, тут будут коды кораблей и выстрелов
    private int[][] myField = new int[Utilities.DIMENSION_FIELD][Utilities.DIMENSION_FIELD],
            enemyField = new int[Utilities.DIMENSION_FIELD][Utilities.DIMENSION_FIELD];
    // это поля с картинками
    public static final int ARRANGMENT = 0, GAME = 1; // константы, которые указывают, что сейчас происходит
    private int playAction; // переменная, которая хранит действие, которое сейчас происходит
    private Field myFieldView, enemyFieldView; // объекты поле - наше и вражеское
    private Ship myShip[] = new Ship[Utilities.TOTAL_SHIPS]; // наши кораблики
    private RelativeLayout relativeLayout;
    private int selectedShip = -1; // выбранный корабль, -1 - ничего не выбрано
    private Button buttonRotate;
    private Button buttonStartGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        getScreenSize(); // определяем разные размеры
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
       /* imageButton2();buttonRotate = (Button) findViewById(R.id.imageButton2);
        buttonRotate.setX(Utilities.screenWidth / 2 + Utilities.size*2);
        buttonRotate.setY(Utilities.marginTop);*/

        buttonStartGame = (Button) findViewById(R.id.buttonStartGame);
        buttonStartGame.setX(Utilities.screenWidth / 2 + Utilities.size*6);
        buttonStartGame.setY(Utilities.marginTop);
        myFieldView = new Field(this, myField, Utilities.MY_SIDE); // создаём наше игровое поле
        // enemyFieldView = new Field(this, enemyField, Utilities.ENEMY_SIDE);
        // создаём поле с корабликами, которые надо расставить
        arrangmentShips();
        relativeLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                touchScreen(motionEvent.getRawX(), motionEvent.getRawY(), motionEvent.getAction());
                return true;
            }
        });
        playAction = ARRANGMENT;
        MyTimer timer = new MyTimer();
        timer.start();
    }
    // в этом методе помещаем наши кораблики для расстановки
    private void arrangmentShips(){
        int i=0;
        myShip[0] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*2, Utilities.marginTop + Utilities.size*0 + Utilities.size*3, Utilities.SHIP_1P);
        myShip[1] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*2, Utilities.marginTop + Utilities.size*2 + Utilities.size*3, Utilities.SHIP_1P);
        myShip[2] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*2, Utilities.marginTop + Utilities.size*4 + Utilities.size*3, Utilities.SHIP_1P);
        myShip[3] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*2, Utilities.marginTop + Utilities.size*6 + Utilities.size*3, Utilities.SHIP_1P);
        myShip[4] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*4, Utilities.marginTop + Utilities.size*0 + Utilities.size*3, Utilities.SHIP_2P);
        myShip[5] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*4, (int)(Utilities.marginTop + Utilities.size*2.5) + Utilities.size*3, Utilities.SHIP_2P);
        myShip[6] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*4, Utilities.marginTop + Utilities.size*5 + Utilities.size*3, Utilities.SHIP_2P);
        myShip[7] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*6, Utilities.marginTop + Utilities.size*0 + Utilities.size*3, Utilities.SHIP_3P);
        myShip[8] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*6, Utilities.marginTop + Utilities.size*4 + Utilities.size*3, Utilities.SHIP_3P);
        myShip[9] = new Ship(this, Utilities.screenWidth / 2 + Utilities.size*8, Utilities.marginTop + Utilities.size*0 + Utilities.size*3, Utilities.SHIP_4P);
    }
    // метод обрабатывает нажатия экрана
    private void touchScreen(float tx, float ty, int touchType){
        if(playAction == ARRANGMENT) {
            if (touchType == MotionEvent.ACTION_DOWN) {
                if(selectedShip!=-1) myShip[selectedShip].setSelect(false); // объявляем предыдущий кораблик не помеченным и снимаем краску
                selectedShip = selectShip(tx, ty);
                if(selectedShip == -1) return; // если ни один корабль не выбран - выходим из метода
            }
            if (touchType == MotionEvent.ACTION_MOVE) { // если передвигаем кораблик
                if (selectedShip > -1 && selectedShip < Utilities.TOTAL_SHIPS) {// если хоть один кораблик выбран, то можно передвигать и всё такое
                    myShip[selectedShip].setXY(tx - Utilities.size / 2, ty - Utilities.size / 2);
                }
            }
            if (touchType == MotionEvent.ACTION_CANCEL || touchType == MotionEvent.ACTION_UP) { // если подняли палец
                if (selectedShip > -1 && selectedShip < Utilities.TOTAL_SHIPS) {// если хоть один кораблик выбран, то можно отпускать
                    putMyShipToMyField(tx, ty, selectedShip);
                    myShip[selectedShip].setMoveReturnToBase(true);
                }
            }
        }
        if(playAction == GAME) {

        }
    }
    // пристраиваем кораблик на поле
    private void putMyShipToMyField(float tx, float ty, int selectedShip){

        // если отпускаем в игровом поле
        if (inZone(tx, ty, Utilities.marginLeftMySide, Utilities.marginTop, Utilities.marginLeftMySide + Utilities.DIMENSION_FIELD* Utilities.size, Utilities.marginTop + Utilities.DIMENSION_FIELD * Utilities.size)) {
            int xF = (int)((tx- Utilities.marginLeftMySide)/ Utilities.size); // координаты в массиве, где отпустили корабль
            int yF = (int)((ty- Utilities.marginTop)/ Utilities.size);
            myShip[selectedShip].setSelect(true); // объявляем кораблик помеченным и красим красным цветом
            // если хвост корабля тоже попадает в массив поля
            if (yF + myShip[selectedShip].getType()-1 < Utilities.DIMENSION_FIELD) {
                if(myShip[selectedShip].getxField()!= Utilities.NOT_IN_FIELD) // если уже размещён в поле, то очищаем его расположение в массиве
                    for(int k=0; k<myShip[selectedShip].getType(); k++) myField[myShip[selectedShip].getxField()][myShip[selectedShip].getyField()+k] = Utilities.FIELD_FREE;
                if (isPlaceAndNearFree(xF, yF, myShip[selectedShip].getType())) { // проверяем, свободно ли там, где мы отпустили кораблик, а также вокруг
                    for(int k=0; k<myShip[selectedShip].getType(); k++) myField[xF][yF+k] = Utilities.FIELD_SHIP; // заполняем место кораблём
                    myShip[selectedShip].setBaseXY(Utilities.marginLeftMySide + xF * Utilities.size, Utilities.marginTop + yF * Utilities.size, Utilities.IN_FIELD); // задаём базовые координаты, куда перемещаться
                    return;
                } else { // если поставить на новое место не получилось, то возвращаем на старое
                    if(myShip[selectedShip].getxField()!= Utilities.NOT_IN_FIELD)
                        for(int k=0; k<myShip[selectedShip].getType(); k++) myField[myShip[selectedShip].getxField()][myShip[selectedShip].getyField()+k] = Utilities.FIELD_SHIP;
                    return;
                }
            }
        }
        // если отпускаем за пределами игрового поля
        else {
            if(myShip[selectedShip].getxField()!= Utilities.NOT_IN_FIELD) { // если уже размещён в поле, то очищаем его расположение в массиве
                for (int k = 0; k < myShip[selectedShip].getType(); k++)
                    myField[myShip[selectedShip].getxField()][myShip[selectedShip].getyField() + k] = Utilities.FIELD_FREE;
                // делаем кораблик не размещённым в игровом поле
                myShip[selectedShip].setBaseXY(myShip[selectedShip].getHomeX(), myShip[selectedShip].getHomeY(), Utilities.NOT_IN_FIELD);
                return;
            }
        }
        return;
    }
    // проверка, пусто ли рядом
    private boolean isPlaceAndNearFree( int ix, int iy, int type){
        for(int i = ix - 1; i <= ix + 1; i++) {
            for (int j = iy - 1; j <= iy + 1*type; j++) {
                if (i >= 0 && j >= 0 && i < Utilities.DIMENSION_FIELD && j < Utilities.DIMENSION_FIELD) {
                    if (myField[i][j] != Utilities.FIELD_FREE) {
                        // Log.d("пометка","лошь!");
                        // for(int l=0; l<10; l++) Log.d("пометка"," "+" "+myField[0][l]+" "+myField[1][l]+" "+myField[2][l]+" "+myField[3][l]+" "+myField[4][l]+" "+myField[5][l]+" "+myField[6][l]+" "+myField[7][l]+" "+myField[8][l]+" "+myField[9][l]);
                        return false;
                    }
                }
            }
        }
        //Log.d("пометка","правда!");
        // for(int l=0; l<10; l++) Log.d("пометка"," "+" "+myField[0][l]+" "+myField[1][l]+" "+myField[2][l]+" "+myField[3][l]+" "+myField[4][l]+" "+myField[5][l]+" "+myField[6][l]+" "+myField[7][l]+" "+myField[8][l]+" "+myField[9][l]);
        return true;
    }
    // проверка, попадают ли координаты Х и Y в определённую зону
    private boolean inZone(float x, float y, float x1, float y1, float x2, float y2){
        if(x>x1 && x<x2 && y>y1 && y<y2) return true;
        else return false;
    }
    // определяем, на какой кораблик нажали
    private int selectShip(float x, float y){
        for(int i = 0; i< Utilities.TOTAL_SHIPS; i++) {
            if (inZone(x, y, myShip[i].getX(), myShip[i].getY(), myShip[i].getX() + myShip[i].getSizeX(), myShip[i].getY() + myShip[i].getSizeY()))
                return i;
        }
        return -1;
    }
    // получаем необходимые размеры
    protected void getScreenSize() {
        // ширина и высота экрана
        Utilities.screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        Utilities.screenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        // размер клетки = ширина поля / количество клеток*2+по клетке с краёв и клетка между ними
       Utilities.size = Utilities.screenWidth/(Utilities.DIMENSION_FIELD*2+5);

        // отступ слева для нашего поля
        Utilities.marginLeftMySide = Utilities.size*2;
        // отступ слева для вражеского поля
        Utilities.marginLeftEnemySide = Utilities.screenWidth/2+ Utilities.size*2;;
        // отступ сверху
        Utilities.marginTop = Utilities.size*2;
    }
    // выбор кораблика
    private void selectedShip(){

    }
    // переодически обновляющийчя метод
    private void update(){
        for(int i = 0; i< Utilities.TOTAL_SHIPS; i++)
            if(myShip[i].isMoveReturnToBase()) myShip[i].returnToBase();
    }

    public void clickButtonStartGame(View view) {
        enemyFieldView = new Field(this, enemyField, Utilities.ENEMY_SIDE);
        playAction= GAME;
    }


   /* public void clickButtonRotate(View view) {
        if(myShip[selectedShip].isSelect()){
            myShip[selectedShip].rotateShip();
        }
    }*/



    // внутренний класс таймера
    class MyTimer extends CountDownTimer {
        MyTimer() {
            super(Integer.MAX_VALUE, 20); // продолжительность работы таймера в милисекундах, интервал срабатывания
        }
        @Override
        public void onTick(long millisUntilFinished) {
            update(); // вызываем метод, в котором происходит обновление игры
        }
        @Override
        public void onFinish() {
        }
    }
    public void Random(View view){

    }
    public void imageButton2(View view) {
        if (myShip[selectedShip].isSelect()) {
            myShip[selectedShip].rotateShip();
        }

    }

}
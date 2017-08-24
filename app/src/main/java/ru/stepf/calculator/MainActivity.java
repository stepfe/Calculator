package ru.stepf.calculator;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Dialog.OnClickListener,NumberFragment.NumberFragmentListener , SecondFragment.SecondFragmentListener, BigFragment.BigFragmentListener {

    private SharedPreferences mSharedPreferences;
    private int numberOfOpenBraces;
    private int numberOfCloseBraces;
    private String stringExample = "";
    private Calculator mCalculator;
    private TextFragment mTextFragment;
    private FragmentManager mFragmentManager;
    private boolean isNumberFragmentActivated;
    private boolean isFragmentsInitialized;
    private PowerManager.WakeLock mWakeLock;
    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = getSharedPreferences("calculator_settings", Context.MODE_PRIVATE);
        setThemeForApp();
        isFragmentsInitialized = false;

        setContentView(R.layout.activity_main);

        loadSettings(savedInstanceState);

        mCalculator = new Calculator();
        mCalculator.setAccuracy(mSharedPreferences.getInt("Accuracy", 8));

        initializeFragments();

        if(!mSharedPreferences.getBoolean("offScreen", true)){
            PowerManager powerManager = (PowerManager)getSystemService(POWER_SERVICE);
            mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Full Wake Lock");
            mWakeLock.acquire();
        }

        if(mSharedPreferences.getBoolean("isFirstStart", true)) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean("isFirstStart", false);
            editor.apply();
            Toast.makeText(getApplicationContext(), "Нажмите на кнопку помощь('?' на панели сверху), чтобы получить информацию о неочевидных возможностях программы", Toast.LENGTH_LONG).show();
        }
    }

    private void setThemeForApp(){
        theme = mSharedPreferences.getInt("Theme", 2);
        switch (theme){
            case 0:
            setTheme(R.style.LightTheme);
                break;
            case 1:
            setTheme(R.style.DarkTheme);
                break;
            case 2:
                setTheme(R.style.BlackAndWhiteTheme);
                break;
        }
    }

    private void loadSettings(Bundle savedInstanceState){
        if(savedInstanceState != null){
            stringExample = savedInstanceState.getString("example");
            isFragmentsInitialized = savedInstanceState.getBoolean("isFragmentsInitialized");
            isNumberFragmentActivated = savedInstanceState.getBoolean("isNumberFragmentActivated");
            numberOfOpenBraces = savedInstanceState.getInt("openBraces");
            numberOfCloseBraces = savedInstanceState.getInt("closeBraces");
        }
    }

    private void initializeFragments(){
        mFragmentManager = getSupportFragmentManager();
        mTextFragment = (TextFragment) mFragmentManager.findFragmentById(R.id.textFragment);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if(!isFragmentsInitialized) {

            Display display = getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);

            int screenWidth = metrics.widthPixels;

            if(screenWidth >= 800){
                BigFragment bigFragment = new BigFragment();
                fragmentTransaction.add(R.id.container, bigFragment, "bigFragment");
            }else {
                NumberFragment numberFragment = new NumberFragment();
                fragmentTransaction.add(R.id.container, numberFragment, "numberFragment");
                isNumberFragmentActivated = true;
            }
            fragmentTransaction.commit();
            isFragmentsInitialized = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("example", stringExample);
        outState.putBoolean("isFragmentsInitialized", isFragmentsInitialized);
        outState.putBoolean("isNumberFragmentActivated", isNumberFragmentActivated);
        outState.putInt("openBraces", numberOfOpenBraces);
        outState.putInt("closeBraces", numberOfCloseBraces);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        MenuItem offScreen = menu.findItem(R.id.offScreen);
        offScreen.setChecked(mSharedPreferences.getBoolean("offScreen", true));

        MenuItem btnHelp = menu.findItem(R.id.help);
        MenuItem btnAbout = menu.findItem(R.id.about);
        MenuItem btnSettings = menu.findItem(R.id.settings);
        switch (theme){
            case 0:
                btnAbout.setIcon(R.drawable.about_light);
                btnHelp.setIcon(R.drawable.help_light);
                btnSettings.setIcon(R.drawable.settings_light);
                break;
            case 1:
                btnAbout.setIcon(R.drawable.about_dark);
                btnHelp.setIcon(R.drawable.help_dark);
                btnSettings.setIcon(R.drawable.settings_dark);
                break;
            case 2:
                btnAbout.setIcon(R.drawable.about_black_and_white);
                btnHelp.setIcon(R.drawable.help_black_and_white);
                btnSettings.setIcon(R.drawable.settings_black_and_white);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.accuracy:
                createAccuracyDialog();
                return true;
            case R.id.theme:
                createThemeDialog();
                return true;
            case R.id.offScreen:
                item.setChecked(!item.isChecked());
                changeScreenSettings(item.isChecked());
                return true;
            case R.id.help:
                createHelpDialog();
                return true;
            case R.id.about:
                createAboutDialog();
        }
        return false;
    }

    private void createAboutDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("О программе");
        builder.setMessage("Данная программа представляет собой калькулятор, который может решать примеры с большим количеством действий и вычислять функции такие, как синусы, арккосинусы и логарифмы. Также программа позволяет сменить тему на более удобную для пользователя");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void createHelpDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Помощь");
        builder.setMessage("Здравствуй, уважаемый пользователь, здесь я в крадце объясню неочевидные особенности программы. " +
                "\n" +
                "\n1)Работа с процентами происходит следующим образом: 20 + 50% = 30 " +
                "\n100 - 10% = 90 и тд. Работают проценты только с четыремя действиями(+, -, *, /) " +
                "\nТо есть: x + 50% = x + 0.5x" +
                "\n" +
                "\n2)Кнопка '=INT' работает также как и '=', но выдает округленный ответ" +
                "\n" +
                "\n3)Если вы хотите продолжить вычисления с полученным ответом нажмите равно еще раз" +
                "\n" +
                "\n4)Если вам нужно написать десятичную дробь без целой части(0.1 например), достаточно просто напечатать 0, и продолжить набирать дробную часть" +
                "\n" +
                "\n5)LG и LB это логарифм по основании 10 и 2 соответственно" +
                "\n" +
                "\n6)По нажатию на кнопку '...' вы переключаетесь на следующий набор кнопок");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void createAccuracyDialog(){
        final String[] change = getResources().getStringArray(R.array.accuracy_values);
        int position = 0;
        for (int i = 0; i< change.length; i++){
            if(mCalculator.getAccuracy() == Integer.valueOf(change[i])){
                position = i;
                break;
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберите максимальное кол-во знаков после запятой");
        builder.setSingleChoiceItems(change, position, null);
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog alertDialog = (AlertDialog) dialog;
                int index = alertDialog.getListView().getCheckedItemPosition();
                int accuracy = Integer.valueOf(change[index]);
                mCalculator.setAccuracy(accuracy);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putInt("Accuracy", accuracy);
                editor.apply();
            }
        });
        builder.show();
    }

    private  void createThemeDialog(){
        String[] change = {"Светлая тема", "Темная тема", "Черным по белому"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Выберете тему");
        builder.setSingleChoiceItems(change, mSharedPreferences.getInt("Theme", 0), null);
        builder.setNegativeButton("Отмена", null);
        builder.setPositiveButton("OK", this);
        builder.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        AlertDialog alertDialog = (AlertDialog) dialog;
        int position = alertDialog.getListView().getCheckedItemPosition();

        finish();
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("Theme", position);
        editor.apply();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void changeScreenSettings(boolean isChecked){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (!isChecked) {
            PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
            mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Full Wake Lock");
            mWakeLock.acquire();
        } else {
            mWakeLock.release();
        }
        editor.putBoolean("offScreen", isChecked);
        editor.apply();
    }






    public void OnNextButtonClick(){
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if(isNumberFragmentActivated) {
            SecondFragment secondFragment = new SecondFragment();
            fragmentTransaction.replace(R.id.container, secondFragment, "secondFragment");
        }else {
            NumberFragment numberFragment = new NumberFragment();
            fragmentTransaction.replace(R.id.container, numberFragment, "numberFragment");
        }
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        isNumberFragmentActivated = !isNumberFragmentActivated;
    }

    public boolean getRadians(){
        return mCalculator.getRadians();
    }

    public void OnRadDegButtonClick(){
        mCalculator.changeRadians();
        String text;
        if(mCalculator.getRadians()){
            text = "Радианы";
        }else {
            text = "Градусы";
        }
        Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT).show();
    }

    public void OnSymbolButtonClick(View view) {
        Button button = (Button) view;

        if(button.getText().equals("-")){
            if(stringExample.length() == 0 || stringExample.endsWith("(") || stringExample.endsWith(" ") && !stringExample.endsWith("_ ")){
                stringExample += "_ ";
                mTextFragment.addText("-");
                return;
            }
        }

        if(isTextEndsWithNumber(stringExample) || stringExample.endsWith(")")) {
            stringExample += " " + button.getText() + " ";
            mTextFragment.addText(button.getText().toString());
        }
    }

    public static boolean isTextEndsWithNumber(String text)
    {
        return text.endsWith("0")||text.endsWith("1")||text.endsWith("2")||text.endsWith("3")||text.endsWith("4")||text.endsWith("5")||text.endsWith("6")||text.endsWith("7")||text.endsWith("8")||text.endsWith("9");
    }

    @Override
    public void OnPercentButtonClick() {
        if(isTextEndsWithNumber(stringExample) && canPlacePercent()){
            stringExample += "%";
            mTextFragment.addText("%");
        }
    }

    private boolean canPlacePercent(){
        if (stringExample.contains(" ")) {
            int index = stringExample.lastIndexOf(" ");
            if ((stringExample.charAt(index - 1) == '+' || stringExample.charAt(index - 1) == '-' || stringExample.charAt(index - 1) == '*' || stringExample.charAt(index - 1) == '/') && !stringExample.endsWith("%"))
                return true;
        }
        return false;
    }

    public void OnSpecialSymbolButtonClick(View view) {
        Button button = (Button) view;
        if (button.getText().equals("^")) {
            if (isTextEndsWithNumber(stringExample) || stringExample.endsWith(")")) {
                stringExample += " ^ ";
                mTextFragment.addText("^");
            }
        } else if (button.getText().equals("√")) {
            if (isTextEndsWithNumber(stringExample) || stringExample.endsWith(")")) {
                stringExample += " √ (";
                mTextFragment.addText("√(");
                numberOfOpenBraces++;
            } else {
                Toast.makeText(getApplicationContext(), "Сначала укажите степень корня", Toast.LENGTH_SHORT).show();
            }
        }else  if(button.getText().equals("log")){
            if (isTextEndsWithNumber(stringExample) || stringExample.endsWith(")")) {
                stringExample += " log (";
                mTextFragment.addText("log(");
                numberOfOpenBraces++;
            } else {
                Toast.makeText(getApplicationContext(), "Сначала укажите основание логарифма", Toast.LENGTH_SHORT).show();
            }
        }else  if (!stringExample.endsWith(")") && !isTextEndsWithNumber(stringExample) && !stringExample.endsWith(".")) {
            if(stringExample.length() != 0 && !stringExample.endsWith("_ ") && !stringExample.endsWith("(") && !stringExample.endsWith(" "))
                stringExample += " ";
            stringExample += button.getText() + " (";
            mTextFragment.addText(button.getText() + "(");
            numberOfOpenBraces++;
        }
    }

    public void OnDotButtonClick() {
        if(isTextEndsWithNumber(stringExample)) {
            if(stringExample.contains(" ")) {
                String lastNumber = stringExample.substring(stringExample.lastIndexOf(" "), stringExample.length());
                if (lastNumber.contains("."))
                    return;
            }else {
                if(stringExample.contains("."))
                    return;
            }

            stringExample += ".";
            mTextFragment.addText(".");
        }
    }
    public void OnNumberButtonClick(View view) {
        Button button = (Button) view;

        if(!stringExample.endsWith(")") && !stringExample.endsWith(String.valueOf(Math.PI)) && !stringExample.endsWith("%")) {
            if(button.getId() == R.id.btnPi)
            {
                if(!isTextEndsWithNumber(stringExample) && !stringExample.endsWith(".")) {
                    stringExample += Math.PI;
                    mTextFragment.addText(button.getText().toString());
                }
            }
            else {
                if (stringExample.endsWith(" 0")|| stringExample.equals("0") || stringExample.endsWith("(0")) {
                    stringExample += ".";
                    mTextFragment.addText(".");
                }
                stringExample += button.getText().toString();
                mTextFragment.addText(button.getText().toString());
            }
        }
    }

    public void OnBraceButtonClick(View view) {
        switch (view.getId()) {
            case R.id.btnOpenBrace:
                if (stringExample.endsWith(" ") || stringExample.endsWith("(") || stringExample.length() == 0 || stringExample.endsWith("_ ")) {
                    numberOfOpenBraces++;
                    stringExample += "(";
                    mTextFragment.addText("(");
                }
                break;
            case R.id.btnCloseBrace:
                if (numberOfCloseBraces < numberOfOpenBraces && !stringExample.endsWith("(") && !stringExample.endsWith(" ")) {
                    numberOfCloseBraces++;
                    stringExample += ")";
                    mTextFragment.addText(")");
                }
                break;
        }
    }

    public void OnClearButtonClick() {
        stringExample = "";
        mTextFragment.clear();
        numberOfCloseBraces = 0;
        numberOfOpenBraces = 0;
    }

    public void OnBackspaceButtonClick() {
        if(stringExample.length() != 0) {
            if (stringExample.endsWith(" ")) {
                deleteSymbolsInExample(3);
                mTextFragment.deleteSymbols(1);
                return;
            } else {
                if(stringExample.endsWith("√ (")){
                    deleteSymbolsInExample(4);
                    mTextFragment.deleteSymbols(2);
                    numberOfOpenBraces --;
                    return;
                }
                if(stringExample.endsWith("asin (") || stringExample.endsWith("acos (") || stringExample.endsWith("atan ("))
                {
                    deleteSymbolsInExample(6);
                    mTextFragment.deleteSymbols(5);
                    numberOfOpenBraces--;
                    return;
                }
                if(stringExample.endsWith("sin (") || stringExample.endsWith("cos (") || stringExample.endsWith("tan (") ){
                    deleteSymbolsInExample(5);
                    mTextFragment.deleteSymbols(4);
                    numberOfOpenBraces--;
                    return;
                }
                if(stringExample.endsWith("log (")){
                    deleteSymbolsInExample(6);
                    mTextFragment.deleteSymbols(4);
                    numberOfOpenBraces--;
                    return;
                }
                if(stringExample.endsWith("lg (") || stringExample.endsWith("lb (")){
                    deleteSymbolsInExample(4);
                    mTextFragment.deleteSymbols(3);
                    numberOfOpenBraces--;
                    return;
                }
                if(stringExample.endsWith("(")) {
                    numberOfOpenBraces--;
                    deleteSymbolsInExample(1);
                    mTextFragment.deleteSymbols(1);
                    return;
                }
                if (stringExample.endsWith(")")) {
                    numberOfCloseBraces--;
                    deleteSymbolsInExample(1);
                    mTextFragment.deleteSymbols(1);
                    return;
                }
                if(stringExample.endsWith(String.valueOf(Math.PI))){
                    mTextFragment.deleteSymbols(1);
                    deleteSymbolsInExample(String.valueOf(Math.PI).length());
                    return;
                }
            }
            deleteSymbolsInExample(1);
            mTextFragment.deleteSymbols(1);
        }
    }

    private void deleteSymbolsInExample(int numOfSymbols){
        stringExample = stringExample.substring(0, stringExample.length() - numOfSymbols);

    }

    public void OnEqualsButtonClick(View view) {
        if (stringExample.length() != 0){
            if(numberOfCloseBraces == numberOfOpenBraces) {
                String answer;
                try {
                    answer = mCalculator.splitByBraces(stringExample);
                    if(view.getId() == R.id.btnIntEquals){
                        int index = answer.indexOf(".");
                        answer = answer.substring(0, index);
                    }
                    stringExample = mTextFragment.printAnswer(answer)? answer:stringExample;
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "Неправильный пример", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(),"Проверьте скобки", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(getApplicationContext(),"Введите пример", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        if(mWakeLock != null)
        mWakeLock.acquire();
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (mWakeLock != null)
        mWakeLock.release();
        super.onPause();
    }

}

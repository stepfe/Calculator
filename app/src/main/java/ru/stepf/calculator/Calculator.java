package ru.stepf.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;

public class Calculator {
    private double firstNumber;
    private double secondNumber;
    private  boolean Radians = false;
    private int accuracy = 8;
    private ArrayList<String> example;

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }


    public void changeRadians() {
        Radians = !Radians;
    }

    public boolean getRadians() {
        return Radians;
    }

    public String splitByBraces(String input) throws Exception
    {
        while (input.contains("("))
        {
            int startIndex = -1;
            int endIndex;
            for (int i = 0; i < input.length(); i++)
            {
                if(input.charAt(i) == '(')
                {
                    startIndex = i;
                }
                if (input.charAt(i) == ')' && startIndex != -1)
                {
                    endIndex = i;
                    String rightPath = input.substring(endIndex + 1, input.length());
                    String leftPath = "";
                    String braceResult = calculate(input.substring(startIndex + 1, endIndex));

                    if(startIndex != 0)
                        leftPath = input.substring(0, startIndex);
                    input = leftPath + braceResult + rightPath;

                    startIndex = -1;
                    if(!input.contains("("))
                        break;
                }
            }
        }
        return round(calculate(input));
    }

    private String round(String num){
        double rounded = new BigDecimal(Double.valueOf(num)).setScale(accuracy, RoundingMode.HALF_UP).doubleValue();
        return String.valueOf(rounded);
    }

    private String calculate(String input) throws Exception{

        example = new ArrayList<>(Arrays.asList(input.split(" ")));
        while (example.size() != 1) {
            while (example.contains("atan")){
                atan(example.indexOf("atan"));
            }

            while (example.contains("acos")){
                acos(example.indexOf("acos"));
            }

            while (example.contains("asin")){
                asin(example.indexOf("asin"));
            }

            while (example.contains("tan")){
                tan(example.indexOf("tan"));
            }

            while (example.contains("cos")){
                cos(example.indexOf("cos"));
            }

            while (example.contains("sin")){
                sin(example.indexOf("sin"));
            }

            while (example.contains("lg")){
                lg(example.indexOf("lg"));
            }

            while (example.contains("lb")){
                lb(example.indexOf("lb"));
            }

            while (example.contains("log")){
                log(example.indexOf("log"));
            }

            for(int i = 0; i < example.size(); i++){
                if(example.get(i).equals("_")){
                    reverseNumber(i);
                }
            }

            while (example.contains("^")){
                involute(example.indexOf("^"));
            }

            while (example.contains("√")){
                roote(example.indexOf("√"));
            }

            while (example.contains("÷")) {
                divide(example.indexOf("÷"));
            }

            while (example.contains("×")) {
                multiplie(example.indexOf("×"));
            }

            while (example.contains("-")) {
                minus(example.indexOf("-"));
            }

            while (example.contains("+")) {
                plus(example.indexOf("+"));
            }

        }
        return example.get(0);
    }

    private void lb(int indexOfLg)throws Exception{
        getNumber(indexOfLg);

        if(firstNumber < 0)
            throw new Exception();

        double logarifm = (Math.log10(firstNumber)/Math.log10(2));
        example.add(indexOfLg, String.valueOf(logarifm));
    }

    private void getNumber(int index){
        firstNumber = Double.valueOf(example.get(index + 1));

        example.remove(index + 1);
        example.remove(index);
    }

    private void log(int indexOfLg) throws Exception{
        getNumbers(indexOfLg);

        if(firstNumber == 1 || firstNumber <= 0 || secondNumber < 0)
            throw new Exception();

        double logarifm = (Math.log10(secondNumber)/Math.log10(firstNumber));
        example.add(indexOfLg - 1, String.valueOf(logarifm));
    }

    private void getNumbers(int index)
    {
        firstNumber = Double.valueOf(example.get(index - 1));
        String second = example.get(index + 1);

        if (second.contains("%"))
            secondNumber = firstNumber * Double.valueOf(second.substring(0, second.length() - 1)) / 100;
        else
            secondNumber = Double.valueOf(example.get(index + 1));

        example.remove(index + 1);
        example.remove(index);
        example.remove(index - 1);
    }

    private void lg(int indexOfLg)throws Exception{
        getNumber(indexOfLg);

        if(firstNumber < 0)
            throw new Exception();

        double logarifm = (Math.log10(firstNumber));
        example.add(indexOfLg, String.valueOf(logarifm));
    }

    private void atan(int indexOfAtan){
        getNumber(indexOfAtan);

        double arctan = (Math.atan(firstNumber));

        if(!Radians){
            arctan = Math.toDegrees(arctan);
        }

        example.add(indexOfAtan, String.valueOf(arctan));
    }

    private void acos(int indexOfAcos){
        getNumber(indexOfAcos);

        double acosinus = (Math.acos(firstNumber));

        if(!Radians){
            acosinus = Math.toDegrees( acosinus);
        }

        example.add(indexOfAcos, String.valueOf(acosinus));
    }

    private void asin(int indexOfAsin){

        getNumber(indexOfAsin);

        double asinus = (Math.asin(firstNumber));

        if(!Radians){
            asinus = Math.toDegrees(asinus);
        }

        example.add(indexOfAsin, String.valueOf(asinus));
    }

    private void tan(int indexOfTangens){

        getNumber(indexOfTangens);

        if(!Radians){
            firstNumber = Math.toRadians(firstNumber);
        }
        double tangens = (Math.tan(firstNumber));

        example.add(indexOfTangens, String.valueOf(tangens));
    }

    private void cos(int indexOfCosinus){

        getNumber(indexOfCosinus);

        if(!Radians){
            firstNumber = Math.toRadians(firstNumber);
        }
        double cosinus = (Math.cos(firstNumber));

        example.add(indexOfCosinus, String.valueOf(cosinus));
    }

    private void sin(int indexOfSinus){

        getNumber(indexOfSinus);

        if(!Radians){
            firstNumber = Math.toRadians(firstNumber);
        }
        double sinus = (Math.sin(firstNumber));

        example.add(indexOfSinus, String.valueOf(sinus));
    }

    private void reverseNumber(int index){

        getNumber(index);

        firstNumber = -firstNumber;

        example.add(index, String.valueOf(firstNumber));
    }

    private void roote(int indexRoote) throws Exception{

        getNumbers(indexRoote);

        if(firstNumber == 0)
            throw new Exception();

        double roote = (Math.pow(secondNumber, (1 / firstNumber)));
        example.add(indexRoote - 1, String.valueOf(roote));
    }

    private void involute(int indexOfInvolute){

        getNumbers(indexOfInvolute);

        double involute = (Math.pow(firstNumber,secondNumber));
        example.add(indexOfInvolute - 1, String.valueOf(involute));
    }

    private void divide(int indexOfDivide) throws Exception{

        getNumbers(indexOfDivide);

        if(secondNumber == 0)
            throw new Exception();

        double res = firstNumber / secondNumber;
        example.add(indexOfDivide - 1, String.valueOf(res));
    }

    private void multiplie(int indexOfMulti){
        getNumbers(indexOfMulti);

        double res = firstNumber * secondNumber;
        example.add(indexOfMulti - 1, String.valueOf(res));
    }

    private void plus(int indexOfPlus){
        getNumbers(indexOfPlus);

        double res = firstNumber + secondNumber;
        example.add(indexOfPlus - 1, String.valueOf(res));
    }

    private void minus(int indexOfMinus){
            getNumbers(indexOfMinus);

            double res = firstNumber - secondNumber;
            example.add(indexOfMinus - 1, String.valueOf(res));
    }
}

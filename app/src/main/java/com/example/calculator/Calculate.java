package com.example.calculator;

import java.util.ArrayList;
import java.util.List;
//import jdk.jshell.JShell;
//import jdk.jshell.SnippetEvent;


public class Calculate {

    public static double CalculateFromString(String strOfNumbers){
        double resultCalculate = 0;
        double intermediateCalculate = 0;

        List<String> fixedSubArrayParts = new ArrayList<String>();
        List<String> subArrayParts = new ArrayList<String>();

        List<String> arrayParts = getArrayParts(strOfNumbers);
        if (arrayParts.size()==0) {
            throw new NegativeArraySizeException("Не удалось получить массив значений.");
        }

        int beginIndex = 0;
        int endIndex = 0;

        while ((beginIndex = arrayParts.indexOf("("))!=-1) {

            for (int i = beginIndex; i < arrayParts.size(); i++) {
                String currentStr = arrayParts.get(i);
                if (currentStr.equals("(")) {
                    beginIndex = i;
                } else if (currentStr.equals(")") || i == arrayParts.size() -1) {
                    endIndex = i;

                    fixedSubArrayParts = arrayParts.subList(beginIndex + 1, currentStr.equals(")") ? endIndex : endIndex + 1);
                    subArrayParts.clear();
                    subArrayParts.addAll(fixedSubArrayParts);
                    intermediateCalculate = CalculateFromList(subArrayParts);

                    RemoveSubArraySetValue(arrayParts, beginIndex, endIndex, intermediateCalculate);
                    break;
                }
            }
        }

        if (arrayParts.size() > 0) {
            resultCalculate = CalculateFromList(arrayParts);
        } else {
            resultCalculate = intermediateCalculate;
        }
        return resultCalculate;
    }

    private static List<String> getArrayParts(String strOfNumbers){
        List<String> arrayParts = new ArrayList<String>();

        String strOperations = "*/+-()";
        String part = "";
        String str;

        strOfNumbers = strOfNumbers.replaceAll("×", "*");
        strOfNumbers = strOfNumbers.replaceAll("÷", "/");
        strOfNumbers = strOfNumbers.replaceAll(",", ".");
        strOfNumbers = strOfNumbers.replaceAll(" ", "");
        char[] chars = strOfNumbers.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            str = Character.toString(chars[i]);

            if (strOperations.indexOf(str)==-1) {
                part+=str;
            }else {
                if (part!="") {
                    arrayParts.add(part);
                    part = "";
                }
                arrayParts.add(str);
            }
        }
        if (part!="") {
            arrayParts.add(part);
            part = "";
        }
        return arrayParts;
    }

    private static double CalculateFromList(List<String> arrayParts) {
        double resultCalculate = 0;

        CalculateOperation(arrayParts, "*", "/");
        CalculateOperation(arrayParts, "+", "-");

        if (arrayParts.size() == 1) {
            resultCalculate = Double.parseDouble(arrayParts.get(0));
        } else {
            System.out.println("Ошибка при расчете.");
        }

        return resultCalculate;
    }

    private static void CalculateOperation(List<String> arrayParts, String Operation1, String Operation2) {
        String prevStr = "";

        double prevNum = 0;
        double currentNum = 0;

        int beginIndex = 0;
        int currentIndex = 0;
        int beginIndexFound1 = -1;
        int beginIndexFound2 = -1;

        while ((beginIndexFound1=arrayParts.indexOf(Operation1))!=-1 | (beginIndexFound2 = arrayParts.indexOf(Operation2))!=-1) {
            if (beginIndexFound1 >= 0 & beginIndexFound2 >= 0) {
                beginIndex = Math.min(beginIndexFound1, beginIndexFound2);
            } else {
                beginIndex = Math.max(beginIndexFound1, beginIndexFound2);
            }
            beginIndex = beginIndex > 0 ? beginIndex - 1 : beginIndex;

            prevStr = "";
            prevNum = 0;
            currentNum = 0;
            currentIndex = 0;
            for (int i = beginIndex; i < arrayParts.size(); i++) {

                String currentStr = arrayParts.get(i);

                if (!currentStr.equals("+") & !currentStr.equals("-") & !currentStr.equals("*") & !currentStr.equals("/")) {
                    prevNum = currentNum;
                    currentNum = Double.parseDouble(currentStr);
                    beginIndex = currentIndex;
                    currentIndex = i;
                }

                if (prevStr.equals("*")) {
                    RemoveSubArraySetValue(arrayParts, beginIndex, i, prevNum * currentNum);
                    break;
                }else if (prevStr.equals("/")) {
                    RemoveSubArraySetValue(arrayParts, beginIndex, i, prevNum / currentNum);
                    break;
                }else if (prevStr.equals("+")) {
                    RemoveSubArraySetValue(arrayParts, beginIndex, i, prevNum + currentNum);
                    break;
                }else if (prevStr.equals("-")) {
                    RemoveSubArraySetValue(arrayParts, beginIndex, i, prevNum - currentNum);
                    break;
                }

                prevStr = currentStr;
            }
        }
    }

    private static void RemoveSubArraySetValue(List<String> arrayParts, int beginIndex, int endIndex, double value) {
        for (int j=endIndex; j>beginIndex; j--) {
            arrayParts.remove(j);
        }
        arrayParts.set(beginIndex, Double.toString(value));
    }

    public static double CalculateWithJShell(String strOfNumbers) {
        double resultCalculate = 0;

//        JShell jShell = JShell.create();
//        SnippetEvent result = jShell.eval(strOfNumbers).get(0);
//        if (result.value() != null) {
//            resultCalculate = Double.parseDouble(result.value());
//        } else {
//            System.out.println("Неизвестная ошибка.");
//        }

        return resultCalculate;
    }
}

/******************************************************/
/* Program Name:   Calculate.java */
/* Name:           Kaori Tagashira */
/* Date yyyy/mm/dd: 2015/01/08 */
/* Description:    与えられた DataSet 型（共通のデータ型）び対して四則演算などの加工をする */
/* Reuse Instruction:*/
/*       */
/*        Purpose: */
/*        Return: */
/******************************************************/
package pkg2014analysis_prototype;

/**
 *
 * @author kaori
 */
public class Calculate {
    
    // Method
    /* DataSet A の指定された軸のデータを DataSet B の指定された軸のデータで割る */
    static double[][] DivideAbyB(double[][] DataA, String axisA, double[][] B, String axisB)/* ●DataSet 型にする */
    {
        double[][] calculation = new double[9][2];/* ●DataSet 型にする */
        return calculation;
    }
    
    // Method
    /* x軸y軸の指定が無い場合は、y軸に対して演算を行う */
    /* 与えられたデータの指定された軸を　指定された定数で割る */
    static double[][] DivideByDigit(double[][] Data, String axis, double Digit)
    {
        double[][] calculation = new double[9][2];/* ●DataSet 型にする */
        return calculation;
    }
}

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
import DataFlow.DataSet;

/**
 *
 * @author kaori
 */
public class Calculate {
    
    // Method
    /* DataSet A の指定された軸のデータを DataSet B の指定された軸のデータで割る */
    static DataSet DivideAbyB(DataSet DataA, String axisA, DataSet B, String axisB)/* ●DataSet 型にする */
    {
        DataSet calculation = new DataSet(axisA,axisB);/* ●DataSet 型にする */
        return calculation;
    }
    
    // Method
    /* x軸y軸の指定が無い場合は、y軸に対して演算を行う */
    /* 与えられたデータの指定された軸を　指定された定数で割る */
    static DataSet DivideByDigit(DataSet Data, String axis, double Digit)
    {
        DataSet calculation = new DataSet("x", axis);/* ●DataSet 型にする */
        return calculation;
    }
}

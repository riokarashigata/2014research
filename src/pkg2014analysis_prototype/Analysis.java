/******************************************************/
/* Program Name:   Analysis.java */
/* Name:           Kaori Tagashira */
/* Date yyyy/mm/dd: 2015/01/08 */
/* Description:    定義された加工プロセスを実行する */
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
public class Analysis {
    // Method
    /* 加工プロセスを実行する */
    public static void main(String[] args) {
        String[] Condition1 = new String[2];/* ●ここを 共通の型 DataSet にする */
        ChartView.Main(Productivity.Main("ACTLOC", "ACTMIN", Condition1),"Hyou");/* 加工プロセスを実行し、グラフを出力する。*/
    }
    
}

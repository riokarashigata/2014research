/******************************************************/
/* Program Name:   Productivity.java */
/* Name:           Kaori Tagashira */
/* Date yyyy/mm/dd: 2015/01/08 */
/* Description:    ユーザが定義した「生産性」の加工プロセスの例 */
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
public class Productivity {
    // Method
    static double[][] Main(String LOC, String MIN, String[] Condition)
    {
        return Calculate.DivideAbyB(DBBlock.getValue("PROJECTID","ACTLOC", Condition),"y", Calculate.DivideByDigit(DBBlock.getValue("PROJECTID","ACTMIN", Condition),"y",60.0),"y");
    }
}

/******************************************************/
/* Program Name:   example_ACTLOC.java */
/* Name:           Kaori Tagashira */
/* Date yyyy/mm/dd: 2015/01/14 */
/* Description:    ユーザが定義した「ACTLOC」の加工プロセスの例 */
/* Reuse Instruction:*/
/*       */
/*        Purpose: */
/*        Return: */
/******************************************************/
package pkg2014analysis_prototype;
import java.util.ArrayList;


/**
 *
 * @author kaori
 */
public class example_ACTLOC {
    /* データを出力するブロックの塊と、グラフを出力するブロックをつなぐ */
    public static void join (String Xname, String Yname, String sql, String ChartType)
    {
        ChartBlock( ACTLOC(Xname, Yname, sql), ChartType );
    }
    
    public static ArrayList<ArrayList<double[]>> ACTLOC(String Xname, String Yname, String sql)
    {
        return DBBlock.getData(Xname, Yname, sql);
    }
    
    public static void ChartBlock(ArrayList<ArrayList<double[]>> Data, String ChartType)
    {
        
    }
}

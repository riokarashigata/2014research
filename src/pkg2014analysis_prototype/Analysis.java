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

import java.util.ArrayList;

/**
 *
 * @author kaori
 */
public class Analysis {
    // Method
    // 加工プロセスを実行する
    public static void main (String[] args) {
        
        // 入力例
        // ●共通の型 ArrayList<ArrayList>
        ArrayList<ArrayList<double[]>> Block = new ArrayList();
        // XMLから変換
        Block = DBBlock("Xname", "Yname", "SELECT * from PSPASSGTDATA");
                
        // 加工プロセスを実行
        ChartBlock.Chart(Block, "LINE");

    }
    
    public static ArrayList<ArrayList<double[]>> DBBlock(String Xname, String Yname, String sql)
    {
        ArrayList<ArrayList<double[]>> Block = new ArrayList();
        return Block;
    }
            
}
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
    /* 加工プロセスを実行する */
    public static void main (String[] args) {
        String[][] UsersInput = new String[2][2];
        ArrayList[] Condition = createCondition(UsersInput);
        ChartView.Main(Productivity.Main("ACTLOC", "1", "ACTMIN", "2", Condition),"Hyou");/* 加工プロセスを実行し、グラフを出力する。*/
    }
 
    /* 条件を受け取って、条件リストを作る */
    /* ●ここにつくるかは不確定 */
    public static ArrayList[] createCondition(String[][] UsersInput)
    {
        /* String[][] UsersInput は、n*2 の配列。*/
        /* Block_ID と、Block_ID ごとの条件リスト（カンマ区切り）が格納されている。 */
        /* 
        UsersInput の例
        {{"1","CLASS_ID=201301, ST_ID=1"},{"2","CLASS_ID=201301,ST_ID=1"}}       
        */
        
        ArrayList[] Condition = new ArrayList[UsersInput.length];/* Block_ID の数だけ箱を確保する */
        
        /* Block_ID ごとに処理を行う */
        for(int i = 0; i < UsersInput.length; i++)
        {
            /* Block_ID ごとの条件リスト（カンマ区切りの一つの文字列）をplitメソッドで分割する */
            String[] ConditionList = UsersInput[i][1].split(",");
            
            Condition[i] = new ArrayList();/* Block_ID ごとに ArrayList をつくる＝BLock_IDごとに可変長！ */
            /* ↑のようにすることで、ブロックごとに条件の数が異なっても対応できる。 */ 
            
            /* Block_ID を先頭に入れる */
            Condition[i].add(Integer.toString(i));
            
            /* 条件を ArrayList につなげていく */
            for(int j = 0; j < ConditionList.length; j++)
            {
                Condition[i].add(ConditionList[j]);                
            }
            
        }
        return Condition;
    }
    
}

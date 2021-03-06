/******************************************************/
/* Program Name:   test_DBBlock.java */
/* Name:           Kaori Tagashira */
/* Date yyyy/mm/dd: 2015/01/11 */
/* Description:    DBBlock.java が動くかを試すプログラム */
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
public class test_DBBlock 
{   
    public static void main (String[] args)
    {
        /* ユーザからの操作 */
        /* ●仮。どこかでまとめて受け取る。 */
        String[] UsersInput1 = {"1","PSPASSGTDATA.CLASS_ID=201301,PSPASSGTDATA.ST_ID=1"};
        String[] UsersInput2 = {"2","PSPASSGTDATA.CLASS_ID=201301,PSPASSGTDATA.ST_ID=1"};
        String[][] UsersInput = new String[2][2];/* ●条件リストの数は可変長にするか長さを取得する必要あり */
        UsersInput[0] = UsersInput1;
        UsersInput[1] = UsersInput2;
        
        ArrayList[] Condition = Analysis.createCondition(UsersInput);/* 実際には Analysis.java で行う。 */
        
        /* 解説：getValue(String FieldNameX, String FieldNameY, ArrayList[] Condition, String Block_ID) */
        ArrayList[] Block = DBBlock.getValue("PROJECTID", "ACTMIN", Condition, "1");
        for(int i = 0; i < Block.length; i++)
        {
            for(int j = 0; j < Block[i].size(); j++)
            {
                System.out.print("\t" + Block[i].get(j));/* ●結果を確認 */
            }
            System.out.println();
        }
        
    }
}


/* 
select PSPASSGTDATA.PROJECTID, PSPASSGTDATA.ACTLOC
from PSPASSGTDATA, USERS 
WHERE PSPASSGTDATA.CLASS_ID = USERS.CLASS_ID
AND PSPASSGTDATA.ST_ID = USERS.ST_ID
AND PSPASSGTDATA.CLASS_ID = 201301
AND PSPASSGTDATA.ST_ID = 1
ORDER BY PSPASSGTDATA.CLASS_ID,
PSPASSGTDATA.ST_ID,
PSPASSGTDATA.PROJECTID;
*/
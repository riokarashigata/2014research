/******************************************************/
/* Program Name:   DBBlock.java */
/* Name:           Kaori Tagashira */
/* Date yyyy/mm/dd: 2015/01/08 */
/* Description:    DB から指定されたプロセスデータをとってきて、DataSet 型（共通の型）で返す */
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
public class DBBlock {
    
    // Method
    /* DB から取ってきた値を DataSet 型（共通の型）に入れて返す */
    static double[][] getValue(String FieldNameX, String FieldNameY, String[] Condition)
    {
        double[][] DBBlock = new double[9][2];
        return DBBlock;
    }
    
    // Method
    /* DB から目的のテーブルを取ってくる。 */
    static double[][] getTable(String FieldName, String[] Condition)
    {
        double[][] DBBlock = new double[9][2];
        return DBBlock;
    }
    
    // Method
    /* SQL 文を生成する */
    static String createSQL(String FieldName, String[] Condition)
    {
        String SELECT = "SELECT PSPASSGTDATA." + FieldName;
        String FROM = " FROM PSPASSGTDATA, USERS";
        String WHERE = " WHERE PSPASSGTDATA.ST_ID = USERS.ST_ID AND PSPASSGTDATA.CLASS_ID = USERS.CLASS_ID";
        WHERE = WHERE + joinWhere(Condition);
        String ORDER_BY = " ORDER BY PSPASSGTDATA.CLASS_ID, PSPASSGTDATA.ST_ID,PSPASSGTDATA.PROJECTID";
        String SQL = SELECT + FROM + WHERE + ORDER_BY;
        return SQL;
    }
    
    // Method
    /* SQL 文のWHEREへの追加。条件リストCondition の中身を AND でつなげて一つの String にして返す */
    static String joinWhere(String[] Condition)
    {
        String Where = "";
        for(int i = 0; i < Condition.length; i++)
        {
            Where = Where + Condition[i];
        }
        return Where;
    }
}

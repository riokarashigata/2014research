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
import DataFlow.DataSet;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author kaori
 */
public class DBBlock {
    
    // Method
    /* DB から取ってきた値を DataSet 型（共通の型）に入れて返す */
    static DataSet getValue(String FieldNameX, String FieldNameY, ArrayList[] Condition, String Block_ID)
    {
        DataSet DBBlock = new DataSet(FieldNameX, FieldNameY);
        return DBBlock;
    }
    
    // Method
    /* DB から目的のテーブルを取ってくる。 */
    static ResultSet getTable(String FieldName, ArrayList[] Condition, String Block_ID)
    {
        ResultSet Table = null;/* ●とりあえず null．後程修正。 */
        return Table;
    }
    
    // Method
    /* SQL 文を生成する */
    static String createSQL(String FieldName, ArrayList[] Condition, String Block_ID)/* ●条件（Condition) の型は、まだ未定。 */
    {
        String SELECT = "SELECT PSPASSGTDATA." + FieldName;
        String FROM = " FROM PSPASSGTDATA, USERS";
        String WHERE = " WHERE PSPASSGTDATA.ST_ID = USERS.ST_ID AND PSPASSGTDATA.CLASS_ID = USERS.CLASS_ID";
        WHERE = (WHERE + joinWhere(Condition, Block_ID));
        String ORDER_BY = " ORDER BY PSPASSGTDATA.CLASS_ID, PSPASSGTDATA.ST_ID,PSPASSGTDATA.PROJECTID";
        String SQL = SELECT + FROM + WHERE + ORDER_BY;
        return SQL;
    }
    
    // Method
    /* SQL 文のWHEREへの追加。条件リストCondition の中身を AND でつなげて一つの String にして返す */
    static String joinWhere(ArrayList[] Condition, String Block_ID)/* ●条件(Condition)の型は、まだ未定。 */
    {
        int B_ID = Integer.parseInt(Block_ID);
        String Where = "";
        /* リストの先頭には条件ではなく Block_ID が入っているので、i=0 ではなく i=1 からスタートする。 */
        for(int i = 1; i < Condition[B_ID].size(); i++)
        {
            Where = Where + Condition[B_ID].get(i);
        }
        return Where;
    }
}

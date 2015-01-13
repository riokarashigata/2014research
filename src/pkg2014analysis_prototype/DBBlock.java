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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author kaori
 */
public class DBBlock {
    
    // Method
    /* DB から取ってきた値を DataSet 型（共通の型）に入れて返す */
    public static /* DataSet*/ ArrayList[] getValue(String FieldNameX, String FieldNameY, ArrayList[] Condition, String Block_ID)
    {
        //DataSet DBBlock = new DataSet(FieldNameX,FieldNameY);/* DataSet */
        
        //ArrayList Data_X = getTable(FieldNameX, Condition, Block_ID);/* ●DataSet 型（共通の型）に変える */
        //ArrayList Data_Y = getTable(FieldNameY, Condition, Block_ID);/* ●DataSet 型（共通の型）に変える */

        ArrayList[] DBBlock = new ArrayList [2];/* x軸の値と y軸の値を格納する */
        DBBlock[0] = new ArrayList();/* ●NullPointerException になるので。 */
        DBBlock[0].add(getTable(FieldNameX, Condition, Block_ID));
        DBBlock[1] = new ArrayList();/* ●NullPointerException になるので。 */
        DBBlock[1].add(getTable(FieldNameY, Condition, Block_ID));
        System.out.println("DBBlock.java > getValue");/* ●確認用 */
         
        return DBBlock;
    }
       
    // Method
    /* DB から目的のテーブルを取ってくる。 */
    /* 個人単位のプロセスデータ */
    private static ArrayList/*ResultSet*/ getTable(String FieldName, ArrayList[] Condition, String Block_ID)
    {
        System.out.println("getTable");/* ●確認 */
        ArrayList DataSet = new ArrayList();/* ●DataSet 後でちゃんとしたやつに変える*/
        System.out.println("getTable2");/* ●確認 */
        try
        {
            /* データベースへの接続 */
            String driverUrl = "jdbc:derby://localhost:1527/PSP_for_E";
            Connection con = DriverManager.getConnection(driverUrl, "root","root");
            /* SQLを実行するためのステートメントの作成 */
            Statement stmt = con.createStatement();
            /* SQLの実行 */
            String sql = createSQL(FieldName, Condition, Block_ID);
            System.out.println(sql);/* ●確認用 */
            ResultSet Table = stmt.executeQuery(sql);
            
            
            // Table.next();
            //int STID = Table.getInt("ST_ID");/* ST_ID の確認用 */
            //Table.previous();
            int STID = 0;
            int flg = 0;
            
            /* 結果の表示 */
            /* 以下、クラスデータとして、個人ごとに記録（一番外のループ） */
            
            while( Table.next())
            {
                if(flg == 0)
                {
                    STID = Table.getInt("ST_ID");/* ST_ID の確認用 */
                    flg = 1;
                }
                /* "ST_ID"が同じ（＝同じ人のデータ）なら */
                /* 個人データを記録していく */
                double [] ProcessData = new double[9];/* ●ここをプロセスデータにする */
                if(Table.getInt("ST_ID") == STID)
                {
                    /* 課題ごとの値を得る */
                    for(int i =1; i <= 8; i++)
                    {
                        String Program = Table.getString("PROGRAMASSIGNMENT");
                        if( Integer.parseInt(Program.substring(Program.length()-1)) == i)
                        {
                            int DATA = Table.getInt(FieldName);
                            if(DATA != 0)
                            {
                                ProcessData[i] = DATA;
                            }
                        }
                    }
                }
                /* ●確認用 */
                for(int i = 0; i < ProcessData.length; i++)
                {
                    System.out.println("ProcessData [" + i + "] = " + ProcessData[i]);
                }
                
                /* ● DataSet に ProcessData を格納する。 */
                DataSet.add(ProcessData);
                STID = Table.getInt("ST_ID");/* "ST_ID"を更新する */
                //●Table.previous();/* 1つ戻る（.next() の逆）*/
            }
            
            /* 後片付け */
            Table.close();
            stmt.close();
            con.close();
            
        } catch ( SQLException e){
            e.printStackTrace();
        }
        
        System.out.println("getTable before return");/* ●確認用 */
       
        //ResultSet Table = null;/* ●とりあえず null．後程修正。 */
        return DataSet;
    }
    
    // Method
    /* SQL 文を生成する */
    private static String createSQL(String FieldName, ArrayList[] Condition, String Block_ID)
    {
        String SELECT = "SELECT PSPASSGTDATA.PROGRAMASSIGNMENT, PSPASSGTDATA.ST_ID, PSPASSGTDATA." + FieldName;
        String FROM = " FROM PSPASSGTDATA, USERS";
        String WHERE = " WHERE PSPASSGTDATA.ST_ID = USERS.ST_ID AND PSPASSGTDATA.CLASS_ID = USERS.CLASS_ID";
        WHERE = (WHERE + joinWhere(Condition, Block_ID));
        String ORDER_BY = " ORDER BY PSPASSGTDATA.CLASS_ID, PSPASSGTDATA.ST_ID,PSPASSGTDATA.PROJECTID";
        String SQL = SELECT + FROM + WHERE + ORDER_BY;
        return SQL;
    }
    
    // Method
    /* SQL 文のWHEREへの追加。条件リストCondition の中身を AND でつなげて一つの String にして返す */
    private static String joinWhere(ArrayList[] Condition, String Block_ID)/* ●条件(Condition)の型は、まだ未定。 */
    {
        int B_ID = Integer.parseInt(Block_ID);
        String Where = "";
        /* リストの先頭には条件ではなく Block_ID が入っているので、i=0 ではなく i=1 からスタートする。 */
        for(int i = 1; i < Condition[B_ID].size(); i++)
        {
            Where = Where + " AND " + Condition[B_ID].get(i);
        }
        return Where;
    }
}

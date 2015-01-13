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

        ArrayList[] DBBlock = new ArrayList [2];
        DBBlock[0].add(getTable(FieldNameX, Condition, Block_ID));
        DBBlock[1].add(getTable(FieldNameY, Condition, Block_ID));
        
        return DBBlock;
    }
    
    // Method
    /* クラスデータなのか個人データなのかを調べる */
    private static String checkDataType(ArrayList[] Condition, String Block_ID)
    {
        String DataType = null;
        int BlockID = Integer.parseInt(Block_ID);
        int count = 0;/* 条件リストの中の"ST_ID="で始まる条件の数を数える */
        /* 特定のブロックについて、条件リストの中身を調べる */
        for(int i = 0; i < Condition[BlockID].size(); i++)/* そのブロックの条件リストを全てみていく */
        {
            String Target = "ST_ID";
            /* 条件リストの中に ST_ID "=" 一つの番号 */
            if((Condition[BlockID].get(i).toString()).startsWith( "ST_ID="))
            {
                count++;/* 条件リストの中の"ST_ID="で始まる条件の数を数える */
            }
            /* "ST_ID="で始まる条件が 1 つなら、個人データ。
            　0 又は 2以上の場合は、クラスデータの可能性。
            */
        }
        if(count == 1)
        {
            DataType = "Pelsonal";/* 個人データが一つ */
        }else{
            DataType = "Class";/* 個人データが複数 */
        }
   
        return DataType;
    }
    
    // Method
    /* DB から目的のテーブルを取ってくる。 */
    /* 個人単位のプロセスデータ */
    private static ArrayList/*ResultSet*/ getTable(String FieldName, ArrayList[] Condition, String Block_ID)
    {
        String DataType = checkDataType(Condition, Block_ID);/* 個人データかクラスデータかを調べる */
        ArrayList DataSet = new ArrayList();/* ●DataSet 後でちゃんとしたやつに変える*/
        try
        {
            /* データベースへの接続 */
            String driverUrl = "jdbc:derby://localhost:1527/PSP_for_E";
            Connection con = DriverManager.getConnection(driverUrl, "root","root");
            /* SQLを実行するためのステートメントの作成 */
            Statement stmt = con.createStatement();
            /* SQLの実行 */
            String sql = createSQL(FieldName, Condition, Block_ID);
            ResultSet Table = stmt.executeQuery(sql);
            
            int STID = Table.getInt("ST_ID");/* ST_ID の確認用 */
        
            /* 結果の表示 */
            /* 以下、クラスデータとして、個人ごとに記録（一番外のループ） */
            
            while( Table.next())
            {
                /* "ST_ID"が同じ（＝同じ人のデータ）なら */
                /* 個人データを記録していく */
                double [] ProcessData = new double[9];/* ●ここをプロセスデータにする */
                while(Table.getInt("ST_ID") == STID)
                {
                    /* 課題ごとの値を得る */
                    for(int i =1; i <= 8; i++)
                    {
                        String Program = Table.getString("PROGRAMASSGITMENT");
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
                /* ● DataSet に ProcessData を格納する。 */
                DataSet.add(ProcessData);
                STID = Table.getInt("ST_ID");/* "ST_ID"を更新する */
                Table.previous();/* 1つ戻る（.next() の逆）*/
            }
            
            /* 後片付け */
            Table.close();
            stmt.close();
            con.close();
            
        } catch ( SQLException e){
            e.printStackTrace();
        }
       
        //ResultSet Table = null;/* ●とりあえず null．後程修正。 */
        return DataSet;
    }
    
    // Method
    /* SQL 文を生成する */
    private static String createSQL(String FieldName, ArrayList[] Condition, String Block_ID)
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
    private static String joinWhere(ArrayList[] Condition, String Block_ID)/* ●条件(Condition)の型は、まだ未定。 */
    {
        int B_ID = Integer.parseInt(Block_ID);
        String Where = "";
        for(int i = 0; i < Condition[B_ID].size(); i++)
        {
            Where = Where + " AND " + Condition[B_ID].get(i);
        }
        return Where;
    }
}

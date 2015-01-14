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
    // DB からデータを取ってきて、共通の型に格納して返す
    public static ArrayList<ArrayList<double[]>> getData(String Xname, String Yname, String sql)
    {        
        ArrayList<ArrayList<double[]>> Data = new ArrayList();

        // 一人分のデータを取ってくる
        ArrayList<double[]> PersonalData = getPersonalData(Xname, Yname, sql);
        
        // ArrayList<ArrayList> にまとめる。
        Data.add(PersonalData);              
        
        return Data;
    }
    
    // 一人分のデータを確認する
    public static void PrintOut(ArrayList<double[]> PersonalData)
    {
        for(int i = 0; i < PersonalData.size(); i++)
        {
            for(int j = 0; j < PersonalData.get(i).length; j++)
            {
                System.out.println(PersonalData.get(i)[j]);
            }
        }
    }
            
    // 一人分のデータを取ってくる
    public static ArrayList<double[]> getPersonalData(String Xname, String Yname, String sql)
    {
        ArrayList<double[]> PersonalData = new ArrayList();
        // xyデータを格納
        double[][] temp = new double[8][2];
        
        try
        {
            // データベースへの接続
            String driverUrl = "jdbc:derby://localhost:1527/PSP_for_E";
            Connection con = DriverManager.getConnection(driverUrl, "root","root");
            // SQLを実行するためのステートメントの作成
            Statement stmt = con.createStatement();
            // SQLの実行
            ResultSet rs = stmt.executeQuery(sql);
                      
            // 結果の表示
            while( rs.next() )
            {
                           
                /* 課題ごとの値を得る */ 
                for(int i = 1; i <= 8; i++)
                {
                    String Program = rs.getString("PROGRAMASSIGNMENT");
                    if( Integer.parseInt(Program.substring(Program.length()-1)) == i )
                    {
                        int DATA = rs.getInt(Xname);
                        if(DATA != 0 ){
                            temp[i-1][0] = DATA; 
                        }
                        DATA = rs.getInt(Yname);
                        if(DATA != 0 )
                        {
                            temp[i-1][1] = DATA;
                        }
                    }
                }                
            }

            /* 後片付け */
            rs.close();
            stmt.close();
            con.close();
                                    
        } catch ( SQLException e){
            e.printStackTrace();
        }
        
        for(int i = 0; i < 8; i++)
        {
            double[] xyData = new double[2];
            xyData[0] = temp[i][0];
            xyData[1] = temp[i][1];
            PersonalData.add(xyData);
            //System.out.println(temp[i][1]);
        }
        
        return PersonalData;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // Method
    public static /* DataSet*/ ArrayList[] getValue(String FieldNameX, String FieldNameY, ArrayList[] Condition, String Block_ID)
    {
        //DataSet DBBlock = new DataSet(FieldNameX,FieldNameY);/* DataSet */
        
        //ArrayList Data_X = getTable(FieldNameX, Condition, Block_ID);/* ●DataSet 型（共通の型）に変える */
        //ArrayList Data_Y = getTable(FieldNameY, Condition, Block_ID);/* ●DataSet 型（共通の型）に変える */

        ArrayList[] DBBlock = new ArrayList [2];/* x軸の値と y軸の値を格納する */
        DBBlock[0] = new ArrayList();/* ●NullPointerException になるので。 */
        DBBlock[0].add(getTable(FieldNameX, Condition, Block_ID));/* x 軸のデータ */
        DBBlock[1] = new ArrayList();/* ●NullPointerException になるので。 */
        DBBlock[1].add(getTable(FieldNameY, Condition, Block_ID));/* y 軸のデータ */
        System.out.println("DBBlock.java > getValue");/* ●確認用 */
         
        return DBBlock;
    }
       
    // Method
    /* DB からデータをとってくる。取ってきたデータを合わせる。 */
    /* クラス単位（DataSet サイズ）のデータ */
    private static ArrayList getTable(String FieldName, ArrayList[] Condition, String Block_ID)
    {
        ArrayList ClassData = new ArrayList();/* ●DataSet 後でちゃんとしたやつに変える*/
        
        /* ●何人分のデータがいるのかを調べる */
        
        
        /* ●ST_ID を設定する */
        int ST_ID = 0;
        
        /* ●人数分だけループを回す */
        /* その都度 ST_ID を更新する */
        /* ●個人のデータを取ってくる */
        double[] Personal = getProcessData(FieldName, Condition, Block_ID,ST_ID);
     
        return ClassData;
    }
    
    // Method
    /* DB からデータをとってくる。 */
    /* 個人単位（ProcessData サイズ）のデータ */
    public /* private */ static double[] getProcessData(String FieldName, ArrayList[] Condition, String Block_ID, int ST_ID)
    {
        /* ●DB からとってきた値を格納する。可変長にできればする。 */
        double [] Data = new double[9];/* ● */
            
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
            
            int STID = ST_ID;/* ● */
            int flg = 0;
                       
            /* 特定の ST_ID のデータを取り出していく。 */
            while( Table.next())
            {
                if(flg == 0)/* 1 回だけ実行させる */
                {
                    STID = Table.getInt("ST_ID");/* ST_ID の確認用 */
                    flg = 1;
                }
                /* "ST_ID"が同じ（＝同じ人のデータ）なら */
                /* 個人データを記録していく */
                if(Table.getInt("ST_ID") == STID)
                {
                    /* 課題ごとの値を得る */
                    for(int i =1; i <= 8; i++)
                    {
                        String Program = Table.getString("PROGRAMASSIGNMENT");
                        if( Integer.parseInt(Program.substring(Program.length()-1)) == i)/* 課題番号が i と同じなら */
                        {
                            int DATA = Table.getInt(FieldName);/* 値を取ってくる */
                            if(DATA != 0)/* 値が 0 でなければ */
                            {
                                Data[i] = DATA;/* 値を格納する */
                            }
                        }
                    }
                }
            }
            /* 後片付け */
            Table.close();
            stmt.close();
            con.close();
            
        } catch ( SQLException e){
            e.printStackTrace();
        }
        
        /* Data に格納されているデータのうち、空白でないデータの数を調べる。 */
        int count = 0;
        for(int i = 1; i <= 8 ; i++)
        {
            if(Data[i] != 0)
            {
                count++;
            }
        }
        
        /* ●最終的な値を格納する */
        double[] ProcessData = new double[count];
        
        /* 余計な値を除いて格納する。 */
        for(int i = 0; i < count; i++)
        {
            ProcessData[i] = Data[i];
        }       

        return ProcessData;
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

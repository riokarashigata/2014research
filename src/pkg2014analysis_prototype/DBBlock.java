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
    public static void PrintOutPersonalData(ArrayList<double[]> PersonalData)
    {
        for(int i = 0; i < PersonalData.size(); i++)
        {
            for(int j = 0; j < PersonalData.get(i).length; j++)
            {
                System.out.println(PersonalData.get(i)[j]);
            }
        }
    }
    
    // 複数人のデータを確認する
    public static void PrintOutData(ArrayList<ArrayList<double[]>> Data)
    {
        for(int i = 0; i < Data.size(); i++)
        {
            for(int j = 0; j < Data.get(i).size(); j++)
            {
                for(int k = 0; k < Data.get(i).get(j).length; k++)
                {
                    System.out.println(Data.get(i).get(j)[k]);   
                }
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
        }
        
        return PersonalData;
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

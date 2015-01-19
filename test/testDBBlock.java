/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pkg2014analysis_prototype.DBBlock;

/**
 *
 * @author kaori
 */
public class testDBBlock {
    
    public static void main(String[] args) {
        String sql = "select PSPASSGTDATA.PROJECTID, PSPASSGTDATA.ACTLOC, PSPASSGTDATA.PROGRAMASSIGNMENT from ROOT.PSPASSGTDATA\n" +
"WHERE PSPASSGTDATA.CLASS_ID = 201301 AND PSPASSGTDATA.ST_ID = 1 ORDER BY PSPASSGTDATA.PROJECTID";
        // ArrayList<ArrayList<double[]>> Block =  DBBlock.getData("PROJECTID", "ACTLOC", sql);
        // DBBlock.PrintOutPersonalData(testPersonalData(sql));
        //testPersonalData(sql);
        DBBlock.PrintOutData(test_getData(sql));
        
    }

    public static ArrayList<double[]> testPersonalData(String sql)
    {
        return DBBlock.getPersonalData("PROJECTID", "ACTLOC", sql);
    }
    
    public static ArrayList<ArrayList<double[]>> test_getData(String sql)
    {
        return DBBlock.getData("PROJECTID", "ACTLOC", sql);
    }
    
}

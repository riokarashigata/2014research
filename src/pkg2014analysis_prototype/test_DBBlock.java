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

/**
 *
 * @author kaori
 */
public class test_DBBlock {
    
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
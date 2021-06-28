public class Operation{

   public String OperName; // begin, commit
   public String fname;  
   public int timestamp;
   public char dataitem;
   public int reject;
   public int TransList;

   public Operation(String n , String f, int t, char d, int tc){
      OperName = n;
      timestamp = t;
      dataitem = d;
      reject = 0;
      TransList = tc;
      fname = f ;
   }



}
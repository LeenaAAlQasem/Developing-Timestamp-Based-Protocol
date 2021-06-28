public class Transaction {

   public int TransName; //name of the trasiction
   public int timeStamp = 0; //timestamp of the transaction (begining of the transicition) 
   public int abortTrans = 0; //timestamp of when abort
 
   
   
   public Transaction(int i,int t){
   
      this.TransName=i;
      timeStamp = t;
      abortTrans = 0;
   
   }
      
}
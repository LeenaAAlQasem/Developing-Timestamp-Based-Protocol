public class Version {

   public char Symbol; // X, Y, Z
   public int []  RTS;
   public int []  WTS;
   public int ReadSize ; // size of the RTS
   public int WriteSize ; // size of the WTS

   public Version (char n){
      Symbol = n ;
      RTS = new int[100];
      WTS = new int[100];
      ReadSize = 0 ;
      WriteSize = 0 ;
   }

   public void setWrite(int w){
      WTS[WriteSize++] = w ; 
   }
   
   public void setRead(int r){
      RTS[ReadSize++] = r ;
   }
   
   
   public int getWrite(){  
   
      if(WriteSize <= 0) 
       return WTS[WriteSize]; 
       
      else 
       return WTS[WriteSize-1];
   }
   
   
   public int getRead(){
   
     if(ReadSize <= 0) 
       return RTS[ReadSize];
     else 
       return RTS[ReadSize-1];   
   }
   
   
   public void resetRTS(int timeS ){
   
      int s = ReadSize ;
      for(int i = s-1; i >=  0; i--){
         if(RTS[i] == timeS){
            RTS[i] = 0 ;
            ReadSize--;
         }else{
            break ; }
      }
   
   }
   
   
   public void resetWTS(int timeS ){
      int s = WriteSize ; 
      for(int i = s-1; i >= 0 ; i--){
         if(WTS[i] == timeS){
            WTS[i]=0 ; 
            WriteSize--;
         } else{ 
        	 break ;} 
      }   
   }
}
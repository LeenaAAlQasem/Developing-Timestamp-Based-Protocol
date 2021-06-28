public class LinkedList<T>  {

   private Node<T> head;
   private Node<T> current;
   private int size ;

   public LinkedList() {
      head = current = null;
      size = 0 ;
   }

   public boolean empty() {
   
      return head == null;
   
   }

   public boolean full() {
   
      return false;
   
   }

   public void findFirst() {
   
      current = head;
   
   }

   public void findNext() {
   
      current = current.next;
   
   }

   public boolean last() {
   
      return current.next == null;
   
   }

   public T retrieve() {
   
      return current.data;
   
   }

   public void update(T e) {
   
      current.data = e;
   
   }

   public void insert(T e) {
   
      Node<T> tmp;
      if (empty()) {
      	
         current = head = new Node<T>(e);
         size++;
      	
      } else {
      	
         tmp = current.next;
         current.next = new Node<T>(e);
         current = current.next;
         current.next = tmp;
         size++;
      	
      }
   
   }

   public void remove() {
   
      if (current == head) {
         head = head.next;
      	
      } else {
      
         Node<T> tmp = head;
      
         while (tmp.next != current)
            tmp = tmp.next;
      
         tmp.next = current.next;
      }
   
      if (current.next == null)
         current = head;
      
      else
         current = current.next;
   	
      size--; 
   }
   
   public int size() {
      return size ;
   	
   }
	
   public boolean exists(T e) {
   	
      boolean found = false ;
   	
      Node<T> p = head ;
   	
      while(p != null) {
      	
         if(p.data.equals(e)) {
            found = true ;
            current = p ;
            break ;
         }
         p=p.next;
      	
      }
   	
      return found ;
   	
   }
	
}

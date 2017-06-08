package core.asyncomm ;

import java.util.* ;
import java.lang.reflect.* ;

public class Topic implements Runnable{
   String name ;
   ArrayDeque<Message> msgQueue ;
   ArrayList<Callback> callbackList ;

   public Topic( String name ) {
      this.setName(name) ;
      this.msgQueue = new ArrayDeque<Message>() ;
      this.callbackList = new ArrayList<Callback>() ;
   }

   /**
    *  设置topic的名称
    *  @method setName
    *  @param  name [description]
    */
   public void setName( String name ){
      this.name = name ;
   }
   public String getName() {
      return this.name ;
   }

   /**
    *  添加一个callback
    *  @method addCallBack
    *  @param   [description]
    *  @param   [description]
    *  @param   [description]
    */
   public void addCallBack( Class c, String metName, Class[] paraList ) {
      Callback cb = new Callback( c, metName, paraList ) ;
      this.callbackList.add(cb) ;
   }

   /**
    *  往消息队列中添加一条消息,
    *  @method addMsg
    *  @param  msg [description]
    */
   public void addMsg( Object[] objList ) {
      synchronized(msgQueue) {
         Message msg = new Message(objList) ;
         msgQueue.add(msg) ;
      }
   }

   /**
    *  调用callback中的方法
    *  @method call
    *  @param  msg [description]
    */
   public void call( Message msg ){
      if( msg.getObj() == null ) {
         System.out.println("topic: when callback msg is null") ;
      }
      for( Callback cb : callbackList ) {
         cb.invokeMet( msg.getObj() ) ;
      }
   }

   /**
    *  如果消息队列不为空，则一直调用
    *  @method run
    */
   public void run() {
      while(true) {
         synchronized(msgQueue) {
            if( !msgQueue.isEmpty() ) {
               this.call( msgQueue.poll() ) ;
               /*System.out.println(msgQueue.size()) ;
               Message msg = msgQueue.poll() ;
               if(msg.getObj() == null ) {
                  System.out.println("topic: when pop queue, msg is null") ;
               }*/
            }
         }
      }
   }
}

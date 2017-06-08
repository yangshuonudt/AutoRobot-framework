package core.asyncomm ;

import java.util.* ;
import java.lang.reflect.* ;

public class TopicManager{

   ArrayList<Topic> topicList = new ArrayList<Topic>() ;

   private static TopicManager topicManager ;
   private TopicManager() {
   }
   public synchronized static TopicManager getTopicManager() {
      if( topicManager == null ) {
         topicManager = new TopicManager() ;
      }
      return topicManager ;
   }

   /**
    *  往<name>topic上发送一条消息
    *  @method pubHandler
    *  @param   [description]
    *  @param   [description]
    */
   public void pubHandler( String name, Object[] objList ) {
      int index = this.getTopic(name) ;
      if( index >= 0 ) {
         topicList.get(index).addMsg(objList) ;
      }
      else {
         System.out.println("没有名为" + name + "的topic，无法发布消息！") ;
      }
   }
   public void pubHandler( String name ) {
      Object[] objList = new Object[0] ;
      this.pubHandler( name, objList ) ;
   }

   /**
    *  订阅<name>topic
    *  @method subHandler
    *  @param   [description]
    *  @param   [description]
    */
   public void subHandler( String name, Class c, String metName, Class[] paraList ) {
      if( this.getTopic(name) < 0 ){
         this.createTopic(name) ;
      }
      int index = this.getTopic(name) ;
      this.topicList.get(index).addCallBack( c, metName, paraList ) ;
   }
   public void subHandler( String name, Class c, String metName ) {
      Class[] paraList = new Class[0] ;
      this.subHandler( name, c, metName, paraList ) ;
   }

   /**
    *  创建一个名为<name>的topic
    *  @method createTopic
    *  @param  name [description]
    */
   public void createTopic( String name ) {
      Topic t = new Topic(name) ;
      this.topicList.add(t) ;
      Thread thread = new Thread(t) ;
      thread.start() ;
   }

   /**
    *  是否存在某一个名称为<name>的topic
    *  @method getTopic
    *  @param  name [description]
    *  @return [description]
    */
   public int getTopic( String name ) {
      for( int i = 0 ; i < topicList.size() ; i ++ ){
         if( topicList.get(i).getName() == name ) {
            return i ;
         }
      }
      return -1 ;
   }

}

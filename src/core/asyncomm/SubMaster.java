package core.asyncomm ;

import java.util.* ;
import java.lang.reflect.* ;

public class SubMaster{
   public static void subscribe( String name, Class c, String metName, Class[] paraList ) {
      TopicManager.getTopicManager().subHandler( name, c, metName, paraList ) ;
   }
   public static void subscribe( String name , Class c, String metName ) {
      TopicManager.getTopicManager().subHandler( name, c, metName )  ;
   }
}

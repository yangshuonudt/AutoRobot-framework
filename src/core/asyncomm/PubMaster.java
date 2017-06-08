package core.asyncomm ;
import java.util.* ;
import java.lang.reflect.* ;

public class PubMaster {
   public static void publish( String name, Object[] objList) {
      TopicManager.getTopicManager().pubHandler(name, objList) ;
   }
   public static void publish( String name ) {
      TopicManager.getTopicManager().pubHandler(name) ;
   }
}

package core.asyncomm ;

import java.util.* ;
import java.lang.reflect.* ;

public class Message{
   private  Object[] objList ;

   public Message( Object[] _objList ){
      this.setObj(_objList) ;
   }

   public void setObj( Object[] _objList ){
      if ( _objList == null ) {
         System.out.println("message: when setobj, it is null") ;
      }
      if( _objList != null ) {
         this.objList = new Object[ _objList.length ] ;
         this.objList = Arrays.copyOf( _objList, _objList.length ) ;
      }
   }

   public Object[] getObj(){
      if ( this.objList == null ) {
         System.out.println("message: when getobj, it is null" ) ;
      }
      return this.objList ;
   }
}

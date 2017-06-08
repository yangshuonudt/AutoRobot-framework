package core.asyncomm ;
import java.util.* ;
import java.lang.reflect.* ;

public class Callback{
   Method met ;
   Object instance ;
   public Callback( Class c, String metName, Class[] paraList ) {
      try{
         this.instance = c.newInstance() ;
         if( paraList.length <= 0 ){
            this.met = c.getMethod(metName) ;
         }
         else {
            this.met = c.getMethod( metName, paraList ) ;
         }
      }catch( InstantiationException ex ){
         System.out.println("无法实例化") ;
      }catch( NoSuchMethodException ex ){
         System.out.println("找不到此方法") ;
      }catch (IllegalAccessException ex) {
			System.out.println("没有权限调用此方法");
		}
   }
   /**
    *  参数不可能为null
    *  @method invokeMet
    *  @param  objList [description]
    */
   public void invokeMet( Object[] objList ){
      try{
         if( objList.length <= 0 ){
            this.met.invoke(this.instance) ;
         }
         else {
            this.met.invoke( this.instance, objList ) ;
         }
      }catch( InvocationTargetException ex ){
         System.out.println("调用此方法时发生意外"+ ex.getTargetException() ) ;
      }
      catch (IllegalAccessException ex) {
			System.out.println("没有权限调用此方法");
		}
   }
}

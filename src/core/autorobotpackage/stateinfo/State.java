/******************************************************
author : yangsen
time   : 2016-04-20, Wensday

name  : State
type  : interface
package :autorobotpackage.stateinfo
version: 0.1
description : 环境信息类和机器人状态信息类都要实现这个接口
为了方便供外部调用状态类的方法。
******************************************************/
package core.autorobotpackage.stateinfo ;

import java.util.* ;

public class State {
   private static Map<String , Object> infoTable = new HashMap<String , Object>() ;

   /**
    *  [设置一个参数]
    *  @method setData
    *  @param  name[参数的名称]
    *  @param  arg [参数的值]
    */
   public static void setData( String name , Object arg ) {
      infoTable.put( name , arg ) ;
   }

   /**
    *  [删除一个参数]
    *  @method deleteData
    *  @param  name [参数名称]
    */
   public static void deleteData( String name ) {
      infoTable.remove(name) ;
   }

   /**
    *  [获取一个参数]
    *  @method getData
    *  @param  name [参数名称]
    *  @return [参数值]
    */
   public static Object getData ( String name ) {
      return infoTable.get(name) ;
   }
}

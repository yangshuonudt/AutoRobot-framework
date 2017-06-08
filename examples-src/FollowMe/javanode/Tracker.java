/**
 *  控制机器人走到指定位置
 *  节点名字：tracker
 *  服务名字：getposition
 *  服务类型：hardwarecontrol/ballpoi
 *
 *  服务名字：adjustpos
 *  服务类型：Empty
 *
 *  话题名字：stareredball
 *  话题类型：std_msgs/String
 *
 *  单例模式
 */
package FollowMe.javanode ;
import java.util.* ;
import javax.json.Json;
import javax.websocket.Session;

import core.autorobotengine.rosproxy.RosProxy;
import edu.wpi.rail.jrosbridge.*;
import edu.wpi.rail.jrosbridge.callback.*;
import edu.wpi.rail.jrosbridge.handler.*;
import edu.wpi.rail.jrosbridge.messages.*;
import edu.wpi.rail.jrosbridge.services.*;
import edu.wpi.rail.jrosbridge.primitives.*;

public class Tracker {
   RosProxy rp = RosProxy.getRosProxy() ;
   private static Tracker tracker ;
   Service getPoiService , adjService ;
   Topic stareBallTopic ;

   private Tracker () {
      this.rp.getRos( "127.0.0.1" , 9090 ) ;
      this.rp.connectRos() ;
      this.getPoiService = new Service( RosProxy.ros , "/getposition" , "messagecollect/ballpoi" ) ;
      this.adjService = new Service( RosProxy.ros , "/adjustpos" , "std_srvs/Empty" );
      this.stareBallTopic = new Topic( RosProxy.ros , "/stareredball" , "std_msgs/String" ) ;
   }

   public static synchronized Tracker getTracker() {
      if ( tracker == null ) {
         tracker = new Tracker() ;
      }
      return tracker ;
   }

   /**
    *  [盯着目标]
    *  @method stareRedBall
    *  @param  cmd [open:打开，close:关闭]
    *  @return [description]
    */
   public void stareRedBall( String cmd ) {
      Message toSend = new Message( "{\"data\": \"" + cmd + "\"}" ) ;
      this.stareBallTopic.publish(toSend) ;
   }

   /**
    *  调整身姿
    *  @method adjustPostion
    */
   public void adjustPostion() {
      ServiceRequest request = new ServiceRequest("{}") ;
      ServiceResponse response = this.adjService.callServiceAndWait(request) ;
   }

   /**
    *  获取红球的位置
    */
   public ArrayList getRedBallPosition() {
      try{
         Thread.sleep(100) ;
      }catch( Exception e ) {
         e.printStackTrace() ;
      }
      ServiceRequest request = new ServiceRequest("{}") ;
      ServiceResponse response = this.getPoiService.callServiceAndWait(request) ;
      String str = response.toString() ;
      str = str.replace( "{" , "" ) ;
      str = str.replace( "}" , "" ) ;
      str = str.replace( "\"" , "" ) ;
      String[] s = str.split(":") ;
      String[] num = s[1].split(",") ;
      return new ArrayList( Arrays.asList( Double.valueOf(num[0]) , Double.valueOf(num[1]) , Double.valueOf(num[2]) )) ;
   }

   public boolean findRedBall() {
      ArrayList poi = getRedBallPosition() ;
      System.out.println("tracker:" + poi.get(0)) ;
      System.out.println("tracker:" + poi.get(1)) ;
      System.out.println("tracker:" + poi.get(2))  ;
      if ( poi.get(0) == 0 || (double)poi.get(1) == 0 || (double)poi.get(2) ==0 ) {
         return false ;
      }
      else {
         return true ;
      }
   }
   public static void main( String[] args ) {
      Tracker tracker = Tracker.getTracker() ;
      tracker.stareRedBall("open") ;
      for ( int i = 0 ; i < 10 ; i ++ ) {
         System.out.println( tracker.findRedBall() ) ;
         try{
            Thread.sleep(1000) ;
         }catch ( InterruptedException e ) {
            e.printStackTrace() ;
         }
         /*ArrayList t = tracker.getRedBallPosition() ;
         System.out.println(t.get(0)) ;
         System.out.println(t.get(1)) ;
         System.out.println(t.get(2)) ;
         System.out.println() ;
         System.out.println() ;*/
      }
      tracker.adjustPostion();
      tracker.stareRedBall("close") ;
   }
}

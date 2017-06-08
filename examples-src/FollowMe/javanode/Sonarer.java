/**
 *  控制机器人说话
 *  节点名字：sonaer
 *  服务名字：openSonar
 *  服务类型：Empty
 *
 *  服务名字:getSonar
 *  服务类型：sonar
 *
 *  服务名字：closeSonar
 *  服务类型:Empty
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

public class Sonarer{
   RosProxy rp = RosProxy.getRosProxy() ;
   private static Sonarer sonarer ;
   Service openSonarService , getSonarService , closeSonarService ;

   private Sonarer() {
      this.rp.getRos( "127.0.0.1" , 9090 ) ;
      this.rp.connectRos() ;
      this.openSonarService = new Service( RosProxy.ros , "/openSonar" , "std_srvs/Empty" ) ;
      this.getSonarService = new Service( RosProxy.ros , "/getSonar" , "messagecollect/sonar" ) ;
      this.closeSonarService = new Service( RosProxy.ros , "/closeSonar" , "std_srvs/Empty" ) ;
   }

   public static synchronized Sonarer getSonarer() {
      if( sonarer == null ) {
         sonarer = new Sonarer() ;
      }
      return sonarer ;
   }

   public void openSonar() {
      ServiceRequest request = new ServiceRequest( "{}" ) ;
      ServiceResponse response = this.openSonarService.callServiceAndWait(request) ;
   }
   public void closeSonar() {
      ServiceRequest request = new ServiceRequest( "{}" ) ;
      ServiceResponse response = this.closeSonarService.callServiceAndWait(request) ;
   }
   public double getSonar( String dir ) {

      long startTime = System.currentTimeMillis() ;
      System.out.println( "Sonarer 获取声呐： " + startTime + "ms" );

      ServiceRequest request = new ServiceRequest( "{\"dir\": \"" + dir + "\"}" ) ;
      ServiceResponse response = this.getSonarService.callServiceAndWait(request) ;
      System.out.println("Sonarer: receive from sonar.py " + response.toString() ) ;
      String ans = response.toString().substring(8 , 16);

      long endTime = System.currentTimeMillis() ;
      System.out.println( "sonar 获取完毕： " + endTime + "ms" );

      return Double.valueOf(ans) ;
   }

   /*public static void main( String[] args ) {
      Sonarer sonarer = Sonarer.getSonarer() ;
      sonarer.openSonar() ;
      for ( int i = 0 ; i < 10 ; i ++ ) {
         try {
            Thread.sleep(1000) ;
         }catch( InterruptedException e ) {
            e.printStackTrace() ;
         }
         System.out.println ( "Sonarer: get sonar of left :" + sonarer.getSonar("left") );
         System.out.println ( "Sonarer: get sonar of right : " + sonarer.getSonar("right") );
      }
      sonarer.closeSonar() ;
   }*/

}

package FollowMe.agent ;

import jade.core.* ;
import jade.core.behaviours.* ;
import jade.lang.acl.* ;
import java.io.* ;
import java.util.* ;

import core.autorobotpackage.mas.PlannerAgent;

public class Planner extends PlannerAgent {
   boolean isBall ;          //是否有红球
   boolean isBarrier ;       //是否有障碍

   boolean killSelf ;

   /*public void setup () {
      //operateSonar("open") ;
      pause(1000) ;
      long startTime = System.currentTimeMillis() ;
      System.out.println( "planner 请求声呐： " + startTime + "ms" );
      operateSonar("left") ;
      long endTime = System.currentTimeMillis() ;
      System.out.println( "planner 拿到声呐： " + endTime + "ms" );

      operateSonar("close") ;
   }*/

   public void setup() {

      System.out.println("Planner : Adding waker behaviour") ;

      isBall = false ;
      isBarrier = false ;

      killSelf = false ;

      speak("stand init" , false) ;
      goToPose("StandInit") ;
      speak("begin wander" , false) ;
      wander() ;
      speak("open sonar" , false) ;
      operateSonar("open") ;
      speak("open tracker" , false) ;
      operateTracker("open") ;

      int count = 0 ;
      while(true) {
         count += 1;
         if ( count >= 100 ) {
            speak("time out" , false) ;
            stopAll();
            break ;
         }

         if ( !isBall ) {
         //   speak("search redball" , true) ;
            if ( findRedBall() ){
               speak("find the red ball" , false) ;
               isBall = true ;
            }
         }

         //speak("search obstacles",true) ;
         boolean fb = findBarrier();
         if ( fb ) {
            speak("find obstacles" , false) ;
            stopWander() ;
            detectAndAvoid() ;
            wander() ;
            isBarrier = true ;
         }
         if (isBall && isBarrier ) {
            stopAll() ;
            break ;
         }
         pause(20) ;
      }

      if( isBall ) speak("find redball" , true) ;
      else speak("not find redball" , true) ;
      if( isBarrier ) speak("find barrier" ,true ) ;
      else speak( "not find barrier" , true) ;

      speak("open tracker" , false) ;
      operateTracker("open") ;
      if(isBarrier) {
         speak("open sonar" , false) ;
         operateSonar("open") ;
      }

      while(true) {
         enterScene() ;
         if ( killSelf ) {
            break ;
         }
      }

      operateTracker("close") ;
      operateSonar("close") ;
   }

   /**
    *  根据目标的有无，障碍的有无选择场景
    *  @method enterScene
    */
   private void enterScene() {

      if ( !isBall ) {

         speak("can not find, please show me target" , false) ;

         int waitCount = 0 ;
         while( true) {
            waitCount += 1 ;
            if ( waitCount >= 50 ) {
               speak( "can not get target, stop the system" , false) ;
               killSelf = true ;
               break ;
            }
            if( findRedBall() ) {
               isBall = true ;
               break ;
            }
            pause(20) ;
         }
      }
      else {

         while(true) {
            boolean fr = findRedBall();
            if (fr) {
               ArrayList rpoi = operateTracker("get") ;
               walkAlong(rpoi) ;
            }else {
               stopWalkAlong() ;
               isBall = false ;
               break ;
            }
            if(findBarrier()) {
               speak("find obstacles" , true) ;
               stopWalkAlong() ;
               detectAndAvoid() ;
            }
         }

      }
   }

   public void pause( int msc ) {
      try {
         Thread.sleep(msc) ;
      }catch(InterruptedException e) {
         e.printStackTrace() ;
      }
   }

   private void stopAll() {
      speak("stop all" , false) ;
      stopWander() ;
      operateSonar("close") ;
      operateTracker("close") ;
   }


   private void detectAndAvoid() {
      double left = operateSonar("left") ;
      double right = operateSonar("right") ;
      if( left < 0.3 ) {
         speak("avoid left" , false) ;
         avoid("avoidLeft" , true) ;
         return ;
      }
      if ( right < 0.3 ) {
         speak("avoid right" , false) ;
         avoid("avoidRight" , true) ;
         return ;
      }
   }

   /**
    * 操作声呐，open，close，get
    *  @method operateSonar
    *  @param  str [description]
    *  @return [description]
    */
   private double operateSonar( String str ) {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("sensor" , AID.ISLOCALNAME) ) ;
      msg.setConversationId("sonar") ;
      msg.setContent(str) ;
      send(msg) ;
      if( str.equals("left") || str.equals("right") ) {
         MessageTemplate mt = MessageTemplate.MatchConversationId("sonar") ;
         ACLMessage reply = blockingReceive(mt) ;
         if ( reply != null ) {
            String content = reply.getContent() ;
            return Double.valueOf(content) ;
         }
      }
      return 0 ;
   }

   /**
    *  操作tracker ： open,close,get,adjust
    *  @method operateTracker
    *  @param  str [description]
    *  @return [description]
    */
   private ArrayList operateTracker( String str ) {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("sensor" , AID.ISLOCALNAME ) ) ;
      msg.setConversationId("tracker") ;
      msg.setContent(str) ;
      send(msg) ;
      if ( str.equals("get") ) {
         MessageTemplate mt = MessageTemplate.and(
                              MessageTemplate.MatchConversationId("tracker-get"),
                              MessageTemplate.MatchPerformative( ACLMessage.PROPOSE )
                              ) ;
         ACLMessage reply = blockingReceive(mt) ;
         if ( reply != null ) {
            try {
               Object content = reply.getContentObject() ;
               System.out.println( "Planner: get from sensor tracker type is:" + content.getClass().getName() ) ;
               return (ArrayList) content ;
            } catch( UnreadableException e ) {
               e.printStackTrace() ;
            }
         }
      }
      if ( str.equals("adjust") ) {
         MessageTemplate mt = MessageTemplate.and(
                              MessageTemplate.MatchConversationId("tracker-adj") ,
                              MessageTemplate.MatchPerformative( ACLMessage.PROPOSE )
                              ) ;
         ACLMessage reply = blockingReceive(mt) ;
      }
      return null ;
   }

/*   private boolean findTarget( String str ) {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("sensor" , AID.ISLOCALNAME) ) ;
      MessageTemplate mt ;
      if ( str.equals("redBall") ) {
         msg.setConversationId("tracker") ;
         mt = MessageTemplate.and(
                              MessageTemplate.MatchConversationId("tracker-is") ,
                              MessageTemplate.MatchPerformative( ACLMessage.PROPOSE )
                              ) ;
      }
      else if ( str.equals("barrier") ) {
         msg.setConversationId("sonar") ;
         mt = MessageTemplate.and(
                              MessageTemplate.MatchConversationId("barrier-is") ,
                              MessageTemplate.MatchPerformative( ACLMessage.PROPOSE )
                              ) ;
      }
      msg.setContent("is") ;
      send(msg) ;
      ACLMessage reply = blockingReceive(mt);
      if ( reply != null ) {
         String content = reply.getContent() ;
         if ( content.equals("yes") ) {
            return true ;
         }
         return false ;
      }
      return false ;

   }
*/
   private boolean findBarrier(){
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("sensor" , AID.ISLOCALNAME) ) ;
      msg.setConversationId("sonar") ;
      msg.setContent("is") ;
      send(msg) ;
      MessageTemplate mt = MessageTemplate.and(
                           MessageTemplate.MatchConversationId("barrier-is") ,
                           MessageTemplate.MatchPerformative( ACLMessage.PROPOSE )
                           ) ;
      ACLMessage reply = blockingReceive(mt) ;
      if ( reply != null ) {
         String content = reply.getContent() ;
         if ( !content.equals("no") ) {
            System.out.println("palnner: find barrier") ;
            return true ;
         }
         return false ;
      }
      return false ;
   }

   private boolean findRedBall() {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("sensor" , AID.ISLOCALNAME ) ) ;
      msg.setConversationId("tracker") ;
      msg.setContent("is") ;
      send(msg) ;
      MessageTemplate mt = MessageTemplate.and(
                           MessageTemplate.MatchConversationId("tracker-is") ,
                           MessageTemplate.MatchPerformative( ACLMessage.PROPOSE )
                           ) ;
      ACLMessage reply = blockingReceive(mt) ;
      if ( reply != null ) {
         String content = reply.getContent() ;
         if ( !content.equals("no") ) {
            System.out.println("palnner: find redball") ;
            return true ;
         }
         return false ;
      }
      return false ;
   }
   private void goToPose( String content ) {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME) ) ;
      msg.setConversationId("pose") ;
      msg.setContent(content) ;
      send(msg) ;
      MessageTemplate mt = MessageTemplate.MatchConversationId("pose") ;
      ACLMessage reply = blockingReceive(mt) ;
   }

   private void speak( String content , boolean block ) {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME) );
      if(block) {
         msg.setConversationId("speak-yes") ;
      }
      else {
         msg.setConversationId("speak-no") ;
      }
      msg.setContent(content) ;
      send(msg) ;
      if (block) {
         MessageTemplate mt = MessageTemplate.MatchConversationId("speak") ;
         ACLMessage reply = blockingReceive(mt) ;
      }
   }
   private void walkAlong( ArrayList poi ) {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME) );
      msg.setConversationId("walk-along") ;
      try {
         msg.setContentObject(poi) ;
      } catch ( IOException e ) {
         e.printStackTrace() ;
      }
      send(msg) ;
   }
   private void stopWalkAlong() {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME ) ) ;
      msg.setConversationId("walk-along") ;
      try {
         msg.setContentObject("stopWalkAlong") ;
         send(msg) ;
      }catch( IOException e ) {
         e.printStackTrace() ;
      }
   }
   private void walkTo( ArrayList poi , boolean block ) {

      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME) );
      if (block) {
         msg.setConversationId("walk-yes") ;
      }
      else {
         msg.setConversationId("walk-no") ;
      }
      try {
         msg.setContentObject(poi) ;
      } catch ( IOException e ) {
         e.printStackTrace() ;
      }
      send(msg) ;

      if (block) {
         MessageTemplate mt = MessageTemplate.MatchConversationId("walk") ;

         ACLMessage reply = blockingReceive(mt) ;
         if( reply != null ) {
            String content = reply.getContent() ;
            System.out.println( "Planner receive ACL WALKTO-REPLY: " + content ) ;
         }
      }
   }
   private void avoid( String dir , boolean block) {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME ) ) ;
      if (block) {
         msg.setConversationId("walk-yes") ;
      }
      else {
         msg.setConversationId("walk-no") ;
      }
      try {
         msg.setContentObject(dir) ;
         send(msg) ;
      }catch( IOException e ) {
         e.printStackTrace() ;
      }
      if (block) {
         MessageTemplate mt = MessageTemplate.MatchConversationId("avoid") ;
         ACLMessage reply = blockingReceive(mt) ;
         if( reply != null ) {
            String content = reply.getContent() ;
            System.out.println( "Planner receive ACL WALKTO-REPLY: " + content ) ;
         }
      }
   }
   private void wander() {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME) );
      msg.setConversationId("walk") ;
      try {
         msg.setContentObject( "wander" ) ;
         send(msg) ;
      } catch ( IOException e ) {
         e.printStackTrace() ;
      }
   }
   private void stopWander() {
      ACLMessage msg = new ACLMessage( ACLMessage.REQUEST ) ;
      msg.addReceiver( new AID("actor" , AID.ISLOCALNAME) );
      msg.setConversationId("walk") ;
      try {
         msg.setContentObject( "stopwander" ) ;
         send(msg) ;
      } catch ( IOException e ) {
         e.printStackTrace() ;
      }
   }

}

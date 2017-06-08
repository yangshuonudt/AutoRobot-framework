package FollowMe.agent ;

import jade.core.* ;
import jade.core.behaviours.* ;
import jade.lang.acl.* ;
import java.io.* ;
import java.util.* ;

import FollowMe.javanode.Sonarer;
import FollowMe.javanode.Tracker;
import core.autorobotpackage.mas.SensorAgent;

public class Sensor extends SensorAgent {
   Sonarer sonarer ;
   Tracker tracker ;

   public void setup() {
      sonarer = Sonarer.getSonarer() ;
      tracker = Tracker.getTracker() ;
      addSonar() ;
      addTracker() ;
   }
   public void addTracker() {
      addBehaviour( new CyclicBehaviour() {
         public void action() {
            MessageTemplate mt = MessageTemplate.and(
                                 MessageTemplate.MatchConversationId("tracker") ,
                                 MessageTemplate.MatchPerformative( ACLMessage.REQUEST )
            ) ;
            ACLMessage msg = myAgent.receive(mt) ;
            if ( msg != null ) {
               String content = msg.getContent() ;
               System.out.println( "Sensro: receive tracker msg: " + content ) ;
               if ( content.equals("open") ) {
                  tracker.stareRedBall("open") ;
               } else if ( content.equals("close") ) {
                  tracker.stareRedBall("close") ;
               } else if ( content.equals("get") ) {
                  ArrayList poi = tracker.getRedBallPosition() ;
                  ACLMessage reply = msg.createReply() ;
                  reply.setPerformative( ACLMessage.PROPOSE );
                  reply.setConversationId("tracker-get") ;
                  try {
                     reply.setContentObject(poi) ;
                  } catch ( IOException e ) {
                     e.printStackTrace() ;
                  }
                  send(reply) ;
               } else if ( content.equals("adjust") ) {
                  tracker.adjustPostion() ;
                  ACLMessage reply = msg.createReply() ;
                  reply.setPerformative( ACLMessage.PROPOSE ) ;
                  reply.setConversationId("tracker-adj") ;
                  reply.setContent("") ;
                  send(reply) ;
               } else if ( content.equals("is") ) {
                  ACLMessage reply = msg.createReply() ;
                  reply.setPerformative( ACLMessage.PROPOSE ) ;
                  reply.setConversationId("tracker-is") ;
                  if ( tracker.findRedBall() ) {
                     reply.setContent("yes") ;
                  }
                  else {
                     reply.setContent("no") ;
                  }
                  send(reply) ;
               }
            }
            else {
               block() ;
            }
         }
      }) ;
   }
   public void addSonar() {
      addBehaviour( new CyclicBehaviour() {
         public void action() {
            MessageTemplate mt = MessageTemplate.and(
                                 MessageTemplate.MatchConversationId("sonar"),
                                 MessageTemplate.MatchPerformative( ACLMessage.REQUEST )
            ) ;
            ACLMessage msg = myAgent.receive(mt) ;
            if( msg != null ) {

               long startTime = System.currentTimeMillis() ;
               System.out.println( "sensor 收到请求： " + startTime + "ms" );

               String content = msg.getContent() ;
               System.out.println( "Sensor: Receive sonar msg: " + content ) ;
               if ( content.equals("open") ) {
                  sonarer.openSonar() ;
               } else if ( content.equals("close") ) {
                  sonarer.closeSonar() ;
               } else if ( content.equals("is") ) {
                  double left , right ;
                  int count = 0 ;
                  for ( int i = 0 ; i < 2 ; i ++ ) {
                     left = sonarer.getSonar("left") ;
                     right = sonarer.getSonar("right") ;
                     if ( left < 0.3 || right < 0.3 ) {
                        count ++ ;
                     }
                     try{
                        Thread.sleep(10) ;
                     } catch( InterruptedException e ) {
                        e.printStackTrace() ;
                     }
                  }
                  ACLMessage reply = msg.createReply() ;
                  reply.setPerformative( ACLMessage.PROPOSE ) ;
                  reply.setConversationId("barrier-is") ;
                  if( count >= 2 ) {
                     reply.setContent("yes") ;
                  }
                  else {
                     reply.setContent("no") ;
                  }
                  send(reply) ;
               } else{
                  double dis = 0 ;
                  if ( content.equals("left") ) {
                     long endTime = System.currentTimeMillis() ;
                     System.out.println( "sensor 开始处理请求： " + endTime + "ms" );
                     dis = sonarer.getSonar("left") ;
                     endTime = System.currentTimeMillis() ;
                     System.out.println( "sensor 处理请求完毕： " + endTime + "ms" );
                  }
                  else if ( content.equals("right") ) {
                     dis = sonarer.getSonar("right") ;
                  }

                  long endTime = System.currentTimeMillis() ;
                  System.out.println( "sensor 发送声呐： " + endTime + "ms" );

                  ACLMessage reply = msg.createReply() ;
                  reply.setPerformative( ACLMessage.PROPOSE ) ;
                  reply.setConversationId("sonar") ;
                  reply.setContent( String.valueOf(dis) ) ;
                  myAgent.send(reply) ;

                  endTime = System.currentTimeMillis() ;
                  System.out.println( "sensor 完成发送： " + endTime + "ms" );
               }
            }
            else {
               block() ;
            }
         }
      }) ;
   }
}

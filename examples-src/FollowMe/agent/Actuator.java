package FollowMe.agent ;

import jade.core.* ;
import jade.core.behaviours.* ;
import jade.lang.acl.* ;
import java.io.* ;
import java.util.* ;

import FollowMe.javanode.Poser;
import FollowMe.javanode.Speaker;
import FollowMe.javanode.Walker;
import core.autorobotpackage.mas.ActuatorAgent;
import core.autorobotpackage.mas.event.SensorEvent;


public class Actuator extends ActuatorAgent{
   Speaker speaker ;
   Walker walker ;
   Poser poser ;

   public void setup() {
      speaker = Speaker.getSpeaker() ;
      walker = Walker.getWalker() ;
      poser = Poser.getPoser() ;
      addSpeak() ;
      addWalk() ;
      addPose() ;
   }
   public void addPose() {
      addBehaviour( new CyclicBehaviour(){
         public void action(){
            MessageTemplate mt = MessageTemplate.and(
                                 MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
                                 MessageTemplate.MatchConversationId("pose")
                                 ) ;
            ACLMessage msg = myAgent.receive(mt) ;
            if ( msg != null ) {
               String content = msg.getContent() ;
               System.out.println("Actuator: receive acl pos content :" + content ) ;
               poser.goToPose(content) ;
               ACLMessage reply = msg.createReply() ;
               reply.setPerformative( ACLMessage.PROPOSE );
               reply.setConversationId("pose") ;
               reply.setContent("") ;
               send(reply) ;
            }
            else {
               block() ;
            }
         }
      }) ;
   }
   public void addSpeak() {
      addBehaviour( new CyclicBehaviour() {

         public void action() {
            MessageTemplate mt1 = MessageTemplate.and(
                                    MessageTemplate.MatchConversationId( "speak-yes" ) ,
                                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
                                 ) ;
            MessageTemplate mt2 = MessageTemplate.and(
                                    MessageTemplate.MatchConversationId( "speak-no" ) ,
                                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST)
                                 ) ;
            MessageTemplate mt = MessageTemplate.or( mt1 , mt2 ) ;
            ACLMessage msg = myAgent.receive(mt) ;
            if ( msg != null ) {
               String content = msg.getContent() ;
               //System.out.println( "Actuator: receive ACL content calss is : " + content.getClass().getName() ) ;
               System.out.println( "Actuator: receive ACL SPEAK content : " + content ) ;
               speaker.say(content) ;
               String id = msg.getConversationId() ;
               if ( id == "speak-yes" ) {
                  ACLMessage reply = msg.createReply() ;
                  reply.setPerformative( ACLMessage.PROPOSE ) ;
                  reply.setConversationId("speak") ;
                  reply.setContent("") ;
                  send(reply) ;
               }
            }
            else {
               block() ;
            }
         }

      } ) ;
   }

   public void addWalk() {
      addBehaviour( new CyclicBehaviour(){
         public void action() {
            MessageTemplate mt1 = MessageTemplate.and(
                                    MessageTemplate.MatchConversationId( "walk" ) ,
                                    MessageTemplate.MatchPerformative( ACLMessage.REQUEST )
            ) ;
            MessageTemplate mt2 = MessageTemplate.and(
                                    MessageTemplate.MatchConversationId( "walk-yes" ) ,
                                    MessageTemplate.MatchPerformative( ACLMessage.REQUEST )
            ) ;
            MessageTemplate mt3 = MessageTemplate.and(
                                    MessageTemplate.MatchConversationId( "walk-no" ) ,
                                    MessageTemplate.MatchPerformative( ACLMessage.REQUEST )
            ) ;
            MessageTemplate mt4 = MessageTemplate.and(
                                    MessageTemplate.MatchConversationId( "walk-along" ) ,
                                    MessageTemplate.MatchPerformative( ACLMessage.REQUEST )
            ) ;

            MessageTemplate mt6 = MessageTemplate.or (mt1 , mt2) ;
            MessageTemplate mt7 = MessageTemplate.or (mt6 , mt3) ;
            MessageTemplate mt  = MessageTemplate.or (mt7 , mt4) ;

            ACLMessage msg = myAgent.receive(mt) ;
            if ( msg != null ) {
               String id = msg.getConversationId() ;
               try {
                  Object content = msg.getContentObject() ;
                  System.out.println( "Actuator: Receive walk msg , class name is:" + content.getClass().getName() ) ;
                  if( content.getClass().getName() == "java.util.ArrayList" ) {
                     System.out.println( "Actuator: receive ACL WALKTO content : " + content ) ;
                     if ( id.startsWith("walk-along") ) {
                        walker.walkAlong( (ArrayList)content ) ;
                     }else {
                        walker.walkTo( (ArrayList)content ) ;
                     }
                     if ( id.endsWith("yes") ) {
                        ACLMessage reply = msg.createReply() ;
                        reply.setPerformative( ACLMessage.PROPOSE ) ;
                        reply.setConversationId("walk") ;
                        reply.setContent("actor:YwalkTo") ;
                        myAgent.send(reply) ;
                     }
                  }
                  else if ( content.getClass().getName() == "java.lang.String" ) {
                     System.out.println( "Actuator: receive ACL WALKTO content : " + content ) ;
                     if( ((String)content).equals("wander") ) {
                        walker.wander() ;
                     }
                     else if ( ((String)content).equals("stopwander") ) {
                        walker.stopWander() ;
                     }
                     else if ( ((String)content).equals("stopWalkAlong") ) {
                        walker.stopWalkAlong() ;
                     }
                     else if ( ((String)content).equals("avoidLeft") ||
                               ((String)content).equals("avoidRight") ) {
                        if ( ((String)content).equals("avoidLeft")) {
                           walker.avoid("left") ;
                        }
                        else {
                           walker.avoid("right") ;
                        }
                        if ( id == "walk-yes" ) {
                           ACLMessage reply = msg.createReply() ;
                           reply.setPerformative( ACLMessage.PROPOSE ) ;
                           reply.setConversationId("avoid") ;
                           reply.setContent("actor:YwalkTo") ;
                           myAgent.send(reply) ;
                        }
                     }
                  }
               } catch ( UnreadableException e ) {
                  e.printStackTrace() ;
               }
            }
            else {
               block() ;
            }
          }
      }) ;
   }
}

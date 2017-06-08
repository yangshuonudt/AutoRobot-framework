/******************************************************
author : yangsen
time   : 2016-04-12, Sauturday

name  : ReceiveAclMsg
type  : class
package :autorobotpackage.mas.basicoperation
version: 0.1

description: 接收acl消息
******************************************************/
package core.autorobotpackage.mas.basicoperation ;

import jade.core.* ;
import jade.lang.acl.* ;

public class ReceiveAclMsg {

   ACLMessage msg ;
   Agent receiver ;
   MessageTemplate mt ;
   boolean hasMT ;

   /**
   * [构造函数，传入消息的接收者和要接收的消息类型]
   * @method ReceiveAclMsg
   * @param rec          [接收消息的agent]
   * @param perfomative  [消息的类型]
   * @return
   */
   public ReceiveAclMsg( Agent rec , int perfomative ) {
      this.receiver = rec ;
      if( perfomative != -1 ) {
         this.mt = MessageTemplate.MatchPerformative(perfomative) ;
         this.hasMT = true ;
      }
      else {
         this.hasMT = false ;
      }
   }
   public ReceiveAclMsg( Agent rec ) {
      this( rec , -1 ) ;
   }

   /**
    * [接收消息，如果限制了消息类型，则接收特定类型的消息.]
    * @method receiveMsg
    */
   public ACLMessage receiveMsg() {
      if(hasMT){
         this.msg = receiver.receive(mt) ;
      }
      else {
         this.msg = receiver.receive() ;
      }
      return this.msg ;
   }

   public ACLMessage getMsg() {
      return this.msg ;
   }
}

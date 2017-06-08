/******************************************************

import jade.core.* ;
import jade.lang.acl.* ;
author : yangsen
time   : 2016-04-12, Sauturday

name  : SendAclMsg
type  : class
package :autorobotpackage.mas.basicoperation
version: 0.1

description: 发送ACL消息
******************************************************/
package core.autorobotpackage.mas.basicoperation ;

import jade.core.* ;
import jade.lang.acl.* ;

public class SendAclMsg{
   ACLMessage msg ;
   Agent sender ;

   /**
    *  [构造函数，重载]
    *  @method SendAclMsg
    *  @param  sender [发送者]
    *  @param  receiver [接受者列表]
    *  @param  performative [消息类型]
    *  @param  content [消息内容]
    *  @param  ontology [本体]
    *  @param  language [语言]
    *  @return [description]
    */
   public SendAclMsg( Agent sender , AID[] receiver , int performative ,
                      String content , String ontology , String language ){
      this.msg = new ACLMessage( performative ) ;
      this.msg.setSender(sender.getAID()) ;
      for( int i = 0 ; i < receiver.length ; i ++ ) {
         this.msg.addReceiver( receiver[i] ) ;
      }
      this.msg.setOntology(ontology) ;
      this.msg.setContent(content) ;
      this.msg.setLanguage(language) ;
   }
   public SendAclMsg( Agent sender , AID[] receiver , int performative ,
                      String content , String ontology ){
      this(sender , receiver , performative , content , ontology ,"English") ;
   }
   public SendAclMsg( Agent sender , AID receiver , int performative ,
                      String content , String ontology ) {
      this(sender , new AID[]{receiver} , performative , content , ontology , "English") ;
   }

   /**
    *  [声明好各种参数后，进行发送]
    *  @method sendMsg
    *  @return [是否发送成功]
    */
   public boolean sendMsg() {
      if( this.msg == null ) {
         System.out.println("发送消息不能为空！\n") ;
         return false ;
      }
      try {
         this.sender.send(msg) ;
         return true ;
      } catch ( Exception e ) {
         e.printStackTrace() ;
         return false ;
      }
   }
}

/******************************************************
author : yangsen
time   : 2016-04-08, Monday

name  : XmlRosNode
type  : class
package :autorobotpackage.mas.xmlparser
version: 0.1

description: 记录xml文件中的节点信息,一个节点里只允许订阅
一个话题，但可以提供多个服务，每个服务要有功能描述
******************************************************/
package core.autorobotpackage.mas.xmlparser ;

import java.util.* ;

public class XmlRosNode {
   private String name ;         //节点的名字
   private String description ;  //描述节点
   private XmlRosTopic topic ;
   private ArrayList<XmlRosService> serviceList ;

   public XmlRosNode() {
      topic = new XmlRosTopic() ;
      serviceList = new ArrayList<XmlRosService>() ;
   }
   public void setName(String name) {
      this.name = name ;
   }
   public String getName() {
      return this.name ;
   }
   public void setDescription(String description) {
      this.description = description ;
   }
   public String getDescription() {
      return this.description ;
   }
   public XmlRosTopic getTopic() {
      return this.topic ;
   }
   public void setTopic(String name , String type) {
      this.topic.setName(name) ;
      this.topic.setType(type) ;
   }
   public void addService(String name , String type , String function) {
      XmlRosService xrs = new XmlRosService() ;
      xrs.setName(name) ;
      xrs.setType(type) ;
      xrs.setFunction(function) ;
      this.serviceList.add(xrs) ;
   }
   public ArrayList<XmlRosService> getServiceList() {
      return this.serviceList ;
   }
}

/******************************************************
author : yangsen
time   : 2016-04-08, Monday

name  : XmlRosNode
type  : class
package :autorobotpackage.mas.xmlparser
version: 0.1

description: 记录xml文件中的节点的topic信息
******************************************************/
package core.autorobotpackage.mas.xmlparser ;

public class XmlRosTopic {
   private String name ;
   private String type ;
   public String getName() {
      return name ;
   }
   public String getType() {
      return type ;
   }
   public void setName(String _name) {
      this.name = _name ;
   }
   public void setType(String _type) {
      this.type = _type ;
   }
}

/******************************************************
author : yangsen
time   : 2016-04-08, Monday

name  : XmlRosService
type  : class
package :autorobotpackage.mas.xmlparser
version: 0.1

description: 记录xml文件中一个节点内的service信息
******************************************************/
package core.autorobotpackage.mas.xmlparser ;

public class XmlRosService {
   private String name ;
   private String type ;
   private String function ;
   public String getName() {
      return this.name ;
   }
   public String getType() {
      return this.type ;
   }
   public String getFunction() {
      return this.function ;
   }
   public void setName(String _name) {
      this.name = _name ;
   }
   public void setType(String _type) {
      this.type = _type ;
   }
   public void setFunction(String _function) {
      this.function = _function ;
   }
}

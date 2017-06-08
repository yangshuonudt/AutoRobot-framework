/******************************************************
author : yangsen
time   : 2016-03-17, Thursday

name  : ProcessingSense
type  : Class
package :autorobotpackage.dataprocess
version: 0.1

description: This class is used for process data and call
for the web service.
******************************************************/

package  core.autorobotpackage.dataprocess;

import core.autorobotpackage.dataprocess.cloudservice.CloudService;
import core.autorobotpackage.dataprocess.cloudservice.FaceRecognize;

public class ProcessingSense {
   
   /**
    *  [返回一个实现了CloudService接口的云服务的代理]
    *  @method getCS
    *  @param  type [description]
    *  @return [description]
    */
   public CloudService getCS( String type ) {
      if( type == "FACE_DETECTION" ) {
         return new FaceRecognize() ;
      }
      else {
         return null ;
      }
   }

}

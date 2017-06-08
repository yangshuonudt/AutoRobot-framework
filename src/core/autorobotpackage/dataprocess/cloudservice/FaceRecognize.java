/******************************************************
author : yangsen
time   : 2016-03-13, Saturday

name  : ProcessingSense
type  : Class
package : AutoRobotPackage.DataProcess
version: 0.1

description: This class is used for process data and call
for the web service.
******************************************************/

package core.autorobotpackage.dataprocess.cloudservice ;

import com.facepp.http.* ;
import com.facepp.error.* ;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.*;
import java.io.File ;
import java.util.* ;

public class FaceRecognize implements CloudService {
   HttpRequests httpRequests ;

   public FaceRecognize () {
      httpRequests = new HttpRequests ( "11949a006011a2f549d7da11db7a83b2" , "9DLrIxCPnt4a6oj5IuzAQc6cL8V3XNRh" , true , true ) ;
   }

   /**
    *  [返回一张图片中的人脸信息]
    *  @method detectFace
    *  @param  str [图片的路径n]
    *  @return [JSONObject类型的人脸信息]
    */
   public JSONObject detectFace ( String str ) {
      JSONObject result = new JSONObject() ;
      try {
         result = httpRequests.detectionDetect(new PostParameters().setImg(new File(str)));
      }
      catch ( FaceppParseException e ) {
         e.printStackTrace () ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
      return result ;
   }

   /**
    *  [在一个组中创造一个person]
    *  @method createPerson
    *  @param  str [图片路径]
    *  @param  name[每个人的名字]
    */
   public void createPerson ( String str , String[] name ) {
      System.out.println("person/create") ;
      try {
         JSONObject result = this.detectFace( str ) ;
         for ( int i = 0 ; i < result.getJSONArray( "face" ).length() ; i ++ ) {
            JSONObject createperson = httpRequests.personCreate ( new PostParameters().setPersonName( name[ i ] ) ) ;
            System.out.println( createperson ) ;
         }
      }
      catch ( FaceppParseException e ) {
         e.printStackTrace () ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [给一个人添加一张图片中的人脸信息]
    *  @method addFace
    *  @param  str [图片路径]
    *  @param  name [人的名字]
    */
   public void addFace ( String str , String name ) {
      try {
         JSONObject result = this.detectFace(str) ;
         System.out.println( "person/addFace" ) ;
         for ( int i = 0 ; i < result.getJSONArray( "face" ).length() ; i ++ ) {
            httpRequests.personAddFace( new PostParameters().setPersonName( name ).setFaceId(
                                        result.getJSONArray("face").getJSONObject(i).getString("face_id"))) ;

         }
      }catch ( FaceppParseException e ) {
         e.printStackTrace() ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [设定人的tag信息]
    *  @method setInfo
    *  @param  name [人的名字]
    *  @param  tag [tag内容]
    */
   public void setInfo ( String name , String tag ) {
      try {
         System.out.println( "person/setInfo" ) ;
         httpRequests.personSetInfo( new PostParameters().setPersonName( name ).setTag( tag ) ) ;
      }catch ( FaceppParseException e ) {
         e.printStackTrace() ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [获取某个人的tag]
    *  @method getInfo
    *  @param  name [人的名字]
    *  @return [tag内容]
    */
   public String getInfo ( String name ) {
      System.out.println( "person/getInfo" ) ;
      String result = new String() ;
      try {
         result = httpRequests.personGetInfo( new PostParameters().setPersonName( name ) ).getString("tag") ;
      }catch ( FaceppParseException e ) {
         e.printStackTrace() ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
      return result ;
   }

   /**
    *  [创造一个group]
    *  @method creatGroup
    *  @param  groupName [group的名字]
    */
   public void creatGroup ( String groupName ) {
      try {
         System.out.println( "group/create" ) ;
         httpRequests.groupCreate( new PostParameters().setGroupName( groupName ) ) ;
      }catch ( FaceppParseException e ) {
         e.printStackTrace() ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [在group中添加一系列的人]
    *  @method groupAddPerson
    *  @param  groupName [目标group的名字]
    *  @param  nameList [人名的列表]
    */
   public void groupAddPerson ( String groupName , String[] namelist ) {
      try {
         System.out.println( "group/addPerson" ) ;
         httpRequests.groupAddPerson( new PostParameters().setGroupName( groupName ).setPersonName ( namelist ) ) ;
      }catch ( FaceppParseException e ) {
         e.printStackTrace() ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [设定group的tag]
    *  @method groupSetInfo
    *  @param  groupName [group的名字]
    *  @param  tag [tag内容]
    */
   public void groupSetInfo ( String groupName , String tag ) {
      try {
         System.out.println( "group/setInfo" ) ;
         httpRequests.groupSetInfo( new PostParameters().setGroupName( groupName ).setTag( tag ) ) ;
      }catch ( FaceppParseException e ) {
         e.printStackTrace() ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [获取group的tag]
    *  @method groupGetInfo
    *  @param  groupName [group名字]
    *  @return [tag内容]
    */
   public JSONObject groupGetInfo ( String groupName ) {
      JSONObject result = new JSONObject() ;
      try {
         System.out.println( "group/getInfo" ) ;
         httpRequests.groupGetInfo( new PostParameters().setGroupName( groupName ) ) ;
      }catch ( FaceppParseException e ) {
         e.printStackTrace() ;
      }catch ( Exception e ) {
         e.printStackTrace() ;
      }
      return result ;
   }

   /**
    *  [训练group，以完成ientify]
    *  @method trainGroup
    *  @param  str [group名字]
    */
   public void trainGroup ( String str ) {
      JSONObject syncRet = new JSONObject() ;
      System.out.println("train/identify") ;
      try{
         syncRet = httpRequests.trainIdentify( new PostParameters().setGroupName(str) ) ;
         System.out.println(syncRet) ;
         System.out.println( httpRequests.getSessionSync( syncRet.getString("session_id") ) ) ;
      }catch(FaceppParseException e) {
         e.printStackTrace() ;
      }catch(Exception e) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [训练person，以完成verify]
    *  @method trainPerson
    *  @param  name [人名]
    */
   public void trainPerson ( String name ) {
      JSONObject syncRet = new JSONObject() ;
      System.out.println("train/verify") ;
      try {
         syncRet = httpRequests.trainVerify( new PostParameters().setPersonName(name) ) ;
         System.out.println(syncRet) ;
         System.out.println( httpRequests.getSessionSync( syncRet.getString("session_id") ) ) ;
      }catch (FaceppParseException e) {
         e.printStackTrace() ;
      }catch (Exception e) {
         e.printStackTrace() ;
      }
   }

   /**
    *  [识别人脸，identify]
    *  @method groupIdentify
    *  @param  group [group名]
    *  @param  str [图片路径]
    *  @return [人脸的信息]
    */
   public JSONObject groupIdentify(String group , String str) {
      System.out.println("recogniztion/identify") ;
      JSONObject result = new JSONObject() ;
      try {
         result = httpRequests.recognitionIdentify( new PostParameters().setGroupName(group).setImg( new File(str) ) ) ;
      }catch(FaceppParseException e) {
         e.printStackTrace() ;
      }catch(Exception e) {
         e.printStackTrace() ;
      }
      return result ;
   }

   /**
    *  [验证人脸，verify]
    *  @method personVerify
    *  @param  person [人名]
    *  @param  str [图片路径]
    *  @return [人脸信息]
    */
   public JSONObject personVerify(String person , String str) {
      System.out.println("recognition/verify") ;
      JSONObject result = new JSONObject() ;
      try{
         String face_id = detectFace(str).getJSONArray("face").getJSONObject(0).getString("face_id") ;
         System.out.println(face_id) ;
         result = httpRequests.recognitionVerify( new PostParameters().setPersonName(person).setFaceId(face_id) ) ;
      }catch(FaceppParseException e) {
         e.printStackTrace() ;
      }catch(Exception e) {
         e.printStackTrace() ;
      }
      return result ;
   }

   /**
    *  从返回的人脸中获取名字
    *  @method getName
    *  @param  arg [description]
    *  @return [description]
    */
   public ArrayList getJSONName( JSONObject arg ) {
      ArrayList name = new ArrayList() ;
      try {
         JSONArray candidate = arg.getJSONArray("face").getJSONObject(0).getJSONArray("candidate");
   		int len = candidate.length() ;
   		for( int i = 0 ; i < len ; i++ ) {
   		   JSONObject obj = candidate.getJSONObject(i) ;
   			name.add( obj.get("person_name") ) ;
   	   }
      }catch( Exception e ) {
         e.printStackTrace() ;
      }
      return name ;
   }

   /**
    *  [从返回的人脸中获取tag]
    *  @method getJSONTag
    *  @param  arg [description]
    *  @return [description]
    */
   public ArrayList getJSONTag( JSONObject arg ) {
      ArrayList tag = new ArrayList() ;
      try{
         JSONArray candidate = arg.getJSONArray("face").getJSONObject(0).getJSONArray("candidate");
   		int len = candidate.length() ;
   		for( int i = 0 ; i < len ; i++ ) {
   		   JSONObject obj = candidate.getJSONObject(i) ;
            tag.add( obj.get("tag") ) ;
   	   }
      }catch( Exception e ) {
         e.printStackTrace() ;
      }
      return tag ;
   }

   /**
    *  从获取的人脸中获取置信度
    *  @method getJSONCon
    *  @param  arg [description]
    *  @return [description]
    */
   public ArrayList getJSONCon( JSONObject arg ) {
      ArrayList confidence = new ArrayList() ;
      try{
         JSONArray candidate = arg.getJSONArray("face").getJSONObject(0).getJSONArray("candidate");
   		int len = candidate.length() ;
   		for( int i = 0 ; i < len ; i++ ) {
   		   JSONObject obj = candidate.getJSONObject(i) ;
            confidence.add( obj.get("confidence") ) ;
   	   }
      }catch( Exception e ) {
         e.printStackTrace() ;
      }
      return confidence ;
   }

/*
public JSONObject detectFace ( String str )    //检测人脸，返回人脸信息
public void createPerson ( String str , String[] name ) //在一个组中创造一个person
public void addFace ( String str , String name ) //给某个人添加一张照片
public void setInfo ( String name , String tag ) //设定某个人的tag
public String getInfo ( String name )  //获取某个人的tag
public void creatGroup ( String groupName )  //创造一个group
public void groupAddPerson ( String groupName , String[] namelist ) //在group中添加一系列的人
public void groupSetInfo ( String groupName , String tag ) //设定group的tag
public JSONObject groupGetInfo ( String groupName )  //获取group的tag
public void trainGroup ( String str )  //训练group，以完成ientify
public void trainPerson ( String name ) //训练person，以完成verify
public JSONObject groupIdentify(String group , String str) //识别人脸，identify
public JSONObject personVerify(String person , String str)  //验证人脸，verify
*/

//   public static void main ( String[] args ) {
//      MyDemo mydemo = new MyDemo () ;

   //   mydemo.createPerson ( "/home/ys/workspace/face++WS/faceRecognize/pic/xi_1.jpg" , new String[] {"习近平"} ) ;
   //   mydemo.createPerson ( "/home/ys/workspace/face++WS/faceRecognize/pic/li_1.jpg" , new String[] {"李克强"} ) ;

   /*   for( int i = 1 ; i <= 4 ; i ++ ) {
         mydemo.addFace( "/home/ys/workspace/face++WS/faceRecognize/pic/xi_"+String.valueOf(i)+".jpg" , "习近平" ) ;
         mydemo.addFace( "/home/ys/workspace/face++WS/faceRecognize/pic/li_"+String.valueOf(i)+".jpg" , "李克强" ) ;
      }
      */
   //   mydemo.setInfo( "习近平" , "主席 总书记 党委" ) ;
   //   mydemo.setInfo( "李克强" , "总理 国务院 政府" ) ;
   //   System.out.println( mydemo.getInfo( "习近平" ));
   //   System.out.println( mydemo.getInfo( "李克强" )) ;

   //   mydemo.creatGroup( "政治局委员" ) ;
   //   mydemo.groupAddPerson ( "政治局委员" , new String[] { "习近平" , "李克强" } ) ;

   //   mydemo.trainGroup("政治局委员") ;
   //   mydemo.trainPerson("习近平") ;
   //   mydemo.trainPerson("李克强") ;
   //   System.out.println( mydemo.groupIdentify("政治局委员" , "/home/ys/workspace/face++WS/faceRecognize/pic/xi_5.jpg" ) ) ;
   //   System.out.println( mydemo.groupIdentify("政治局委员" , "/home/ys/workspace/face++WS/faceRecognize/pic/xi_6.jpg" ) ) ;
   //   System.out.println( mydemo.personVerify("习近平" , "/home/ys/workspace/face++WS/faceRecognize/pic/xi_6.jpg") ) ;
   //   System.out.println( mydemo.detectFace("/home/ys/workspace/face++WS/faceRecognize/pic/none_1.jpg") ) ;
   //}
}

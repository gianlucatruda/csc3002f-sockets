import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSend{
public static void main(String[]args){
//convert to byte file
  String rcvd="rcv.jpeg";
  File FR=new File(rcvd);
  byte[]b=new byte[55];//55 =example
  try{
    FileOutputStream fos=new FileOutputStream(FR);
    fos.write(b);
    fos.close();
  }
  catch(Exception e){
    System.out.println(e);
  }

}
}

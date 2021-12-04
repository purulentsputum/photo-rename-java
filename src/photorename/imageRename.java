

package photorename;

import java.io.File;

/**
 *
 * @author rosssellars
 * @created 2015-11-21
 */
public class imageRename {

    public boolean renameFile(String oldFilePath, String newFilePath){
        Boolean retVar = Boolean.FALSE;
         File oldFile = new File(oldFilePath);
         File newFile = new File(newFilePath);
         
         if (!newFile.exists()){
             retVar = oldFile.renameTo(newFile);
             
         }else{
             // error - already exists so do nothing an dreturn false
             retVar = Boolean.FALSE;
         }
        
        return retVar;
    }
    
    public static  String updateFileName(String oldName, java.util.Date dateTaken){
        String retVar = "";
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH-mm");
        String datePart = sdf.format(dateTaken);
        String pathPart = "";
        String namePart = "";
        
        int slashPosition = oldName.lastIndexOf('\\');
        if (slashPosition > 1){
            pathPart = oldName.substring(0, slashPosition+1);
            namePart = oldName.substring(slashPosition+1,oldName.length()); 
        }
        
        
        retVar = pathPart + datePart + " " + namePart; 
        
        return retVar;
    }
}



package photorename;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author rosssellars
 * @created $(now)
 */
public class photoFileName {
    public final static String DATEFORMAT = "yyyy-MM-dd HH-mm";
    public final static String JPEG = "jpeg";
    public final static String JPG = "jpg";
    public final static String GIF = "gif";
    public final static String TIFF = "tiff";
    public final static String TIF = "tif";
    public final static String PNG = "png";
    public final static String BMP = "bmp";
    public final static String[] FILTER = {JPEG,JPG,GIF,TIFF,TIF,PNG,BMP};
    
    public static String directory;
    
    
    public static String getFileName(){
        
        String retVar = "";
        
        JFileChooser fileChooser = new JFileChooser();
        javax.swing.filechooser.FileFilter imageFilter = new FileExtensionFilterForSwing("images", FILTER);
        fileChooser.setFileFilter(imageFilter);
        int returnVal = fileChooser.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            retVar = file.getAbsolutePath() ;
        }
        
        return retVar;
}
    
    public static String getDirectory(){
        
        String retVar = "";
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setApproveButtonText("Select File Folder");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));  //use.home is alternative
        
        int returnVal = fileChooser.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
 
            File file = fileChooser.getSelectedFile();
            retVar = file.getAbsolutePath() ;
            
        }
        return retVar;
    }
    
    private static String[] getDirectoryFileList(String directoryPath,java.io.FileFilter imageFilter, boolean fileNameOnly  ){
        String[]  retVar =null;
        
        File[] listOfFiles = new File(directoryPath).listFiles(imageFilter);
        if (!(listOfFiles==null)){
            retVar = new String[listOfFiles.length];
            int loopVar = 0;
            for (File file: listOfFiles){
                if(fileNameOnly){
                    retVar[loopVar++]=file.getName();
                }else{
                    retVar[loopVar++]=file.getPath();
                } 
                
            }
        }
            
        
        return retVar;
    }
    public static String[] getDirectoryFileListForImages(String directoryPath, boolean fileNameOnly){
        /**
         * @return Array of file paths that are images within selected directory
         */
        java.io.FileFilter imageFilter = new FileFilterByExtension( FILTER);
        return getDirectoryFileList(directoryPath,imageFilter, fileNameOnly);
    }
    
    public static String[] getDirectoryFileListDateStart(String directoryPath, boolean fileNameOnly){

        java.io.FileFilter imageFilter = new FileFilterDateStart();
        return getDirectoryFileList(directoryPath,imageFilter, fileNameOnly);
    }
    public static String[] getDirectoryFileListNotDateStart(String directoryPath, boolean fileNameOnly){

        java.io.FileFilter imageFilter = new FileFilterNotDateStart();
        return getDirectoryFileList(directoryPath,imageFilter, fileNameOnly);
    }
    public static String[] getDirectoryFileListDateOnlyStart(String directoryPath, boolean fileNameOnly){

        java.io.FileFilter imageFilter = new FileFilterDateOnly();
        return getDirectoryFileList(directoryPath,imageFilter, fileNameOnly);
    }
    public static String[] getDirectoryFileListForImagesAndDateStart(String directoryPath, boolean fileNameOnly){

        java.io.FileFilter imageFilter = new FileFilterByExtensionAndDateStart( FILTER);
        return getDirectoryFileList(directoryPath,imageFilter, fileNameOnly);
    }
    public static String[] getDirectoryFileListForImagesAndNotDateStart(String directoryPath, boolean fileNameOnly){

        java.io.FileFilter imageFilter = new FileFilterByExtensionAndNotDateStart( FILTER);
        return getDirectoryFileList(directoryPath,imageFilter, fileNameOnly);
    }
    public static String[] getDirectoryFileListForImagesAndDateStartIncorrect(String directoryPath, boolean fileNameOnly){

        java.io.FileFilter imageFilter = new FileFilterByExtensionAndDateStartIncorrect( FILTER);
        return getDirectoryFileList(directoryPath,imageFilter, fileNameOnly);
    }
    public static HashMap<Integer,String> convertStringToMap(String[] stringArray){
        HashMap retVar = new HashMap<Integer,String>();
        for (int loopVar = 0; loopVar <stringArray.length;loopVar++){
            retVar.put(loopVar,stringArray[loopVar]);
        }
        return retVar;
    }
    public static String[] removeItemAtIndex(int index, String[] stringArray){
        String[] retVar = new String[stringArray.length-1];
        for (int loopVar=0;loopVar<stringArray.length;loopVar++){
            if(loopVar<index){
                retVar[loopVar]=stringArray[loopVar];
            }else if (loopVar>index){
                retVar[loopVar-1]=stringArray[loopVar];
            }
        }
        return retVar;
    }
    
    public static boolean compareNameAndDate(String fileName, java.util.Date dateTaken){
        boolean retVar = Boolean.FALSE;
        
        String dateStrTaken = dateToString(dateTaken);
        
        String dateStrName = fileName.substring(0, 15);
        
        if(dateStrTaken.equalsIgnoreCase(dateStrName)){
            retVar = Boolean.TRUE;
        }
        
        return retVar;
    }
    
    /**
     * 
     * @param fileName
     * @param dateTaken
     * @return boolean true if the start of the filename is the same as the date taken in the DEFAULTDATEFORMAT
     */
    public static boolean compareNameAndDate(String fileName, String dateTaken){
        
        String fileNameOnly = "";
        String dateStrName = "";
        boolean retVar = Boolean.FALSE;

        //error trapping for nulls and short filenames
        if (!(fileName==null)){
            fileNameOnly = getNamefromFileName(fileName);
            
            if(!(dateTaken==null)&&(dateTaken.length()>1)&&(fileNameOnly.length()>15)){
                
                dateStrName = fileNameOnly.substring(0, 16).trim();
                
                if(dateTaken.equalsIgnoreCase(dateStrName)){
                    retVar = Boolean.TRUE;
                }
            }
        }
        return retVar;
    }
    
    public static String getNamefromFileName(String fileName){
        int slashLocation = fileName.lastIndexOf(File.separator);  //'\\'
        String fileNameOnly=fileName.substring(slashLocation+1).trim();
        return fileNameOnly;
    }
    public static String getPathfromFileName(String fileName){
        String retVar = "";
        int slashLocation = fileName.lastIndexOf(File.separator);  //'\\'
        if (slashLocation>0){
            retVar=fileName.substring(0,slashLocation).trim();
        }
        return retVar;
    }
    public static boolean doesNameStartWithDateOnly(String fileName)
    {
        return (doesNameStartWithDate(fileName) && !doesNameStartWithDateAndTime(fileName));
    }    
    public static boolean doesNameStartWithDateAndTime(String fileName){
        boolean retVar = Boolean.FALSE;
        java.util.Date date;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATEFORMAT);
        String dateStrName = " ";
        
        String fileNameOnly=getNamefromFileName(fileName);
        
        if(fileNameOnly.length()>DATEFORMAT.length()){
            dateStrName = fileNameOnly.substring(0, 17);
        }
    
        try
         {
            date = sdf.parse(dateStrName);
            retVar = Boolean.TRUE;
         } catch (ParseException ex) {
            //handle exception
        }
        
        return retVar;
    }
    public static boolean doesNameStartWithDate(String fileName){
        boolean retVar = Boolean.FALSE;
        java.util.Date date;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("YYYY-MM-DD");
        String dateStrName = " ";
        
        String fileNameOnly=getNamefromFileName(fileName);
        
        if(fileNameOnly.length()>DATEFORMAT.length()){
            dateStrName = fileNameOnly.substring(0, 11);
        }
    
        try
         {
            date = sdf.parse(dateStrName);
            retVar = Boolean.TRUE;
         } catch (ParseException ex) {
            //handle exception
        }
        
        return retVar;
    }
    
    public static String dateToString(java.util.Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        return sdf.format(date);
    }
    
    public static java.util.Date stringToDate(String date){
         java.util.Date retVar = null;
          SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        
        try
         {
	  retVar= sdf.parse(date);
         } catch (ParseException e)
         {
        //handle exception
        }

         return retVar;
    }
    
    public static boolean renameFile(String oldFilePath, String newFilePath){
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
        
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATEFORMAT);
        String datePart = sdf.format(dateTaken);
        
        return updateFileName(oldName,datePart);
    }
    
    public static String updateFileName(String oldName, String dateTaken){
        String retVar = "";
        
        String pathPart = getPathfromFileName(oldName);
        String namePart = getNamefromFileName(oldName); 
        
        if (doesNameStartWithDateAndTime(namePart)){
            //remove existing date
            namePart = namePart.substring(16, namePart.length()).trim(); 
        }else if (doesNameStartWithDateOnly(oldName)){
            namePart = namePart.substring(11, namePart.length()).trim(); 
        }
        if (pathPart.length()>1){
            retVar = pathPart + File.separator + dateTaken + " " + namePart;
        }else{
            retVar = dateTaken + " " + namePart;
        }
        
        return retVar;
    }
    
    
     
}

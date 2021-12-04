

package photorename;


import java.io.File;

import photorename.Image;







/**
 *
 * @author rosssellars
 * @created $(now)
 */
public class ImageMetadata {
    
    private File file;
   
    private java.util.HashMap<Integer, Object> exif;
    private java.util.HashMap<Integer, Object> gps;
    private Image image;
    private  javax.imageio.metadata.IIOMetadata iioMetadata;
        
    ImageMetadata(){
       file = null;        
    }
    ImageMetadata(File file){
        this.file = file;
        initialise();
    }
    ImageMetadata(String fileName){
        this.file= new File(fileName);
        initialise();
    }
    
    private void initialise(){
        image = new Image(file);
        exif = image.getExifTags();
        gps = image.getGpsTags();
        iioMetadata = image.getIIOMetadata();
    }
    
    public File getFile(){
        return file;
    }
    public String getDateTakenAsString(){
        String retVar = (String)exif.get(EXIFtag.getEXIFtagFromKey("Exif.Image.DateTimeOriginal").getTagDecimal());
        if (!(retVar==null)){
            //change format from "YYYY:MM:DD HH:MM:SS" to "YYYY-MM-DD HH-MM"
            retVar = retVar.substring(0,  16);
            retVar = retVar.replace(":", "-");
        }
        return  retVar;
    }
    public java.util.Date getDateTaken(){
        java.util.Date retVar = new java.util.Date();
        retVar = photoFileName.stringToDate(getDateTakenAsString());
        
        return retVar;
    }
    public String getGPS(){
        /*
        * format 27 deg 26' 57.46" S, 151 deg 59' 17.73 E
        */
        String strTemp;
        strTemp = (String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLatitudeRef").getTagDecimal(),"");
        strTemp = strTemp + "#" +(String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLatitude").getTagDecimal(),"");
        strTemp = strTemp + "#" +(String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLongitudeRef").getTagDecimal(),"");
        strTemp = strTemp + "#" +(String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLongitude").getTagDecimal(),"");
        return strTemp;
    }
    public double getLatitude(){
        double retVar = 0.0;
        String coordStr = (String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLatitude").getTagDecimal(),"");
        if (coordStr.length()>5){
            retVar =  getCoordinate(coordStr);
            String coordRef = (String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLatitudeRef").getTagDecimal(),"");
            if (!coordRef.equalsIgnoreCase("N")) retVar = -retVar;
        }
        return retVar;
    }
    public double getLongitude(){
        double retVar = 0.0;
        String coordStr = (String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLongitude").getTagDecimal(),"");
        if (coordStr.length()>5){
            retVar =  getCoordinate(coordStr);
            String coordRef = (String)gps.getOrDefault(EXIFtag.getEXIFtagFromKey("Exif.GPSInfo.GPSLongitudeRef").getTagDecimal(),"");
            if (!coordRef.equalsIgnoreCase("E")) retVar = -retVar;
        }
        return retVar;
    }
    private double getCoordinate(String RationalArray) {

        //num + "/" + den
        String[] arr = RationalArray.substring(1, RationalArray.length()-1).split(",");
        String[] deg = arr[0].trim().split("/");
        String[] min = arr[1].trim().split("/");
        String[] sec = arr[2].trim().split("/");

        double degNumerator = Double.parseDouble(deg[0]);
        double degDenominator = 1D; try{degDenominator = Double.parseDouble(deg[1]);} catch(Exception e){}
        double minNumerator = Double.parseDouble(min[0]);
        double minDenominator = 1D; try{minDenominator = Double.parseDouble(min[1]);} catch(Exception e){}
        double secNumerator = Double.parseDouble(sec[0]);
        double secDenominator = 1D; try{secDenominator = Double.parseDouble(sec[1]);} catch(Exception e){}

        double m = 0;
        if (degDenominator != 0 || degNumerator != 0){
            m = (degNumerator / degDenominator);
        }

        if (minDenominator != 0 || minNumerator != 0){
            m += (minNumerator / minDenominator) / 60D;
        }

        if (secDenominator != 0 || secNumerator != 0){
            m += (secNumerator / secDenominator / 3600D);
        }

        return m;
    }
    
  
}


/*
 *
 * http://www.sno.phy.queensu.ca/~phil/exiftool/TagNames/EXIF.html
 * http://www.javaxt.com/documentation/?jar=javaxt-core&package=javaxt.io&class=Image
 *  
 * 
 * 
 */ 


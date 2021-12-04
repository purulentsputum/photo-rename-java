/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photorename;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rosssellars
 */
public enum EXIFtag {
    
    CAMERA("0x010f",271,"Exif.Image.Make","Ascii","The manufacturer of the recording equipment. This is the manufacturer of the DSC, scanner, video digitizer or other equipment that generated the image. When the field is left blank, it is treated as unknown."),
    DESCRIPTION("0x010e",270,"Exif.Image.ImageDescription","Ascii","A character string giving the title of the image. It may be a comment such as \"1988 company picnic\" or the like. Two-bytes character codes cannot be used. When a 2-bytes code is necessary, the Exif Private tag <UserComment> is to be used"),
    MODEL("0x0110",272,"Exif.Image.Model","Ascii","The model name or model number of the equipment. This is the model name or number of the DSC, scanner, video digitizer or other equipment that generated the image. When the field is left blank, it is treated as unknown."),
    DATETIMECREATED("0x0132",306,"Exif.Image.DateTime","Ascii","The date and time of image creation. In Exif standard, it is the date and time the file was changed."),
    ARTIST("0x013b",315,"Exif.Image.Artist","Ascii","This tag records the name of the camera owner, photographer or image creator. "),
    TIMEZONE("0x882a",34858,"Exif.Image.TimeZoneOffset","Short","This optional tag encodes the time zone of the camera clock (relativeto Greenwich Mean Time) used to create the DataTimeOriginal tag-valuewhen the picture was taken. It may also contain the time zone offsetof the clock used to create the DateTime tag-value when the image wasmodified."),
    DATETIMEORIGINAL("0x9003",36867,"Exif.Image.DateTimeOriginal","Ascii","The date and time when the original image data was generated."),
    DATETIMEDIGITISED("0x9004",36868,"Exif.Photo.DateTimeDigitized","Ascii","The date and time when the image was stored as digital data."),
    GPSVersionID("0x0000",0,"Exif.GPSInfo.GPSVersionID","Byte","Indicates the version of <GPSInfoIFD>. The version is given as 2.0.0.0. This tag is mandatory when <GPSInfo> tag is present. (Note: The <GPSVersionID> tag is given in bytes, unlike the <ExifVersion> tag. When the version is 2.0.0.0, the tag value is 02000000.H)."),
    GPSLatitudeRef("0x0001",1,"Exif.GPSInfo.GPSLatitudeRef","Ascii","Indicates whether the latitude is north or south latitude. The ASCII value 'N' indicates north latitude, and 'S' is south latitude."),
    GPSLatitude("0x0002",2,"Exif.GPSInfo.GPSLatitude","Rational","Indicates the latitude. The latitude is expressed as three RATIONAL values giving the degrees, minutes, and seconds, respectively. When degrees, minutes and seconds are expressed, the format is dd/1,mm/1,ss/1. When degrees and minutes are used and, for example, fractions of minutes are given up to two decimal places, the format is dd/1,mmmm/100,0/1."),
    GPSLongitudeRef("0x0003",3,"Exif.GPSInfo.GPSLongitudeRef","Ascii","Indicates whether the longitude is east or west longitude. ASCII 'E' indicates east longitude, and 'W' is west longitude."),
    GPSLongitude("0x0004",4,"Exif.GPSInfo.GPSLongitude","Rational","Indicates the longitude. The longitude is expressed as three RATIONAL values giving the degrees, minutes, and seconds, respectively. When degrees, minutes and seconds are expressed, the format is ddd/1,mm/1,ss/1. When degrees and minutes are used and, for example, fractions of minutes are given up to two decimal places, the format is ddd/1,mmmm/100,0/1."),
    GPSAltitudeRef("0x0005",5,"Exif.GPSInfo.GPSAltitudeRef","Byte","Indicates the altitude used as the reference altitude. If the reference is sea level and the altitude is above sea level, 0 is given. If the altitude is below sea level, a value of 1 is given and the altitude is indicated as an absolute value in the GSPAltitude tag. The reference unit is meters. Note that this tag is BYTE type, unlike other reference tags."),
    GPSAltitude("0x0006",6,"Exif.GPSInfo.GPSAltitude","Rational","Indicates the altitude based on the reference in GPSAltitudeRef. Altitude is expressed as one RATIONAL value. The reference unit is meters."),
    GPSTimeStamp("0x0007",7,"Exif.GPSInfo.GPSTimeStamp","Rational","Indicates the time as UTC (Coordinated Universal Time). <TimeStamp> is expressed as three RATIONAL values giving the hour, minute, and second (atomic clock)."),
    GPSSatellites("0x0008",8,"Exif.GPSInfo.GPSSatellites","Ascii","Indicates the GPS satellites used for measurements. This tag can be used to describe the number of satellites, their ID number, angle of elevation, azimuth, SNR and other information in ASCII notation. The format is not specified. If the GPS receiver is incapable of taking measurements, value of the tag is set to NULL."),
    GPSStatus("0x0009",9,"Exif.GPSInfo.GPSStatus","Ascii","Indicates the status of the GPS receiver when the image is recorded. 'A' means measurement is in progress, and 'V' means the measurement is Interoperability."),
    GPSMeasureMode("0x000a",10,"Exif.GPSInfo.GPSMeasureMode","Ascii","Indicates the GPS measurement mode. '2' means two-dimensional measurement and '3' means three-dimensional measurement is in progress."),
    GPSDOP("0x000b",11,"Exif.GPSInfo.GPSDOP","Rational","Indicates the GPS DOP (data degree of precision). An HDOP value is written during two-dimensional measurement, and PDOP during three-dimensional measurement."),
    GPSSpeedRef("0x000c",12,"Exif.GPSInfo.GPSSpeedRef","Ascii","Indicates the unit used to express the GPS receiver speed of movement. 'K' 'M' and 'N' represents kilometers per hour, miles per hour, and knots."),
    GPSSpeed("0x000d",13,"Exif.GPSInfo.GPSSpeed","Rational","Indicates the speed of GPS receiver movement."),
    GPSTrackRef("0x000e",14,"Exif.GPSInfo.GPSTrackRef","Ascii","Indicates the reference for giving the direction of GPS receiver movement. 'T' denotes true direction and 'M' is magnetic direction."),
    GPSTrack("0x000f",15,"Exif.GPSInfo.GPSTrack","Rational","Indicates the direction of GPS receiver movement. The range of values is from 0.00 to 359.99."),
    GPSImgDirectionRef("0x0010",16,"Exif.GPSInfo.GPSImgDirectionRef","Ascii","Indicates the reference for giving the direction of the image when it is captured. 'T' denotes true direction and 'M' is magnetic direction."),
    GPSImgDirection("0x0011",17,"Exif.GPSInfo.GPSImgDirection","Rational","Indicates the direction of the image when it was captured. The range of values is from 0.00 to 359.99."),
    GPSMapDatum("0x0012",18,"Exif.GPSInfo.GPSMapDatum","Ascii","Indicates the geodetic survey data used by the GPS receiver. If the survey data is restricted to Japan, the value of this tag is 'TOKYO' or 'WGS-84'."),
    GPSDestLatitudeRef("0x0013",19,"Exif.GPSInfo.GPSDestLatitudeRef","Ascii","Indicates whether the latitude of the destination point is north or south latitude. The ASCII value 'N' indicates north latitude, and 'S' is south latitude."),
    GPSDestLatitude("0x0014",20,"Exif.GPSInfo.GPSDestLatitude","Rational","Indicates the latitude of the destination point. The latitude is expressed as three RATIONAL values giving the degrees, minutes, and seconds, respectively. If latitude is expressed as degrees, minutes and seconds, a typical format would be dd/1,mm/1,ss/1. When degrees and minutes are used and, for example, fractions of minutes are given up to two decimal places, the format would be dd/1,mmmm/100,0/1."),
    GPSDestLongitudeRef("0x0015",21,"Exif.GPSInfo.GPSDestLongitudeRef","Ascii","Indicates whether the longitude of the destination point is east or west longitude. ASCII 'E' indicates east longitude, and 'W' is west longitude."),
    GPSDestLongitude("0x0016",22,"Exif.GPSInfo.GPSDestLongitude","Rational","Indicates the longitude of the destination point. The longitude is expressed as three RATIONAL values giving the degrees, minutes, and seconds, respectively. If longitude is expressed as degrees, minutes and seconds, a typical format would be ddd/1,mm/1,ss/1. When degrees and minutes are used and, for example, fractions of minutes are given up to two decimal places, the format would be ddd/1,mmmm/100,0/1."),
    GPSDestBearingRef("0x0017",23,"Exif.GPSInfo.GPSDestBearingRef","Ascii","Indicates the reference used for giving the bearing to the destination point. 'T' denotes true direction and 'M' is magnetic direction."),
    GPSDestBearing("0x0018",24,"Exif.GPSInfo.GPSDestBearing","Rational","Indicates the bearing to the destination point. The range of values is from 0.00 to 359.99."),
    GPSDestDistanceRef("0x0019",25,"Exif.GPSInfo.GPSDestDistanceRef","Ascii","Indicates the unit used to express the distance to the destination point. 'K', 'M' and 'N' represent kilometers, miles and knots."),
    GPSDestDistance("0x001a",26,"Exif.GPSInfo.GPSDestDistance","Rational","Indicates the distance to the destination point."),
    GPSProcessingMethod("0x001b",27,"Exif.GPSInfo.GPSProcessingMethod","Undefined","A character string recording the name of the method used for location finding. The first byte indicates the character code used, and this is followed by the name of the method."),
    GPSAreaInformation("0x001c",28,"Exif.GPSInfo.GPSAreaInformation","Undefined","A character string recording the name of the GPS area. The first byte indicates the character code used, and this is followed by the name of the GPS area."),
    GPSDateStamp("0x001d",29,"Exif.GPSInfo.GPSDateStamp","Ascii","A character string recording date and time information relative to UTC (Coordinated Universal Time). The format is YYYY:MM:DD."),
    GPSDifferential("0x001e",30,"Exif.GPSInfo.GPSDifferential","Short","Indicates whether differential correction is applied to the GPS receiver. ");
 


    private int tagDecimal;
    private String tagHex;
    private String key;
    private String description;
    private String tagDataType;
    
    private static Map<Integer,EXIFtag> tagDecimalToEnumMapping;
    private static Map<String,EXIFtag> tagHexToEnumMapping;
    private static Map<String,EXIFtag> keyToEnumMapping;
    
    private EXIFtag(String tagHex,int tagDecimal,String key, String tagDataType,String description){
        this.tagDecimal=tagDecimal;
        this.tagHex=tagHex;
        this.key=key;
        this.description=description;
        this.tagDataType = tagDataType;

    }
    public int getTagDecimal(){
        return tagDecimal;
    }
    public String getTagHex(){
        return tagHex;
    }
    public String getKey(){
        return key;
    }
    public String getDescription(){
        return description;
    }
    
    public static EXIFtag getEXIFtagFromTagDecimal(int index){
        if(tagDecimalToEnumMapping ==null){
            initMapping();
        }
        return tagDecimalToEnumMapping.get(index);
    }
    public static EXIFtag getEXIFtagFromTagHex(String index){
        if(tagHexToEnumMapping ==null){
            initMapping();
        }
        return tagHexToEnumMapping.get(index);
    }
    public static EXIFtag getEXIFtagFromKey(String index){
        if(keyToEnumMapping ==null){
            initMapping();
        }
        return keyToEnumMapping.get(index);
    }
    
    
    private static void initMapping(){
        tagDecimalToEnumMapping = new HashMap<>();
        tagHexToEnumMapping = new HashMap<>();
        keyToEnumMapping = new HashMap<>();

        for (EXIFtag value:values()){
            tagDecimalToEnumMapping.put(value.getTagDecimal(),value);
            tagHexToEnumMapping.put(value.getTagHex(),value);
            keyToEnumMapping.put(value.getKey(),value);
        }
    }
    
}

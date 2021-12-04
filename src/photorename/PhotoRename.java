/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package photorename;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author rosssellars
 */
public class PhotoRename {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        photoFileName.directory = photoFileName.getDirectory();
        
        if(photoFileName.directory.length()>1){
                // ok, open the main frame

                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new PhotoRenameListForm().setVisible(true);
                        
                    }
                });
            }else{
                //no good show shut it down
                System.exit(0);
            } 
        
        //String tempStr = photoFileName.getFileName();
        //JOptionPane.showMessageDialog(null,tempStr);
        
        //ImageMetadata metadata = new ImageMetadata(tempStr);
        
        
        
        //JOptionPane.showMessageDialog(null,"Start with date " + photoFileName.doesNameStartWithDate(tempStr));
        
        //java.util.Date date = imageMetadata.getDateTakenJPG(tempStr);
        //JOptionPane.showMessageDialog(null,"Date taken " + metadata.getDateTakenAsString());
        //JOptionPane.showMessageDialog(null,"GPS " + metadata.getGPS());
        
        //date = imageMetadata.getDateGPS(tempStr);
        //if (!(date == null)){JOptionPane.showMessageDialog(null,"Date GPS taken " + photoFileName.dateToString(date));}
        
        //date = imageMetadata.getDateDigitizedJPG(tempStr);
        //JOptionPane.showMessageDialog(null,"Date taken " + photoFileName.dateToString(date));
        
        
        //JOptionPane.showMessageDialog(null,"Start with date taken " + photoFileName.compareNameAndDate(tempStr, date));
        
        
        //JOptionPane.showMessageDialog(null,imageRename.updateFileName(tempStr, new java.util.Date()));
        
        //JOptionPane.showMessageDialog(null,photoFileName.getDirectory());
        
        
        
        
        
    }
   
    
}



package photorename;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author rosssellars
 * @created $(now)
 */
public class FileFilterByExtensionAndDateStartIncorrect implements FileFilter{
    String extensions[];
    
     public FileFilterByExtensionAndDateStartIncorrect(String extension) {
        this( new String[] { extension });
    }
    public FileFilterByExtensionAndDateStartIncorrect(String extensions[]) {
        this.extensions = (String[]) extensions.clone();
        toLower(this.extensions);
  }
  private void toLower(String array[]) {
    for (int i = 0, n = array.length; i < n; i++) {
      array[i] = array[i].toLowerCase();
    }
  }
    
    @Override
    public boolean accept(File file) {
        boolean retVar = false;
        String fileName = file.getPath();
        String dateTaken = new ImageMetadata(fileName).getDateTakenAsString();
        if (file.isDirectory()) {
            retVar = false;
        } else if (photoFileName.compareNameAndDate(fileName, dateTaken)) {
            retVar = false;
        } else if(photoFileName.doesNameStartWithDateAndTime(file.getName())) {
            //not directory, dates dont match and does start with date, now check if is an image
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) {
                String extension = extensions[i];
                if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                    retVar = true;
                }
            }
        } 
        return retVar;
    }
   
    

}

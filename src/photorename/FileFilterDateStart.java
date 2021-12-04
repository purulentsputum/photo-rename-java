

package photorename;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author rosssellars
 * @created $(now)
 */
public class FileFilterDateStart implements FileFilter{

    
    public FileFilterDateStart(){
        
    }
    
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        } else {
            return (photoFileName.doesNameStartWithDateAndTime(file.getName()));
        }
    }


}

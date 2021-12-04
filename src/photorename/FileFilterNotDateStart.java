

package photorename;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author rosssellars
 * @created $(now)
 */
public class FileFilterNotDateStart implements FileFilter{
    
    public FileFilterNotDateStart(){
        
    }
    
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        } else {
            return (!photoFileName.doesNameStartWithDateAndTime(file.getName()));
        }

    }
}

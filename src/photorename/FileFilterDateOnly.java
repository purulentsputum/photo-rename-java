

package photorename;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author rosssellars
 * @created $(now)
 */
public class FileFilterDateOnly implements FileFilter{

    public FileFilterDateOnly(){
        
    }
    
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return false;
        } else {
            return (photoFileName.doesNameStartWithDateOnly(file.getName()));
        }
    }
}

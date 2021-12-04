

package photorename;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

/**
 *
 * @author rosssellars
 * @created $(now)
 */
public class FileFilterByExtension implements FileFilter{

    
    String extensions[];
    
    public FileFilterByExtension( String extension) {
        this( new String[] { extension });
    }
    public FileFilterByExtension( String extensions[]) {
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
        if (file.isDirectory()) {
            return false;
        } else {
            String path = file.getAbsolutePath().toLowerCase();
            for (int i = 0, n = extensions.length; i < n; i++) {
                String extension = extensions[i];
                if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
                    return true;
                }
            }
        }
        return false;
    }

    
}

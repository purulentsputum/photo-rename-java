/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package photorename;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

import javafx.scene.control.DatePicker;


/**
 *
 * @author rosssellars
 */
public class PhotoRenameListForm extends javax.swing.JFrame {

    String[] allImages;
    String[] noDateTime;
    String[] imagesWithDateTime;
    String[] wrongDateTime;
    String[] dateOnly;
    ArrayList<String> fileList;

    private int lastSelectedFileIndex=0;
    //private String newFileName = "";
    //private String selectedFileName = "";
    
    //private DatePicker dateTakenManual;
    
    
    /**
     * Creates new form PhotoRenameListForm
     */
    public PhotoRenameListForm() {
        initComponents();
        this.lblDirName.setText(photoFileName.directory);
        this.lstFiles.setSelectionMode(SINGLE_SELECTION);
        setDefaults();
        populateFileListArrays();
        populateFilterCBO();
        populateFileList();
        //this.dateTakenManual = new DatePicker(LocalDate.now());
    }
    
    private void populateFilterCBO(){
        this.cboFilter.removeAllItems();
        this.cboFilter.addItem("All images");
        this.cboFilter.addItem("Images without date and time");
        this.cboFilter.addItem("Images with date and time");
        this.cboFilter.addItem("Images with incorrect date and time");
        this.cboFilter.addItem("Images with date only");
        this.cboFilter.setSelectedIndex(1);
    }
    
    private void setDefaults(){
        lastSelectedFileIndex=0;
        setImageDefaults();
        //newFileName = "";
        //selectedFileName = "";
    }
    private void setImageDefaults(){
        this.lblFileDateCreated.setText("Date Created");
        this.lblGPS.setText("GPS location");
        this.lblImage.setIcon(null);
        this.txtNewName.setText(" ");
        this.btnRename.setText("no date recorded");
        this.btnRename.setEnabled(false);
        this.lblImage.setIcon(null);
    }
    private void populateFileListArrays(){
        allImages = photoFileName.getDirectoryFileListForImages(this.lblDirName.getText(),true);
        noDateTime = photoFileName.getDirectoryFileListForImagesAndNotDateStart(this.lblDirName.getText(),true);
        imagesWithDateTime = photoFileName.getDirectoryFileListForImagesAndDateStart(this.lblDirName.getText(),true);
        wrongDateTime = photoFileName.getDirectoryFileListForImagesAndDateStartIncorrect(this.lblDirName.getText(),true);
        dateOnly = photoFileName.getDirectoryFileListDateOnlyStart(this.lblDirName.getText(),true);
    }

    private void populateFileList(){
        int listCount=0;
        switch (this.cboFilter.getSelectedIndex()){
            case 0: //All images
                //fileList = photoFileName.getDirectoryFileListForImages(this.lblDirName.getText(),true);
                this.lstFiles.setListData(allImages);
                fileList = new ArrayList<>(Arrays.asList(allImages));
                listCount = getLength(allImages);
                this.btnRenameAll.setEnabled(false);
               break;
            case 1: //Images without date and time
                //fileList = photoFileName.getDirectoryFileListForImagesAndNotDateStart(this.lblDirName.getText(),true);
                this.lstFiles.setListData(noDateTime);
                fileList = new ArrayList<>(Arrays.asList(noDateTime));
                listCount = getLength(noDateTime);
                this.btnRenameAll.setEnabled(true);
                break;
            case 2: //Images with date and time
                //fileList = photoFileName.getDirectoryFileListForImagesAndDateStart(this.lblDirName.getText(),true);
                this.lstFiles.setListData(imagesWithDateTime);
                fileList = new ArrayList<>(Arrays.asList(imagesWithDateTime));
                listCount = getLength(imagesWithDateTime);
                this.btnRenameAll.setEnabled(false);
                break;
            case 3: //Images with incorrect date and time
                //fileList = photoFileName.getDirectoryFileListForImagesAndDateStartIncorrect(this.lblDirName.getText(),true);
                this.lstFiles.setListData(wrongDateTime);
                fileList = new ArrayList<>(Arrays.asList(wrongDateTime));
                listCount = getLength(wrongDateTime);
                this.btnRenameAll.setEnabled(true);
                break;
            case 4: //Images with date only
                //fileList = photoFileName.getDirectoryFileListDateOnlyStart(this.lblDirName.getText(),true);
                this.lstFiles.setListData(dateOnly);
                fileList = new ArrayList<>(Arrays.asList(dateOnly));
                listCount = getLength(dateOnly);
                this.btnRenameAll.setEnabled(true);
                break;
            }
        
        if (listCount>0) {
            if (listCount>lastSelectedFileIndex){
                this.lstFiles.setSelectedIndex(lastSelectedFileIndex);
             }else{
                this.lstFiles.setSelectedIndex(listCount-1);
            }
        }
    }
    
    private int getLength(String[] stringArray){
        int retVar = 0;
        if (!(stringArray==null)&&(stringArray.length>0)){
            retVar=stringArray.length;
        }
        return retVar;
    }
    
    private String addPathToFileName(String fileName){
        return this.lblDirName.getText() + File.separator + fileName;
    }
    private void populateFileDetails() {
        if(this.lstFiles.getSelectedIndex()<0){
            setImageDefaults();
        }else{
            String selectedFileName = addPathToFileName(this.lstFiles.getSelectedValue());

            ImageMetadata metadata = new ImageMetadata(selectedFileName);
            String dateTaken = metadata.getDateTakenAsString();
            if (dateTaken == null){dateTaken = "unknown";}

            this.lblFileDateCreated.setText("Image created on " + dateTaken );
            this.lblGPS.setText("Image taken at " + metadata.getLatitude() + ", " + metadata.getLongitude());


            if (dateTaken.equalsIgnoreCase("unknown")){
                //newFileName = selectedFileName;
                this.txtNewName.setText(this.lstFiles.getSelectedValue());
                this.btnRename.setText("Rename file manually");
                this.btnRename.setEnabled(true);
            }else{
                this.btnRename.setEnabled(true);
                //newFileName = photoFileName.updateFileName(selectedFileName, dateTaken);
                this.txtNewName.setText(photoFileName.updateFileName(this.lstFiles.getSelectedValue(), dateTaken));
                this.btnRename.setText("Rename file" );
             }
            if(photoFileName.compareNameAndDate(selectedFileName, dateTaken)){
                //no need to rename as already correct
                this.txtNewName.setText(this.lstFiles.getSelectedValue());
                this.btnRename.setEnabled(false);
            }



            try{
                BufferedImage selectedImage = ImageIO.read(new File(selectedFileName));

                double imageHeight = selectedImage.getHeight();
                double imageWidth = selectedImage.getWidth();

                double imageAspectRatio = imageHeight/imageWidth;
                double labelAspectRatio = this.lblImage.getHeight()/(double)this.lblImage.getWidth();

                int scaledHeight;
                int scaledWidth;

                if (imageAspectRatio>labelAspectRatio){
                    //taller than label
                    scaledHeight = this.lblImage.getHeight();
                    scaledWidth = (int) (imageWidth*(this.lblImage.getHeight()/imageHeight));

                }else{
                    //same as or wider than label
                    scaledWidth = this.lblImage.getWidth();
                    scaledHeight = (int) (imageHeight*(this.lblImage.getWidth()/imageWidth));
                }


                ImageIcon iconSelected = new ImageIcon(selectedImage.getScaledInstance(scaledWidth, scaledHeight, java.awt.Image.SCALE_FAST));
                this.lblImage.setIcon(iconSelected);

            } catch (IOException ex) {
                //this.panImage.
            }
        }

    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblDirName = new javax.swing.JLabel();
        btnNewDirectory = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstFiles = new javax.swing.JList<>();
        panImage = new javax.swing.JPanel();
        lblImage = new javax.swing.JLabel();
        panFileDetails = new javax.swing.JPanel();
        panFileDetailsSubPanel = new javax.swing.JPanel();
        lblFileDateCreated = new javax.swing.JLabel();
        lblGPS = new javax.swing.JLabel();
        btnRename = new javax.swing.JButton();
        panDate = new javax.swing.JPanel();
        txtNewName = new javax.swing.JTextField();
        cboFilter = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        btnRenameAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblDirName.setText("jLabel2");

        btnNewDirectory.setText("Select New Folder");
        btnNewDirectory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewDirectoryActionPerformed(evt);
            }
        });

        lstFiles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstFiles.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstFilesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstFiles);

        javax.swing.GroupLayout panImageLayout = new javax.swing.GroupLayout(panImage);
        panImage.setLayout(panImageLayout);
        panImageLayout.setHorizontalGroup(
            panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );
        panImageLayout.setVerticalGroup(
            panImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                .addContainerGap())
        );

        panFileDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("File details"));

        lblFileDateCreated.setText("jLabel1");

        lblGPS.setText("jLabel1");

        btnRename.setText("jButton2");
        btnRename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenameActionPerformed(evt);
            }
        });

        txtNewName.setText("jTextField1");

        javax.swing.GroupLayout panDateLayout = new javax.swing.GroupLayout(panDate);
        panDate.setLayout(panDateLayout);
        panDateLayout.setHorizontalGroup(
            panDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNewName)
        );
        panDateLayout.setVerticalGroup(
            panDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panDateLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(txtNewName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout panFileDetailsSubPanelLayout = new javax.swing.GroupLayout(panFileDetailsSubPanel);
        panFileDetailsSubPanel.setLayout(panFileDetailsSubPanelLayout);
        panFileDetailsSubPanelLayout.setHorizontalGroup(
            panFileDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFileDetailsSubPanelLayout.createSequentialGroup()
                .addGroup(panFileDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGPS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFileDateCreated, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRename, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE))
                .addContainerGap())
        );
        panFileDetailsSubPanelLayout.setVerticalGroup(
            panFileDetailsSubPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panFileDetailsSubPanelLayout.createSequentialGroup()
                .addComponent(lblFileDateCreated)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblGPS)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRename)
                .addGap(33, 33, 33))
        );

        javax.swing.GroupLayout panFileDetailsLayout = new javax.swing.GroupLayout(panFileDetails);
        panFileDetails.setLayout(panFileDetailsLayout);
        panFileDetailsLayout.setHorizontalGroup(
            panFileDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFileDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panFileDetailsSubPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panFileDetailsLayout.setVerticalGroup(
            panFileDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panFileDetailsLayout.createSequentialGroup()
                .addComponent(panFileDetailsSubPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        cboFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All images", "Images without dates", "Images with dates", "Images with incorrect dates" }));
        cboFilter.setSelectedIndex(1);
        cboFilter.setToolTipText("");
        cboFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFilterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 197, Short.MAX_VALUE)
        );

        btnRenameAll.setText("Rename All");
        btnRenameAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenameAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panFileDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(lblDirName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(cboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRenameAll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnNewDirectory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDirName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnNewDirectory)))
                    .addComponent(panImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRenameAll)
                        .addGap(18, 18, 18)
                        .addComponent(panFileDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFilterActionPerformed
        populateFileList();
        populateFileDetails();
    }//GEN-LAST:event_cboFilterActionPerformed

    private void lstFilesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFilesValueChanged
        int selIndex = lstFiles.getSelectedIndex();
        if (selIndex <0){
            //nothing selected
            lstFiles.setSelectedIndex(this.lastSelectedFileIndex);
        }else{
            this.lastSelectedFileIndex=selIndex;
            populateFileDetails();
        }

    }//GEN-LAST:event_lstFilesValueChanged

    private void btnRenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenameActionPerformed
        this.btnRename.setEnabled(false);
        int retVal = JOptionPane.showConfirmDialog(null, "Do you want to rename this image to '" + this.txtNewName.getText() +"' ?");
        if (retVal==0){
            boolean retValB = photoFileName.renameFile(addPathToFileName(this.lstFiles.getSelectedValue()), addPathToFileName(this.txtNewName.getText()));
            int temp = lastSelectedFileIndex;
            setDefaults();
            lastSelectedFileIndex=temp;
            populateFileList();
            //remove renamed file
            //this.lstFiles.remove(temp);
            this.lstFiles.setSelectedIndex(temp);
        }
        
    }//GEN-LAST:event_btnRenameActionPerformed

    private void btnNewDirectoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewDirectoryActionPerformed
        photoFileName.directory = photoFileName.getDirectory();
        this.lblDirName.setText(photoFileName.directory);
        setDefaults();
        
        populateFileList();
    }//GEN-LAST:event_btnNewDirectoryActionPerformed

    private void btnRenameAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenameAllActionPerformed
        // TODO add your handling code here:
        this.btnRename.setEnabled(false);
        this.btnRenameAll.setEnabled(false);
        int retVal = JOptionPane.showConfirmDialog(null, "Do you want to rename all images with embedded dates to this. '" + this.txtNewName.getText() +"' ?");
        if (retVal==0){
            // go for it
            

            for (String item:fileList){
                
                String oldFileName;
                String newFileName;
                
                String selectedFileName = addPathToFileName(item);
                ImageMetadata metadata = new ImageMetadata(selectedFileName);
                String dateTaken = metadata.getDateTakenAsString();
                
                if (!(dateTaken==null)){
                    if (!photoFileName.compareNameAndDate(item, dateTaken)){
                        // not the same
                        oldFileName = addPathToFileName(item);
                        newFileName = addPathToFileName(photoFileName.updateFileName(item, dateTaken));
                        boolean retValB = photoFileName.renameFile(oldFileName, newFileName);
                    }
                } 
            }

            // lastly reset lists
            setDefaults();
            populateFileListArrays();
            populateFileList();
        }
        
    }//GEN-LAST:event_btnRenameAllActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewDirectory;
    private javax.swing.JButton btnRename;
    private javax.swing.JButton btnRenameAll;
    private javax.swing.JComboBox<String> cboFilter;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDirName;
    private javax.swing.JLabel lblFileDateCreated;
    private javax.swing.JLabel lblGPS;
    private javax.swing.JLabel lblImage;
    private javax.swing.JList<String> lstFiles;
    private javax.swing.JPanel panDate;
    private javax.swing.JPanel panFileDetails;
    private javax.swing.JPanel panFileDetailsSubPanel;
    private javax.swing.JPanel panImage;
    private javax.swing.JTextField txtNewName;
    // End of variables declaration//GEN-END:variables
}

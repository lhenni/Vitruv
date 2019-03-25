package tools.vitruv.framework.vsum.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import tools.vitruv.framework.util.VitruviusConstants;
import tools.vitruv.framework.util.bridges.EMFBridge;
import tools.vitruv.framework.util.datatypes.VURI;
import tools.vitruv.framework.vsum.VsumConstants;

public class FileSystemHelper {
    private final File vsumProjectFolder;

    public FileSystemHelper(final File vsumProjectFolder) {
        this.vsumProjectFolder = vsumProjectFolder;
        createFolderIfNotExisting(vsumProjectFolder);
        createFolderInFolder(getVsumProjectFolder(), VsumConstants.UUID_PROVIDER_AND_RESOLVER_FOLDER_NAME);
        createFolderInFolder(getVsumProjectFolder(), VsumConstants.CORRESPONDENCE_FOLDER_NAME);
        createFolderInFolder(getVsumProjectFolder(), VsumConstants.VSUM_FOLDER_NAME);
    }

    public VURI getCorrespondencesVURI() {
        File correspondenceFile = getCorrespondenceFile();
        return VURI.getInstance(EMFBridge.getEmfFileUriForFile(correspondenceFile));
    }

    public VURI getUuidProviderAndResolverVURI() {
        File uuidFile = getUuidProviderAndResolverFile();
        return VURI.getInstance(EMFBridge.getEmfFileUriForFile(uuidFile));
    }

    public VURI getConsistencyMetadataVURI(final String key) {
        File metadataFile = getConsistencyMetadataFile(key);
        return VURI.getInstance(EMFBridge.getEmfFileUriForFile(metadataFile));
    }

    public void saveCorrespondenceModelMMURIs() {
        File correspondenceModelFile = getCorrespondenceFile();
        // FIXME This does nothing reasonable anymore
        Set<VURI> mmURIsSet = new HashSet<VURI>();// Arrays.asList(mmURIs));
        saveVURISetToFile(mmURIsSet, correspondenceModelFile.getAbsolutePath());
    }

    public File getCorrespondenceFile() {
        String fileName = getCorrespondenceFileName();
        return getCorrespondenceFile(fileName);
    }

    public File getCorrespondenceFile(final String fileName) {
        File correspondenceFile = getFileInFolder(getCorrespondenceFolder(), fileName);
        return correspondenceFile;
    }

    private File getConsistencyMetadataFile(final String key) {
        return getFileInFolder(getConsistencyMetadataFolder(), key);
    }

    private static String getCorrespondenceFileName() {
        String fileExtSeparator = VitruviusConstants.getFileExtSeparator();
        String fileExt = VitruviusConstants.getCorrespondencesFileExt();
        return "Correspondences" + fileExtSeparator + fileExt;
    }

    public File getUuidProviderAndResolverFile() {
        String fileExtSeparator = VitruviusConstants.getFileExtSeparator();
        String fileExt = VitruviusConstants.getUuidFileExt();
        String fileName = "Uuid" + fileExtSeparator + fileExt;
        return getFileInFolder(getUuidProviderAndResolverFolder(), fileName);
    }

    public void saveVsumVURIsToFile(final Set<VURI> vuris) {
        String fileName = getVsumMapFileName();
        saveVURISetToFile(vuris, fileName);
    }

    private static void saveVURISetToFile(final Set<VURI> vuris, final String fileName) {
        Set<String> stringSet = new HashSet<String>(vuris.size());
        for (VURI vuri : vuris) {
            stringSet.add(vuri.getEMFUri().toString());
        }
        saveObjectToFile(stringSet, fileName);
    }

    public static void saveObjectToFile(final Object object, final String fileName) {
        try {
            // TODO: this code could be optimized in a way that a new method is provide for sets of
            // strings where only the new strings are appended to the file
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(object);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException("Could not save '" + object + "' to file '" + fileName + "':  " + e);
        }
    }

    public Set<VURI> loadVsumVURIsFromFile() {
        String fileName = getVsumMapFileName();
        return loadVURISetFromFile(fileName);

    }

    private static Set<VURI> loadVURISetFromFile(final String fileName) {
        Set<String> stringSet = loadStringSetFromFile(fileName);
        Set<VURI> vuris = new HashSet<VURI>(stringSet.size() * 2);
        for (String str : stringSet) {
            vuris.add(VURI.getInstance(str));
        }
        return vuris;
    }

    @SuppressWarnings("unchecked")
    private static Set<String> loadStringSetFromFile(final String fileName) {
        Object obj = loadObjectFromFile(fileName);
        if (obj == null) {
            return Collections.emptySet();
        } else if (obj instanceof Set<?>) {
            return (Set<String>) obj;
        } else {
            throw new RuntimeException("The file with the name '" + fileName + "' does not contain a set of strings!");
        }
    }

    public static Object loadObjectFromFile(final String fileName) {
        return loadObjectFromFile(fileName, null);
    }

    public static Object loadObjectFromFile(final String fileName, final ClassLoader cl) {
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream) {
                @Override
                protected Class<?> resolveClass(final ObjectStreamClass desc)
                        throws IOException, ClassNotFoundException {
                    try {
                        return super.resolveClass(desc);
                    } catch (ClassNotFoundException e) {
                        if (cl != null) {
                            String name = desc.getName();
                            return Class.forName(name, false, cl);
                        } else {
                            throw e;
                        }
                    }
                }
            };
            Object obj = ois.readObject();
            ois.close();
            return obj;
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getProject(final String projectPath) {
        return new File(projectPath);
    }

    public File getVsumProjectFolder() {
        return this.vsumProjectFolder;
    }

    // private void deleteAndRecreateProject(final IProject vsumProject) {
    // try {
    // vsumProject.delete(true, new NullProgressMonitor());
    // createProject(vsumProject);
    // } catch (CoreException e) {
    // // soften
    // throw new RuntimeException(e);
    // }
    // }

    // public void createProject(final IProject project) {
    // try {
    // project.create(null);
    // project.open(null);
    // // IProjectDescription description = project.getDescription();
    // // description.setNatureIds(new String[] { VITRUVIUSNATURE.ID });
    // // project.setDescription(description, null);
    // createFolder(getCorrespondenceFolder(project));
    // createFolder(getVsumFolder(project));
    // } catch (CoreException e) {
    // // soften
    // throw new RuntimeException(e);
    // }
    // }

    private File getVsumFolder() {
        return getFolderInFolder(getVsumProjectFolder(), VsumConstants.VSUM_FOLDER_NAME);
    }

    private File getCorrespondenceFolder() {
        return getFolderInFolder(getVsumProjectFolder(), VsumConstants.CORRESPONDENCE_FOLDER_NAME);
    }

    private File getUuidProviderAndResolverFolder() {
        return getFolderInFolder(getVsumProjectFolder(), VsumConstants.UUID_PROVIDER_AND_RESOLVER_FOLDER_NAME);
    }

    private File getConsistencyMetadataFolder() {
        return createFolderInFolder(getVsumProjectFolder(), VsumConstants.CONSISTENCY_METADATA_FOLDER_NAME);
    }

    private String getVsumMapFileName() {
        File file = getVsumInstancesFile();
        return file.getAbsolutePath();
    }

    public File getVsumInstancesFile() {
        return getFileInFolder(getVsumFolder(), VsumConstants.VSUM_INSTANCES_FILE_NAME);
    }

    private File getFolderInFolder(final File parentFolder, final String folderName) {
        File innerFolder = new File(parentFolder, folderName);
        if (!innerFolder.exists() || !innerFolder.isDirectory()) {
            throw new IllegalStateException("Folder " + folderName + " does not exist");
        }
        return innerFolder;
    }

    private File getFileInFolder(final File folder, final String fileName) {
        File innerFile = new File(folder, fileName);
        return innerFile;
    }

    private File createFolderInFolder(final File parentFolder, final String folderName) {
        File innerFolder = new File(parentFolder, folderName);
        return createFolderIfNotExisting(innerFolder);
    }

    private File createFolderIfNotExisting(final File folder) {
        if (!folder.exists()) {
            folder.mkdir();
        }
        return folder;
    }

    public VURI getVMVURI() {
        File vaveFile = getVMFile();
        return VURI.getInstance(EMFBridge.getEmfFileUriForFile(vaveFile));
    }

    public File getVMFile() {
        String fileName = getVMFileName();
        return getVMFile(fileName);
    }

    private static String getVMFileName() {
        String fileExtSeparator = VitruviusConstants.getFileExtSeparator();
        String fileExt = VitruviusConstants.getVMFileExt();
        String fileName = "VaVe" + fileExtSeparator + fileExt;
        return fileName;
    }

    public File getVMFile(final String fileName) {
        File vaveFile = getFileInFolder(getVMFolder(), fileName);
        return vaveFile;
    }

    private File getVMFolder() {
        return getFolderInFolder(getVsumProjectFolder(), VsumConstants.VAVE_FOLDER_NAME);
    }

}

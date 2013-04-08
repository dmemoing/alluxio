package tachyon;

import java.io.IOException;

import org.apache.log4j.Logger;

import tachyon.conf.CommonConf;
import tachyon.conf.MasterConf;

/**
 * Format Tachyon.
 */
public class Format {
  private final static Logger LOG = Logger.getLogger(CommonConf.LOGGER_TYPE);

  public static void main(String[] args) throws IOException {
    if (args.length != 0) {
      LOG.info("java -cp target/tachyon-" + Version.VERSION +
          "-jar-with-dependencies.jar tachyon.Format");
      System.exit(-1);
    }

    MasterConf masterConf = MasterConf.get();
    LOG.info("Deleting " + masterConf.CHECKPOINT_FILE);
    CommonUtils.deleteFile(masterConf.CHECKPOINT_FILE);
    LOG.info("Deleting " + masterConf.LOG_FILE);
    CommonUtils.deleteFile(masterConf.LOG_FILE);

    CommonConf commonConf = CommonConf.get();
    String folder = commonConf.UNDERFS_ADDRESS + commonConf.DATA_FOLDER;
    UnderFileSystem ufs = UnderFileSystem.getUnderFileSystem(folder);
    LOG.info("Formatting " + folder);
    ufs.delete(folder, true);
    if (!ufs.mkdirs(folder, true)) {
      LOG.info("Failed to create " + folder);
    }

    folder = commonConf.UNDERFS_ADDRESS + commonConf.WORKERS_FOLDER;
    LOG.info("Formatting " + folder);
    ufs.delete(folder, true);
    if (!ufs.mkdirs(folder, true)) {
      LOG.info("Failed to create " + folder);
    }
  }
}
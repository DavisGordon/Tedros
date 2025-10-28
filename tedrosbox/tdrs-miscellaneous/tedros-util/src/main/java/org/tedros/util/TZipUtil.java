package org.tedros.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public abstract class TZipUtil {

	private static final Logger LOGGER = TLoggerUtil.getLogger(TZipUtil.class);

	public TZipUtil() {

	}

	public static boolean unZip(InputStream is, String outputFolder) {

		try {
			// create the input stream for the file, then the input stream for the actual
			// zip file
			// final InputStream is = new FileInputStream(zipFile);
			ArchiveInputStream<ZipArchiveEntry> ais = new ArchiveStreamFactory()
					.createArchiveInputStream(ArchiveStreamFactory.ZIP, is);
			// cycle through the entries in the zip archive and write them to the system
			// temp dir
			ZipArchiveEntry entry = (ZipArchiveEntry) ais.getNextEntry();
			while (entry != null) {
				File outputFile = new File(outputFolder, entry.getName()); // don't do this anonymously, need it for the
																			// list
				LOGGER.info("file unzip : " + outputFile.getAbsoluteFile());

				if (entry.isDirectory()) {
					outputFile.mkdirs();
				} else {
					OutputStream os = new FileOutputStream(outputFile);
					IOUtils.copy(ais, os); // copy from the archiveinputstream to the output stream
					os.close(); // close the output stream
				}

				entry = (ZipArchiveEntry) ais.getNextEntry();
			}

			ais.close();
			is.close();

			LOGGER.info("Done");
			return true;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			return false;
		}
	}

	public static void unZipIt(InputStream zipFile, String outputFolder) {

		try {
			byte[] buffer = new byte[1024];

			// get the zip file content
			// ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipInputStream zis = new ZipInputStream(zipFile);
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);

				LOGGER.info("file unzip : " + newFile.getAbsoluteFile());

				// create all non exists folders
				// else you will hit FileNotFoundException for compressed folder

				if (ze.isDirectory()) {
					new File(newFile.getParent()).mkdirs();
				} else {
					FileOutputStream fos = null;

					new File(newFile.getParent()).mkdirs();

					fos = new FileOutputStream(newFile);

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					fos.close();
				}

				try {
					ze = zis.getNextEntry();
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
			}

			zis.closeEntry();
			zis.close();

			LOGGER.info("Done");

		} catch (IOException ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
	}

}

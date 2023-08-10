package ai.magicemerge.bluebird.app.service.common.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorInputStream;
import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class ZipUtils {

	public static void CompressFileToLZ4(String inputFilePath, String outputFilePath) {
		try {
			InputStream in = Files.newInputStream(Paths.get(inputFilePath));
			OutputStream fout = Files.newOutputStream(Paths.get(outputFilePath));
			BufferedOutputStream out = new BufferedOutputStream(fout);
			FramedLZ4CompressorOutputStream lzOut = new FramedLZ4CompressorOutputStream(out);
			final byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = in.read(buffer))) {
				lzOut.write(buffer, 0, n);
			}
			lzOut.close();
			in.close();
		} catch (IOException e) {
			log.error("compress file error", e);
		}
	}



	public static void UnCompressLZ4ToFile(String inputFilePath, String outputFilePath) {
		try {
			InputStream fin = Files.newInputStream(Paths.get(inputFilePath));
			BufferedInputStream in = new BufferedInputStream(fin);
			OutputStream out = Files.newOutputStream(Paths.get(outputFilePath));
			FramedLZ4CompressorInputStream zIn = new FramedLZ4CompressorInputStream(in);
			final byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = zIn.read(buffer))) {
				out.write(buffer, 0, n);
			}
			out.close();
			zIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
//		ZipUtils.CompressFileToLZ4("/Users/gygeszean/apps/one_shop/oneshop.iml", "/Users/gygeszean/apps/one_shop/one_shop.lz4");
		ZipUtils.UnCompressLZ4ToFile("/Users/gygeszean/apps/one_shop/one_shop.lz4", "/Users/gygeszean/apps/one_shop/test/");
	}


}

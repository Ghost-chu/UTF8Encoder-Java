package com.nide8;

import java.io.File;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class FileScanner {
	File root;

	public FileScanner() {
		this.root = Executor.root;
	}

	public void run() {
		List<String> filePaths = new LinkedList<String>();
        filePaths = getAllFilePaths(root, filePaths);
        UTF8Encoder.echo("文件结构扫描完毕 >> 共计 "+filePaths.size()+" 个文件");
        UTF8Encoder.echo("正在收集文件信息 >> 正在统计文件编码信息");
        List<FileInfo> encodingInfo = getEncodingInfo(filePaths);
        UTF8Encoder.echo("正在创建文件备份 >> 正在为转码前的文件进行备份");
        backupFile(encodingInfo);
        UTF8Encoder.echo("正在进行编码转换 >> 正在转换文件编码格式");
        fileConvert(encodingInfo);
	}
	
	private void fileConvert(List<FileInfo> encodingInfo) {
		
		for (int i = 0; i < encodingInfo.size(); i++) {
			FileInfo info = encodingInfo.get(i);
			UTF8Encoder.echo("正在进行编码转换 ("+(i+1)+"/"+encodingInfo.size()+") >> "+info.getPath());
			try {
				File originFile = new File(info.getPath());
				File tmpFile = new File(originFile.getParent(), originFile.getName()+".converting");
				if(tmpFile.exists())
					Files.delete(tmpFile.toPath());
				Files.copy(originFile.toPath(), tmpFile.toPath());
				EncodeUtils.convert(tmpFile, info.getEncodeing(), originFile, EncodeUtils.CODE_UTF8);
				if(tmpFile.exists())
					Files.delete(tmpFile.toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
	}
	
	private void backupFile(List<FileInfo> filePaths) {
//		for (String path : filePaths) {
//			try {
//				map.put(path, EncodeUtils.getEncode(path, false));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		for (int i = 0; i < filePaths.size(); i++) {
			FileInfo path = filePaths.get(i);
			UTF8Encoder.echo("正在创建文件备份 ("+(i+1)+"/"+filePaths.size()+") >> "+path.getPath());
			try {
				File targetFile = new File(path.getPath());
				//UUID randomUuid = UUID.randomUUID();
				File bakFile = new File(Executor.bak_root, targetFile.getPath());
				File bakFileParent = new File(bakFile.getParent());
				
				if(!bakFileParent.exists())
					if(!bakFileParent.mkdirs())
						bakFileParent.mkdir();
				if(bakFile.exists()) //删除重复备份 避免报错
					bakFile.delete();
				try {
					Files.copy(targetFile.toPath(), bakFile.toPath());
				}catch (Exception e) {
					UTF8Encoder.echo("文件备份失败 ("+(i+1)+"/"+filePaths.size()+") >> "+bakFile.getAbsolutePath()+"###"+e.getMessage());
					if(Executor.isDebugging)
						e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<FileInfo> getEncodingInfo(List<String> filePaths) {
		LinkedList<FileInfo> infos = new LinkedList<>();
//		for (String path : filePaths) {
//			try {
//				map.put(path, EncodeUtils.getEncode(path, false));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		for (int i = 0; i < filePaths.size(); i++) {
			String path = filePaths.get(i);
			UTF8Encoder.echo("正在收集文件信息 ("+(i+1)+"/"+filePaths.size()+") >> "+path);
			File tempFile = new File(path);
			if(!Executor.getFilenameExtension().shouldConvert(ext(tempFile.getName()))) {
				if(Executor.isDebugging)
					UTF8Encoder.echo("DEBUG >> "+"Not in whitelist.");
				continue;
			}
			
			try {
				String encodeString = EncodeUtils.getEncode(path, false);
				if(Executor.isDebugging)
					UTF8Encoder.echo("DEBUG >> "+encodeString);
				//map.put(path,encodeString);
				
				if(EncodeUtils.isEmptyFile(path)) {
					if(Executor.isDebugging)
						UTF8Encoder.echo("DEBUG >> 跳过空文件:"+encodeString);
					continue;
				}
				
				if(encodeString!=EncodeUtils.CODE_UTF8) {
					infos.add(new FileInfo(path, encodeString));
				}else {
					if(Executor.isDebugging)
						UTF8Encoder.echo("DEBUG >> "+"Skipped file.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//return map;
		return infos;
		
	}
	private static String ext(String filename) {
        int index = filename.lastIndexOf(".");
 
        if (index == -1) {
            return null;
        }
        String result = filename.substring(index + 1);
        return result;
    }

	private static List<String> getAllFilePaths(File rootPath, List<String> filePathList) {
		File[] files = rootPath.listFiles();
		if (files == null) {
			return filePathList;
		}
		for (File file : files) {
			if (file.isDirectory()) {
				//filePathList.add(file.getPath() + " <------------这是文件夹");
				if(file.getAbsolutePath().equals(Executor.bak_root.getAbsolutePath())) //排除备份目录
					continue;
				getAllFilePaths(file, filePathList);
			} else {
				filePathList.add(file.getPath());
				UTF8Encoder.echo("正在扫描文件结构 >> "+file.getPath());
			}
		}
		return filePathList;

	}
	
	class FileInfo{
		String path;
		String encodeing;
		public FileInfo(String path,String encodingString) {
			this.path=path;
			this.encodeing=encodingString;
		}
		public String getEncodeing() {
			return encodeing;
		}
		public String getPath() {
			return path;
		}
	}
}

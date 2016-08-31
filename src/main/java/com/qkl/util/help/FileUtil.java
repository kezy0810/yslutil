package com.qkl.util.help;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**文件处理
 * <p>Description：文件处理  </p>
 * @project_Name yc_util
 * @class_Name FileUtil.java
 * @author kezhiyi
 * @date 2015年7月21日
 * @version v1.0
 */
public class FileUtil {

	
	 /** 
     * 将存放在filePath目录下的文件，并且文件名以prefilename开头的xls文件删除
     * @author kezhiyi
     * @date  2015年7月21日
     * @param filePath :待删除的文件路径 
     * @param prefilename:待删除文件名前缀
     * @return 
     */ 
	public static boolean deleteMatchXlsFiles(String filePath, String prefilename)
    {
		File file = new File(filePath);
		if ((!file.exists()) && 
				(!file.mkdirs())) {
			System.out.println("创建导出目录失败！");
			return false;
				}
		
		String[]  filelist=  file.list();
		if(filelist==null||filelist.length<1){
			System.out.println("文件夹下没有文件！");
			return true;
		}
		try{
			for(int i=0;i<filelist.length;i++){
				 File rmFile = new File(filePath+"/"+filelist[i]);
				 if(rmFile.isDirectory()){
					 continue;
				 }
				if(filelist[i].startsWith(prefilename)&&filelist[i].endsWith("xls")){
					rmFile.delete();
				}			
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
    }
	
	
	/** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @author kezhiyi
     * @date 2015年7月21日
     * @param sourceFilePath :待压缩的文件路径 
     * @param prefilename:待压缩文件名前缀
     * @param postfilename:待压缩文件名后缀
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public static boolean fileToZip(String sourceFilePath,String prefilename,String postfilename,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                if(zipFile.exists()){  //判断压缩文件是否存在，如果存在则删除重新创建
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                    zipFile.delete();
                }  
                    String[] sourceFiles = sourceFile.list();  
                    if(null == sourceFiles || sourceFiles.length<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                    }else{  
                    	
                    	
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<sourceFiles.length;i++){  
                        	File mFile = new File(sourceFilePath+"/"+sourceFiles[i]);
                        	if(mFile.isDirectory()){
                        		 continue;
                        	}
                        	if(!sourceFiles[i].startsWith(prefilename)){
            					continue;
            				}
                        	if(!sourceFiles[i].toLowerCase().endsWith(postfilename.toLowerCase())){
                        		sourceFiles[i] = sourceFiles[i] + ".xls";
            				}
                        	
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(sourceFiles[i]);  
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFilePath+"/"+sourceFiles[i]);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    }  
                  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                	if(null != fis) fis.close();
                    if(null != bis) bis.close();  
                    if(null != zos) zos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
    } 
	
    
    /** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @author kezhiyi
     * @date 2015年7月21日
     * @param sourceFilePath :待压缩的文件路径 
     * @param fileList:待压缩文件名前缀
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
    public static boolean xlsfileListToZip(String sourceFilePath,List<String> fileList,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
          
        if(sourceFile.exists() == false){  
            System.out.println("待压缩的文件目录："+sourceFilePath+"不存在.");  
        }else{  
            try {  
                File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
                if(zipFile.exists()){  //判断压缩文件是否存在，如果存在则删除重新创建
                    System.out.println(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
                    zipFile.delete();
                }  
                     
                    if(null == fileList || fileList.size()<1){  
                        System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                    }else{  
                    	
                    	
                        fos = new FileOutputStream(zipFile);  
                        zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                        byte[] bufs = new byte[1024*10];  
                        for(int i=0;i<fileList.size();i++){  
                        	File mFile = new File(sourceFilePath+"/"+fileList.get(i));
                        	if(mFile.isDirectory()){
                        		 continue;
                        	}
                        	String xlsFileName=fileList.get(i);
                        	if(!xlsFileName.toLowerCase().endsWith("xls")){
                        		xlsFileName = xlsFileName+".xls";
            				}
                        	
                            //创建ZIP实体，并添加进压缩包  
                            ZipEntry zipEntry = new ZipEntry(xlsFileName);  
                            zipEntry.setUnixMode(644);
                            zos.putNextEntry(zipEntry);  
                            //读取待压缩的文件并写进压缩包里  
                            fis = new FileInputStream(sourceFilePath+"/"+xlsFileName);  
                            bis = new BufferedInputStream(fis, 1024*10);  
                            int read = 0;  
                            while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                                zos.write(bufs,0,read);  
                            }  
                        }  
                        flag = true;  
                    }  
                  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            } finally{  
                //关闭流  
                try {  
                	if(null != bis) bis.close();  
                	if(null!=fis) fis.close();
                    if(null != zos) zos.close();  
                    if(null != fos) fos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                    throw new RuntimeException(e);  
                }  
            }  
        }  
        return flag;  
    } 
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();//递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    /**  
     * 删除单个文件  
     * @param   fileName    被删除文件的文件名  
     * @return 单个文件删除成功返回true,否则返回false  
     */  
    public static boolean deleteFile(String fileName){   
        File file = new File(fileName);   
        if(file.isFile() && file.exists()){   
            file.delete();   
            System.out.println("删除单个文件"+fileName+"成功！");   
            return true;   
        }else{   
            System.out.println("删除单个文件"+fileName+"失败！");   
            return false;   
        }   
    }   
    /**  
     * 删除目录（文件夹）以及目录下的文件  
     * @param   dir 被删除目录的文件路径  
     * @return  目录删除成功返回true,否则返回false  
     */  
    public static boolean deleteDirectory(String dir){   
        //如果dir不以文件分隔符结尾，自动添加文件分隔符   
        if(!dir.endsWith(File.separator)){   
            dir = dir+File.separator;   
        }   
        File dirFile = new File(dir);   
        //如果dir对应的文件不存在，或者不是一个目录，则退出   
        if(!dirFile.exists() || !dirFile.isDirectory()){   
            System.out.println("删除目录失败"+dir+"目录不存在！");   
            return false;   
        }   
        boolean flag = true;   
        //删除文件夹下的所有文件(包括子目录)   
        File[] files = dirFile.listFiles();   
        for(int i=0;i<files.length;i++){   
            //删除子文件   
            if(files[i].isFile()){   
                flag = deleteFile(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
            //删除子目录   
            else{   
                flag = deleteDirectory(files[i].getAbsolutePath());   
                if(!flag){   
                    break;   
                }   
            }   
        }   
            
        if(!flag){   
            System.out.println("删除目录失败");   
            return false;   
        }   
            
        //删除当前目录   
        if(dirFile.delete()){   
            System.out.println("删除目录"+dir+"成功！");   
            return true;   
        }else{   
            System.out.println("删除目录"+dir+"失败！");   
            return false;   
        }   
    }   
    
    
    /**
     * 将输入流转换为byte数组
     * */
    public static byte[] InPutStreamToByteArray(InputStream in) throws IOException {
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024*4];
        int n=0;
        while ( (n=in.read(buffer)) !=-1) {
            out.write(buffer,0,n);
        }
        return out.toByteArray();
    }
    
    
    
	public static void main(String[] args) {
		String sourceFilePath = "C:/Users/Public/Documents/RTXC File List";
		String zipFilePath="C:/Users/Public/Documents/RTXC File List";
		String fileName ="1234.zip";
		List<String> fileList = new ArrayList<String>();
		fileList.add("投资记录2015-07-21(1).xls");
		fileList.add("投资记录2015-07-21.xls");
		fileList.add("线上客户2015-07-22(1).xls");
		FileUtil.xlsfileListToZip(sourceFilePath, fileList, zipFilePath, fileName);
	}
	
}

package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 文件上传、下载、多个文件压缩下载
 *
 * @author LW
 * @date 2020-09-11 10:40:00
 */
@Slf4j
public class FileUtil {

    public static String FORMAT_STRING ="yyyyMMddHHmmss";
    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    public static boolean uploadFile(MultipartFile file,String path,Date date) {
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING);
        String filePath = path + format.format(date) + file.getOriginalFilename();
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            out.write(file.getBytes());
        } catch (Exception e) {
            log.error("文件上传失败!",e);
            return false;
        }
        log.info("文件上传成功!");
        return true;
    }


    /**
     * 文件下载
     *
     * @param response
     * @param fileName
     * @param filePath
     */
    public static void downloadFile(HttpServletResponse response,String fileName,String filePath) {
        File file = new File(filePath + fileName);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis)) {
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf8"));
                byte[] buffer = new byte[1024];
                //输出流
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

    /**
     *
     * 下载多个文件压缩包
     *
     * 可以用作web页面勾选文件列表，将勾选的文件/文件夹打包下载
     * 父文件夹与子文件夹不能有同名文件，否则会报java.util.zip.ZipException: duplicate entry
     * 因为它是遍历文件夹内所有的子文件夹，将所有文件打包到同一个zip中，同名文件会有冲突
     *
     * @param response
     */
    public static void downloadZip(HttpServletResponse response,List<String> fileList)  {
//        List<String> list = new ArrayList<>();
//        list.add("D:\\chandao\\xampp\\zentaoep\\bin");
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_STRING);
        String fileName = format.format(new Date());
        // 生成压缩包
        // 关联response输出流，直接将zip包文件内容写入到response输出流并下载
        try (ZipOutputStream zos = new ZipOutputStream(response.getOutputStream())) {
            //zip文件作为附件下载
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Type", "application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            // 循环读取文件路径集合，获取每一个文件的路径
            for (String fp : fileList) {
                // 根据文件路径创建文件
                File file = new File(fp);
                // 将每一个文件写入zip文件包内，即进行打包
                zipFile(file, zos);
                // 刷新缓冲区
                response.flushBuffer();
            }
        } catch (IOException e) {
            log.error("下载多个文件压缩包错误",e);
        }
    }

    /**
     * 封装压缩文件的方法
     *
     * @param inputFile
     * @param zipoutputStream
     */
    private static void zipFile(File inputFile, ZipOutputStream zipoutputStream) {
        // 创建输入流读取文件
        try(FileInputStream fis = new FileInputStream(inputFile);
            BufferedInputStream bis = new BufferedInputStream(fis))  {
            // 判断文件是否存在
            if (inputFile.exists()) {
                // 判断是否属于文件，还是文件夹
                if (inputFile.isFile()) {
                    // 将文件写入zip内，即将文件进行打包
                    ZipEntry ze = new ZipEntry(inputFile.getName());
                    // 获取文件名
                    zipoutputStream.putNextEntry(ze);
                    // 写入文件的方法，同上
                    byte[] b = new byte[1024];
                    long l = 0;
                    while (l < inputFile.length()) {
                        int j = bis.read(b, 0, 1024);
                        l += j;
                        zipoutputStream.write(b, 0, j);
                    }
                } else {
                    // 如果是文件夹，则使用穷举的方法获取文件，写入zip
                    try {
                        File[] files = inputFile.listFiles();
                        for (File file : files) {
                            zipFile(file, zipoutputStream);
                        }
                    } catch (Exception e) {
                        log.error("压缩文件失败",e);
                    }
                }
            }
        } catch (Exception e) {
            log.error("压缩文件异常",e);
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath){
        boolean flag = false;
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        try{
            flag = file.delete();
        }catch (Exception e){
            log.error("文件删除错误",e);
        }
        return flag;
    }


}

# MultipartFile转成File
```Java
 /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File("temp_file",file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```
文件使用完记得删除
```Java
if(file1.getParentFile().exists()){
                //这里FileUtil用的是hutool
                boolean del = FileUtil.del(file1);
                log.debug("删除临时文件结果：{}",del);
            }
```
另外一种用Hutool的写法
```Java
/**
     * MultipartFile 转 file
     * @param file
     * @return
     * @throws IOException
     */
    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File("temp_file" , Objects.requireNonNull(file.getOriginalFilename()));
        if (!convFile.getParentFile().exists()) {
            System.out.println("mkdir:" + convFile.getParentFile().mkdirs());
        }
        convFile.createNewFile();
        File file1 = FileUtil.writeFromStream(file.getInputStream(), convFile);
        //这里删除文件根据业务需求更换地方
        if(convFile.getParentFile().exists()){
            boolean del = FileUtil.del(convFile);
            log.debug("删除临时文件结果：{}",del);
        }
        return file1;
    }
```
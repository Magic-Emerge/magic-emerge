//package ai.magicemerge.bluebird.app.client;
//
//import cn.hutool.core.date.DateTime;
//import cn.hutool.core.lang.UUID;
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClientBuilder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.InputStream;
//
//@Component
//public class OSSApiClient {
//
//    public String upload(MultipartFile file) throws Exception {
//        String endpoint = AliyunConfig.END_POINT;
//        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
//        String accessKeyId = AliyunConfig.ACCESS_KEY_ID;
//        String accessKeySecret = AliyunConfig.ACCESS_KEY_SECRET;
//        String bucketName = AliyunConfig.BUCKET_NAME;
//        String folder = AliyunConfig.FOLDER;
//        try {
//            // 创建OSSClient实例。
//            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//            // 填写本地文件的完整路径。如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//            InputStream inputStream = file.getInputStream();
//            //获取文件真实名称
//            String originalFilename = file.getOriginalFilename();
//            //重命名，防止相同文件出现覆盖
//            //生成的f4f2e1a3-391a-4d5a-9438-0c9f5d27708c 需要替换成 f4f2e1a3391a4d5a94380c9f5d27708c
//            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//            //新的文件名
//            originalFilename = uuid + originalFilename;
//            //2、把文件按照日期进行分类
//            String datePath = new DateTime().toString("yyyy/MM/dd");
//            //拼接
//            originalFilename = folder + "/" + datePath + "/" + originalFilename;
//            // oss实现上传文件
//            ossClient.putObject(bucketName, originalFilename, inputStream);
//            // 关闭OSSClient
//            ossClient.shutdown();
//
//            //把上传之后文件路径返回,手动拼接出来
//            return "https://" + bucketName + "." + endpoint + "/" + originalFilename;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}

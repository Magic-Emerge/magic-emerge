//package ai.magicemerge.bluebird.app.client;
//
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AliyunConfig implements InitializingBean {
//    @Value("${aliyun.oss.file.endpoint}")
//    private String endpoint;
//
//    /**
//     * AccessKeyId
//     */
//    @Value("${aliyun.oss.file.accessKeyId}")
//    private String accessKeyId;
//
//    /**
//     * AccessKeySecret
//     */
//    @Value("${aliyun.oss.file.accessKeySecret}")
//    private String accessKeySecret;
//
//    /**
//     * Bucket名称
//     */
//    @Value("${aliyun.oss.file.bucketName}")
//    private String bucketName;
//
//    /**
//     * 上传文件夹路径
//     */
//    @Value("${aliyun.oss.file.folder}")
//    private String folder;
//
//    public static String END_POINT;
//    public static String ACCESS_KEY_ID;
//    public static String ACCESS_KEY_SECRET;
//    public static String BUCKET_NAME;
//    public static String FOLDER;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        END_POINT = endpoint;
//        ACCESS_KEY_ID = accessKeyId;
//        ACCESS_KEY_SECRET = accessKeySecret;
//        BUCKET_NAME = bucketName;
//        FOLDER = folder;
//    }
//}

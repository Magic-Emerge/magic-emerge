
package ai.magicemerge.bluebird.app.service.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;


@Slf4j
public class FileDownloadUtil {
    /**
     * 删除文件及其子文件
     *
     * @param file 文件或文件夹
     */
    public static void delFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
            file.delete();
        }
    }

    /**
     * 压缩文件夹下的所有文件，并进行浏览器下载（使用list解决listFiles乱码问题）
     *
     * @param packageName 下载下来的压缩文件的名字
     * @param fileName    需要下载的文件全名
     * @param request
     * @param response
     */
    public static void downloadFile(String packageName, String fileName,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception {

        /**以上，临时压缩文件创建完成*/

        /**进行浏览器下载*/
        // 清空response
        response.reset();
        // 获得浏览器代理信息
        String agent = request.getHeader("User-Agent").toUpperCase();
        // 判断浏览器代理并分别设置响应给浏览器的编码格式
        String finalFileName = null;
        if ((agent.indexOf("MSIE") > 0) || ((agent.indexOf("RV") != -1) && (agent.indexOf("FIREFOX") == -1))) {
            finalFileName = URLEncoder.encode(packageName, "UTF-8");
        } else {
            finalFileName = new String(packageName.getBytes("UTF-8"), "ISO8859-1");
        }
        // 告知浏览器下载文件，而不是直接打开，浏览器默认为打开
        response.setContentType("application/x-download");
        // 下载文件的名称
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.addHeader("Content-Disposition", "attachment;filename=\"" + finalFileName + "\"");
        response.addHeader("ContentType", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        //输出到本地
        ServletOutputStream servletOutputStream = response.getOutputStream();
        DataOutputStream temps = new DataOutputStream(servletOutputStream);


        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        byte[] b = new byte[2048];

        File file = new File(fileName);
        try {
            int len=-1;
            while((len=in.read(b))!=-1) {
                temps.write(b, 0, len);
            }
            temps.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (temps != null) {
                temps.close();
            }
            if (in != null) {
                in.close();
            }
            if (file != null) {
                delFile(file);
            }
            if (servletOutputStream != null) {
                servletOutputStream.close();
            }
        }
    }
}

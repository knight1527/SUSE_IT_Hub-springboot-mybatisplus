package icu.duanqihang.suse_it.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author knight1527
 * Created with IntelliJ IDEA.
 * User: suse_QiHang
 * Date: 2021/06/30 15:07
 * Description:
 * Version: V1.0
 */
public class FileTypeUtils {
    // 默认判断文件头前三个字节内容
    public static int default_check_length = 10;
    final static HashMap<String, String> fileTypeMap = new HashMap<>();

    // 初始化文件头类型，不够的自行补充
    static {
        fileTypeMap.put("ffd8ffe000104a464946", "jpg");
        fileTypeMap.put("89504e470d0a1a0a0000", "png");
        fileTypeMap.put("47494638396126026f01", "gif");
    }

    /**
     * @param fileName
     * @return String
     * @description 通过文件后缀名获取文件类型
     * @author xyz
     */
    public static String getFileTypeBySuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
    }

    /**
     * @param inputStream
     * @return String
     * @description 通过文件头魔数获取文件类型
     * @author xyz
     */
    public static String getFileTypeByMagicNumber(InputStream inputStream) {
        byte[] bytes = new byte[default_check_length];
        try {
            // 获取文件头前三位魔数的二进制
            inputStream.read(bytes, 0, bytes.length);
            // 文件头前三位魔数二进制转为16进制
            String code = bytesToHexString(bytes);
            System.out.println(code);
            for (Map.Entry<String, String> item : fileTypeMap.entrySet()) {
                if (code.equals(item.getKey())) {
                    return item.getValue();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param bytes
     * @return String
     * @description 字节数组转为16进制
     * @author xyz
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}

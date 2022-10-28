package com.example.autoimpactanalysis.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.springframework.util.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
/**
 * @project_name: autoimpactanalysis
 * @ClassName: OfficeUtil
 * @Author: kevin
 * @Date: 2022/10/23 18:04
 * @Description: TODO
 * @Version: V1.0
 */
public class OfficeUtil {
    /**
     * 通过html生成PDF
     *
     * @param htmlContent html格式内容
     * @param file        输出文件file
     */
    public static void createdPdfByItextHtml(String htmlContent, File file) {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        PdfWriter writer = null;
        try {
            // 1. 获取生成pdf的html内容
            inputStream = new ByteArrayInputStream(htmlContent.getBytes("utf-8"));
            outputStream = new FileOutputStream(file);
            Document document = new Document();
            writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            // 2. 添加字体
//            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
//            fontImp.register(getFontPath());
            // 3. 设置编码
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, inputStream, Charset.forName("UTF-8"), new CustomXMLWorkerFontProvider());
            // 4. 关闭,(不关闭则会生成无效pdf)
            document.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 通过HTML生成Word
     *
     * @param htmlbody
     * @param fileName
     * @return
     * @throws Exception
     */
    public static File createWordByHtml(String htmlbody, String fileName) throws Exception {
        File file = new File(fileName);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(htmlbody.getBytes());
        outputStream.flush();
        outputStream.close();
        return file;
    }

    public static void createDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static String generateHtmlBody(String detailContent) {
        detailContent = StringUtils.isEmpty(detailContent) ? "<h1 style=\"margin-left:28px\"><strong><span style=\";font-family:宋体;font-size:29px\">当前无展示内容</span></strong></h1>" : detailContent;
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title></title>\n" +
                "</head>\n" +
                "<body>\n" +
                detailContent +
                "</body>\n" +
                "</html>";
        return html;
    }
}

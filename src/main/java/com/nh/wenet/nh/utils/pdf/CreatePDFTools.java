package com.nh.wenet.nh.utils.pdf;


import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生成pdf工具
 * Created by hp on 2018/5/25.
 */
@Slf4j
public class CreatePDFTools {

    private static String outputFilePath = new PdfConfig().getPdfRootPath();

    /**
     * 创建PDF文件到服务器
     *
     * @param htmlContent
     * @param outputFile
     * @return
     * @throws Exception
     */
    public static String generate(String htmlContent, String outputFile, String pdfName)
            throws Exception {
        String pathFile = "";
        OutputStream out = null;
        ITextRenderer iTextRenderer = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(htmlContent
                    .getBytes("UTF-8")));
            if (outputFile == null || outputFile == "") {
                outputFile = outputFilePath + "/" + pdfName;
            }
            File f = new File(outputFile);
            if (f != null && !f.getParentFile().exists()) {
                f.getParentFile().mkdir();
            }
            out = new FileOutputStream(outputFile);

            iTextRenderer = (ITextRenderer) ITextRendererObjectFactory.getObjectPool().borrowObject();

            try {
                iTextRenderer.setDocument(doc, null);
                iTextRenderer.layout();
                iTextRenderer.createPDF(out);
                pathFile = outputFile;
            } catch (Exception e) {
                ITextRendererObjectFactory.getObjectPool().invalidateObject(
                        iTextRenderer);
                iTextRenderer = null;
                log.error("Cannot return object from pool.", e);
            }

        } catch (Exception e) {
            log.error("Cannot return object from pool.", e);
        } finally {
            if (out != null)
                out.close();

            if (iTextRenderer != null) {
                try {
                    ITextRendererObjectFactory.getObjectPool().returnObject(
                            iTextRenderer);
                } catch (Exception ex) {
                    log.error("Cannot return object from pool.", ex);
                }
            }
        }
        return pathFile;
    }

    /**
     * @param fileName   模板名称
     * @param data       数据
     * @param outPdfPath pdf保存路径
     * @param pdfName    pdf名称
     * @return
     * @throws Exception
     */
    public static String createPDf(String fileName, Object data, String outPdfPath, String pdfName) throws Exception {
        String htmlData = FreeMarkerUtil.getContent(fileName, data);
        return CreatePDFTools.generate(htmlData, outPdfPath, pdfName);
    }

    public static void main(String args[]) throws Exception {
        Map<String, Object> temdata = new HashMap<>();
        temdata.put("docTitle", "测试pdf");
        temdata.put("doccontent", "12334455");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd天 HH时mm分ss秒");

        temdata.put("createTime", simpleDateFormat.format(new Date()));
        String outPdfPath = createPDf("index.ftl", temdata, "D:\\opt\\app\\pdf\\templates\\hell234e212.pdf", "hell234e212.pdf");

        log.info("生成pdf路径：" + outPdfPath);
    }


}

package com.nh.wenet.nh.utils.pdf;

import lombok.Data;

/**
 * pdf模板配置、字体配置路径
 */
@Data
public class PdfConfig {  //
    private String fontPath = PdfConfig.class.getResource("/").getPath()+"pdf/fonts";
    private String pdfRootPath = PdfConfig.class.getResource("/").getPath()+"pdf";

}

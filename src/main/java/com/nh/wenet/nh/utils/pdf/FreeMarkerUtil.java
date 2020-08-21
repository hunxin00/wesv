package com.nh.wenet.nh.utils.pdf;

import com.google.common.collect.Maps;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Created by fgm on 2017/4/22.
 * FREEMARKER 模板工具类
 */
@Slf4j
public class FreeMarkerUtil {

    private static final String WINDOWS_SPLIT = "\\";

    private static final String UTF_8 = "UTF-8";

    private static Map<String, FileTemplateLoader> fileTemplateLoaderCache = Maps.newConcurrentMap();

    private static Map<String, Configuration> configurationCache = Maps.newConcurrentMap();


    private static String templatePath;

    static {
        templatePath = new PdfConfig().getPdfRootPath();
    }

    public static Configuration getConfiguration(String templateFilePath) {
        if (null != configurationCache.get(templateFilePath)) {
            return configurationCache.get(templateFilePath);
        }
        Configuration config = new Configuration(Configuration.VERSION_2_3_25);
        config.setDefaultEncoding(UTF_8);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        FileTemplateLoader fileTemplateLoader = null;
        if (null != fileTemplateLoaderCache.get(templateFilePath)) {
            fileTemplateLoader = fileTemplateLoaderCache.get(templateFilePath);
        }
        try {
            fileTemplateLoader = new FileTemplateLoader(new File(templateFilePath));
            fileTemplateLoaderCache.put(templateFilePath, fileTemplateLoader);
        } catch (IOException e) {
            log.error("fileTemplateLoader init error!", e);
        }
        config.setTemplateLoader(fileTemplateLoader);
        configurationCache.put(templateFilePath, config);
        return config;

    }


    /**
     * @description 获取模板
     */
    public static String getContent(String fileName, Object data) {
        String templatePath = getPDFTemplatePath(fileName);
        String templateFileName = getTemplateName(templatePath);
        String templateFilePath = getTemplatePath(templatePath);
        if (templatePath == null || templateFilePath == "" || templateFilePath.length() == 0) {
            log.error("templatePath can not be empty!");
        }
        try {
            Template template = getConfiguration(templateFilePath).getTemplate(templateFileName);
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            writer.flush();  // step5 生成数据
            return writer.toString().replaceAll("<br>", "<br/>");
        } catch (Exception ex) {
            log.error("FreeMarkerUtil process fail", ex);
        }
        return "";
    }


    private static String getTemplatePath(String templatePath) {
        if (templatePath == null || templatePath == "" || templatePath.length() == 0) {
            return "";
        }
        if (templatePath.contains(WINDOWS_SPLIT)) {
            return templatePath.substring(0, templatePath.lastIndexOf(WINDOWS_SPLIT));
        }
        return templatePath.substring(0, templatePath.lastIndexOf("/"));
    }

    private static String getTemplateName(String templatePath) {
        if (templatePath == null || templatePath == "" || templatePath.length() == 0) {
            return "";
        }
        if (templatePath.contains(WINDOWS_SPLIT)) {
            return templatePath.substring(templatePath.lastIndexOf(WINDOWS_SPLIT) + 1);
        }
        return templatePath.substring(templatePath.lastIndexOf("/") + 1);
    }

    /**
     * @param fileName PDF文件名    (hello.pdf)
     * @return 匹配到的模板名
     * @description 获取PDF的模板路径,
     * 默认按照PDF文件名匹对应模板
     */
    public static String getPDFTemplatePath(String fileName) {
        String classpath = templatePath;
        String templatePath = classpath + "/templates";
        File file = new File(templatePath);
        if (!file.isDirectory()) {
            log.error("templatePath:" + templatePath + "PDF模板文件不存在,请检查templates文件夹!");
        }
        String pdfFileName = fileName.substring(0, fileName.lastIndexOf("."));
        File defaultTemplate = null;
        File matchTemplate = null;
        for (File f : file.listFiles()) {
            if (!f.isFile()) {
                continue;
            }
            String templateName = f.getName();
            if (templateName.lastIndexOf(".ftl") == -1) {
                continue;
            }
            if (defaultTemplate == null) {
                defaultTemplate = f;
            }
            if (fileName != null && defaultTemplate != null) {
                break;
            }
            templateName = templateName.substring(0, templateName.lastIndexOf("."));
            if (templateName.toLowerCase().equals(pdfFileName.toLowerCase())) {
                matchTemplate = f;
                break;
            }
        }
        if (matchTemplate != null) {
            return matchTemplate.getAbsolutePath();
        }
        if (defaultTemplate != null) {
            return defaultTemplate.getAbsolutePath();
        }

        return null;

    }

    /**
     *
     * @param htmlStr  富文本内容html    img标签闭合，style中添加默认pdf中字体，防止生成pdf缺失内容
     *
     * @return
     */
    public static String replaceTag(String htmlStr) {
        //解析传递的字符串 parse 包含 <body>标签
        Document parse = Jsoup.parseBodyFragment(htmlStr);
        Elements imgs = parse.getElementsByTag("img");
        for (Element img : imgs) {
            img.append("\t");
            String altAttr = img.attr("alt");
            if (!altAttr.contains("[")) {
                img.attr("width", "475px");
            }
        }

        Elements tables = parse.getElementsByTag("table");
        for (Element table : tables) {
            table.removeAttr("border");
            table.attr("border", "1px");
            table.addClass("tl_talbe");
        }
        Elements styleEl = parse.select("[style]");
        for (Element el : styleEl) {
            String styleStr = el.attr("style");
            if (!styleStr.contains("font-family")) {
                continue;
            }
            String s1 = styleStr.substring(0, styleStr.indexOf("font-family"));
            String s2 = styleStr.substring(styleStr.indexOf("font-family"), styleStr.length());
            String s3 = s2.substring(s2.indexOf(";"));
            styleStr = s1 + "font-family: SimSun;" + s3;
            el.removeAttr("style");

            el.attr("style", styleStr);
        }


        Elements as = parse.getElementsByTag("a");
        for (Element a : as) {   //针对APO
            String hrf = a.attr("href");
            String content = "";
            if (hrf.contains(">")) {
                int inx = hrf.indexOf(">");
                int last = hrf.indexOf("<");
                content = hrf.substring(inx + 1, last);
                hrf = hrf.substring(0, inx);
                a.removeAttr("href");
                a.attr("href", hrf);
                a.appendText(content);
            }
        }

        Elements divEl = parse.getElementsByTag("div");
        for (Element div : divEl) {
            div.append("\t");
        }

        //newStr  该字符串包含<body>标签
        String newStr = parse.body().toString();
        //过滤<body>标签
        newStr = newStr.substring(6, newStr.length() - 7);
        //返回修改后字符串
        return newStr.replaceAll("<br>", "<br/>").replaceAll("ptext-p\"", "ptext-p")
                .replaceAll("span微软雅黑\",\"sans-serif\"'", "span")
                .replaceAll("&nbsp;", "\t").replaceAll("'=", "=")
                .replaceAll("\"=", "=");
    }


    public String cssStr(String str) {
        if (!str.contains("font-family")) {
            return str;
        }
        String s1 = str.substring(0, str.indexOf("margin-bottom"));
        String s2 = str.substring(str.indexOf("margin-bottom"), str.length());
        String s3 = s2.substring(s2.indexOf(";"));

        return s1 + "margin-bottom:0pt" + s3;
    }

    public static void main(String[] args) {

        String srt = "<div class=\"forEditer\"><p style=\"text-align: center;font-family:宋体;\"><img src=\"http://oss01-cn-beijing-sinopec-d01-a.yun-inc.sinopec.com/wzy-vpc2-dev001/JZGK/JCGF/44a66ae5a7d949e5a805677944666990.jpg\" style=\"max-width:100%;\">12213131313131<br></p></div>";
        String lll = replaceTag(srt);
        System.out.println(lll);


    }


}

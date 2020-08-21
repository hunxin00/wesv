package com.nh.wenet.nh.utils.pdf;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.IOException;

/**
 * Created by hp on 2018/5/25.
 */
public class ITextRendererObjectFactory extends BasePoolableObjectFactory {
    private static GenericObjectPool itextRendererObjectPool = null;

    @Override
    public Object makeObject() throws Exception {
        ITextRenderer renderer = createTextRenderer();
        return renderer;
    }

    public static synchronized ITextRenderer createTextRenderer()
            throws DocumentException, IOException {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        addFonts(fontResolver);
        return renderer;
    }

    public static ITextFontResolver addFonts(ITextFontResolver fontResolver)
            throws DocumentException, IOException {
        File fontsDir = new File(new PdfConfig().getFontPath());
        if (fontsDir != null && fontsDir.isDirectory()) {
            File[] files = fontsDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f == null || f.isDirectory()) {
                    break;
                }
                fontResolver.addFont(f.getAbsolutePath(), BaseFont.IDENTITY_H,
                        BaseFont.NOT_EMBEDDED);
            }
        }
        return fontResolver;
    }

    public static GenericObjectPool getObjectPool() {
        synchronized (ITextRendererObjectFactory.class) {
            if (itextRendererObjectPool == null) {
                itextRendererObjectPool = new GenericObjectPool(
                        new ITextRendererObjectFactory());
                GenericObjectPool.Config config = new GenericObjectPool.Config();
                config.lifo = false;
                config.maxActive = 15;
                config.maxIdle = 5;
                config.minIdle = 1;
                config.maxWait = 5 * 1000;
                itextRendererObjectPool.setConfig(config);
            }
        }

        return itextRendererObjectPool;
    }
}

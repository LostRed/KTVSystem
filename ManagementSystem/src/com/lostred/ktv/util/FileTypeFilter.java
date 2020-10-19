package com.lostred.ktv.util;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * 文件后缀名过滤器
 */
public class FileTypeFilter extends FileFilter {
    /**
     * 后缀名
     */
    private final String suffix;

    /**
     * 构造文件后缀名过滤器
     *
     * @param suffix 后缀名
     */
    public FileTypeFilter(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File pathName) {
        if (pathName.isDirectory()) {
            return true;
        }
        return pathName.getAbsolutePath().endsWith(suffix);
    }

    @Override
    public String getDescription() {
        return ".wav";
    }
}

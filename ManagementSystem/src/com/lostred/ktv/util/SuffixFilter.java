package com.lostred.ktv.util;

import java.io.File;
import java.io.FileFilter;

/**
 * 文件后缀名过滤器
 */
public class SuffixFilter implements FileFilter {
    /**
     * 后缀名
     */
    private final String suffix;

    /**
     * 构造文件后缀名过滤器
     *
     * @param suffix 后缀名
     */
    public SuffixFilter(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public boolean accept(File pathName) {
        return pathName.getAbsolutePath().endsWith(suffix);
    }
}

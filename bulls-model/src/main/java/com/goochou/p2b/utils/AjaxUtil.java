package com.goochou.p2b.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class AjaxUtil {

    public static void str2front(HttpServletResponse response, String message) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("CacheControl", "no-cache");

        PrintWriter print = null;
        try {
            print = response.getWriter();
            print.print(message);
            print.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (print != null) {
                print.close();
            }
        }
    }

}

package com.yonghyeon.ingram.util;

public class Script {

    public static String back(String msg) { // 유틸리티 관련 함수들은 여러 번 사용되므로 static 메소드로 구현을 하는 것이 적합
        StringBuffer sb = new StringBuffer();
        sb.append("<script>");
        sb.append("alert('"+msg+"');");
        sb.append("history.back();");
        sb.append("</script>");

        return sb.toString();
    }
}

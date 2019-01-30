package cn.voofchat.codedemo.snapshot_1.common;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: zhengzhixiong@baijiahulian.com
 * @date: 2018/10/8
 * @time: 下午7:55
 * Description:
 */
public class App {

    /**
     * 小驼峰转下划线
     */
    private static Pattern CAMEL_TO_UNDERLINE = Pattern.compile("[A-Z]([a-z\\d]+)?");

    /**
      * 实体对象转成Map
      * @param obj 实体对象
      * @return
      */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(camel2Underline(field.getName()), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line){
        if(line == null || "".equals(line)){
            return "";
        }

        line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));

        StringBuffer sb = new StringBuffer();
        Matcher matcher = CAMEL_TO_UNDERLINE.matcher(line);
        while( matcher.find() ){
            String word = matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end() == line.length() ? "" : "_");
        }
        return sb.toString();
    }



    public static void main(String[] args) {

        Student stu = new Student();
        stu.setAddress("武汉");
        stu.setId(1);
        stu.setName("郑志雄");
        stu.setGsxTime("12313123");

        Map<String, Object> stringObjectMap = object2Map(stu);
        System.out.println(stringObjectMap);
    }
}

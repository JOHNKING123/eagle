package cc.msyt.eagle.core.common.utils;


import com.fasterxml.jackson.databind.JavaType;

public class JSonUtils {

//    private static XMLSerializer xmlserializer = new XMLSerializer();

    public static <T> T loadJson(String content, Class<T> valueType){
        return (T)JsonMapper.fromJsonString(content, valueType);
    }

    /**
     * @param content
     *            JavaBean class type
     * @param javaType
     *            json string
     * @return JavaBean
     */
    public static <T> T loadJson(String content, JavaType javaType) {
        try {
            return JsonMapper.getInstance().fromJson(content, javaType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String toJson(Object object)  {
        return JsonMapper.toJsonString(object);
    }


    /*
	 * Singleton Implementation
	 */
    public static JSonUtils getInstance() {
        return Nested.Instance;
    }

    private static class Nested {
        static final JSonUtils Instance = new JSonUtils();

        private Nested() {
        }
    }


}

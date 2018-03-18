package com.yk.platform.common.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.sf.json.JSONObject;

public class JSONUtil
{

    private static final Logger logger = Logger.getLogger(JSONUtil.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static
    {
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);
    }

    public static String toJSONString(Object obj)
    {
        if (obj == null)
            return null;
        Writer write = new StringWriter();
        try
        {
            mapper.writeValue(write, obj);
        }
        catch (JsonGenerationException e)
        {
            logger.error(e.toString());
        }
        catch (JsonMappingException e)
        {
            logger.error(e.toString());
        }
        catch (IOException e)
        {
            logger.error(e.toString());
        }
        return write.toString();
    }

    public static <T> T toObject(String jsonStr, Class<T> classType)
    {
        if (jsonStr == null)
            return null;
        T t = null;
        try
        {
            t = mapper.readValue(jsonStr.getBytes("utf-8"), classType);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
            System.out.println(e);
            logger.error(e.toString());
        }
        catch (JsonMappingException e)
        {
            logger.error(e.toString());
        }
        catch (IOException e)
        {
            logger.error(e.toString());
        }
        return t;
    }

    public static Object toCollection(String jsonStr, JavaType javaType)
    {
        if (jsonStr == null)
            return null;
        Object obj = null;
        try
        {
            obj = mapper.readValue(jsonStr.getBytes("utf-8"), javaType);
        }
        catch (JsonParseException e)
        {
            e.printStackTrace();
            System.out.println(e);
            logger.error(e.toString());
        }
        catch (JsonMappingException e)
        {
            logger.error(e.toString());
        }
        catch (IOException e)
        {
            logger.error(e.toString());
        }
        return obj;
    }

    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses)
    {
        return mapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 解析json格式化String对象到Map结构中
     * @param customParas
     * @return
     */
    public static Map<String, String> getParasMap(String customParas)
    {
        JSONObject jasonObject = JSONObject.fromObject(customParas);
        Map<String, String> filedmap = (Map<String, String>) jasonObject;
        Map<String, String> map = MapUtil.mapTransfer(filedmap);
        return map;
    }
}

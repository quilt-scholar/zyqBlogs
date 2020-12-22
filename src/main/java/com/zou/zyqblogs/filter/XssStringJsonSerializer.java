package com.zou.zyqblogs.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.text.StringEscapeUtils;

import java.io.IOException;

//创建一个xss字符串Json序列化器：用来防止json出现xss
public class XssStringJsonSerializer extends JsonSerializer<String> {
    //重写处理类型方法，指定要处理的类型
    @Override
    public Class<String> handledType() {
    return String.class;
    }
    //重写序列化方法：将value转义通过StringEscapeUtils，在将转义后的json字符串写入jsonGenerator中
    @Override
    public void serialize(String value, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
      if (value != null) {
        String encodedValue = StringEscapeUtils.escapeHtml4(value);
        jsonGenerator.writeString(encodedValue);
      }
    }
}
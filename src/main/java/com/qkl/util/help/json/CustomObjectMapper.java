package com.qkl.util.help.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

/**
 * 解决Date类型返回json格式为自定义格式
 * 
 * @Title: CustomObjectMapper.java
 * @Package com.qkl.util.help.json
 * @Description:
 * @author guowei
 * @date 2015年6月25日 下午3:50:12
 * @version V1.0
 */
public class CustomObjectMapper extends ObjectMapper {

	/**
	 * @Fields ALLOW_SINGLE_QUOTES:允许单引号
	 */
	private boolean ALLOW_SINGLE_QUOTES = false;
	/**
	 * @Fields ALLOW_UNQUOTED_FIELD_NAMES: 字段和值都加引号
	 */
	private boolean ALLOW_UNQUOTED_FIELD_NAMES = false;
	/**
	 * @Fields WRITE_NUMBERS_AS_STRINGS: 数字也加引号
	 */
	private boolean WRITE_NUMBERS_AS_STRINGS = false;
	private boolean QUOTE_NON_NUMERIC_NUMBERS = false;
	private boolean NULL_VALUE_SERIALIZE = false;

	private boolean DATE_FORMAT_SERIALIZE = false;

	public CustomObjectMapper(boolean ALLOW_SINGLE_QUOTES,
			boolean ALLOW_UNQUOTED_FIELD_NAMES,
			boolean WRITE_NUMBERS_AS_STRINGS,
			boolean QUOTE_NON_NUMERIC_NUMBERS, boolean NULL_VALUE_SERIALIZE,
			boolean DATE_FORMAT_SERIALIZE) {
		super();
		ALLOW_SINGLE_QUOTES = ALLOW_SINGLE_QUOTES;
		ALLOW_UNQUOTED_FIELD_NAMES = ALLOW_UNQUOTED_FIELD_NAMES;
		WRITE_NUMBERS_AS_STRINGS = WRITE_NUMBERS_AS_STRINGS;
		QUOTE_NON_NUMERIC_NUMBERS = QUOTE_NON_NUMERIC_NUMBERS;
		NULL_VALUE_SERIALIZE = NULL_VALUE_SERIALIZE;
		DATE_FORMAT_SERIALIZE = DATE_FORMAT_SERIALIZE;

		this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,
				ALLOW_SINGLE_QUOTES);

		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
				ALLOW_UNQUOTED_FIELD_NAMES);

		this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS,
				WRITE_NUMBERS_AS_STRINGS);
		this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS,
				QUOTE_NON_NUMERIC_NUMBERS);

		if (DATE_FORMAT_SERIALIZE) {
			// 格式化日期
			CustomSerializerFactory factory = new CustomSerializerFactory();
			factory.addGenericMapping(Date.class, new JsonSerializer<Date>() {
				@Override
				public void serialize(Date value, JsonGenerator jsonGenerator,
						SerializerProvider provider) throws IOException,
						JsonProcessingException {
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					jsonGenerator.writeString(sdf.format(value));

				}
			});
			this.setSerializerFactory(factory);
		}
		if (NULL_VALUE_SERIALIZE) {
			// 空值处理为空串
			this.getSerializerProvider().setNullValueSerializer(
					new JsonSerializer<Object>() {

						@Override
						public void serialize(Object value, JsonGenerator jg,
								SerializerProvider sp) throws IOException,
								JsonProcessingException {
							jg.writeString("");
						}
					});
		}

	}

}

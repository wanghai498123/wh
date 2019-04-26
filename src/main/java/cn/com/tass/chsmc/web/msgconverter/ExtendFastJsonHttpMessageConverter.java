package cn.com.tass.chsmc.web.msgconverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 描述: 阿里默认的MessageConverter 采用toJsonString存在bug
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 *
 * @author 
 * @created 2016-4-26 上午10:57:59
 * @version 1.0
 */
@Configuration
public class ExtendFastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	public static final Charset UTF8 = Charset.forName("UTF-8");
	private Charset charset = UTF8;
	private SerializerFeature[] features = new SerializerFeature[0];

	public ExtendFastJsonHttpMessageConverter() {
		super(new MediaType[] { new MediaType("application", "json", UTF8),
				new MediaType("application", "*+json", UTF8) });
	}

	protected boolean supports(Class<?> clazz) {
		return true;
	}

	public Charset getCharset() {
		return this.charset;
	}

	public void setCharset(Charset charset) {
		this.charset = charset;
	}

	public SerializerFeature[] getFeatures() {
		return this.features;
	}

	public void setFeatures(SerializerFeature[] features) {
		this.features = features;
	}

	protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		InputStream in = inputMessage.getBody();

		byte[] buf = new byte[1024];
		while (true) {
			int len = in.read(buf);
			if (len == -1) {
				break;
			}

			if (len > 0)
				baos.write(buf, 0, len);

		}

		byte[] bytes = baos.toByteArray();
		return JSON.parseObject(bytes, 0, bytes.length, this.charset.newDecoder(), clazz, new Feature[0]);
	}

	protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,
			HttpMessageNotWritableException {
		OutputStream out = outputMessage.getBody();
		//修复直接toJsonString的Bug
		JSONObject jsonObject = (JSONObject)JSON.toJSON(obj);
		String text=JSON.toJSONString(jsonObject, this.features);
		byte[] bytes = text.getBytes(this.charset.name());
		out.write(bytes);
	}
}
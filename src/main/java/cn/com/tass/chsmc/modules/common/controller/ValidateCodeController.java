package cn.com.tass.chsmc.modules.common.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.tass.chsmc.interceptor.annotation.IgnoreLogin;

/**
 * 标题: 验证码控制器
 * <p>
 * 描述: 生成验证码图片
 * <p>
 * 版权: Copyright (c) 2015
 * <p>
 * 公司: 江南天安 [www.tass.com.cn]
 * <p>
 * 
 * @author 卢灿 [lucan@tass.com.cn]
 * @created 2016-1-28 下午09:46:20
 * @version 1.0
 */

@Controller
@RequestMapping("/common")
public class ValidateCodeController {
	private Color getRandColorA(int s, int e) {
		Random localRandom = new Random();
		if (s > 255)
			s = 255;
		if (e > 255)
			e = 255;
		int i = s + localRandom.nextInt(e - s);
		int j = s + localRandom.nextInt(e - s);
		int k = s + localRandom.nextInt(e - s);
		return new Color(i, j, k);
	}

	@RequestMapping("/validateImage")
	@IgnoreLogin
	public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		int i = 60;
		int j = 20;
		
		//禁止缓存
		resp.setHeader("Pragma", "No-cache");
		resp.setHeader("Cache-Control", "No-cache");
		resp.setDateHeader("Expires", 0);
		  
		BufferedImage buffImg = new BufferedImage(i, j, 1);
		Graphics localGraphics = buffImg.getGraphics();
		Random localRandom = new Random();
		localGraphics.setColor(getRandColorA(200, 250));
		localGraphics.fillRect(0, 0, i, j);
		localGraphics.setFont(new Font("Times new Roman", 0, 18));
		localGraphics.setColor(getRandColorA(160, 200));
		for (int k = 0; k < 20; ++k) {
			int l = localRandom.nextInt(i);
			int i1 = localRandom.nextInt(j);
			int i2 = localRandom.nextInt(12);
			int i3 = localRandom.nextInt(12);
			localGraphics.drawLine(l, i1, l + i2, i1 + i3);
		}
		String validCode = "";
		for (int l = 0; l < 4; ++l) {
			String rand = String.valueOf(localRandom.nextInt(10));
			validCode = validCode + rand;
			localGraphics.setColor(new Color(20 + localRandom.nextInt(110), 20 + localRandom.nextInt(110),
					20 + localRandom.nextInt(110)));
			localGraphics.drawString(rand, 13 * l + 6, 16);
		}
		req.getSession().setAttribute("validateCode", validCode);
		localGraphics.dispose();
		resp.setContentType("text/plain;charset=utf-8");
		ImageIO.write(buffImg, "JPEG", resp.getOutputStream());
	}
}

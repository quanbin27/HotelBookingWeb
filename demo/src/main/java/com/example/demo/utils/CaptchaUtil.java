package com.example.demo.utils;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.FlatColorBackgroundProducer;
import cn.apiclub.captcha.gimpy.BlockGimpyRenderer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.NumbersAnswerProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
public class CaptchaUtil {

    public static Captcha createCaptcha(int width, int height) {
        return new Captcha.Builder(width, height)
                .addBackground(new FlatColorBackgroundProducer(Color.LIGHT_GRAY))
                .addText(new NumbersAnswerProducer(6),
                        new DefaultWordRenderer(
                                Arrays.asList(Color.BLACK),
                                Arrays.asList(new Font("Arial", Font.PLAIN, 40))
                        ))
                .addNoise(new CurvedLineNoiseProducer(Color.BLACK, 1.5f))
                .gimp(new BlockGimpyRenderer())
                .build();
    }

    public static void setCaptcha(HttpSession session, Captcha captcha) {
        session.setAttribute("captcha", captcha);
    }

    public static String getCaptchaCode(HttpSession session) {
        Captcha captcha = (Captcha) session.getAttribute("captcha");
        return captcha.getAnswer();
    }

    public static BufferedImage getCaptchaImage(HttpSession session) {
        Captcha captcha = (Captcha) session.getAttribute("captcha");
        return captcha.getImage();
    }
}

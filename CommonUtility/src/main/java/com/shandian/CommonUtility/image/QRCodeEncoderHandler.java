package com.shandian.CommonUtility.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swetake.util.Qrcode;

public class QRCodeEncoderHandler {
    private Logger logger = LoggerFactory.getLogger(QRCodeEncoderHandler.class);

    /**
     * 
     * 方法描述： 生成二维码(QRCode)图片
     *
     * @author: 刘平贵
     * @date： 日期：2017年7月18日 时间：上午11:48:05
     * @param content 图片内容
     * @param directory
     * @param imgPath
     * @return
     * @version 1.0
     */
    public boolean encoderQRCode(String content, String directory, String imgPath) {
        boolean result = true;
        File file = new File(directory);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdir();
        }
        Qrcode qrcodeHandler = new Qrcode();
        qrcodeHandler.setQrcodeErrorCorrect('M');
        qrcodeHandler.setQrcodeEncodeMode('B');
        qrcodeHandler.setQrcodeVersion(7);
        byte[] contentBytes = null;
        try {
            contentBytes = content.getBytes("gb2312");
        } catch (UnsupportedEncodingException e) {
            logger.error("获取字节数组报错：{}", e.getMessage());
            result = false;
            return result;
        }
        BufferedImage bufImg = new BufferedImage(280, 280, BufferedImage.TYPE_INT_RGB);
        Graphics2D gs = bufImg.createGraphics();
        gs.setBackground(Color.WHITE);
        gs.clearRect(0, 0, 280, 280);
        // 设定图像颜色> BLACK
        gs.setColor(Color.BLACK);
        // 设置偏移量 不设置可能导致解析出错
        int pixoff = 2;
        // 输出内容> 二维码
        if (contentBytes.length > 0 && contentBytes.length < 120) {
            boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
            for (int i = 0; i < codeOut.length; i++) {
                for (int j = 0; j < codeOut.length; j++) {
                    if (codeOut[j][i]) {
                        gs.fillRect(j * 6 + pixoff, i * 6 + pixoff, 6, 6);
                    }
                }
            }
        } else {
            logger.error("QRCode content bytes length = {}, not in [ 0,120 ].", contentBytes.length);
        }
        gs.dispose();
        bufImg.flush();
        File imgFile = new File(imgPath);
        // 生成二维码QRCode图片
        try {
            ImageIO.write(bufImg, "png", imgFile);
        } catch (IOException e) {
            logger.error("生成图片报错：{}", e.getMessage());
            result = false;
            return result;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String imgPath = "D:/test/Michael_QRCode.png";
        String content = "weixin://wxpay/bizpayurl?pr=a45VOY4";
        QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
        handler.encoderQRCode(content, "D:\\test", imgPath);
        System.out.println("encoder QRcode success");
    }
}

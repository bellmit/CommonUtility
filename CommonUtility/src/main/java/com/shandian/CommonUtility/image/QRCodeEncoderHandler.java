package com.shandian.CommonUtility.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCodeEncoderHandler {
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
        try {
            File file = new File(directory);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            Qrcode qrcodeHandler = new Qrcode();
            qrcodeHandler.setQrcodeErrorCorrect('M');
            qrcodeHandler.setQrcodeEncodeMode('B');
            qrcodeHandler.setQrcodeVersion(7);
            byte[] contentBytes = content.getBytes("gb2312");
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
                System.err.println("QRCode content bytes length = " + contentBytes.length + " not in [ 0,120 ]. ");
            }
            gs.dispose();
            bufImg.flush();
            File imgFile = new File(imgPath);
            // 生成二维码QRCode图片
            ImageIO.write(bufImg, "png", imgFile);
        } catch (Exception e) {
            e.printStackTrace();
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
        // //普通上传
        // PutExtra extra = new PutExtra();
        //
        // String localFile = "K:\\image\\0315.jpg";
        // PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
        // System.out.println(ret);
    }
}

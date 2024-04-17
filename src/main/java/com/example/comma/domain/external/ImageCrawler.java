package com.example.comma.domain.external;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.comma.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.comma.global.error.ErrorCode.SIGNLANGUAGE_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
public class ImageCrawler {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public List<String> crawlImageUrls(String searchWord) {
        try {
            // 검색 페이지 URL
            String searchUrl = "https://sldict.korean.go.kr/front/search/searchAllList.do?searchKeyword=" + searchWord;

            // 검색 페이지에서 첫 번째 결과 페이지로 이동
            Document doc = Jsoup.connect(searchUrl).get();
            Elements spanElements = doc.select("span[class=tit]");
            Element spanElement = spanElements.first();
            assert spanElement != null;
            Element aElement = spanElement.selectFirst("a");
            assert aElement != null;

            String aElementText = aElement.text().trim();

            if (!searchWord.equals(aElementText)) {
                throw new EntityNotFoundException(SIGNLANGUAGE_NOT_FOUND);
            }

            String href = aElement.attr("href");

            // fnSearchContentsView 인자 추출
            String[] argsArray = href.split("'");
            String originNo = argsArray[1];
            String topCategory = argsArray[2];

            // URL 생성
            String url = "https://sldict.korean.go.kr/front/sign/signContentsView.do" +
                    "?origin_no=" + originNo +
                    "&top_category=" + topCategory +
                    "&category=" +
                    "&searchKeyword=" + URLEncoder.encode(searchWord, "UTF-8") +
                    "&searchCondition=" +
                    "&search_gubun=" +
                    "&museum_type=00" +
                    "&current_pos_index=0";

            // 상세 페이지로 이동하여 이미지 URL 추출
            Document detailDoc = Jsoup.connect(url).get();
            Elements imgElements = detailDoc.select("img[alt=수어동작 이미지]");

            List<String> imageUrls = new ArrayList<>();

            for (Element imgElement : imgElements) {
                String imageUrl = imgElement.attr("src");
                if (imageUrl.startsWith("http://")) {
                    imageUrl = imageUrl.replace("http://", "https://");
                }
                imageUrls.add(imageUrl);
            }

            return imageUrls;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    //이미지 S3 업로드
    public String uploadFile(byte[] fileData, String fileName) throws IOException {
        String directory = "mergedImg/";
        String contentType = "image/jpg";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType);
        metadata.setContentLength(fileData.length);

        amazonS3.putObject(new PutObjectRequest(bucketName, directory + fileName, inputStream, metadata));

        return "https://" + bucketName + ".s3.amazonaws.com/" + directory + fileName;
    }


    public static byte[] mergeImages(List<String> imageUrls) throws IOException {
        BufferedImage[] images = new BufferedImage[imageUrls.size()];
        int totalWidth = 0;
        int maxHeight = 0;

        for (int i = 0; i < imageUrls.size(); i++) {
            URL imageUrl = new URL(imageUrls.get(i));
            BufferedImage image = ImageIO.read(imageUrl);
            images[i] = image;
            totalWidth += image.getWidth();
            maxHeight = Math.max(maxHeight, image.getHeight());
        }

        BufferedImage mergedImage = new BufferedImage(totalWidth, maxHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = mergedImage.createGraphics();

        int x = 0;
        for (BufferedImage image : images) {
            g2d.drawImage(image, x, 0, null);
            x += image.getWidth();
        }
        g2d.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(mergedImage, "jpg", baos);
        baos.flush();
        byte[] bytes = baos.toByteArray();
        baos.close();

        return bytes;
    }





}





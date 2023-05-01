package br.com.gab.Sistema.ERP.Controller;

import br.com.gab.Sistema.ERP.Generics.RetornoDTO;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import software.amazon.awssdk.regions.Region;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;

/*
 *
 * CLEAN CODE POR FAVOR, TIO BOB AGRADECE - ASMUEL
 *
 * */

// TODO: FAZER O QUE O SAMUEL PEDE

@Controller
@RequestMapping(value = "/image")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ImageController {
    final String bucket = "teste-gabriel-para-criar-uma-bucket-para-estudo";

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CrossOrigin(value = "http://localhost:8081")
    public RetornoDTO salvarImagem(@RequestParam(required = true, value = "image") MultipartFile formData, @RequestParam(value = "name") String name, HttpServletResponse httpServletResponse) throws Exception {
        RetornoDTO retornoDTO = new RetornoDTO();
        Region region = Region.US_EAST_2; // Enum com a Região que fullObject S3 foi Criado
        String key = name;
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion("us-east-2").withCredentials(new ProfileCredentialsProvider()).build();
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(formData.getContentType());
        objectMetadata.setContentLength(formData.getSize());
        Upload upload = transferManager.upload(bucket, key, formData.getInputStream(), objectMetadata);
        upload.waitForUploadResult();
        retornoDTO.setSuccess(true);
        return retornoDTO;

    }

    @ResponseBody
    @GetMapping(value = "/getKeys")
    public RetornoDTO getListImages() {
        RetornoDTO retornoDTO = new RetornoDTO();
        List<HashMap<String, String>> params = new ArrayList<>();
        ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucket);
        ListObjectsV2Result result;
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        do {
            result = s3Client.listObjectsV2(request);
            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                String key = objectSummary.getKey();
                if (key.contains("jpg") || key.contains("jpeg") || key.contains("png")) {
                    HashMap<String, String> hash = new HashMap<>();
                    hash.put("nome", key);
                    params.add(hash);
                }
            }
            request.setContinuationToken(result.getNextContinuationToken());
        } while (result.isTruncated());
        retornoDTO.setSuccess(true);
        retornoDTO.setObject(params);
        return retornoDTO;
    }

    @ResponseBody
    @CrossOrigin(value = "http://localhost:8081")
    @PostMapping(value = "/get")
    public RetornoDTO getImage(@RequestBody String name) throws IOException {
        RetornoDTO retornoDTO = new RetornoDTO();
        Region region = Region.US_EAST_2;
        String key = name;
        if(key.contains("="))
            key = key.replace("=", "");
        AmazonS3 s3Client = new AmazonS3Client(new ProfileCredentialsProvider());
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucket, key));
        InputStream objData = s3Object.getObjectContent();

        Image image = ImageIO.read(objData);
        BufferedImage bi = this.createResizedCopy(image, 576, 580, true);
        ImageIO.write(bi, "gif", new File("/home/gabriwl/projetos/Sistema-ERP/src/main/resources/media/"));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", byteArrayOutputStream);
        byteArrayOutputStream.flush();

        byte[] imageByte = byteArrayOutputStream.toByteArray();
        retornoDTO.setObject(imageByte);
        retornoDTO.setSuccess(true);
        return retornoDTO;
    }


    BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
        int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
        BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
        Graphics2D g = scaledBI.createGraphics();
        if (preserveAlpha) {
            g.setComposite(AlphaComposite.Src);
        }
        g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
        g.dispose();
        return scaledBI;
    }

    private static void displayTextInputStream(InputStream input) throws IOException {
        // Read the text input stream one line at a time and display each line.
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String line = null;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println();
    }


    public byte[] downloadFile(final String bucketName, final String keyName) {
        AmazonS3 amazonS3 = new AmazonS3Client();
        byte[] content = null;

        final S3Object s3Object = amazonS3.getObject(bucketName, keyName);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        try {
            content = stream.readAllBytes();
            s3Object.close();
        } catch (final IOException ex) {
            System.err.println("eita");
        }
        return content;
    }


}

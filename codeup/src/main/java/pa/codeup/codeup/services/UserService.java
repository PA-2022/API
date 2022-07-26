package pa.codeup.codeup.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pa.codeup.codeup.dto.AuthEntity;
import pa.codeup.codeup.dto.Images;
import pa.codeup.codeup.dto.Token;
import pa.codeup.codeup.dto.UserDao;
import pa.codeup.codeup.entities.User;
import pa.codeup.codeup.repositories.AuthRepository;
import pa.codeup.codeup.repositories.ImageRepository;
import pa.codeup.codeup.repositories.TokenRepository;
import pa.codeup.codeup.repositories.UserRepository;

import java.io.*;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import java.io.FileOutputStream;
import java.util.stream.Collectors;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;



@Service
public class UserService {

    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final AuthRepository authRepository;

    private static final String REGION = "eu-west-1";
    private static final String BUCKET_NAME = "my-awesome-compartment";
    private static final String DESTINATION_FOLDER = "dossier";

    @Autowired
    public UserService(TokenRepository tokenRepository, MailjetEmailService mailjetEmailService, UserRepository userRepository, ImageRepository imageRepository, AuthRepository authRepository) {
        this.tokenRepository = tokenRepository;
        this.emailService = mailjetEmailService;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.authRepository = authRepository;
    }

    private String passwordChangeTokenCreation(String username, Long userId){
        String toEncodeTokenString = username + Instant.now();
        String token = Base64.getEncoder().encodeToString(toEncodeTokenString.getBytes()).replaceAll("[^a-zA-Z0-9]", "");
        this.tokenRepository.save(new Token(null, token, userId, true));
        return token;
    }

    public boolean invalidateToken(Token token) {
        token.setActive(false);
        this.tokenRepository.saveAndFlush(token);
        return true;
    }

    public boolean sendPasswordChangeEmail(UserDao user) {
        String token = passwordChangeTokenCreation(user.getUsername(), user.getId());
        String frontUrl = "http://codeup-pa.herokuapp.com/change-password/" + token;
        String emailContent = "<table style=\"max-width: 670px; background: #fff; border-radius: 3px; text-align: center; -webkit-box-shadow: 0 6px 18px 0 rgba(0,0,0,.06); -moz-box-shadow: 0 6px 18px 0 rgba(0,0,0,.06); box-shadow: 0 6px 18px 0 rgba(0,0,0,.06);\" border=\"0\" width=\"95%\" cellspacing=\"0\" cellpadding=\"0\" align=\"center\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"height: 40px;\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"padding: 0 35px;\">\n" +
                "<h1 style=\"color: #1e1e2d; font-weight: 500; margin: 0; font-size: 32px; font-family: 'Rubik',sans-serif;\">You have requested to reset your password</h1>\n" +
                "<p style=\"color: #455056; font-size: 15px; line-height: 24px; margin: 0;\">A unique link to reset your password has been generated for you. To reset your password, click the following link and follow the instructions.</p>\n" +
                "<a href=\"" + frontUrl + "\" style=\"background: #20e277; text-decoration: none !important; font-weight: 500; margin-top: 35px; color: #fff; text-transform: uppercase; font-size: 14px; padding: 10px 24px; display: inline-block; border-radius: 50px;\">Reset Password</a></td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"height: 40px;\">&nbsp;</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"height: 20px;\">&nbsp;</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: center;\">\n" +
                "<p style=\"font-size: 14px; color: rgba(69, 80, 86, 0.7411764705882353); line-height: 18px; margin: 0 0 0;\">&copy; <strong>codeup</strong></p>\n" +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"height: 80px;\">&nbsp;</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>";
        return this.emailService.sendEmail(user.getUsername(), user.getEmail(), "Change your password", emailContent);
    }

    public boolean changePassword(String password, String tokenStr) {
        Token token = this.tokenRepository.getTokenByTokenEquals(tokenStr);
        if(token == null || !token.isActive()) {
            return false;
        }
        UserDao user = this.userRepository.getUserById(token.getUserId());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        this.userRepository.saveAndFlush(user);
        this.invalidateToken(token);
        return true;
    }

    public boolean isTokenActive(String tokenStr) {
        Token token = this.tokenRepository.getTokenByTokenEquals(tokenStr);
        return token != null && token.isActive();
    }

    public boolean emailUserLostPassword(String email) {
        UserDao user = this.userRepository.findByEmail(email);
        if(user == null) {
            return false;
        }
        return sendPasswordChangeEmail(user);
    }

    public int findByFileName(String filename) {
        return this.imageRepository.countAllByImageNameLike("%" + filename.substring(0, filename.lastIndexOf('.')) + "%");
    }

    public String uploadImage(MultipartFile multipartFile, UserDao user) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream( file );
        fos.write( multipartFile.getBytes() );
        fos.close();

        int filenameCount = this.findByFileName(file.getName());

        String filename = file.getName();
        if(filenameCount > 0 ){
            String[] filenameAndExtension = file.getName().split("\\.");
            filename = (filenameAndExtension[0] + '_' + filenameCount + "." + filenameAndExtension[1]);
        }

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                "AKIAS376PSEQ6ZL26KFP",
                "zP6tc7Rsj1PffKK+HkLhO+4qB7hKN3J+H/uudmik");

        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSignerOverride("AWSS3V4SignerType");

        AmazonS3 s3Client =  AmazonS3ClientBuilder
                .standard()
                .withRegion(REGION)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withClientConfiguration(clientConfig)
                .build();

        String destinationPath = DESTINATION_FOLDER +'/'+ filename;

        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, destinationPath, file);


        s3Client.putObject(putObjectRequest);
        String url = ((AmazonS3Client) s3Client).getResourceUrl(BUCKET_NAME, DESTINATION_FOLDER + '/' + filename);

        user.setProfilePictureUrl(url);
        user.setProfilePictureName(filename);
        this.imageRepository.save(new Images(null, url, filename));

        this.userRepository.saveAndFlush(user);
        return url;
    }

    public User updateUser(User updatedUser) {
        UserDao userDto = this.userRepository.getUserById(updatedUser.getId());
        AuthEntity authEntity = this.authRepository.getByUsername(userDto.getUsername());

        userDto.setUsername(updatedUser.getUsername());
        userDto.setEmail(updatedUser.getEmail());
        userDto.setFirstname(updatedUser.getFirstname());
        userDto.setLastname(updatedUser.getLastname());
        this.userRepository.saveAndFlush(userDto);
        AuthEntity au = new AuthEntity(userDto.getUsername(), authEntity.getAuthority());

        this.authRepository.delete(authEntity);
        this.authRepository.save(au);


        return userDto.toEntity();

    }

    public User addUser(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        UserDao userDao = this.userRepository.save(user.createDao());
        this.authRepository.save(new AuthEntity(user.getUsername(), "ROLE_USER"));
        return userDao.toEntity();
    }

    public List<User> findAll() {
        return this.userRepository.findAll().stream().map(UserDao::toEntity).collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return this.userRepository.getUserById(id).toEntity();
    }

    public List<UserDao> findAllByUsernameLike(String username) {
        return this.userRepository.findAllByUsernameLike(username);
    }

    public List<UserDao> findAllByEmailLike(String email) {
        return this.userRepository.findAllByEmailLike(email);
    }
}

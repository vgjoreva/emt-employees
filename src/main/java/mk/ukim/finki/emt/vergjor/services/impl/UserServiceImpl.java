package mk.ukim.finki.emt.vergjor.services.impl;

import mk.ukim.finki.emt.vergjor.models.*;
import mk.ukim.finki.emt.vergjor.repository.AccountActivationsRepository;
import mk.ukim.finki.emt.vergjor.repository.DepartmentRepository;
import mk.ukim.finki.emt.vergjor.repository.RoleRepository;
import mk.ukim.finki.emt.vergjor.repository.UserRepository;
import mk.ukim.finki.emt.vergjor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private final AccountActivationsRepository accountActivationsRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           DepartmentRepository departmentRepository,
                           AccountActivationsRepository accountActivationsRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.accountActivationsRepository = accountActivationsRepository;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {

        departmentRepository.save(new Department("Development"));
        departmentRepository.save(new Department("Testing"));

        roleRepository.save(new Role(1, "USER"));
        roleRepository.save(new Role(2, "ADMIN"));
        roleRepository.save(new Role(3, "MANAGER"));
        roleRepository.save(new Role(4, "EMPLOYEE"));

        User user =
                new User(
                        "Veronika Gjoreva",
                        "veronika.goreva@students.finki.ukim.mk",
                        passwordEncoder.encode("potato"),
                        EmploymentLevel.MID_LEVEL_TESTER,
                        departmentRepository.findByDepartmentID(2),
                        roleRepository.findByRoleID(1));

        userRepository.save(user);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime start1 = LocalDateTime.parse("2019-05-27 04:09:02", formatter);
        LocalDateTime end1 = start1.plusHours(24);

        accountActivationsRepository.save(
                new AccountActivation(
                        ThreadLocalRandom.current().nextInt(100000, 900000),
                        false,
                        true,
                        start1,
                        end1,
                        roleRepository.findByRoleID(4),
                        user
                ));

        User user2 = new User("Goran Nushkov",
                "veronika.gjoreva@pm.me",
                passwordEncoder.encode("potato"),
                EmploymentLevel.SENIOR_DEVELOPER,
                departmentRepository.findByDepartmentID(1),
                roleRepository.findByRoleID(4));

        userRepository.save(user2);

        LocalDateTime start = LocalDateTime.parse("2019-05-25 18:52:02", formatter);
        LocalDateTime end = start.plusHours(24);

        accountActivationsRepository.save(
                new AccountActivation(
                        ThreadLocalRandom.current().nextInt(100000, 900000),
                        true,
                        true,
                        start,
                        end,
                        roleRepository.findByRoleID(4),
                        user2
                ));

    }


    @Override
    public void registerUser(User user) {

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("veronika.gjoreva", "wmljcpkxeeqlpfyb");
                    }
                });

        try {

            if(user.getLevel() == EmploymentLevel.JUNIOR_DEVELOPER ||
                    user.getLevel() == EmploymentLevel.MID_LEVEL_DEVELOPER ||
                    user.getLevel() == EmploymentLevel.SENIOR_DEVELOPER)
                user.setDepartmentID(departmentRepository.findByDepartmentID(1));
            else if(user.getLevel() == EmploymentLevel.JUNIOR_TESTER ||
                    user.getLevel() == EmploymentLevel.MID_LEVEL_TESTER||
                    user.getLevel() == EmploymentLevel.SENIOR_TESTER)
                user.setDepartmentID(departmentRepository.findByDepartmentID(2));

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoleID(roleRepository.findByRoleID(1));
            userRepository.save(user);

            AccountActivation accountActivation = new AccountActivation();

            int activationCode = ThreadLocalRandom.current().nextInt(100000, 900000);
            accountActivation.setActivation_code(activationCode);

            accountActivation.setRegisteredAt(LocalDateTime.now());
            accountActivation.setValidUntil(accountActivation.getRegisteredAt().plusHours(24));
            accountActivation.setUserIsActivated(false);
            accountActivation.setCodeIsValid(true);
            accountActivation.setUser_id(user);
            accountActivation.setEmployee_position(roleRepository.findByRoleID(4));

            accountActivationsRepository.save(accountActivation);

            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
            message.setSubject("Activation Code");

            String activationLink = "http://localhost:3000/activation/"+activationCode;

            System.out.println(user.getFull_name());
            String activationMsg =  "Dear "+user.getFull_name()+",\n\nYou have successfully created a new account!\n" +
                    "In order to log in you must enter the following activation code:\n" + activationCode +
                    "\n\nOr click in the link:\n" + activationLink;

            message.setText(activationMsg);

            Transport.send(message);
            System.out.println("message sent successfully");


        } catch (MessagingException e) {throw new RuntimeException(e);}

    }

    @Override
    public void activateUserAccount(int code){

        AccountActivation activation = accountActivationsRepository.findById(code).get();
        activation.setUserIsActivated(true);

        accountActivationsRepository.save(activation);

        User user = activation.getUserID();
        user.setRoleID(activation.getEmployee_position());

        userRepository.save(user);

    }

    @Override
    public User findUserById(String user_id) {
        return userRepository.findByUserID(user_id);
    }

    @Override
    public List<User> findByDepartmentID(int department_id) {
        return userRepository.findByDepartmentID(department_id);
    }

    @Override
    public String existsByEmail(String email) {
        System.out.println(email);
        if(userRepository.existsByEmail(email)){
            return "Email already exists";
        }
        else return "Valid";
    }

    @Override
    public String isActivationCodeValid(int code){
        if(accountActivationsRepository.existsById(code)
            && !accountActivationsRepository.findById(code).get().isUserIsActivated()
            && accountActivationsRepository.findById(code).get().isCodeIsValid()){
            return "True";
        }
        else return "False";
    }

    @Override
    public boolean isUserRegistered(String user_id){
        return accountActivationsRepository.isUserRegistered(user_id);
    }

    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static String generatePassword(int length) {
        StringBuilder returnValue = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    @Override
    public void sendNewPassword(String email){

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("veronika.gjoreva", "wmljcpkxeeqlpfyb");
                    }
                });

        try {

            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(email));
            message.setSubject("New password request");

            User user = userRepository.findByEmail(email);

            String newPassword = generatePassword(8);
            updateUserPassword(user.getUser_id(), newPassword);

            String newPasswordMsg =  "Dear "+user.getFull_name()+",\n\nYou have requested to create a new password.\n" +
                    "Here is your new password:\n" + newPassword;

            message.setText(newPasswordMsg);

            Transport.send(message);
            System.out.println("message sent successfully");


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateUserPassword(String id, String password){
        User user = userRepository.getOne(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void editUserInfo(User user){

        if(user.getLevel() == EmploymentLevel.JUNIOR_DEVELOPER ||
                user.getLevel() == EmploymentLevel.MID_LEVEL_DEVELOPER ||
                user.getLevel() == EmploymentLevel.SENIOR_DEVELOPER)
            user.setDepartmentID(departmentRepository.findByDepartmentID(1));
        else if(user.getLevel() == EmploymentLevel.JUNIOR_TESTER ||
                user.getLevel() == EmploymentLevel.MID_LEVEL_TESTER||
                user.getLevel() == EmploymentLevel.SENIOR_TESTER)
            user.setDepartmentID(departmentRepository.findByDepartmentID(2));

        userRepository.save(user);

    }

    @Override
    public void changePassword(String id, String password){
        User user = findUserById(id);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public String isAccountValid(String id){
        if(accountActivationsRepository.isUserValid(id)){
            System.out.println(accountActivationsRepository.isUserValid(id));
            return "True";
        }
        else return "False";
    }

}

package mk.ukim.finki.emt.vergjor.services.impl;

import mk.ukim.finki.emt.vergjor.models.AccountActivation;
import mk.ukim.finki.emt.vergjor.models.Department;
import mk.ukim.finki.emt.vergjor.models.User;
import mk.ukim.finki.emt.vergjor.repository.AccountActivationsRepository;
import mk.ukim.finki.emt.vergjor.repository.DepartmentRepository;
import mk.ukim.finki.emt.vergjor.repository.UserRepository;
import mk.ukim.finki.emt.vergjor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final DepartmentRepository departmentRepository;

    @Autowired
    private final AccountActivationsRepository accountActivationsRepository;

    public UserServiceImpl(UserRepository userRepository,
                           DepartmentRepository departmentRepository,
                           AccountActivationsRepository accountActivationsRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.accountActivationsRepository = accountActivationsRepository;
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

            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(user.getEmail()));
            message.setSubject("Activation Code");

            AccountActivation accountActivation = new AccountActivation();

            int activationCode = ThreadLocalRandom.current().nextInt(100000, 900000);
            accountActivation.setActivation_code(activationCode);
            String activationLink = "http://localhost:8080/activation/"+activationCode;

            String activationMsg =  "Dear "+user.getFull_name()+",\n\nYou have successfully created a new account!\n" +
                    "In order to log in you must enter the following activation code:\n" + activationCode +
                    "\n\nOr click in the link:\n" + activationLink;

            message.setText(activationMsg);

            Transport.send(message);
            System.out.println("message sent successfully");

            Department d = new Department();
            d.setDepartment_name("221");
            departmentRepository.save(d);

            user.setUser_department(d);
            userRepository.save(user);

            accountActivation.setRegisteredAt(LocalDateTime.now());
            accountActivation.setUserIsActivated(false);
            accountActivation.setUser_id(user);

            accountActivationsRepository.save(accountActivation);

        } catch (MessagingException e) {throw new RuntimeException(e);}

    }

    @Override
    public User findUserById(String user_id) {
        return userRepository.findById(user_id).get();
    }

    @Override
    public List<User> findByDepartmentID(int department_id) {
        return userRepository.findByDepartmentID(department_id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}

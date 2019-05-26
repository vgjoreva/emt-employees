package mk.ukim.finki.emt.vergjor.schedulers;


import mk.ukim.finki.emt.vergjor.models.AccountActivation;
import mk.ukim.finki.emt.vergjor.repository.AccountActivationsRepository;
import mk.ukim.finki.emt.vergjor.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Profile("cron-validate-active-codes")
@Component
public class ValidateActivationCodeScheduler {


    private final UserService userService;
    private final AccountActivationsRepository activationsRepository;

    public ValidateActivationCodeScheduler(UserService userService, AccountActivationsRepository activationsRepository) {
        this.userService = userService;
        this.activationsRepository = activationsRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void cleanExpiredCarts() {
        List<AccountActivation> expiredCodes =
                activationsRepository
                        .findAll()
                        .stream()
                        .filter(accountActivation -> accountActivation.isCodeIsValid() && !accountActivation.isUserIsActivated() &&
                                (accountActivation.getValidUntil().isEqual(LocalDateTime.now()) || accountActivation.getValidUntil().isBefore(LocalDateTime.now()) ))
                        .collect(Collectors.toList());

        expiredCodes.forEach(accountActivation -> accountActivation.setCodeIsValid(false));
        expiredCodes.forEach(activationsRepository::save);
    }


}

package mk.ukim.finki.emt.vergjor.schedulers;

import mk.ukim.finki.emt.vergjor.models.AccountActivation;
import mk.ukim.finki.emt.vergjor.models.User;
import mk.ukim.finki.emt.vergjor.repository.AccountActivationsRepository;
import mk.ukim.finki.emt.vergjor.repository.UserRepository;
import mk.ukim.finki.emt.vergjor.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Profile("cron-remove-expired")
@Component
public class ClearInvalidAccountsScheduler {

    private final UserService userService;
    private final AccountActivationsRepository activationsRepository;
    private final UserRepository userRepository;

    public ClearInvalidAccountsScheduler(UserService userService, AccountActivationsRepository activationsRepository, UserRepository userRepository) {
        this.userService = userService;
        this.activationsRepository = activationsRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredCarts() {
        List<AccountActivation> expiredAccounts =
                activationsRepository
                        .findAll()
                        .stream()
                        .filter(accountActivation -> !accountActivation.isCodeIsValid() ||
                                (!accountActivation.isUserIsActivated() &&
                                (accountActivation.getValidUntil().isEqual(LocalDateTime.now()) ||
                                        accountActivation.getValidUntil().isBefore(LocalDateTime.now()) )))
                        .collect(Collectors.toList());

        expiredAccounts.forEach(e -> userRepository.deleteById(e.getUserID().getUser_id()));
        expiredAccounts.forEach(activationsRepository::delete);
    }


}

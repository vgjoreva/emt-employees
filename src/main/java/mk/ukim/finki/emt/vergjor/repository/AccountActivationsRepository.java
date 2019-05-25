package mk.ukim.finki.emt.vergjor.repository;


import mk.ukim.finki.emt.vergjor.models.AccountActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountActivationsRepository extends JpaRepository<AccountActivation, Integer> {

}

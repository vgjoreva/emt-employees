package mk.ukim.finki.emt.vergjor.repository;


import mk.ukim.finki.emt.vergjor.models.AccountActivation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountActivationsRepository extends JpaRepository<AccountActivation, Integer> {


    @Query("select case when count(a) > 0 then true else false end from AccountActivation a where a.userID.user_id = :user_id and a.userIsActivated = true")
    boolean isUserRegistered(@Param("user_id") String user_id);

    @Query("select case when count(a) > 0 then true else false end from AccountActivation a where a.userID.user_id = :id and a.userIsActivated = false and a.codeIsValid = true")
    boolean isUserValid(@Param("id") String id);

}

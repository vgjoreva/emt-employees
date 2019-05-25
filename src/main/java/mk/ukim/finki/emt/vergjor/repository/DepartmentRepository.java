package mk.ukim.finki.emt.vergjor.repository;

import mk.ukim.finki.emt.vergjor.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}

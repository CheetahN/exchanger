package main.repository;

import main.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    @Query(value = "select e.userId from #{#entityName} e where e.amountSource > :amount")
    List<Long> findOver(Long amount);
}

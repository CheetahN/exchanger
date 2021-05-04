package main.repository;

import main.model.Exchange;
import main.model.IExchangeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    @Query(value = "select e.userId from #{#entityName} e where e.amountUSD > :value")
    List<Long> findOver(Double value);

    @Query(value = "select e.userId from #{#entityName} e group by e.userId having sum(e.amountUSD) > :value")
    List<Long> findTotalOver(Double value);

    @Query(value = "select e.source as source, e.target as target, count(*) as count from #{#entityName} e " +
            "group by e.target, e.source " +
            "order by count(*) desc")
    List<IExchangeCount> getTopDirections();
}

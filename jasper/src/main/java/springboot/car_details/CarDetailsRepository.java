package springboot.car_details;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface CarDetailsRepository
		extends CrudRepository<CarDetails, Integer> {

	@Query(value = "select * from car_details c where c.type= ?1", nativeQuery = true)
	public List<CarDetails> findBycartype(String type);

	public List<CarDetails> findByModalname(String modalname);

	public CarDetails findById(int id);

	@Query(value = "select * from car_details", nativeQuery = true)
	public List<CarDetails> getAllCars();

	@Query(value = "select * from car_details c where c.id not in  (?1 , ?2)", nativeQuery = true)
	public List<CarDetails> getInBetween(int id1, int id2);

	@Query(value = "select * from car_details c where c.price < ?1", nativeQuery = true)
	public List<CarDetails> getBylessthenmount(int price);

	@Query(value = "select * from car_details c where c.price > ?1", nativeQuery = true)
	public List<CarDetails> getBymorethenmount(int price);

	/*
	 * @Query(value = "select * from car_details", nativeQuery = true) public
	 * List<CarDetails> getAllcardetailspaging(Pageable pageable);
	 */

	public List<CarDetails> findByType(String string, Pageable pageable);

	public List<CarDetails> findAll(Pageable pageable);

	/*
	 * @Query("select c from car_details c where type like %?1%") public
	 * Page<CarDetails> findByTypes(String string, Pageable pageable);
	 */

}

package springboot.car_details;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@Component
public class CarDetailsService {

	private List<CarDetails> Detailds = new ArrayList<>(Arrays.asList(new CarDetails()

	));

	@Autowired
	private CarDetailsRepository carDetailsRepository;

	/*
	 * public List<CarDetails> getAllInformation() { //return Detailds; }
	 */

	public void addcardetails(CarDetails cardetails) {

		carDetailsRepository.save(cardetails);

	}

	public List<CarDetails> getAllcardetails() {

		return carDetailsRepository.getAllCars();
	}

	public List<CarDetails> getByType(String type) {
		return carDetailsRepository.findBycartype(type);
	}

	public List<CarDetails> getByModalname(String modalname) {
		return carDetailsRepository.findByModalname(modalname);
	}

	public void getCarDetailsNotYetBooked(HashSet<?> hashSet) {

		System.out.println("-------------------------------------");
		System.out.println(Detailds.size());

	}

	public String generateReport(String name) {
		// TODO Auto-generated method stub

		try {
			List<CarDetails> CarDetails = carDetailsRepository.getAllCars();
			String reportPath = "C:\\Users\\Manjunath Venkatesh\\Desktop\\jasper\\jasper\\src\\main\\resources\\Report";

			JasperReport jasperReport = JasperCompileManager.compileReport(reportPath + "\\jaspercar.jrxml");

			// Get your data source
			JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(CarDetails);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jrBeanCollectionDataSource);
			// Export the report to a PDF file
			JasperExportManager.exportReportToPdfFile(jasperPrint, "src/main/resources/report/" + name + ".pdf");

			System.out.println("Done");

			return "Report successfully generated @path= " + reportPath + "\\" + name + ".pdf";

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

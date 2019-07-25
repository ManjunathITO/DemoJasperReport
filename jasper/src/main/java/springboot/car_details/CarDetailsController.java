package springboot.car_details;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;

@RestController
@Component
public class CarDetailsController {

	@Autowired
	private CarDetailsService carDetailsService;

	/*
	 * @RequestMapping("/information") public List<CarDetails> getAllinformation() {
	 * //return carbookingserviceobj.getAllInformation();
	 * 
	 * 
	 * }
	 */

	@RequestMapping(method = RequestMethod.POST, value = "/carinformation")
	public void adddetails(@RequestBody CarDetails cardetails) {
		carDetailsService.addcardetails(cardetails);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/carinformation")
	public List<CarDetails> getAllinformation() {
		return carDetailsService.getAllcardetails();

	}

	@RequestMapping("/carinformation/fortype/{type}")
	public List<CarDetails> getByType(@PathVariable("type") String type) {
		return carDetailsService.getByType(type);
	}

	@RequestMapping("/carinformation/foemodal/{modalname}")
	public List<CarDetails> getModalname(@PathVariable("modalname") String modalname) {
		return carDetailsService.getByModalname(modalname);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/report")
	public void generateReport() {
		java.io.InputStream jasprStream = this.getClass().getResourceAsStream("/Report/jaspercar.jrxml");

		try {
			JasperReport jasperReport = JasperCompileManager.compileReport(jasprStream);
			JRSaver.saveObject(jasperReport, "jaspercar.jasper");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null);

			JRPdfExporter exporter = new JRPdfExporter();

			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("jaspercar.pdf"));

			SimplePdfReportConfiguration reportConfig = new SimplePdfReportConfiguration();
			reportConfig.setSizePageToContent(true);
			reportConfig.setForceLineBreakPolicy(false);

			SimplePdfExporterConfiguration exportConfig = new SimplePdfExporterConfiguration();
			exportConfig.setMetadataAuthor("baeldung");
			exportConfig.setEncrypted(true);

			exportConfig.setAllowedPermissionsHint("PRINTING");

			exporter.setConfiguration(reportConfig);
			exporter.setConfiguration(exportConfig);

			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@RequestMapping(method=RequestMethod.GET, value="/pdfreport")
	public String generateReportpdf(@RequestParam String name) {

		return carDetailsService.generateReport(name);
	}
	

}

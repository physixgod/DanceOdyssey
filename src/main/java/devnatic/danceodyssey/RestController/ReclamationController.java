package devnatic.danceodyssey.RestController;


import devnatic.danceodyssey.DAO.Entities.Reclamation;

import devnatic.danceodyssey.Services.IReclamationServices;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/,http://localhost:8086")
@RequestMapping("/reclamation")
public class ReclamationController {
    private final IReclamationServices reclamationServices;

    @PostMapping("/AddReclamation")
    public ResponseEntity<Reclamation> Addreclamation(@RequestBody Reclamation reclamation){
        Reclamation newReclamarion =reclamationServices.Addreclamation(reclamation);
        return new ResponseEntity<>(newReclamarion,HttpStatus.CREATED);
    }



    @PutMapping("/UpdateReclamation/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable Integer id, @RequestBody Reclamation reclamation) {
        Reclamation updatedReclamation = reclamationServices.updateReclamationById(id, reclamation);
        if (updatedReclamation != null) {
            return new ResponseEntity<>(updatedReclamation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Or handle as you wish
        }
    }

    @GetMapping("/Showall")
    public ResponseEntity<List<Reclamation>> ShowReclamation(){
      List<Reclamation> reclamations = reclamationServices.Showreclamation();
      return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Reclamation> GetReclamationbyId(@PathVariable("id") int id){
        Reclamation reclamations = reclamationServices.findReclamationById(id);
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    @DeleteMapping("/deletereclamation/{id}")
    public ResponseEntity<Reclamation> Deletereclamation(@PathVariable("id") int id){
        reclamationServices.Deletereclamation(id);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }

    @GetMapping("/export-to-excel")
    public void exportToExcel(HttpServletResponse response) {
        try {
            // Create a Workbook
            Workbook workbook = new XSSFWorkbook();

            // Create a Sheet
            Sheet sheet = workbook.createSheet("Reclamations");

            // Create a Header Row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Reclamation ID", "Description", "Response", "Date", "Reason"}; // Added "Reason" header
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Fetch Reclamations
            List<Reclamation> reclamations = reclamationServices.Showreclamation();

            // Populate Data Rows
            int rowNum = 1;
            for (Reclamation reclamation : reclamations) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(reclamation.getReclamationID());
                row.createCell(1).setCellValue(reclamation.getReclamationDescription());
                row.createCell(2).setCellValue(reclamation.getReclamationResponse());
                row.createCell(3).setCellValue(reclamation.getReclamationDate().toString());
                row.createCell(4).setCellValue(reclamation.getReclamationReason());
                row.createCell(5).setCellValue(reclamation.getPriority());// Added cell for "Reason"
            }

            // Set the Response Content Type and Header
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=reclamations.xlsx");

            // Write Workbook to Response Output Stream
            workbook.write(response.getOutputStream());

            // Close Workbook
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



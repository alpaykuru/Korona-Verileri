package com.example.demo.Controller;

import com.example.demo.Service.CoronaDataService;
import com.example.demo.model.CoronaData;
import com.example.demo.model.dto.CoronaDataAddDTO;
import com.example.demo.model.dto.CoronaGraphicDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/coronaData")
public class CoronaDataController {

    @Autowired
    CoronaDataService coronaDataService;

    @CrossOrigin(origins = "*")
    @GetMapping("/add")
    public ResponseEntity<CoronaDataAddDTO> createCoronaData(@RequestParam String text) throws ParseException {
        CoronaData coronaData = coronaDataService.parseText(text);
        CoronaDataAddDTO coronaDataAddDTO =coronaDataService.saveOrInform(coronaData);
        return new ResponseEntity<>(coronaDataAddDTO, HttpStatus.OK);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/il")
    public ResponseEntity<ArrayList<CoronaGraphicDTO>> findByIl(@RequestParam String selectedIl, @RequestParam Boolean kumulatif) {
        ArrayList<CoronaGraphicDTO> dtoList = coronaDataService.getCoronaDatas(selectedIl,kumulatif);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

}

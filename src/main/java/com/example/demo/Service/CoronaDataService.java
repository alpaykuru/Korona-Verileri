package com.example.demo.Service;

import com.example.demo.model.CoronaData;
import com.example.demo.model.dto.CoronaDataAddDTO;
import com.example.demo.model.dto.CoronaGraphicDTO;

import java.text.ParseException;
import java.util.ArrayList;

public interface CoronaDataService {
    CoronaData createCoronaData(CoronaData coronaData);
    ArrayList<CoronaGraphicDTO> getCoronaDatas(String il, Boolean kumulatif);
    CoronaData parseText(String text) throws ParseException;
    CoronaDataAddDTO saveOrInform(CoronaData c);
}

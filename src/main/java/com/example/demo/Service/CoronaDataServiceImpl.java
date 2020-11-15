package com.example.demo.Service;

import com.example.demo.model.CoronaData;
import com.example.demo.model.Il;
import com.example.demo.model.dto.CoronaDataAddDTO;
import com.example.demo.model.dto.CoronaDataDTO;
import com.example.demo.model.dto.CoronaGraphicDTO;
import com.example.demo.repository.CoronaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CoronaDataServiceImpl implements CoronaDataService {

    @Autowired
    CoronaDataRepository coronaDataRepository;

    @Override
    public CoronaData createCoronaData(CoronaData coronaData) {
        return coronaDataRepository.save(coronaData);
    }

    @Override
    public  ArrayList<CoronaGraphicDTO> getCoronaDatas(String il, Boolean kumulatif) {
        List <CoronaData> data = coronaDataRepository.findByIl(il);
        if(data.size() ==0){
            return null;
        }
        Collections.sort(data);
        data = kumulatif ? getDataListKumulatif(data) : data;
        ArrayList<CoronaDataDTO> vefatList = new ArrayList<>();
        ArrayList<CoronaDataDTO> taburcuList = new ArrayList<>();
        ArrayList<CoronaDataDTO> vakaList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for(CoronaData c :data){
            vefatList.add(new CoronaDataDTO(dateFormat.format(c.getTarih()),c.getVefat()));
            taburcuList.add(new CoronaDataDTO(dateFormat.format(c.getTarih()),c.getTaburcu()));
            vakaList.add(new CoronaDataDTO(dateFormat.format(c.getTarih()),c.getVaka()));
        }
        ArrayList<CoronaGraphicDTO> dtoList = new ArrayList<>();
        dtoList.add(new CoronaGraphicDTO("taburcu",taburcuList));
        dtoList.add(new CoronaGraphicDTO("vefat",vefatList));
        dtoList.add(new CoronaGraphicDTO("vaka",vakaList));
        return dtoList;
    }

    @Override
    public CoronaData parseText(String text) throws ParseException {
        CoronaData coronaData = null;
        String date = findDate(text);
        text = swapDate(text, date);
        ArrayList<String> sentences = getSentences(text);
        if(date.length() == 0){
            coronaData = new CoronaData(null, findIl(sentences), null,
                    findData(sentences, "vaka"), findData(sentences, "vefat"), findData(sentences, "taburcu"));
        }else{
            coronaData = new CoronaData(null, findIl(sentences),
                    new SimpleDateFormat("dd.MM.yyyy").parse(date),
                    findData(sentences, "vaka"), findData(sentences, "vefat"), findData(sentences, "taburcu"));
        }
        return coronaData;
    }


    @Override
    public CoronaDataAddDTO saveOrInform(CoronaData c ){
        CoronaDataAddDTO coronaDataAddDTO = new CoronaDataAddDTO();
        if(c.getIl().length() ==0){
            coronaDataAddDTO.setSonuc("İl Bilgisi Bulunamadı");
            return coronaDataAddDTO;
        }
        if(c.getTarih() == null){
            coronaDataAddDTO.setSonuc("Tarih Bilgisi Bulunamadı");
            return coronaDataAddDTO;
        }
        createCoronaData(c);
        coronaDataAddDTO.setSonuc("Yeni Haber Eklendi");
        return coronaDataAddDTO;
    }

    private String findIl(ArrayList<String> sentences) {
        for (String s : sentences) {
            for (Map.Entry<String, String> entry : Il.getIller().entrySet()) {
                if (s.indexOf(entry.getValue()) > 0) {
                    return entry.getValue();
                }
            }
        }

        return "";
    }

    private Long findData(ArrayList<String> sentences, String type) {
        Long data = new Long(0);
        String sent = "";
        for (String s : sentences) {
            if (s.indexOf(type) > 0) {
                sent = s;
                break;
            }
        }
        for (int i = 0; i < sent.length(); i++) {
            if (numberCheck(sent.charAt(i))) {
                if (i + 1 > sent.length()) {
                    return new Long(Integer.parseInt(sent.charAt(i) + ""));
                }
                for (int j = i + 1; j < sent.length(); j++) {
                    if (!numberCheck(sent.charAt(j))) {
                        return new Long(Integer.parseInt(sent.substring(i, j)));
                    }
                }
            }
        }
        return data;
    }

    private ArrayList<String> getSentences(String text) {
        ArrayList<String> sentences = new ArrayList<>();
        String[] list = text.split("\\.");
        for (String s : list) {
            sentences.add(s);
        }
        return sentences;
    }

    private Boolean numberCheck(char c) {
        if (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9') {
            return true;
        }
        return false;
    }

    private String swapDate(String text, String date) {
        return text.substring(0, text.indexOf(date)) + " BTA " + text.substring(text.indexOf(date) + date.length(), text.length());
    }

    private String findDate(String text) {
        String sonuc = "";
        boolean bul = false;
        for (int i = 0; i < text.length() - 3; i++) {
            bul = false;
            for (int j = i; j < i + 4; j++) {
                if (numberCheck(text.charAt(j))) {
                    bul = true;
                } else {
                    bul = false;
                    break;
                }
            }
            if (bul) {
                if (i > 3) {
                    if (text.charAt(i - 1) == '.' && numberCheck(text.charAt(i - 2)) && numberCheck(text.charAt(i - 3)) && text.charAt(i - 4) == '.') {
                        sonuc = text.substring(i - 6, i) + text.substring(i, i + 4);
                        break;
                    }
                }
            }
        }
        return sonuc;
    }

    private List<CoronaData> getDataListKumulatif(List<CoronaData> data) {
        if (data.size() == 0) {
            return null;
        }
        int sumVefat = 0;
        int sumTaburcu = 0;
        int sumVaka = 0;
        for (int i = 0; i < data.size(); i++) {
            sumVefat = 0;
            sumTaburcu = 0;
            sumVaka = 0;
            for (int j = 0; j < data.size() - i; j++) {
                sumVefat += data.get(j).getVefat();
                sumTaburcu += data.get(j).getTaburcu();
                sumVaka += data.get(j).getVaka();
            }
            data.get(data.size() - (i + 1)).setVaka(new Long(sumVaka));
            data.get(data.size() - (i + 1)).setTaburcu(new Long(sumTaburcu));
            data.get(data.size() - (i + 1)).setVefat(new Long(sumVefat));
        }
        int sum = 0;
        return data;
    }
}

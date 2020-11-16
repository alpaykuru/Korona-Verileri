package Service;

import com.example.demo.Service.CoronaDataServiceImpl;
import com.example.demo.model.CoronaData;
import com.example.demo.model.dto.CoronaDataAddDTO;
import com.example.demo.model.dto.CoronaGraphicDTO;
import com.example.demo.repository.CoronaDataRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CoronaDataServiceImplTest {

    @InjectMocks
    private CoronaDataServiceImpl coronaDataService;

    @Mock
    private CoronaDataRepository coronaDataRepository;

    @Test
    public void createCoronaDataTest() {
        CoronaData coronaData = new CoronaData(null, "Ankara", new Date(), new Long(0), new Long(0), new Long(0));
        Mockito.when(coronaDataRepository.save(coronaData)).thenReturn(coronaData);
        CoronaData coronaData1 = coronaDataService.createCoronaData(coronaData);
        assertEquals(coronaData.getIl(), coronaData1.getIl());
        assertEquals(coronaData.getTarih(), coronaData1.getTarih());
        assertEquals(coronaData.getTaburcu(), coronaData1.getTaburcu());
        assertEquals(coronaData.getVaka(), coronaData1.getVaka());
        assertEquals(coronaData.getVefat(), coronaData1.getVefat());
    }

    @Test
    public void getCoronaDatasTest() {
        ArrayList<CoronaGraphicDTO> list = new ArrayList<>();
        list.add(new CoronaGraphicDTO("taburcu", null));
        Mockito.when(coronaDataRepository.findByIl("Adıyaman")).thenReturn(Arrays.asList(
                new CoronaData(null, "Adıyaman", new Date(), new Long(0), new Long(0), new Long(0))
        ));
        ArrayList<CoronaGraphicDTO> items = coronaDataService.getCoronaDatas("Adıyaman", false);
        assertEquals(items.get(0).getId(), new CoronaGraphicDTO("taburcu", null).getId());
    }

    @Test
    public void parseTextTest() throws ParseException {
        String text = "21.03.2020 tarihinde Ankara için korona virüs ile ilgili yeni bir açıklama yapıldı. Korona virüs salgınında yapılan testlerde 1 yeni vaka tespit edildi.taburcu sayısı ise 1 oldu.  1 kişin de vefat ettiği öğrenildi.";
        CoronaData coronaData = new CoronaData(null, "Ankara", new SimpleDateFormat("dd.MM.yyyy").parse("21.03.2020"), new Long(1), new Long(1), new Long(0));
        CoronaData coronaData1 = coronaDataService.parseText(text);
        assertEquals(coronaData.getIl(), coronaData1.getIl());
        assertEquals(coronaData.getTarih(), coronaData1.getTarih());
        assertEquals(coronaData.getTaburcu(), coronaData1.getTaburcu());
        assertEquals(coronaData.getVaka(), coronaData1.getVaka());
        assertEquals(coronaData.getVefat(), coronaData1.getVefat());
    }

    @Test
    public void saveOrInformSuccessTest() throws ParseException {
        CoronaData coronaData = new CoronaData(null, "Ankara", new SimpleDateFormat("dd.MM.yyyy").parse("21.03.2020"), new Long(1), new Long(1), new Long(0));
        CoronaDataAddDTO coronaDataAddDTO = new CoronaDataAddDTO("Yeni Haber Eklendi");
        CoronaDataAddDTO coronaDataAddDTO1 = coronaDataService.saveOrInform(coronaData);
        assertEquals(coronaDataAddDTO1.getSonuc(), coronaDataAddDTO.getSonuc());
    }

    @Test
    public void saveOrInformDateMissingTest(){
        CoronaData coronaData = new CoronaData(null, "Ankara",null, new Long(1), new Long(1), new Long(0));
        CoronaDataAddDTO coronaDataAddDTO = new CoronaDataAddDTO("Tarih Bilgisi Bulunamadı");
        CoronaDataAddDTO coronaDataAddDTO1 = coronaDataService.saveOrInform(coronaData);
        assertEquals(coronaDataAddDTO1.getSonuc(), coronaDataAddDTO.getSonuc());
    }

    @Test
    public void saveOrInformCityMissingTest(){
        CoronaData coronaData = new CoronaData(null, "", new Date(), new Long(1), new Long(1), new Long(0));
        CoronaDataAddDTO coronaDataAddDTO = new CoronaDataAddDTO("İl Bilgisi Bulunamadı");
        CoronaDataAddDTO coronaDataAddDTO1 = coronaDataService.saveOrInform(coronaData);
        assertEquals(coronaDataAddDTO1.getSonuc(), coronaDataAddDTO.getSonuc());
    }
}

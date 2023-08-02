package org.matgyeojo.service;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.matgyeojo.dto.GyeonggiHospital;
import org.matgyeojo.dto.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HospitalService {

    private final org.matgyeojo.repository.HospitalRepository hospitalRepository;
    private final org.matgyeojo.repository.GyeonggiHospitalRepository gyeonggiHospitalRepository;

    @Value("${api.url}")  //서울 동물병원 api주소 불러오는거
    private String apiUrl;
    
    @Value("${gyeonggiapi.url}")  //서울 동물병원 api주소 불러오는거
    private String gyeonggiapiUrl;

    @Autowired
    public HospitalService(org.matgyeojo.repository.HospitalRepository hospitalRepository,
                          org.matgyeojo.repository.GyeonggiHospitalRepository gyeonggiHospitalRepository) {
        this.hospitalRepository = hospitalRepository;
        this.gyeonggiHospitalRepository = gyeonggiHospitalRepository;
    }
    
    @Transactional(readOnly = true)
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    //서울 동물병원
//    @Transactional
//    public void saveDataFromApi() {
//        // Open API 호출하여 데이터 받아오기 (RestTemplate 사용 예시)
//        RestTemplate restTemplate = new RestTemplate();
//
//        // GET 요청을 보낼 때 사용하는 데이터 타입이 String이기 때문에 responseType을 String.class로 지정합니다.
//        String responseData = restTemplate.getForObject(apiUrl, String.class);
//        
//        // 받아온 JSON 데이터를 필요한 형태로 파싱 (예시로는 Jackson ObjectMapper 사용)
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            JsonNode rootNode = objectMapper.readTree(responseData);
//            JsonNode rowNode = rootNode.get("LOCALDATA_020301").get("row"); 
//            
//            // 트랜잭션 시작
//            for (JsonNode dataNode : rowNode) {
//                double xValue = dataNode.get("X").asDouble();
//                double yValue = dataNode.get("Y").asDouble();
//                String rdnhlAddr = dataNode.get("RDNWHLADDR").asText();
//                String nositetel = dataNode.get("SITETEL").asText();
//                String trdStateNm = dataNode.get("TRDSTATENM").asText();
//                String nordnwhladdr = dataNode.get("RDNWHLADDR").asText();
//                
//                if (Double.isNaN(xValue) || Double.isNaN(yValue) || xValue == 0 || yValue == 0 ||
//                        rdnhlAddr == null || rdnhlAddr.isEmpty() || nositetel == null || nositetel.isEmpty() ||
//                        nordnwhladdr == null || nordnwhladdr.isEmpty() || 
//                        "폐업".equals(trdStateNm) || trdStateNm.contains("취소") || "휴업".equals(trdStateNm)) {
//                    continue;
//                }
//                
//                Hospital hospital = new Hospital();
//                hospital.setBPLCNM(dataNode.get("BPLCNM").asText());
//                hospital.setX(dataNode.get("X").asDouble());
//                hospital.setY(dataNode.get("Y").asDouble());
//                hospital.setRDNWHLADDR(dataNode.get("RDNWHLADDR").asText());
//                hospital.setTRDSTATENM(dataNode.get("TRDSTATENM").asText());
//                hospital.setSITETEL(dataNode.get("SITETEL").asText());
//                hospital.setRandomHospitalPartner(); // 랜덤 값 설정
//
//                // 이미 해당 RDNWHLADDR으로 저장된 데이터가 있는지 확인
//                boolean isDuplicate = hospitalRepository.existsRDNWHLADDR(dataNode.get("RDNWHLADDR").asText());
//                if (isDuplicate) {
//                    continue; // 중복 데이터가 있으면 저장을 건너뛰고 다음 데이터로 넘어감
//                }
//
//                hospitalRepository.save(hospital);
//            }
//            System.out.println("서울동물병원 저장완료");
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            // 예외 처리 필요
//        }
//    }

    //경기도 동물병원
    @Transactional
    public void saveDataFromXmlApi() {
        // Open API 호출하여 데이터 받아오기 (RestTemplate 사용 예시)
        RestTemplate restTemplate = new RestTemplate();

        // GET 요청을 보낼 때 사용하는 데이터 타입이 String이기 때문에 responseType을 String.class로 지정합니다.
        String responseData = restTemplate.getForObject(gyeonggiapiUrl, String.class);

        // XML 데이터를 파싱하여 MySQL에 저장
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(responseData));
            Document document = builder.parse(is);
            NodeList nodeList = document.getElementsByTagName("row");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                String bsnStateNm = getElementText(element, "BSN_STATE_NM");
                String refineWgs84Lat = getElementText(element, "REFINE_WGS84_LAT");
                String refineWgs84Logt = getElementText(element, "REFINE_WGS84_LOGT");
                String refinerotnonmaddr = getElementText(element, "REFINE_LOTNO_ADDR");
                
                
             // REFINE_WGS84_LAT 또는 REFINE_WGS84_LOGT가 null, REFINE_ROADNM_ADDR가 null 인지 확인
                if (refineWgs84Lat == null || refineWgs84Lat.isEmpty() || refineWgs84Logt == null || refineWgs84Logt.isEmpty() 
                	|| refinerotnonmaddr == null || refinerotnonmaddr.isEmpty() ) {
                    continue; 
                }
                
                if (!bsnStateNm.contains("폐업 등") && !bsnStateNm.contains("휴업 등")) {
                    // 이미 해당 RDNWHLADDR으로 저장된 데이터가 있는지 확인 true, false 반환
                    boolean hospitalExists = gyeonggiHospitalRepository.existsByREFINE_ROADNM_ADDR(refinerotnonmaddr);
                    if (hospitalExists) {
                        continue; // Skip saving if a duplicate is found
                    }
                    GyeonggiHospital gyeonggiHospital = new GyeonggiHospital();
                    gyeonggiHospital.setBIZPLC_NM(getElementText(element, "BIZPLC_NM"));
                    gyeonggiHospital.setREFINE_WGS84_LAT(Double.parseDouble(refineWgs84Lat));
                    gyeonggiHospital.setREFINE_WGS84_LOGT(Double.parseDouble(refineWgs84Logt));
                    gyeonggiHospital.setREFINE_LOTNO_ADDR(refinerotnonmaddr);
                    gyeonggiHospital.setBSN_STATE_NM(bsnStateNm);
                    // LOCPLC_FACLT_TELNO 필드 값이 빈 값인 경우에는 null로 설정
                    String telNo = getElementText(element, "LOCPLC_FACLT_TELNO");
                    gyeonggiHospital.setLOCPLC_FACLT_TELNO(!telNo.isEmpty() ? telNo : null);
                    gyeonggiHospital.setRandomHospitalPartner();
                    gyeonggiHospitalRepository.save(gyeonggiHospital);
                }
            }
            System.out.println("경기도 동물병원 저장완료");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // XML 문서에서 주어진 태그의 텍스트 값을 추출하는 기능
    private String getElementText(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }
    
    //프론트엔드한테 입력받은 hospitalAddress를 조건검색
    public List<GyeonggiHospital> findGyeonggiHospitalsByRoadAddress(String hospitalAddress) {
        return gyeonggiHospitalRepository.findByREFINE_LOTNO_ADDRContainingOrderByHospitalPartnerDesc(hospitalAddress);
    }
}
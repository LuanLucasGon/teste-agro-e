package com.example.teste_agro_e.services;

import com.example.teste_agro_e.domain.Brand;
import com.example.teste_agro_e.domain.Model;
import com.example.teste_agro_e.domain.Years;
import com.example.teste_agro_e.dtos.BrandDTO;
import com.example.teste_agro_e.dtos.FipeInfoDTO;
import com.example.teste_agro_e.dtos.ModelDTO;
import com.example.teste_agro_e.dtos.YearsDTO;
import com.example.teste_agro_e.repositories.BrandRepository;
import com.example.teste_agro_e.repositories.ModelRepository;
import com.example.teste_agro_e.repositories.YearsRepository;
import com.example.teste_agro_e.shared.exceptions.CustomException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class FipeService {

    private static final String API_FIPE_URL = "https://fipe.parallelum.com.br/api/v2/";
    private static final long CACHE_DURATION_MINUTES = 60;

    private LocalDateTime lastUpdated;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private YearsRepository yearsRepository;

    private final WebClient webClient;

    @Autowired
    public FipeService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(API_FIPE_URL).build();
    }

    @PostConstruct
    public void init() {
        loadCacheIfNecessary();
    }

    private boolean isCacheExpired() {
        return lastUpdated == null || lastUpdated.isBefore(LocalDateTime.now().minusMinutes(CACHE_DURATION_MINUTES));
    }

    private void loadCacheIfNecessary() {
        if (isCacheExpired()) {
            catchBrands();
        }
    }

    private boolean isModelCacheExpired(String brandCode) {
        Optional<Model> model = modelRepository.findFirstByBrandCode(brandCode);
        return model.isEmpty() || lastUpdated.isBefore(LocalDateTime.now().minusMinutes(CACHE_DURATION_MINUTES));
    }

    private boolean isYearsCacheExpired(String brandCode) {
        Optional<Years> years = yearsRepository.findFirstByBrandCode(brandCode);
        return years.isEmpty() || lastUpdated.isBefore(LocalDateTime.now().minusMinutes(CACHE_DURATION_MINUTES));
    }

    public void catchModels(String brandCode) {
        try {
            if (isModelCacheExpired(brandCode)) {
                List<ModelDTO> response = fetchFromApi("/trucks/brands/" + brandCode + "/models", ModelDTO.class);

                if (response != null) {
                    response.forEach(dto -> {
                        Model model = new Model();
                        model.setBrandCode(brandCode);
                        model.setCode(dto.code());
                        model.setName(dto.name());
                        modelRepository.save(model);
                    });
                    lastUpdated = LocalDateTime.now();
                }
            }
        } catch (Exception e) {
            throw new CustomException("ERROR_FETCHING_MODELS", "Erro ao buscar os modelos disponíveis: " + e.getMessage());
        }
    }

    public void catchBrands() {
        try {
            if (isCacheExpired()) {
                List<BrandDTO> response = fetchFromApi("trucks/brands", BrandDTO.class);

                if (response != null) {
                    response.forEach(dto -> {
                        Brand brand = new Brand();
                        brand.setCode(dto.code());
                        brand.setName(dto.name());
                        brandRepository.save(brand);
                    });
                    lastUpdated = LocalDateTime.now();
                }
            }
        } catch (Exception e) {
            throw new CustomException("ERROR_FETCHING_BRANDS", "Erro ao buscar as marcas disponíveis: " + e.getMessage());
        }
    }

    public void catchYears(String brandCode, String modelCode) {
        try {
            if (isYearsCacheExpired(brandCode)) {
                List<YearsDTO> response = fetchFromApi(String.format("trucks/brands/%s/models/%s/years", brandCode, modelCode), YearsDTO.class);
                if (response != null) {
                    response.forEach(dto -> {
                        Years years = new Years();
                        years.setBrandCode(brandCode);
                        years.setCode(dto.code());
                        years.setName(dto.name());
                        yearsRepository.save(years);
                    });
                    lastUpdated = LocalDateTime.now();
                }
            }
        } catch (Exception e) {
            throw new CustomException("ERROR_FETCHING_YEARS", "Erro ao buscar os anos disponíveis: " + e.getMessage());
        }
    }

    public Double getFipePrice(String brandId, String modelId, String yearId) {
        List<FipeInfoDTO> response = fetchFromApi(String.format("trucks/brands/%s/models/%s/years/%s", brandId, modelId, yearId), FipeInfoDTO.class);
        if (response != null && !response.isEmpty()) {
            lastUpdated = LocalDateTime.now();
            return parsePrice(response.get(0).price());
        }
        throw new CustomException("ERROR_FETCHING_FIPE_PRICE", "Não foi possível buscar o preço na tabela FIPE");
    }

    private <T> List<T> fetchFromApi(String uri, Class<T> responseType) {
        try {
            return webClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(responseType)
                    .collectList()
                    .block();
        } catch (Exception e) {
            throw new CustomException("ERROR_FETCHING_API", "Erro ao buscar dados da API FIPE: " + e.getMessage());
        }
    }

    public static double parsePrice(String price) {
        try {
            String cleanPrice = price.replace("R$", "").trim();
            NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
            Number number = format.parse(cleanPrice);
            return number.doubleValue();
        } catch (ParseException e) {
            throw new CustomException("ERROR_PARSING_PRICE", "Erro ao converter o preço: " + price);
        }
    }
}

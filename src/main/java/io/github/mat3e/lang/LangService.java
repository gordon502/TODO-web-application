package io.github.mat3e.lang;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
class LangService {
    private LangRepository repository;

    LangService(LangRepository repository) {
        this.repository = repository;
    }

    List<LangDTO> findAll() {
        var langDTOList = new ArrayList<LangDTO>();

        var langList = repository.findAll();

        for (Lang lang : langList) {
            langDTOList.add(new LangDTO(lang));
        }
        return langDTOList;
    }
}

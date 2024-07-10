package com.imdb.ws.service;

import com.imdb.ws.data.TitleBasicRepository;
import com.imdb.ws.dto.TitleBasicDTO;
import com.imdb.ws.entity.TitleBasic;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TitleService {

    @Autowired
    private TitleBasicRepository titleRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<TitleBasicDTO> getTitlesWithSameDirectorAndWriterAlive() {
        List<TitleBasic> titles = titleRepository.findTitlesWithSameDirectorAndWriterAlive();
        return titles.stream()
                .map(title -> modelMapper.map(title, TitleBasicDTO.class))
                .collect(Collectors.toList());
    }

    public List<TitleBasicDTO> getCommonTitles(String actor1, String actor2) {
        List<TitleBasic> titles = titleRepository.findCommonTitles(actor1, actor2);
        return titles.stream()
                .map(title -> modelMapper.map(title, TitleBasicDTO.class))
                .collect(Collectors.toList());
    }

    public List<TitleBasicDTO> getBestTitlesByGenre(String genre) {
        List<TitleBasic> titles = titleRepository.findBestTitlesByGenre(genre);
        return titles.stream()
                .map(title -> modelMapper.map(title, TitleBasicDTO.class))
                .collect(Collectors.toList());
    }
}


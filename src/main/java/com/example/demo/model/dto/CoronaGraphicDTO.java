package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class CoronaGraphicDTO {
    private String id;
    ArrayList<CoronaDataDTO> data;
}

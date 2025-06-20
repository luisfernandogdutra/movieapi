package com.dutra.movieAPI.dto;

import java.util.List;

public class IntervalDTO {
    private List<MinMaxDTO> min;
    private List<MinMaxDTO> max;

    public IntervalDTO(List<MinMaxDTO> min, List<MinMaxDTO> max) {
        this.min = min;
        this.max = max;
    }

    public List<MinMaxDTO> getMin() {
        return min;
    }

    public void setMin(List<MinMaxDTO> min) {
        this.min = min;
    }

    public List<MinMaxDTO> getMax() {
        return max;
    }

    public void setMax(List<MinMaxDTO> max) {
        this.max = max;
    }
}

package com.project.credits.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreditResponseWithPaging {
    private List<CreditResponse> credits;
    private long totalElements;
    private long totalPages;
}

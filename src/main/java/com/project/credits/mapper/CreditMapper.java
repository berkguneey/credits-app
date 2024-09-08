package com.project.credits.mapper;

import com.project.credits.entity.Credit;
import com.project.credits.request.CreateCreditRequest;
import com.project.credits.response.CreditResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface CreditMapper {

    @Mapping(source = "amount", target = "principalAmount")
    @Mapping(source = "amount", target = "totalAmount")
    Credit toCredit(CreateCreditRequest request);

    CreditResponse toCreditResponse(Credit entity);

    List<CreditResponse> toCreditResponse(List<Credit> entity);

}

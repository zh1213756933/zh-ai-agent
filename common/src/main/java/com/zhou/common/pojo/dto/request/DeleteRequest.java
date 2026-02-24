package com.zhou.common.pojo.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class DeleteRequest implements Serializable {

    @Parameter(description = "id")
    private String id;

    @Serial
    private static final long serialVersionUID = 1L;

}

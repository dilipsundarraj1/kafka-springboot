package com.kafkaautomation.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Webhook {

    private Integer timestamp;
    private String name;
    private String riskLevel;
}
